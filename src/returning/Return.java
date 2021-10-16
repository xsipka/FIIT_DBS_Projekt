package returning;

import copy.Copy;

public class Return {
	
	private int borrowId;
	private int returnId;
	private int readerId;
	private String title;
	private String status;
	private String fromDate;
	private String dueDate;
	private String fine;
	private boolean inTime;
	private Copy copy;
	
	
	public int getBorrowId() {
		return borrowId;
	}
	
	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
	}

	public int getReturnId() {
		return returnId;
	}

	public void setReturnId(int returnId) {
		this.returnId = returnId;
	}

	public int getReaderId() {
		return readerId;
	}

	public void setReaderId(int readerId) {
		this.readerId = readerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getFine() {
		return fine;
	}

	public void setFine(String fine) {
		this.fine = fine;
	}

	public boolean isInTime() {
		return inTime;
	}

	public void setInTime(boolean inTime) {
		this.inTime = inTime;
	}

	public Copy getCopy() {
		return copy;
	}

	public void setCopy(Copy copy) {
		this.copy = copy;
	}

}
