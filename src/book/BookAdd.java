package book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.DatabaseMain;

public class BookAdd {
	
	DatabaseMain database;
	
	
	// Adds a book and a copy record to the database
	public void addCopyAndBookRecord(String title, String author, String isbn, String copyStatus) {
			
		database = new DatabaseMain();
		Connection conn;
		Statement st;
		ResultSet result;
		
		String newBook = "INSERT INTO library.book (title, author, isbn) VALUES ('" + title +"', '"+author+"', '"+ isbn +"')";
		
		try {
			conn = database.connectToDatabase();
			
			try {
				conn.setAutoCommit(false);
				database.addToDatabase(newBook, conn);	// adds a book record
				
				st = conn.createStatement();
		    	result = st.executeQuery("SELECT MAX(book_ID) book_ID FROM library.book");
		    	result.next();
		    	int bookId = result.getInt("book_ID");	// gets the book ID of added book
		    	
		    	String newCopy = "INSERT INTO library.copy (copyStatus, book_ID) VALUES ('" + copyStatus + "'," + bookId + ")";
	    		database.addToDatabase(newCopy, conn);	// insert copy record of added book
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
