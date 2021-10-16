package copy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import book.Book;
import book.BookAdd;
import main.DatabaseMain;

public class CopyAddScene {
	
	DatabaseMain database;
	BookAdd bookAdd;
	Book book;
	Copy copy;
	
	
	// adds book copy to database
	public void addCopy(String title, String author, String isbn, String copyStatus) {
		
	    /* Checks if added book already exists. If yes, then add only a copy record.
	  	If no, then add book record and copy record. */
	    if (checkIfBookExists(title, author, isbn) == true) {
	    	addCopyRecord(isbn, copyStatus);
	    	//System.out.println("existuje");
	    }
	    else {
	    	bookAdd = new BookAdd();
	    	bookAdd.addCopyAndBookRecord(title, author, isbn, copyStatus);
	    	//addCopyRecord(isbn, copyStatus);
	    	//System.out.println("neexistuje");
	    }
		return;
	}
	
	
	// Checks if added book already exists. If yes, return true, else return false.
	public boolean checkIfBookExists(String title, String author, String isbn) {
		
		database = new DatabaseMain();	
	    Connection conn;
	    String searchQuery = "SELECT * FROM library.book WHERE title='"+title+"' AND author='"+author+"' AND isbn='"+isbn+"'";
    	
        try {
			conn = database.connectToDatabase();
	    	if (database.checkIfExists(searchQuery, conn) == true) {
				conn.close();
				return true;
			}
			conn.close();
			
        } catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	// Adds a copy record to the database
	public void addCopyRecord(String isbn, String copyStatus) {
		
		database = new DatabaseMain();
		Statement st;
		ResultSet result;
		
		String searchQuery = "SELECT * FROM library.book WHERE isbn=" + isbn;
		
		/* Finds a book record in book table from which we get book_ID.
		   Then insert new copy record to copy table. */
		try {
			Connection conn = database.connectToDatabase();
			
			try {
				conn.setAutoCommit(false);
				st = conn.createStatement();
		    	result = st.executeQuery(searchQuery);
		    	
		    	if (result.next() == true) { 
		    		String newCopy = "INSERT INTO library.copy (copyStatus, book_ID) VALUES ('"+copyStatus+"'," +result.getInt("book_ID")+")";
		    		database.addToDatabase(newCopy, conn);
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





