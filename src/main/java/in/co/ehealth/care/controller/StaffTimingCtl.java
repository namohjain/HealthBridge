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
import in.co.ehealth.care.bean.StaffBean;
import in.co.ehealth.care.bean.StaffTimingBean;
import in.co.ehealth.care.bean.UserBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.model.StaffTimingModel;
import in.co.ehealth.care.model.UserModel;
import in.co.ehealth.care.util.DataUtility;
import in.co.ehealth.care.util.DataValidator;
import in.co.ehealth.care.util.PropertyReader;
import in.co.ehealth.care.util.ServletUtility;

/**
 * Servlet implementation class StaffTimingCtl
 */

@WebServlet(name = "StaffTimingCtl", urlPatterns = { "/ctl/staffTiming" })
public class StaffTimingCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(StaffTimingCtl.class);

	protected boolean validate(HttpServletRequest request) {

		log.debug("StaffTimingCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("time"))) {
			request.setAttribute("time", PropertyReader.getValue("error.require", "Timing"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("date"))) {
			request.setAttribute("date", PropertyReader.getValue("error.require", "Date"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("wardName"))) {
			request.setAttribute("wardName", PropertyReader.getValue("error.require", "WardName"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("roomNo"))) {
			request.setAttribute("roomNo", PropertyReader.getValue("error.require", "RoomNo"));
			pass = false;
		}

		log.debug("StaffTimingCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("StaffTimingCtl Method populatebean Started");

		StaffTimingBean bean = new StaffTimingBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setTiming(DataUtility.getString(request.getParameter("time")));
		bean.setWardName(DataUtility.getString(request.getParameter("wardName")));
		bean.setRoomNo(DataUtility.getString(request.getParameter("roomNo")));
		bean.setDate(DataUtility.getDate(request.getParameter("date")));
		populateDTO(bean, request);

		log.debug("StaffTimingCtl Method populatebean Ended");

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

		StaffTimingModel model = new StaffTimingModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			StaffTimingBean bean;
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
		log.debug("StaffTimingCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation")); // get model
		
		HttpSession session=request.getSession();
		
		StaffTimingModel model = new StaffTimingModel();
		UserModel usermodel = new UserModel();
		
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)) {
			StaffTimingBean bean = (StaffTimingBean) populateBean(request);
			StaffBean dBean=(StaffBean)session.getAttribute("staff");
			bean.setStaffId(dBean.getId());
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
			StaffTimingBean bean = (StaffTimingBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(EHCView.STAFF_TIMING_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(EHCView.STAFF_TIMING_LIST_CTL, request, response);
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(EHCView.STAFF_TIMING_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("StaffTimingCtl Method doPostEnded");
	}

	@Override
	protected String getView() {
		return EHCView.STAFF_TIMING_VIEW;
	}

}
