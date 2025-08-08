package in.co.ehealth.care.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.ehealth.care.bean.BaseBean;
import in.co.ehealth.care.bean.MedicineBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.model.MedicineModel;
import in.co.ehealth.care.util.DataUtility;
import in.co.ehealth.care.util.DataValidator;
import in.co.ehealth.care.util.PropertyReader;
import in.co.ehealth.care.util.ServletUtility;

/**
 * Servlet implementation class MedicineCtl
 */

@WebServlet(name = "MedicineCtl", urlPatterns = { "/ctl/medicine" })
public class MedicineCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(MedicineCtl.class);
	
	

	

	protected boolean validate(HttpServletRequest request) {

		log.debug("AssignFacultyCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("cName"))) {
			request.setAttribute("cName", PropertyReader.getValue("error.require", "Company Name"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price", PropertyReader.getValue("error.require", "Price"));
			pass = false;
		}
		
		if ("-----Select-----".equalsIgnoreCase(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		log.debug("MedicineCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("MedicineCtl Method populatebean Started");

		MedicineBean bean = new MedicineBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setCompanyName(DataUtility.getString(request.getParameter("cName")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));
		bean.setPrice(DataUtility.getString(request.getParameter("price")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));

		populateDTO(bean, request);

		log.debug("MedicineCtl Method populatebean Ended");

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

		MedicineModel model = new MedicineModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			MedicineBean bean;
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
		log.debug("StudentCtl Method doGet Ended");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("MedicineCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation")); // get model
		
		MedicineModel model = new MedicineModel();
		
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)) {
			MedicineBean bean = (MedicineBean) populateBean(request);
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
			MedicineBean bean = (MedicineBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(EHCView.MEDICINE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(EHCView.MEDICINE_LIST_CTL, request, response);
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(EHCView.MEDICINE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("MedicineCtl Method doPostEnded");
	}

	@Override
	protected String getView() {
		return EHCView.MEDICINE_VIEW;
	}

}
