package copy;

public class Copy {
	
	private int copyId;
	private String status;
	private int bookId;
	private int brwId;
	private int rtnId;
	
	
	// gets copy's id
	public int getCopyId() {
		return copyId;
	}
	
	// sets copy's id
	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}
	
	// gets copy's status
	public String getStatus() {
		return status;
	}
	
	// sets copy's status
	public void setStatus(String status) {
		this.status = status;
	}
	
	// gets copy's book id
	public int getBookId() {
		return bookId;
	}
	
	// sets copy's book id
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	// gets copy's borrow id
	public int getBrwId() {
		return brwId;
	}
	
	// sets copy's borrow id
	public void setBrwId(int brwId) {
		this.brwId = brwId;
	}
	
	// gets copy's return id
	public int getRtnId() {
		return rtnId;
	}
	
	// sets copy's return id
	public void setRtnId(int rtnId) {
		this.rtnId = rtnId;
	}

}
