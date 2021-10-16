package book;

import java.sql.Connection;
import java.sql.Statement;

import main.DatabaseMain;

public class BookDelete {
	
	DatabaseMain database;
	
	
	// deletes a book record from database
	public void deleteBookRecord(int bookId, Connection conn) {
		
		database = new DatabaseMain();
		Statement st;		
	    //Connection conn;
	    
	    try {
			//conn = database.connectToDatabase();
			st = conn.createStatement();
			String searchQuery = "DELETE FROM library.book WHERE book_ID=" + bookId;
		    st.executeUpdate(searchQuery);
		    //conn.close();
			
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}

}
