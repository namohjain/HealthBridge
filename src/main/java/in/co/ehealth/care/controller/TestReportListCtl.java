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
import in.co.ehealth.care.bean.TestReportBean;
import in.co.ehealth.care.bean.UserBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.model.TestReportModel;
import in.co.ehealth.care.util.DataUtility;
import in.co.ehealth.care.util.PropertyReader;
import in.co.ehealth.care.util.ServletUtility;

@WebServlet(name = "TestReportListCtl", urlPatterns = { "/ctl/testReportList" })
public class TestReportListCtl extends BaseCtl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(TestReportListCtl.class);

	
	@SuppressWarnings("unused")
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
	    TestReportBean bean = new TestReportBean();

	    HttpSession session = request.getSession();

	    // Get logged-in user role
	    UserBean user = (UserBean) session.getAttribute("user");
	    DoctorBean doctor = (DoctorBean) session.getAttribute("doctor");
	    PatientBean patient = (PatientBean) session.getAttribute("patient");

	    // Set filter by session (role-based filtering)
	    if (doctor != null) {
	        bean.setDoctorId(doctor.getId());  // ✅ doctor sees only own patients
	    }

	    if (patient != null) {
	        bean.setPatientId(patient.getId()); // ✅ patient sees only own reports
	    }

	    // Optional: filters via form input (if search fields exist)
	    bean.setPatientName(DataUtility.getString(request.getParameter("patientName")));
	    bean.setDoctorName(DataUtility.getString(request.getParameter("doctorName")));

	    return bean;
	}

	
	@SuppressWarnings({ "rawtypes", "unused" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("TestReportListCtl doGet Start");
		List list = null;

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		TestReportBean bean = (TestReportBean) populateBean(request);

		// Get logged-in user from session and set patientId
		//UserBean userBean = (UserBean) request.getSession().getAttribute("user");
		//if (userBean != null && userBean.getRoleId() == 4) { // Assuming 2 = Patient
		 //   bean.setPatientId(userBean.getId());
		//}
		 
		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");

		TestReportModel model = new TestReportModel();
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
		log.debug("TestReportListCtl doPOst End");
	}

	
	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("TestReportListCtl doPost Start");
		
		
		List list = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		
		TestReportBean bean = (TestReportBean) populateBean(request);

		// Get logged-in user from session and set patientId
		//UserBean userBean = (UserBean) request.getSession().getAttribute("user");
		//if (userBean != null && userBean.getRoleId() == 4) {
		 //   bean.setPatientId(userBean.getId());
		//}
	 
		String op = DataUtility.getString(request.getParameter("operation"));
		
		String[] ids = request.getParameterValues("ids");
		
		TestReportModel model = new TestReportModel();
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
				ServletUtility.redirect(EHCView.TEST_REPORT_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					TestReportBean deletebean = new TestReportBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
					}
					ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(EHCView.TEST_REPORT_LIST_CTL, request, response);
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
		log.debug("TestReportListCtl doGet End");

	}
	
	protected String getView() {
		return EHCView.TEST_REPORT_LIST_VIEW;
	}
}
