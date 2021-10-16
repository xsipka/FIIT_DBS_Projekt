package borrowing;

//import java.util.ArrayList;

import copy.Copy;

public class Borrow {
	
	//private ArrayList <String> titles;
	//private ArrayList <Copy> copies;
	private Copy copy;
	private int readerId;
	private int borrowId;
	private int copyId;
	private String fromDate;
	private String dueDate;
	private String title;
	
	
	/*public ArrayList <Copy> getCopies() {
		return copies;
	}
	
	public void setCopies(ArrayList <Copy> copies) {
		this.copies = copies;
	}*/
	
	public int getReaderId() {
		return readerId;
	}
	
	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}
	
	public int getBorrowId() {
		return borrowId;
	}
	
	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
	}
	
	public String getFromDate() {
		return fromDate;
	}
	
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
	public String getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Copy getCopy() {
		return copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}

	/*public ArrayList <String> getTitles() {
		return titles;
	}

	public void setTitles(ArrayList <String> titles) {
		this.titles = titles;
	}*/
	
	public int getCopyId() {
		return copyId;
	}

	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}
	
	
}
