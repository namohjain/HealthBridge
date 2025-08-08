package in.co.ehealth.care.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StaffBean extends BaseBean {
	
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String contactNo;
	private Date dob;
	private String email;
	private String qualification;
	private String experience;
	private long userId;

	@Override
	public String getKey() {
		return String.valueOf(id);
	}

	@Override
	public String getValue() {
		return firstName +" "+lastName;
	}

}
