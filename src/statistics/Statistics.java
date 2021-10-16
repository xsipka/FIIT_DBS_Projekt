package statistics;


public class Statistics {


	private String email;
	private String value;
	
	public void addEmail(String newEmail) {
		this.email = newEmail;
	}
	
	public void addValue(String newValue) {
		this.value = newValue;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getValue() {
		return value;
	}
	
}