package in.co.ehealth.care.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.ehealth.care.bean.BaseBean;
import in.co.ehealth.care.bean.ScheduleDoctorBean;
import in.co.ehealth.care.bean.UserBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.model.ScheduleDoctorModel;
import in.co.ehealth.care.util.DataUtility;
import in.co.ehealth.care.util.PropertyReader;
import in.co.ehealth.care.util.ServletUtility;

@WebServlet(name = "ScheduleDoctorListCtl", urlPatterns = { "/ctl/scheduleList" })
public class ScheduleDoctorListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(ScheduleDoctorListCtl.class);

	
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("ScheduleDoctorListCtl populateBean method start");
		ScheduleDoctorBean bean = new ScheduleDoctorBean();

		bean.setDoctorName(DataUtility.getString(request.getParameter("name")));
		
		log.debug("ScheduleDoctorListCtl populateBean method end");
		return bean;
	}

	
	@SuppressWarnings("rawtypes")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("ScheduleDoctorListCtl doGet Start");
		List list = null;

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		ScheduleDoctorBean bean = (ScheduleDoctorBean) populateBean(request);

		// Restrict schedule view to the logged-in doctor only
		UserBean userBean = (UserBean) request.getSession().getAttribute("user");
		if (userBean != null && "Doctor".equalsIgnoreCase(userBean.getRoleName())) {
		    bean.setDoctorName(userBean.getFirstName());
		}
		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		ScheduleDoctorModel model = new ScheduleDoctorModel();
		try {
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
		log.debug("ScheduleDoctorListCtl doPOst End");
	}

	
	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("ScheduleDoctorListCtl doPost Start");
		
		
		List list = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		
		ScheduleDoctorBean bean = (ScheduleDoctorBean) populateBean(request);
		UserBean userBean = (UserBean) request.getSession().getAttribute("user");
		if (userBean != null && "Doctor".equalsIgnoreCase(userBean.getRoleName())) {
		    bean.setDoctorName(userBean.getFirstName());
		}
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		String[] ids = request.getParameterValues("ids");
		
		ScheduleDoctorModel model = new ScheduleDoctorModel();
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
				ServletUtility.redirect(EHCView.SCHEDULE__DOCTOR_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					ScheduleDoctorBean deletebean = new ScheduleDoctorBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
					}
					ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(EHCView.SCHEDULE_DOCTOR_LIST_CTL, request, response);
				return;

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
		log.debug("ScheduleDoctorListCtl doGet End");

	}
	
	protected String getView() {
		return EHCView.SCHEDULE_DOCTOR_LIST_VIEW;
	}
}
