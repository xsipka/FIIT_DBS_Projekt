package reservation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import book.Book;
import main.DatabaseMain;
import reader.Reader;

public class ReservationScene {
	
	DatabaseMain database;
	Reader reader;
	
	
	// checks if current book is reserved or not
	public boolean checkIfReserved(int bookId) {
		
		database = new DatabaseMain();	
	    Connection conn;
	    
	    String searchQuery = "SELECT * FROM library.reservation_book WHERE book_ID=" + bookId;
	    
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
	
	
	// add records about reservation to the tables
	public void makeReservation(ArrayList<Book> books, int readerId) {
		
		database = new DatabaseMain();	
	    Connection conn;
	    Statement st;
	    int reservationId = 0;
	    
	    SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");  
	    Date date = new Date();
	    
	    // gets reader id from getId method if we are logged as reader
	    if (readerId == 0) {
	    	reader = new Reader();
	    	readerId = reader.getId();
	    }
	    
	    // query for reservation table 
	    String resv = "INSERT INTO library.reservation (resvDate, reader_ID) VALUES ('" + form.format(date) +"', "+ readerId +")";
		
	    try {
			conn = database.connectToDatabase();
			
			try {
				conn.setAutoCommit(false);
				st = conn.createStatement();
				st.executeUpdate(resv, Statement.RETURN_GENERATED_KEYS);
				ResultSet rs = st.getGeneratedKeys();
				
				if (rs.next()){
					reservationId=rs.getInt(1);	// gets reservation id from inserted record
				}
				// inserts records into reservation_book table for every book reserved
				for (Book temp : books) {
				    String resvBook = "INSERT INTO library.reservation_book (resv_ID, book_ID) VALUES (" + reservationId +", "+ temp.getId() +")";
					database.addToDatabase(resvBook, conn);
					System.out.println("rezervujem: " + temp.getTitle());
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
