package in.co.ehealth.care.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.ehealth.care.bean.AppointmentBean;
import in.co.ehealth.care.bean.BaseBean;
import in.co.ehealth.care.bean.PrescriptionBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.model.AppointmentModel;
import in.co.ehealth.care.model.PrescriptionModel;
import in.co.ehealth.care.util.DataUtility;
import in.co.ehealth.care.util.DataValidator;
import in.co.ehealth.care.util.PropertyReader;
import in.co.ehealth.care.util.ServletUtility;

/**
 * Servlet implementation class PrescriptionCtl
 */

@WebServlet(name = "PrescriptionCtl", urlPatterns = { "/ctl/prescription" })
public class PrescriptionCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(PrescriptionCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {

	}

	protected boolean validate(HttpServletRequest request) {

		log.debug("AssignFacultyCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("patientName"))) {
			request.setAttribute("patientName", PropertyReader.getValue("error.require", "Patient Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("prescription"))) {
			request.setAttribute("prescription", PropertyReader.getValue("error.require", "Prescription"));
			pass = false;
		}

		log.debug("PrescriptionCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("PrescriptionCtl Method populatebean Started");

		PrescriptionBean bean = new PrescriptionBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPatientName(DataUtility.getString(request.getParameter("patientName")));
		bean.setPrescription(DataUtility.getString(request.getParameter("prescription")));
		bean.setDate(DataUtility.getDate(request.getParameter("date")));

		populateDTO(bean, request);

		log.debug("PrescriptionCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("StudentCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		PrescriptionModel model = new PrescriptionModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		try {
			PrescriptionBean bean = new PrescriptionBean();
			
			long aId = DataUtility.getLong(request.getParameter("aId"));
			if (aId > 0) {
				AppointmentBean sBean;

				sBean = new AppointmentModel().findByPK(aId);

				bean.setPatientName(sBean.getPatientName());
				request.getSession().setAttribute("appointment", sBean);
				ServletUtility.setBean(bean, request);

			} else {
				ServletUtility.redirect(EHCView.SCHEDULE_DOCTOR_LIST_CTL, request, null);
			}

			if (id > 0 || op != null) {

				try {
					bean = model.findByPK(id);
					ServletUtility.setBean(bean, request);

				} catch (ApplicationException e) {
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;
				}
			}

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("StudentCtl Method doGet Ended");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("PrescriptionCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation")); // get model

		PrescriptionModel model = new PrescriptionModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)) {
			PrescriptionBean bean = (PrescriptionBean) populateBean(request);
			AppointmentBean pBean = (AppointmentBean) request.getSession().getAttribute("appointment");
			bean.setDoctorId(pBean.getDoctorId());
			bean.setDoctorName(pBean.getDoctorName());
			bean.setPatientId(pBean.getId());
			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
				} else {
					long pk = model.add(bean);
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
			PrescriptionBean bean = (PrescriptionBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(EHCView.PRESCRIPTION_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(EHCView.PRESCRIPTION_LIST_CTL, request, response);
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(EHCView.PRESCRIPTION_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("PrescriptionCtl Method doPostEnded");
	}

	@Override
	protected String getView() {
		return EHCView.PRESCRIPTION_VIEW;
	}

}
