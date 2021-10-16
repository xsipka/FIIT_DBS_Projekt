package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseMain {
	
	// connects to database
	public Connection connectToDatabase() throws Exception {
        
		try {
            String url = "jdbc:mysql://localhost:3306/library?useTimezone=true&serverTimezone=UTC";
            String username = "root";
            String password = "";
           
            Connection conn = DriverManager.getConnection(url, username, password);
            //System.out.println("pripojenie uspesne");
            return conn;
            
        } catch(Exception e) {
        	System.out.println(e);
        	}
		
        return null;
    }
	
	
	// adds a new record to the database
	public void addToDatabase(String query, Connection conn) {
		
		Statement st;
		
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
			//conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// checks if record exits 
	public boolean checkIfExists(String query, Connection conn) {
		
		Statement st;
		ResultSet result;
		
		try {
			st = conn.createStatement();
			result = st.executeQuery(query);
	    	
	    	if (result.next() == true) {
				return true;
			}
	    	conn.close();
	    	
	    } catch (Exception e) {
			e.printStackTrace();
		}
    	
		return false;
	}
	
	
	// finds and return searched rows
	public ResultSet findRecords(String query, Connection conn) {
		
		ResultSet result = null;
		Statement st;
		
		try {
			st = conn.createStatement();
			result = st.executeQuery(query);
	    	
	    	if (result.next() == true) {
				return result;
			}
	    	conn.close();
	    	
	    } catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
