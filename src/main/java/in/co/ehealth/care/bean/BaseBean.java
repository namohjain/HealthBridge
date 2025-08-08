package in.co.ehealth.care.bean;

import java.sql.Timestamp;

import lombok.Data;

/**
 * BaseBean JavaBean encapsulates Generic attributes
 */

@Data
public abstract class BaseBean implements DropdownListBean,Comparable<DropdownListBean> {
	
	
	protected long id;
	protected String createdBy;
	protected String modifiedBy;
	protected Timestamp createdDatetime;
	protected Timestamp modifiedDatetime;
	
	@Override
	public int compareTo(DropdownListBean o) {
		return o.getValue().compareTo(o.getValue());
	}
	
	
	

	
	
	
}
