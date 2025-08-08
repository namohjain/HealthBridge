package in.co.ehealth.care.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.ehealth.care.bean.BaseBean;
import in.co.ehealth.care.bean.PatientBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.model.PatientModel;
import in.co.ehealth.care.util.DataUtility;
import in.co.ehealth.care.util.PropertyReader;
import in.co.ehealth.care.util.ServletUtility;

@WebServlet(name = "PatientListCtl", urlPatterns = { "/ctl/patientList" })
public class PatientListCtl extends BaseCtl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(PatientListCtl.class);

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("PatientListCtl populateBean Start");
		PatientBean bean = new PatientBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		
		log.debug("PatientListCtl populateBean End");
		return bean;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("PatientListCtl doGet Start");

		List list = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		PatientBean bean = (PatientBean) populateBean(request);
		PatientModel model = new PatientModel();

		try {
			list = model.search(bean, pageNo, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setSize(model.search(bean).size(), request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		log.debug("PatientListCtl doGet End");
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("PatientListCtl doPost Start");

		List list = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		PatientBean bean = (PatientBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		PatientModel model = new PatientModel();

		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(EHCView.PATIENT_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					for (String id : ids) {
						PatientBean deleteBean = new PatientBean();
						deleteBean.setId(DataUtility.getInt(id));
						model.delete(deleteBean);
					}
					ServletUtility.setSuccessMessage("Data deleted successfully", request);
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(EHCView.PATIENT_LIST_CTL, request, response);
				return;
			}

			list = model.search(bean, pageNo, pageSize);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setSize(model.search(bean).size(), request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}

		log.debug("PatientListCtl doPost End");
	}

	@Override
	protected String getView() {
		return EHCView.PATIENT_LIST_VIEW;
	}
}
