package in.co.ehealth.care.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestReportBean extends BaseBean{

	private long patientId;
	private String patientName;
	private long doctorId;
	private String doctorName;
	private String fileName;
	private String fileType;
	private byte[] reportFile;

	private String description;
	
	
	@Override
	public String getKey() {
		return null;
	}
	@Override
	public String getValue() {
		return null;
	}
	
	
}
