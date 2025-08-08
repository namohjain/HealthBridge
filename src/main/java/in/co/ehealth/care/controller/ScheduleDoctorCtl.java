package in.co.ehealth.care.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.ehealth.care.bean.BaseBean;
import in.co.ehealth.care.bean.DoctorBean;
import in.co.ehealth.care.bean.ScheduleDoctorBean;
import in.co.ehealth.care.bean.UserBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.model.ScheduleDoctorModel;
import in.co.ehealth.care.model.UserModel;
import in.co.ehealth.care.util.DataUtility;
import in.co.ehealth.care.util.DataValidator;
import in.co.ehealth.care.util.PropertyReader;
import in.co.ehealth.care.util.ServletUtility;

/**
 * Servlet implementation class ScheduleDoctorCtl
 */

@WebServlet(name = "ScheduleDoctorCtl", urlPatterns = { "/ctl/schedule" })
public class ScheduleDoctorCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(ScheduleDoctorCtl.class);

	protected boolean validate(HttpServletRequest request) {

		log.debug("ScheduleDoctorCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("time"))) {
			request.setAttribute("time", PropertyReader.getValue("error.require", "Timeing"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "Date"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}

		log.debug("ScheduleDoctorCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("ScheduleDoctorCtl Method populatebean Started");

		ScheduleDoctorBean bean = new ScheduleDoctorBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setTime(DataUtility.getString(request.getParameter("time")));
		bean.setAddress(DataUtility.getString(request.getParameter("address")));
		bean.setCity(DataUtility.getString(request.getParameter("city")));
		bean.setDate(DataUtility.getDate(request.getParameter("date")));
		populateDTO(bean, request);

		log.debug("ScheduleDoctorCtl Method populatebean Ended");

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

		ScheduleDoctorModel model = new ScheduleDoctorModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			ScheduleDoctorBean bean;
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
		log.debug("ScheduleDoctorCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation")); // get model
		
		HttpSession session=request.getSession();
		
		ScheduleDoctorModel model = new ScheduleDoctorModel();
		UserModel usermodel = new UserModel();
		
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)) {
			ScheduleDoctorBean bean = (ScheduleDoctorBean) populateBean(request);
			DoctorBean dBean=(DoctorBean)session.getAttribute("doctor");
			bean.setDoctorId(dBean.getId());
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
			ScheduleDoctorBean bean = (ScheduleDoctorBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(EHCView.SCHEDULE_DOCTOR_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(EHCView.SCHEDULE_DOCTOR_LIST_CTL, request, response);
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(EHCView.SCHEDULE__DOCTOR_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("ScheduleDoctorCtl Method doPostEnded");
	}

	@Override
	protected String getView() {
		return EHCView.SCHEDULE_DOCTOR_VIEW;
	}

}
