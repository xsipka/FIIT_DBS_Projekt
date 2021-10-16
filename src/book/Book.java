package book;

import java.util.ArrayList;

import copy.Copy;

public class Book {
	
	private int id;
	private String title;
	private String author;
	private String isbn;
	private ArrayList<Copy> copies;
	private int numOfCopies;
	private boolean reserved;
	
	// sets book's id	
	public void setId(int Id) {
		this.id = Id;
	}
		
	// sets book's title
	public void setTitle(String Title) {
		this.title = Title;
	}
		
	// sets book's author
	public void setAuthor(String Author) {
		this.author = Author;
	}
		
	// sets book's isbn
	public void setIsbn(String ISBN) {
		this.isbn = ISBN;
	}

	// sets book's copies
	public void setCopies(ArrayList<Copy> Copies) {
		this.copies = Copies;
	}
	
	// sets number of copies	
	public void setNumOfCopies(int numOfCopies) {
		this.numOfCopies = numOfCopies;
	}	
	
	// gets book's id
	public int getId() {
		return id;
	}
		
	// gets book's title
	public String getTitle() {
		return title;
	}
		
	// gets book's author
	public String getAuthor() {
		return author;
	}
		
	// gets book's isbn
	public String getIsbn() {
		return isbn;
	}
	
	// gets book's copies
	public ArrayList<Copy> getCopies() {
		return copies;
	}

	// gets number of copies
	public int getNumOfCopies() {
		return numOfCopies;
	}
	
	
	public boolean isReserved() {
		return reserved;
	}
	
	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
	
	
	
	
}
