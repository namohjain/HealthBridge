package in.co.ehealth.care.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentBean extends BaseBean {

	private long patientId;
	private String patientName;
	private long doctorId;
	private String doctorName;
	private long scheduleId;
	private Date date;
	private String allergy;
	
	
	@Override
	public String getKey() {
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
