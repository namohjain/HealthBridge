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
import in.co.ehealth.care.bean.UserBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.model.PatientModel;
import in.co.ehealth.care.model.UserModel;
import in.co.ehealth.care.util.DataUtility;
import in.co.ehealth.care.util.DataValidator;
import in.co.ehealth.care.util.PropertyReader;
import in.co.ehealth.care.util.ServletUtility;

/**
 * Servlet implementation class PatientCtl
 */

@WebServlet(name = "PatientCtl", urlPatterns = { "/patient" })
public class PatientCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(PatientCtl.class);

	protected boolean validate(HttpServletRequest request) {

		log.debug("AssignFacultyCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("userName"))) {
			request.setAttribute("userName", PropertyReader.getValue("error.require", "UserName"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;

		} else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.password", "Password"));
			return false;
		} else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.password", "Password"));
			return false;
		}

		if (DataValidator.isNull(request.getParameter("contactNo"))) {
			request.setAttribute("contactNo", PropertyReader.getValue("error.require", "Contact No"));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("contactNo"))) {
			request.setAttribute("contactNo", PropertyReader.getValue("error.invalid", "Contact No"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of birth"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email"));
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

		log.debug("PatientCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("PatientCtl Method populatebean Started");

		PatientBean bean = new PatientBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setUserName(DataUtility.getString(request.getParameter("userName")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setContactNo(DataUtility.getString(request.getParameter("contactNo")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setCity(DataUtility.getString(request.getParameter("city")));
		bean.setAddress(DataUtility.getString(request.getParameter("address")));

		populateDTO(bean, request);

		log.debug("PatientCtl Method populatebean Ended");

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

		PatientModel model = new PatientModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {

			PatientBean bean;
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
		log.debug("PatientCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation")); // get model
		
		PatientModel model = new PatientModel();
		UserModel usermodel = new UserModel();
		
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)) {
			PatientBean bean = (PatientBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
				} else {
					UserBean userBean=new UserBean();
					userBean.setContactNo(bean.getContactNo());
					userBean.setUserName(bean.getUserName());
					userBean.setFirstName(bean.getFirstName());
					userBean.setLastName(bean.getLastName());
					userBean.setPassword(bean.getPassword());
					userBean.setRoleId(4L);
					userBean.setRoleName("Patient");
					long uPk=usermodel.add((UserBean)populateDTO(userBean, request));
					bean.setUserId(uPk);
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
			PatientBean bean = (PatientBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(EHCView.PATIENT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(EHCView.PATIENT_LIST_CTL, request, response);
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(EHCView.PATIENT_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("PatientCtl Method doPostEnded");
	}

	@Override
	protected String getView() {
		return EHCView.PATIENT_VIEW;
	}

}
