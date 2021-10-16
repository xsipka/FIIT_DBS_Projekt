package reader;


public class Reader {
	

	static private int id;
	static private String firstName;
	static private String lastName;
	static private String email;
	static private String password;
	
	
	// sets reader's id	
	public void setId(int Id) {
		this.id = Id;
	}
	
	// sets reader's first name
	public void setFirstName(String FirstName) {
		this.firstName = FirstName;
	}
	
	// sets reader's last name
	public void setLastName(String LastName) {
		this.lastName = LastName;
	}
	
	// sets reader's email
	public void setEmail(String Email) {
		this.email = Email;
	}
	
	// sets reader's password
	public void setPassword(String Password) {
		this.password = Password;
	}
	
	
	// gets reader's id
	public int getId() {
		return id;
	}
	
	// gets reader's first name
	public String getFirstName() {
		return firstName;
	}
	
	// gets reader's last name
	public String getLastName() {
		return lastName;
	}
	
	// gets reader's email
	public String getEmail() {
		return email;
	}
	
	// gets reader's password
	public String getPassword() {
		return password;
	}
	
}
