package in.co.ehealth.care.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.ehealth.care.bean.BaseBean;
import in.co.ehealth.care.bean.DoctorBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.model.DoctorModel;
import in.co.ehealth.care.util.DataUtility;
import in.co.ehealth.care.util.PropertyReader;
import in.co.ehealth.care.util.ServletUtility;




@WebServlet(name = "DoctorListCtl", urlPatterns = { "/ctl/doctorList" })
public class DoctorListCtl extends BaseCtl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(DoctorListCtl.class);

	
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("DoctorListCtl populateBean method start");
		DoctorBean bean = new DoctorBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		
		log.debug("DoctorListCtl populateBean method end");
		return bean;
	}

	
	@SuppressWarnings({ "unused", "rawtypes" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("DoctorListCtl doGet Start");
		List list = null;

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		DoctorBean bean = (DoctorBean) populateBean(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		DoctorModel model = new DoctorModel();
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
		log.debug("DoctorListCtl doPOst End");
	}

	
	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("DoctorListCtl doPost Start");
		
		
		List list = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		
		DoctorBean bean = (DoctorBean) populateBean(request);
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		String[] ids = request.getParameterValues("ids");
		
		DoctorModel model = new DoctorModel();
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
				ServletUtility.redirect(EHCView.DOCTOR_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					DoctorBean deletebean = new DoctorBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
					}
					ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(EHCView.DOCTOR_LIST_CTL, request, response);
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
		log.debug("DoctorListCtl doGet End");

	}
	
	protected String getView() {
		return EHCView.DOCTOR_LIST_VIEW;
	}
}
