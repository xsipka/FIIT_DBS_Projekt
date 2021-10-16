package returning;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import copy.Copy;
import main.DatabaseMain;


public class ReturnScene {
	
	DatabaseMain database;
	Copy copy;
	Return oneItem;
	
	
	// founds all currently borrowed books by the reader
	public ArrayList<Return> foundBorrowed(String readerId){
		
		ArrayList<Return> borrowed = new ArrayList<Return>();
		database = new DatabaseMain();
		Statement st;
		ResultSet result;		
	    Connection conn;
		
	    try {
	    	conn = database.connectToDatabase();
	    	st = conn.createStatement();
	    	String searchQuery = "SELECT copy.copy_ID, copy.brw_ID, copy.copyStatus, "
	    			+ "book.title, borrowed.fromDate, borrowed.dueDate "
	    			+ "FROM library.copy "
	    			+ "INNER JOIN library.book ON library.copy.book_ID=library.book.book_ID "
	    			+ "INNER JOIN library.borrowed ON library.copy.brw_ID=library.borrowed.brw_ID "
	    			+ "WHERE reader_ID=" + readerId;
	    	result = st.executeQuery(searchQuery);
					
		   	while(result.next()) {
		   		copy = new Copy();
		   		oneItem = new Return();
		   		copy.setCopyId(result.getInt("copy_ID"));
		   		copy.setStatus(result.getString("copyStatus"));
		   		copy.setBrwId(result.getInt("brw_ID"));
		   		
		   		oneItem.setCopy(copy);
		   		oneItem.setBorrowId(result.getInt("brw_ID"));
		   		oneItem.setFromDate(result.getString("fromDate"));
		   		oneItem.setDueDate(result.getString("dueDate"));
		   		oneItem.setStatus(result.getString("copyStatus"));
		   		oneItem.setTitle(result.getString("title"));
		   		oneItem.setReaderId(Integer.parseInt(readerId));
		   		borrowed.add(oneItem);
		   	}
		   	conn.close();
		    
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
		return borrowed;
	}
	
	
	// return 1 if return date is before due date, that means return of books is in time
	public int checkDate(String returnDate, String due) {
		
		try {
			Date rtnDate = new SimpleDateFormat("yyyy-MM-dd").parse(returnDate);
			Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(due); 
			
			if (dueDate.compareTo(rtnDate) >= 0) {
	            //System.out.println("dueDate is after rtnDate");
	            return 1;
	        }
			else {
				return 0;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return 0;
	}
	
	
	public void returnBooks(ArrayList<Return> toReturn, String fine, String returnDate) {
		
		database = new DatabaseMain();
		Statement st;
		ResultSet result;		
	    Connection conn;
	    
	    int inTime;
	    inTime = checkDate(returnDate, toReturn.get(0).getDueDate());
	    
	    String returnRecord = "INSERT INTO library.returned (inTime, rtnDate, fine, lib_ID, brw_ID) "
	    		+ "VALUES (" + inTime + ", '" + returnDate + "', '" + fine + "', " + 1 + ", " + toReturn.get(0).getBorrowId() + ")";
	
	    try {
	    	conn = database.connectToDatabase();
	    	
	    	try {
	    		conn.setAutoCommit(false);
	    		database.addToDatabase(returnRecord, conn);
		    	st = conn.createStatement();
		    	result = st.executeQuery("SELECT MAX(rtn_ID) rtn_ID FROM library.returned");
		    	result.next();
		    	int returnId = result.getInt("rtn_ID");
		    	
		    	for (int i = 0; i < toReturn.size(); i++) {
		    		String alterCopy = "UPDATE library.copy "
				    		+ "SET  rtn_ID = " + returnId + ", brw_ID = null, copyStatus = '" + toReturn.get(i).getStatus() + "' "
				    		+ "WHERE copy_ID = " + toReturn.get(i).getCopy().getCopyId();
		    		database.addToDatabase(alterCopy, conn);
		    	}
		    	conn.commit();
			   	conn.close();
			   	
	    	} catch(SQLException e){
	    		   conn.rollback();
	    		}
		    
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	
	public void prolongBooks(ArrayList<Return> toProlong, String newDueDate) {
		
		database = new DatabaseMain();	
	    Connection conn;
	    
	    try {
	    	conn = database.connectToDatabase();
	    	
	    	for (int i = 0; i < toProlong.size(); i++) {
	    		String alterCopy = "UPDATE library.borrowed "
			    		+ "SET  dueDate = '" + newDueDate + "' "
			    		+ "WHERE brw_ID = " + toProlong.get(i).getBorrowId();
	    		database.addToDatabase(alterCopy, conn);
	    	}
		   	conn.close();
		    
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
}
