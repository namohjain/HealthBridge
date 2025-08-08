package in.co.ehealth.care.controller;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.ehealth.care.bean.BaseBean;
import in.co.ehealth.care.bean.PatientBean;
import in.co.ehealth.care.bean.ScheduleDoctorBean;
import in.co.ehealth.care.bean.AppointmentBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.model.AppointmentModel;
import in.co.ehealth.care.model.ScheduleDoctorModel;
import in.co.ehealth.care.util.DataUtility;
import in.co.ehealth.care.util.DataValidator;
import in.co.ehealth.care.util.PropertyReader;
import in.co.ehealth.care.util.ServletUtility;

/**
 * Servlet implementation class AppointmentCtl
 */

@WebServlet(name = "AppointmentCtl", urlPatterns = { "/ctl/appointment" })
public class AppointmentCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(AppointmentCtl.class);

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

		if (DataValidator.isNull(request.getParameter("doctorName"))) {
			request.setAttribute("doctorName", PropertyReader.getValue("error.require", "Doctor Name"));
			pass = false;
		}

		if ("-----Select-----".equalsIgnoreCase(request.getParameter("wardId"))) {
			request.setAttribute("wardId", PropertyReader.getValue("error.require", "Appointment Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("allergy"))) {
			request.setAttribute("allergy", PropertyReader.getValue("error.require", "Allergy"));
			pass = false;
		}

		log.debug("AppointmentCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("AppointmentCtl Method populatebean Started");

		AppointmentBean bean = new AppointmentBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPatientName(DataUtility.getString(request.getParameter("patientName")));
		bean.setDoctorName(DataUtility.getString(request.getParameter("doctorName")));
		bean.setAllergy(DataUtility.getString(request.getParameter("allergy")));
		bean.setDate(DataUtility.getDate(request.getParameter("date")));

		populateDTO(bean, request);

		log.debug("AppointmentCtl Method populatebean Ended");

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

		AppointmentModel model = new AppointmentModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		try {
			AppointmentBean bean = new AppointmentBean();
			PatientBean pBean = (PatientBean) request.getSession().getAttribute("patient");
			long sId = DataUtility.getLong(request.getParameter("sId"));
			if (sId > 0) {
				ScheduleDoctorBean sBean;

				sBean = new ScheduleDoctorModel().findByPK(sId);

				bean.setDate(sBean.getDate());
				bean.setDoctorName(sBean.getDoctorName());
				bean.setPatientName(pBean.getFirstName() + " " + pBean.getLastName());
				request.getSession().setAttribute("schedule", sBean);
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
		log.debug("AppointmentCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation")); // get model

		AppointmentModel model = new AppointmentModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)) {
			AppointmentBean bean = (AppointmentBean) populateBean(request);
			PatientBean pBean = (PatientBean) request.getSession().getAttribute("patient");
			ScheduleDoctorBean schedule = (ScheduleDoctorBean) request.getSession().getAttribute("schedule");
			bean.setScheduleId(schedule.getId());
			bean.setDoctorId(schedule.getDoctorId());
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
			AppointmentBean bean = (AppointmentBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(EHCView.APPOINTMENT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(EHCView.APPOINTMENT_LIST_CTL, request, response);
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(EHCView.APPOINTMENT_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("AppointmentCtl Method doPostEnded");
	}

	@Override
	protected String getView() {
		return EHCView.APPOINTMENT_VIEW;
	}

}
