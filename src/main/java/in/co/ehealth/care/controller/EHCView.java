package in.co.ehealth.care.controller;

public interface EHCView {

	public String APP_CONTEXT = "/E-Health-Care";
	public String PAGE_FOLDER = "/jsp";
	public String ERROR_VIEW = PAGE_FOLDER + "/Error.jsp";
	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/userRegistration.jsp";
	public String DOCTOR_VIEW = PAGE_FOLDER + "/doctor.jsp";
	public String DOCTOR_LIST_VIEW = PAGE_FOLDER + "/doctorList.jsp";
	public String STAFF_VIEW = PAGE_FOLDER + "/staff.jsp";
	public String STAFF_LIST_VIEW = PAGE_FOLDER + "/staffList.jsp";
	
	
	public String STAFF_TIMING_VIEW = PAGE_FOLDER + "/staffTiming.jsp";
	public String STAFF_TIMING_LIST_VIEW = PAGE_FOLDER + "/staffTimingList.jsp";
	
	
	public String SCHEDULE_DOCTOR_VIEW = PAGE_FOLDER + "/schedule.jsp";
	public String SCHEDULE_DOCTOR_LIST_VIEW = PAGE_FOLDER + "/scheduleList.jsp";
	
	public String PATIENT_VIEW = PAGE_FOLDER + "/patient.jsp";
	public String PATIENT_LIST_VIEW = PAGE_FOLDER + "/patientList.jsp";
	
	public String PRESCRIPTION_VIEW = PAGE_FOLDER + "/prescription.jsp";
	public String PRESCRIPTION_LIST_VIEW = PAGE_FOLDER + "/prescriptionList.jsp";
	
	public String APPOINTMENT_VIEW = PAGE_FOLDER + "/appointment.jsp";
	public String APPOINTMENT_LIST_VIEW = PAGE_FOLDER + "/appointmentList.jsp";
	
	public String MEDICINE_VIEW = PAGE_FOLDER + "/medicine.jsp";
	public String MEDICINE_LIST_VIEW = PAGE_FOLDER + "/medicineList.jsp";
	
	public String TEST_REPORT_VIEW = PAGE_FOLDER + "/testReport.jsp";
	public String TEST_REPORT_LIST_VIEW = PAGE_FOLDER + "/testReportList.jsp";
	
	public String SUCCESS_VIEW = PAGE_FOLDER + "/success.jsp";
	
	
	public String LOGIN_VIEW = PAGE_FOLDER + "/login.jsp";
	public String WELCOME_VIEW = PAGE_FOLDER + "/welcome.jsp";
	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/changePassword.jsp";
	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/myProfile.jsp";
	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/forgetPassword.jsp";
	
	public String CONTACT_US_VIEW =PAGE_FOLDER + "/contactUs.jsp";
	
	public String ABOUT_US_VIEW = PAGE_FOLDER + "/aboutUs.jsp";

	public String ERROR_CTL = "/ctl/ErrorCtl";

	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";
	
	public String DOCTOR_CTL = APP_CONTEXT + "/ctl/doctor";
	public String DOCTOR_LIST_CTL = APP_CONTEXT + "/ctl/doctorList";
	
	public String STAFF_CTL = APP_CONTEXT + "/ctl/staff";
	public String STAFF_LIST_CTL = APP_CONTEXT + "/ctl/staffList";
	
	public String STAFF_TIMING_CTL = APP_CONTEXT + "/ctl/staffTiming";
	public String STAFF_TIMING_LIST_CTL = APP_CONTEXT + "/ctl/staffTimingList";
	
	public String PATIENT_CTL = APP_CONTEXT + "/patient";
	public String PATIENT_LIST_CTL = APP_CONTEXT + "/ctl/PatientList";
	
	public String APPOINTMENT_CTL = APP_CONTEXT + "/ctl/appointment";
	public String APPOINTMENT_LIST_CTL = APP_CONTEXT + "/ctl/appointmentList";
	
	public String PRESCRIPTION_CTL = APP_CONTEXT + "/ctl/prescription";
	public String PRESCRIPTION_LIST_CTL = APP_CONTEXT + "/ctl/prescriptionList";
	
	public String SCHEDULE__DOCTOR_CTL = APP_CONTEXT + "/ctl/schedule";
	public String SCHEDULE_DOCTOR_LIST_CTL = APP_CONTEXT + "/ctl/scheduleList";
	
	public String MEDICINE_CTL = APP_CONTEXT + "/ctl/medicine";
	public String MEDICINE_LIST_CTL = APP_CONTEXT + "/ctl/medicineList";
	
	public String TEST_REPORT_CTL = APP_CONTEXT + "/ctl/testReport ";
	public String TEST_REPORT_LIST_CTL = APP_CONTEXT + "/ctl/testReportList";
	
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/registration";
	public String LOGIN_CTL = APP_CONTEXT + "/login";
	public String WELCOME_CTL = APP_CONTEXT + "/welcome";
	public String LOGOUT_CTL = APP_CONTEXT + "/LoginCtl";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/changePassword";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/myProfile";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/forgetPassword";

}
