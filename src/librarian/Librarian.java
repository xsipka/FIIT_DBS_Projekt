package librarian;

public class Librarian {
	
	static private boolean loggedIn;

	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	public void setLoggedIn(boolean loggedIn) {
		Librarian.loggedIn = loggedIn;
	}
	
}
