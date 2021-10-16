package register;

//import java.sql.DriverManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import main.DatabaseMain;
import reader.NewReader;

public class RegisterScene {
	
	DatabaseMain database;
	NewReader newReader;
	
	// checks if registration's data are viable, if everything OK return true
	public boolean checkRegData (String firstName, String lastName, String email, String password) {
		
		// checks if variables contain values
		if (!firstName.isEmpty() && !lastName.isEmpty() && !password.isEmpty() && !email.isEmpty()) { // && !gender.isEmpty() && !phoneNum.isEmpty()) {
			
			// checks for forbidden characters
			if (firstName.matches(".*\\d.*") || lastName.matches(".*\\d.*")) { //|| !phoneNum.matches("[0-9]+")) {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			return false;
		}
	}
	
	
	// adds new reader to the database
	public void addNewReader(String firstName, String lastName, String email, String password) throws Exception {
		
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(NewReader.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();
		
		try {
			newReader = new NewReader();
			newReader.setFirstName(firstName);
			newReader.setLastName(lastName);
			newReader.setEmail(email);
			newReader.setPassword(password);
			
			session.beginTransaction();
			session.save(newReader);
			session.getTransaction().commit();
			//System.out.println("Ujo pridaný :)\n");
			
		} finally {
			factory.close();
		}

	}
	

}
