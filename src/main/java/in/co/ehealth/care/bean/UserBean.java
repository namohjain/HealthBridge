package in.co.ehealth.care.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBean extends BaseBean {

	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String contactNo;
	private long roleId;
	private String roleName;
	
	private String confirmPassword;
	

	@Override
	public String getKey() {
		return String.valueOf(id);
	}

	@Override
	public String getValue() {
		return firstName+" "+lastName;
	}

	
	
}
