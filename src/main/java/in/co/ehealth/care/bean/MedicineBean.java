package in.co.ehealth.care.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicineBean extends BaseBean {
	
	private String name;
	private String companyName;
	private String price;
	private String description;
	private String status; 
	
	

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return String.valueOf(id);
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}

}
