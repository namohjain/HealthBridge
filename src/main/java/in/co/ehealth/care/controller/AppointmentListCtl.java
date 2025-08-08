package in.co.ehealth.care.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


import in.co.ehealth.care.bean.BaseBean;
import in.co.ehealth.care.bean.PatientBean;
import in.co.ehealth.care.bean.UserBean;
import in.co.ehealth.care.bean.AppointmentBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.model.AppointmentModel;
import in.co.ehealth.care.util.DataUtility;
import in.co.ehealth.care.util.PropertyReader;
import in.co.ehealth.care.util.ServletUtility;

@WebServlet(name = "AppointmentListCtl", urlPatterns = { "/ctl/appointmentList" })
public class AppointmentListCtl extends BaseCtl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(AppointmentListCtl.class);

	
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("AppointmentListCtl populateBean method start");
		AppointmentBean bean = new AppointmentBean();

		bean.setDoctorName(DataUtility.getString(request.getParameter("doctorName")));
		bean.setPatientName(DataUtility.getString(request.getParameter("patientName")));
		
		log.debug("AppointmentListCtl populateBean method end");
		return bean;
	}

	
	@SuppressWarnings({ "rawtypes", "unused" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("AppointmentListCtl doGet Start");
		List list = null;

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		AppointmentBean bean = (AppointmentBean) populateBean(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		HttpSession session=request.getSession();
		
		AppointmentModel model = new AppointmentModel();
		try {
			
			UserBean user=(UserBean) session.getAttribute("user");
			
			if(user.getRoleId()==4) {
				PatientBean patient=(PatientBean) session.getAttribute("patient");
				bean.setPatientId(patient.getId());
			}
			if(user.getRoleId()==2) {
				long sId=DataUtility.getLong(request.getParameter("sId"));
				bean.setScheduleId(sId);
			}
			
			
			
			list = model.search(bean, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setSize(model.search(bean).size(), request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("AppointmentListCtl doPOst End");
	}

	
	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("AppointmentListCtl doPost Start");
		
		
		List list = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		HttpSession session=request.getSession();
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		
		AppointmentBean bean = (AppointmentBean) populateBean(request);
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		String[] ids = request.getParameterValues("ids");
		
		AppointmentModel model = new AppointmentModel();
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(EHCView.APPOINTMENT_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					AppointmentBean deletebean = new AppointmentBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
					}
					ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(EHCView.APPOINTMENT_LIST_CTL, request, response);
				return;

			}
			
			UserBean user=(UserBean) session.getAttribute("user");
			
			if(user.getRoleId()==4) {
				PatientBean patient=(PatientBean) session.getAttribute("patient");
				bean.setPatientId(patient.getId());
			}
			
			list = model.search(bean, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setSize(model.search(bean).size(), request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("AppointmentListCtl doGet End");

	}
	
	protected String getView() {
		return EHCView.APPOINTMENT_LIST_VIEW;
	}
}
