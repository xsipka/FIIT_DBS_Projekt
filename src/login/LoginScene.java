package login;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.DatabaseMain;
import reader.Reader;



public class LoginScene {
	
	
	DatabaseMain database;
	Reader reader;
	
	int readerID;
	String firstName;
	String lastName;
	
	// logs reader into an existing account
	public boolean readerLogIn(String email, String password) throws Exception {
			
		database = new DatabaseMain();
		Statement st;
	    ResultSet result;		
	    Connection conn;
	    
		String loginData = "SELECT * FROM library.reader WHERE email='" + email +"' AND password='" + password +"'";
			
		try {
			conn = database.connectToDatabase();
			st = conn.createStatement();
			result = st.executeQuery(loginData);	
			// if login is successful, then get the rest of the user's data
			if (result.next() == true) {
				readerID = result.getInt("reader_ID");
				firstName = result.getString("firstName");
				lastName = result.getString("lastName");
					
				// sets reader's data
				reader = new Reader();
				reader.setId(readerID);
				reader.setFirstName(firstName);
				reader.setLastName(lastName);
				reader.setEmail(email);
				reader.setPassword(password);
					
				conn.close();
				return true;
			}
			else {
				conn.close();
				return false;
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
		
		

}
