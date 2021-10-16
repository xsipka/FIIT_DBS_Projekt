package copy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import main.DatabaseMain;

public class CopyFind {
	
	DatabaseMain database;
	Copy copy;
	
	
	// returns array list with info about all copies of current book
	public ArrayList<Copy> findCopies(int bookId) {
			
		ArrayList<Copy> copiesList = new ArrayList<Copy>();
			
		database = new DatabaseMain();
		Statement st;
	    ResultSet result;		
	    Connection conn;
	        
	    try {
	    	conn = database.connectToDatabase();
	    	st = conn.createStatement();
	    	String searchQuery = "SELECT * FROM library.copy WHERE book_ID=" + bookId;
	    	result = st.executeQuery(searchQuery);
				
	    	while(result.next()) {
	    		copy = new Copy();
	    		copy.setCopyId(result.getInt("copy_ID"));
	    		copy.setBookId(result.getInt("book_ID"));
	    		copy.setBrwId(result.getInt("brw_ID"));
	    		copy.setRtnId(result.getInt("rtn_ID"));
	    		copy.setStatus(result.getString("copyStatus"));
	    		copiesList.add(copy);
	           }
	    	conn.close();
	    
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }			
	    return copiesList;
	}
	
	
}
