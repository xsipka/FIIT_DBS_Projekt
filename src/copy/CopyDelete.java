package copy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import book.BookDelete;
import main.DatabaseMain;

public class CopyDelete {
	
	DatabaseMain database;
	BookDelete bookDelete;
	
	// deletes selected books from the database
	public void deleteSelected(ArrayList<Copy> toDelete) {
		
		int copyId;
		int bookId = 0;
		
		database = new DatabaseMain();
		Statement st;		
	    Connection conn;
	    
	    try {
			conn = database.connectToDatabase();
			
			try {
				conn.setAutoCommit(false);
				st = conn.createStatement();
				
				// goes through the list of copies and deletes them
				for (int copyNum = 0; copyNum < toDelete.size(); copyNum++) {
		    		copyId = toDelete.get(copyNum).getCopyId();
		    		bookId = toDelete.get(copyNum).getBookId();
		    		String searchQuery = "DELETE FROM library.copy WHERE copy_ID=" + copyId;
			    	st.executeUpdate(searchQuery);
			    }
				/* Check if book has any other existing copies.
			       If not, then also deletes the book record. */
			    if (checkOtherCopies(bookId, conn) == false) {
			    	bookDelete = new BookDelete();
			    	bookDelete.deleteBookRecord(bookId, conn);
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
	
	
	// Checks if there are other copies of deleted book
	public boolean checkOtherCopies(int bookId, Connection conn) {
		database = new DatabaseMain();
		PreparedStatement pst = null;
		ResultSet result;		
	    //Connection conn;
        
        try {
			//conn = database.connectToDatabase();
	    	String searchQuery = "SELECT * FROM library.copy WHERE book_ID=" + bookId;
	    	pst = conn.prepareStatement(searchQuery);
			result = pst.executeQuery();
			
			if (result.next() == true) {
				return true;
			}
			//conn.close();
			
        } catch (Exception e) {
			e.printStackTrace();
		}
        // if there are no other copies found return false
        return false;
	}
	
	
	
	
}
