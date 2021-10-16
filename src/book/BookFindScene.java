package book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import copy.Copy;
import copy.CopyFind;
import main.DatabaseMain;
import reservation.ReservationScene;


public class BookFindScene {
	
	DatabaseMain database;
	CopyFind copyFind;
	ReservationScene reserved;
	Book book;
	Copy copy;
	
	// saves all found books into an array list
	public ArrayList<Book> findBooks(String toSearch) {
		
		ArrayList<Book> booksList = new ArrayList<Book>();
		
		database = new DatabaseMain();
		Statement st;
        ResultSet result;
		
		try {
			Connection conn = database.connectToDatabase();
			st = conn.createStatement();
            String searchQuery = "SELECT * FROM library.book WHERE CONCAT(title, author, isbn) LIKE '%"+ toSearch + "%'";
            result = st.executeQuery(searchQuery);
            
            while(result.next()) {
            	
                // adds a new book to the array list
            	book = new Book();
                book.setId(result.getInt("book_ID"));
                book.setTitle(result.getString("title"));
                book.setAuthor(result.getString("author"));
                book.setIsbn(result.getString("isbn"));
                
                copyFind = new CopyFind();
                ArrayList<Copy> foundCopies = copyFind.findCopies(book.getId());
                book.setCopies(foundCopies);
                book.setNumOfCopies(foundCopies.size());
                booksList.add(book);
                
                reserved = new ReservationScene();
                book.setReserved(reserved.checkIfReserved(result.getInt("book_ID")));
            }
            conn.close();
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		// returns array list with data about all found books and their copies
		return booksList;	
	}
	
	
		
	// checks if copy is currently borrowed
	public boolean isBorrowed(int brwId) {
		if (brwId > 0) {
			return true;
		}
		else {
			return false;
		}
	}
}
