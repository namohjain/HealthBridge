package in.co.ehealth.care.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import in.co.ehealth.care.bean.BaseBean;
import in.co.ehealth.care.bean.PatientBean;
import in.co.ehealth.care.bean.TestReportBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.model.DoctorModel;
import in.co.ehealth.care.model.PatientModel;
import in.co.ehealth.care.model.TestReportModel;
import in.co.ehealth.care.util.DataUtility;
import in.co.ehealth.care.util.DataValidator;
import in.co.ehealth.care.util.PropertyReader;
import in.co.ehealth.care.util.SendReport;
import in.co.ehealth.care.util.ServletUtility;

@WebServlet(name = "TestReportCtl", urlPatterns = { "/ctl/testReport" })
@MultipartConfig
public class TestReportCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(TestReportCtl.class);
	
	

	@Override
	protected void preload(HttpServletRequest request) {
		try {
			request.setAttribute("doctorList", new DoctorModel().list());
			request.setAttribute("patientList", new PatientModel().list());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	protected boolean validate(HttpServletRequest request) {

		log.debug("AssignFacultyCtl Method validate Started");

		boolean pass = true;

		
		if ("-----Select-----".equalsIgnoreCase(request.getParameter("doctor"))) {
			request.setAttribute("doctorId", PropertyReader.getValue("error.require", "Doctor Name"));
			pass = false;
		}
		
		if ("-----Select-----".equalsIgnoreCase(request.getParameter("patient"))) {
			request.setAttribute("patientId", PropertyReader.getValue("error.require", "Patient Name"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}
		
		
		Part part = null;
		try {
			part = request.getPart("file");
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (ServletException e) {
			
			e.printStackTrace();
		}
		String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

		if (DataValidator.isNull(fileName)) {
			request.setAttribute("file", PropertyReader.getValue("error.require", "Report File"));
			pass = false;
		}

		log.debug("TestReportCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("TestReportCtl Method populatebean Started");

		TestReportBean bean = new TestReportBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setDoctorId(DataUtility.getLong(request.getParameter("doctorId")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		bean.setPatientId(DataUtility.getLong(request.getParameter("patientId")));

		populateDTO(bean, request);

		log.debug("TestReportCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("TestReportCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		TestReportModel model = new TestReportModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			TestReportBean bean;
			try {
				bean = model.findByPK(id);

				ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("TestReportCtl Method doGet Ended");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("TestReportCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation")); // get model
		
		TestReportModel model = new TestReportModel();
		
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)) {
			TestReportBean bean = (TestReportBean) populateBean(request);
			
			try {
				
				PatientBean pBean = new PatientModel().findByPK(bean.getPatientId());
				Part filePart = request.getPart("file");
				String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
				String fileType = filePart.getContentType();
				InputStream inputStream = filePart.getInputStream();
				byte[] fileContent = inputStream.readAllBytes();

				bean.setFileName(fileName);
				bean.setFileType(fileType);
				bean.setReportFile(fileContent); // Add this to bean
				
				if (id > 0) {
					model.update(bean);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
				} else {
					long pk = model.add(bean);
					SendReport.sendReportFile(fileName,pBean.getEmail());
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(e.getMessage(), request);
			}
			ServletUtility.forward(getView(), request, response);
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			TestReportBean bean = (TestReportBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(EHCView.TEST_REPORT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(EHCView.TEST_REPORT_LIST_CTL, request, response);
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(EHCView.TEST_REPORT_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("TestReportCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return EHCView.TEST_REPORT_VIEW;
	}

}
