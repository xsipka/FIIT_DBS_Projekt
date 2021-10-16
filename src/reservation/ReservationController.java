package reservation;

import java.util.ArrayList;

import book.Book;
import book.BookFindScene;
import home.HomeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import librarian.Librarian;


public class ReservationController {
	
    @FXML
    private TextField txTitle;

    @FXML
    private TextField txAuthor;
    
    @FXML
    private TextField txReaderId;
    
    @FXML
    private Button btnSearch;
    
    @FXML
    private Button btnReturn;

    @FXML
    private TableView<Book> tableView;

    @FXML
    private TableColumn<Book, String> columnTitle;

    @FXML
    private TableColumn<Book, String> columnAuthor;

    @FXML
    private TableColumn<Book, String> columnReserved;
    
    @FXML
    private Button btnMakeReservation;
    
    // list of books that will appear in the table
    ObservableList<Book> obsFoundBooks = FXCollections.observableArrayList();;
    
    BookFindScene bookFind;
    ReservationScene reservation;
    Librarian librarian;
    
    // displays all found books in the table. New elements are added everytime we press the button.
    public void searchButtonClicked(ActionEvent event) throws Exception {
    	
    	String title = txTitle.getText();
    	String author = txAuthor.getText();
    	ArrayList<Book> foundBooks;
    	bookFind = new BookFindScene();
    	
    	if (title.isEmpty() && author.isEmpty()) {
    		return;
    	}
    	
    	// find books, either by aothor's name or title
    	if (title.isEmpty()) {
    		foundBooks = bookFind.findBooks(author);
    	}
    	else {
    		foundBooks = bookFind.findBooks(title);
    	}
    	
    	// adds all found elements to the observable list
    	for (Book temp : foundBooks){
    		obsFoundBooks.add(temp);
    	}
    	
    	// loads table with found data
    	loadFoundBooks(obsFoundBooks);
    }
    
    
    // creates a reservation for selected books
    public void makeReservationButtonClicked(ActionEvent event) throws Exception {
    	
    	// if we are logged as employee we get reader id from text fiels
    	// otherwise we get reader id through get method
    	int readerId = 0;
    	String strReaderId = txReaderId.getText();
    	
    	if (!strReaderId.isEmpty()) {
    		readerId = Integer.parseInt(strReaderId);	
    	}
    	
    	// saves all selected rows to an array list
    	ArrayList<Book> books = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());
    	
    	reservation = new ReservationScene();
    	reservation.makeReservation(books, readerId);
    	clearScene();
    }
    
    
    // loads info about found books to the table
 	public void loadFoundBooks(ObservableList<Book> foundBooks) {
 		columnTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
 		columnAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
 		columnReserved.setCellValueFactory(new PropertyValueFactory<Book, String>("reserved"));
 		
 	    tableView.setItems(foundBooks);
 	    tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
 	}
 	
 	
 	// nullifies text fields, table and observable list
 	public void clearScene() {
 		
 		txTitle.setText("");
 		txAuthor.setText("");
 		txReaderId.setText("");
 		tableView.getItems().clear();
 	}
 	
 	
 	public void loggedAsEmployee() {
 		txReaderId.setDisable(false);
 	}
 	
 	
 	public void returnHomeButtonClicked(ActionEvent event) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/Home.fxml"));
        Parent newScene = loader.load();
        Scene copyAdd = new Scene(newScene);
         
        librarian = new Librarian();
        if (librarian.isLoggedIn()) {
        	HomeController newSceneController = loader.getController();
        	newSceneController.employeeLogin();
        }

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(copyAdd);
		window.show();
    }
}

