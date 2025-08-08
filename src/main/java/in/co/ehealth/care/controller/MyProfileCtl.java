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
import in.co.ehealth.care.bean.UserBean;
import in.co.ehealth.care.exception.ApplicationException;
import in.co.ehealth.care.exception.DuplicateRecordException;
import in.co.ehealth.care.model.UserModel;
import in.co.ehealth.care.util.DataUtility;
import in.co.ehealth.care.util.DataValidator;
import in.co.ehealth.care.util.PropertyReader;
import in.co.ehealth.care.util.ServletUtility;

/**
 * Servlet implementation class MyProfileCtl
 */

/**
 * MyProfile functionality Controller. Performs operation for Add, update
 * operations of MyProfile
 * 
 * @author Navigable Set
 * @version 1.0
 * @Copyright (c) Navigable Set
 */
@WebServlet(name = "MyProfileCtl", urlPatterns = { "/ctl/myProfile" })
public class MyProfileCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";
	public static final String OP_CHANGE_MY_PASSWORD = "ChangePassword";

	private static Logger log = Logger.getLogger(MyProfileCtl.class);

	/**
	 * Validate input Data Entered By User
	 * 
	 * @param request
	 * @return
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("MyProfileCtl Method validate Started");

		boolean pass = true;

		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op) || op == null) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("userName"))) {
			request.setAttribute("userName", PropertyReader.getValue("error.require", "User Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("firstName"))) {
			System.out.println("firstName" + request.getParameter("firstName"));
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.name", "First Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.name", "Last Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("contactNo"))) {
			request.setAttribute("contactNo", PropertyReader.getValue("error.require", "ContactNo"));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("contactNo"))) {
			request.setAttribute("contactNo", PropertyReader.getValue("error.invalid", "ContactNo"));
			pass = false;
		}

		log.debug("MyProfileCtl Method validate Ended");
		return pass;
	}

	/**
	 * Populates bean object from request parameters
	 * 
	 * @param request
	 * @return
	 */

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("MyProfileCtl Method PopulateBean Started ");
		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setUserName(DataUtility.getString(request.getParameter("userName")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setContactNo(DataUtility.getString(request.getParameter("contactNo")));

		populateDTO(bean, request);

		log.debug("MyProfileCtl Method PopulateBean End ");
		return bean;
	}

	/**
	 * Display Concept for viewing profile page view
	 */
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("MyProfileCTl Method doGet Started");

		HttpSession session = request.getSession(true);

		UserBean userBean = (UserBean) session.getAttribute("user");

		long id = userBean.getId();

		String op = DataUtility.getString(request.getParameter("operation"));
		// get Model

		UserModel model = new UserModel();

		if (id > 0 || op != null) {
			System.out.println("in id>0 condition");
			UserBean bean;
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
		log.debug("MyProfileCtl Method doGet Ended");
	}

	/**
	 * Submit Concept
	 */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("MyprofileCtl Method doPost Started");

		HttpSession session = request.getSession(true);

		UserBean userBean = (UserBean) session.getAttribute("user");

		long id = userBean.getId();

		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		UserModel model = new UserModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			try {
				if (id > 0) {
					userBean.setFirstName(bean.getFirstName());
					userBean.setLastName(bean.getLastName());
					userBean.setContactNo(bean.getContactNo());
					userBean.setUserName(bean.getUserName());
					model.update(userBean);
					
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Profile has been updated Successfully. ", request);
				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {
			ServletUtility.redirect(EHCView.CHANGE_PASSWORD_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("MyProfileCtl doPost method end");
	}

	/**
	 * Returns the VIEW page of this Controller
	 * 
	 * @return
	 */
	@Override
	protected String getView() {

		return EHCView.MY_PROFILE_VIEW;
	}

}
