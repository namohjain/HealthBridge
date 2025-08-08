package in.co.ehealth.care.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PrescriptionBean extends BaseBean{
	
	private long patientId;
	private String patientName;
	private long doctorId;
	private String doctorName;
	private String prescription;
	private Date date;

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
