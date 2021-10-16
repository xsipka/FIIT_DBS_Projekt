package returning;

import java.util.ArrayList;

import javax.swing.JOptionPane;

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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import librarian.Librarian;

public class ReturnController {

    @FXML
    private Button btnShowBorrowed;

    @FXML
    private Button btnReturnBooks;

    @FXML
    private TextField txReaderId;
    
    @FXML
    private TextField txReturnDate;

    @FXML
    private TextField txFine;
    
    @FXML
    private TextField txProlongedDate;
    
    @FXML
    private Button btnProlongBooks;

    @FXML
    private Button btnReturnHome;

    @FXML
    private TableView<Return> tableView;

    @FXML
    private TableColumn<Return, String> columnBrwId;

    @FXML
    private TableColumn<Return, String> columnTitle;

    @FXML
    private TableColumn<Return, String> columnFromDate;

    @FXML
    private TableColumn<Return, String> columnDueDate;

    @FXML
    private TableColumn<Return, String> columnStatus;
    
    Librarian librarian;
    ReturnScene returnScene;
    
    // list of currently borrowed books that will appear in the table
    ObservableList<Return> obsBorrowed = FXCollections.observableArrayList();
    
    
    // loads and displays borrowed books into tableview
    public void showBorrowedButtonClicked(ActionEvent event) throws Exception {
    	String readerId = txReaderId.getText();
    	ArrayList<Return> borrowed;
    	
    	if (readerId.isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Please enter valid reader ID.");
    		return;
    	}
    	returnScene = new ReturnScene();
    	borrowed = returnScene.foundBorrowed(readerId);
    	obsBorrowed = FXCollections.observableArrayList(borrowed);
    	loadTableData(obsBorrowed);
    }
    
    
    public void loadTableData(ObservableList<Return> toLoad) {
    	columnTitle.setCellValueFactory(new PropertyValueFactory<Return, String>("title"));
    	columnBrwId.setCellValueFactory(new PropertyValueFactory<Return, String>("borrowId"));
 		columnFromDate.setCellValueFactory(new PropertyValueFactory<Return, String>("fromDate"));
 		columnDueDate.setCellValueFactory(new PropertyValueFactory<Return, String>("dueDate"));
 		columnStatus.setCellValueFactory(new PropertyValueFactory<Return, String>("Status"));
 		
 		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
 	    tableView.setItems(toLoad);
 	    tableView.setEditable(true);
 	    columnStatus.setCellFactory(TextFieldTableCell.forTableColumn());	// editable status copy column
 	    tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    
    
    // changes status of a selected copy
    public void changeCopyStatus(@SuppressWarnings("rawtypes") CellEditEvent edittedCell) {
    	Return selected =  tableView.getSelectionModel().getSelectedItem();
        selected.setStatus(edittedCell.getNewValue().toString());
    }
    
    
    // gets selected books from the table and prolongs their borrow time (and removes them from the table)
    public void prolongButtonClicked(ActionEvent event) throws Exception {  
    	String newDueDate = txProlongedDate.getText();
    	ArrayList<Return> toProlong = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());
    	returnScene = new ReturnScene();
    	returnScene.prolongBooks(toProlong, newDueDate);
    	JOptionPane.showMessageDialog(null, "Books were successfully prolonged.");
    	toProlong.forEach(row -> tableView.getItems().remove(row));
    }
    
    
    // creates return record for selected books
    public void returnBooksButtonClicked(ActionEvent event) throws Exception {
    	String returnDate = txReturnDate.getText();
    	String fine = txFine.getText();
    	
    	ArrayList<Return> toReturn= new ArrayList<>(tableView.getSelectionModel().getSelectedItems());
    	returnScene = new ReturnScene();
    	returnScene.returnBooks(toReturn, fine, returnDate);
    	JOptionPane.showMessageDialog(null, "Books were successfully returned.");
    	toReturn.forEach(row -> tableView.getItems().remove(row));
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

