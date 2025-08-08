package in.co.ehealth.care.bean;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffTimingBean extends BaseBean {

	private long staffId;
	private String staffName;
	private Date date;
	private String timing;
	private String wardName;
	private String roomNo;
	
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
