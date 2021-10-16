package book;

import java.util.ArrayList;

import copy.Copy;
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


public class BookFindController {
	
	BookFindScene bookFindScene;
	Book book;
	Copy copy;
	Librarian librarian;
	
	String toSearch;
	ObservableList<Book> obsFoundBooks;
	
    @FXML
    private TextField txSearchBar;

    @FXML
    private Button btnSearch;
    
    @FXML
    private TableView<Book> tableView;

    @FXML
    private TableColumn<Book, String> columnTitle;

    @FXML
    private TableColumn<Book, String> columnAuthor;
    
    @FXML
    private TableColumn<Book, String> columnNumOfCopies;
    
    
    // displays all found books and their copies
    public void searchButtonClicked(ActionEvent event) throws Exception {
    	
    	toSearch = txSearchBar.getText();
    	if (toSearch.isEmpty()) {
    		return;
    	}
    	
    	bookFindScene = new BookFindScene();
    	//book = new Book();
    	//copy = new Copy();
    	
    	ArrayList<Book> foundBooks = bookFindScene.findBooks(toSearch);
    	obsFoundBooks = FXCollections.observableArrayList(foundBooks);
    	loadFoundBooks(obsFoundBooks);
    }
    
    
    
	// loads info about copies to the table
	public void loadFoundBooks(ObservableList<Book> foundBooks) {
		columnTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("Title"));
		columnAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("Author"));
		columnNumOfCopies.setCellValueFactory(new PropertyValueFactory<Book, String>("NumOfCopies"));
		
	    tableView.setItems(foundBooks);
	    //tableView.setEditable(true);
	    //colCopyStatus.setCellFactory(TextFieldTableCell.forTableColumn());
	    tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
