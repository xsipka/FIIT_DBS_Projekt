package statistics;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import borrowing.Borrow;
import copy.Copy;
import main.DatabaseMain;

public class StatisticsScene {
	
	DatabaseMain database;
	
	public ArrayList<Statistics> FindUsersWithManyBooks() {
			
		database = new DatabaseMain();
		Statement st;
		ResultSet result;		
	    Connection conn;
	    ArrayList<Statistics> foundItems = new ArrayList<Statistics>();
	    
		        
	    try {
	    	conn = database.connectToDatabase();
	    	st = conn.createStatement();
	    	
	    	String searchQuery = "SELECT reader.email, "
	    			+ "COUNT(reader.email) "
	    			+ "FROM reader "
	    			+ "JOIN borrowed ON reader.reader_ID = borrowed.reader_ID "
	    			+ "GROUP by reader.email "
	    			+ "HAVING COUNT(reader.email) > 10;";
	    	result = st.executeQuery(searchQuery);
					
		   	while(result.next()) {
		   		Statistics statistics = new Statistics();
		   		statistics.addEmail(result.getString("email"));
		   		statistics.addValue(Integer.toString(result.getInt("COUNT(reader.email)")));
		   		foundItems.add(statistics);
		   	}
		   	conn.close();
		    
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return foundItems;
	}
	
	public ArrayList<Statistics> SumAllUsersFines() {
		
		database = new DatabaseMain();
		Statement st;
		ResultSet result;		
	    Connection conn;
	    ArrayList<Statistics> foundItems = new ArrayList<Statistics>();
	    
		        
	    try {
	    	conn = database.connectToDatabase();
	    	st = conn.createStatement();
	    	
	    	String searchQuery = "SELECT reader.email, returned.fine "
	    			+ "FROM reader "
	    			+ "JOIN returned ON reader.reader_ID = returned.brw_ID "
	    			+ "GROUP by email;";
	    	result = st.executeQuery(searchQuery);
					
		   	while(result.next()) {
		   		Statistics statistics = new Statistics();
		   		statistics.addEmail(result.getString("email"));
		   		statistics.addValue(Integer.toString(result.getInt("fine")));
		   		foundItems.add(statistics);
		   	}
		   	conn.close();
		    
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return foundItems;
	}
	
	public ArrayList<Statistics> FindUsersWithReturnedBooks() {
		database = new DatabaseMain();
		Statement st;
		ResultSet result;		
	    Connection conn;
	    ArrayList<Statistics> foundItems = new ArrayList<Statistics>();
	    
		        
	    try {
	    	conn = database.connectToDatabase();
	    	st = conn.createStatement();
	    	
	    	String searchQuery = "SELECT reader.email, returned.fine, "
	    			+ "COUNT(reader.email) AS returned "
	    			+ "FROM reader "
	    			+ "JOIN returned ON reader.reader_ID = returned.brw_ID "
	    			+ "GROUP by reader.email";
	    	result = st.executeQuery(searchQuery);
					
		   	while(result.next()) {
		   		Statistics statistics = new Statistics();
		   		statistics.addEmail(result.getString("email"));
		   		statistics.addValue(Integer.toString(result.getInt("fine")));
		   		foundItems.add(statistics);
		   	}
		   	conn.close();
		    
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return foundItems;
	}
}
