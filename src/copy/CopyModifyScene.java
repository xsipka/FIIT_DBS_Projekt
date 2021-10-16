package copy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.DatabaseMain;


public class CopyModifyScene {
	
	DatabaseMain database;
	CopyFind copyFind;
	
	
	// gets us an array list of book copies
	public ArrayList<Copy> getCopies(String title, String author, String isbn) {
		
		ArrayList<Copy> foundCopies = new ArrayList<Copy>();
		
		// first we get book_ID and check if it is valid
		int bookId = getBookId(title, author, isbn);
		
		// if it's valid we can get info about copies
		if (bookId > 0) {
			copyFind = new CopyFind();
	        foundCopies = copyFind.findCopies(bookId);
		}
		// return an array list of found copies
		return foundCopies;
	}
	
	
	// gets us a book_ID of searched book
	public int getBookId(String title, String author, String isbn) {
		
		database = new DatabaseMain();
		ResultSet result;		
	    Connection conn;
	    String searchQuery = "SELECT book_ID FROM library.book WHERE title='"+title+"' AND author='"+author+"' AND isbn='"+isbn+"'";
	    int bookId;
	    
        try {
			conn = database.connectToDatabase();
			result = database.findRecords(searchQuery, conn);
			bookId = result.getInt("book_ID");
			
			conn.close();
			return bookId;
			
        } catch (Exception e) {
			e.printStackTrace();
		}
        // if the book is not found return 0
		return 0;
	}
	
	
	// saves the modified changes
	public void saveChanges(ArrayList<Copy> copies) {
		
		String copyStatus;
		int copyId;
		
		database = new DatabaseMain();
		PreparedStatement pst;		
	    Connection conn;
	    
	    try {
			conn = database.connectToDatabase();
			
			try {
				conn.setAutoCommit(false);
				
				// goes through the list of copies and updates their status
				for (int copyNum = 0; copyNum < copies.size(); copyNum++) {
		    		copyStatus = copies.get(copyNum).getStatus();
		    		copyId = copies.get(copyNum).getCopyId();
		    		String searchQuery = "UPDATE library.copy SET copyStatus=? WHERE copy_ID=" + copyId;
		    		pst = conn.prepareStatement(searchQuery);
			    	pst.setString(1, copyStatus);
			    	pst.executeUpdate();
			    }
				conn.commit();
				conn.close();
				
			} catch(SQLException e){
	    		   conn.rollback();
	    		}
			
	    } catch (Exception e) {
			e.printStackTrace();
		}	
    	
	    return;
	}
	
	
	
	
	
	
	
}
