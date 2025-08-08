package in.co.ehealth.care.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDoctorBean  extends BaseBean{
	
	private long doctorId;
	private String doctorName;
	private String time;
	private Date date;
	private String address;
	private String city;
	

	@Override
	public String getKey() {
		return String.valueOf(id);
	}

	@Override
	public String getValue() {
		return doctorName;
	}

}
