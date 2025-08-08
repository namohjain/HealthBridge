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
import in.co.ehealth.care.bean.DoctorBean;
import in.co.ehealth.care.bean.PatientBean;
import in.co.ehealth.care.bean.UserBean;
import in.co.ehealth.care.bean.PrescriptionBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.model.PrescriptionModel;
import in.co.ehealth.care.util.DataUtility;
import in.co.ehealth.care.util.PropertyReader;
import in.co.ehealth.care.util.ServletUtility;

@WebServlet(name = "PrescriptionListCtl", urlPatterns = { "/ctl/prescriptionList" })
public class PrescriptionListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(PrescriptionListCtl.class);

	
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("PrescriptionListCtl populateBean method start");
		PrescriptionBean bean = new PrescriptionBean();

		bean.setDoctorName(DataUtility.getString(request.getParameter("doctorName")));
		bean.setPatientName(DataUtility.getString(request.getParameter("patientName")));
		
		log.debug("PrescriptionListCtl populateBean method end");
		return bean;
	}

	
	@SuppressWarnings("rawtypes")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("PrescriptionListCtl doGet Start");
		List list = null;

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		PrescriptionBean bean = (PrescriptionBean) populateBean(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		HttpSession session=request.getSession();
		
		PrescriptionModel model = new PrescriptionModel();
		try {
			
			UserBean user=(UserBean) session.getAttribute("user");
			
			if(user.getRoleId()==4) {
				PatientBean patient=(PatientBean) session.getAttribute("patient");
				bean.setPatientId(patient.getId());
			}
			if(user.getRoleId()==2) {
				DoctorBean doctor=(DoctorBean) session.getAttribute("doctor");
				bean.setDoctorId(doctor.getId());
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
		log.debug("PrescriptionListCtl doPOst End");
	}

	
	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("PrescriptionListCtl doPost Start");
		
		
		List list = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		HttpSession session=request.getSession();
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		
		PrescriptionBean bean = (PrescriptionBean) populateBean(request);
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		String[] ids = request.getParameterValues("ids");
		
		PrescriptionModel model = new PrescriptionModel();
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
				ServletUtility.redirect(EHCView.PRESCRIPTION_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					PrescriptionBean deletebean = new PrescriptionBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
					}
					ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(EHCView.PRESCRIPTION_LIST_CTL, request, response);
				return;

			}
			
			UserBean user=(UserBean) session.getAttribute("user");
			
			if(user.getRoleId()==4) {
				PatientBean patient=(PatientBean) session.getAttribute("patient");
				bean.setPatientId(patient.getId());
			}
			if(user.getRoleId()==2) {
				DoctorBean doctor=(DoctorBean) session.getAttribute("doctor");
				bean.setDoctorId(doctor.getId());
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
		log.debug("PrescriptionListCtl doGet End");

	}
	
	protected String getView() {
		return EHCView.PRESCRIPTION_LIST_VIEW;
	}
}
