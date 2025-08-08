package in.co.ehealth.care.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientBean extends BaseBean {

	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String contactNo;
	private Date dob;
	private String email;
	private String city;
	private String address;
	private long userId;
	
	@Override
	public String getKey() {
		return String.valueOf(id);
	}

	@Override
	public String getValue() {
		return firstName+ " "+lastName;
	}

}
