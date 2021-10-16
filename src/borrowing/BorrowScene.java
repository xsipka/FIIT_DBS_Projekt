package borrowing;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import copy.Copy;
import main.DatabaseMain;

public class BorrowScene {
	
	DatabaseMain database;
	Copy copy;
	Borrow toBorrow;
	
	
	// return one copy found by copy ID
	public Borrow findCopy(int copyId) {
			
		database = new DatabaseMain();
		Statement st;
		ResultSet result;		
	    Connection conn;
		        
	    try {
	    	conn = database.connectToDatabase();
	    	st = conn.createStatement();
	    	String searchQuery = "SELECT copy.copy_ID, copy.book_ID, copy.brw_ID, book.title "
	    			+ "FROM library.copy "
	    			+ "INNER JOIN library.book ON library.copy.book_ID=library.book.book_ID "
	    			+ "WHERE copy_ID="+ copyId;
	    	result = st.executeQuery(searchQuery);
					
		   	while(result.next()) {
		   		copy = new Copy();
		   		toBorrow  = new Borrow();
		   		copy.setCopyId(result.getInt("copy_ID"));
		   		copy.setBookId(result.getInt("book_ID"));
		   		copy.setBrwId(result.getInt("brw_ID"));
		   		toBorrow.setTitle(result.getString("title"));
		   		toBorrow.setCopyId(result.getInt("copy_ID"));
		   		toBorrow.setCopy(copy);
		   	}
		   	conn.close();
		    
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return toBorrow;
	}
	
	
	public Borrow calculateDate(Borrow toBorrow) {
		SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");  
	    Date fromDate = new Date();
	    LocalDate dueDate =  LocalDate.now().plusDays(30);
	    
	    toBorrow.setFromDate(form.format(fromDate));
	    toBorrow.setDueDate(dueDate.toString());
		return toBorrow;
	}
	
	
	public void borrowBooks(ArrayList<Borrow> toBorrow, String readerId){
		
		database = new DatabaseMain();	
	    Connection conn;
	    Statement st;
		ResultSet result;
		
	    String borrow = "INSERT INTO library.borrowed (fromDate, dueDate, lib_ID, reader_ID) "
	    		+ "VALUES ('" + toBorrow.get(0).getFromDate() +"', '"+toBorrow.get(0).getDueDate()+"', "
	    		+ ""+ 1 +"," + readerId + ") ";
	    
	    try {
	    	conn = database.connectToDatabase();
	    	
	    	try {
	    		conn.setAutoCommit(false);
		    	database.addToDatabase(borrow, conn);
		    	st = conn.createStatement();
		    	result = st.executeQuery("SELECT MAX(brw_ID) brw_ID FROM library.borrowed");
		    	result.next();
		    	int borrowId = result.getInt("brw_ID");
		    	
		    	for (int i = 0; i < toBorrow.size(); i++) {
		    		String alterCopy = "UPDATE library.copy "
				    		+ "SET  brw_ID = " + borrowId + ", rtn_ID = null "
				    		+ "WHERE copy_ID = " + toBorrow.get(i).getCopyId();
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
	
	
	
}
