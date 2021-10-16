package borrowing;


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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import librarian.Librarian;

public class BorrowController {

    @FXML
    private TextField txReaderId;

    @FXML
    private TextField txCopyId;

    @FXML
    private Button btnAddToTable;
    
    @FXML
    private Button btnDelete;
    
    @FXML
    private Button btnBorrow;
    
    @FXML
    private Button btnreturn;
    
    @FXML
    private TableView<Borrow> tableView;

    @FXML
    private TableColumn<Borrow, String> columnCopyId;

    @FXML
    private TableColumn<Borrow, String> columnTitle;

    @FXML
    private TableColumn<Borrow, String> columnFromDate;

    @FXML
    private TableColumn<Borrow, String> columnDueDate;

    
    BorrowScene borrowScene;
    Borrow oneItem;
    Librarian librarian;
    
    // list of books that will appear in the table
    ObservableList<Borrow> toBorrow = FXCollections.observableArrayList();
    
    
    // adds a copy to the borrow tableview
    public void addToTableButtonClicked(ActionEvent event) throws Exception {
    	
    	String copyId = txCopyId.getText();
    	
    	if (copyId.isEmpty()) {
    		return;
    	}
    	
    	borrowScene = new BorrowScene();
    	oneItem = new Borrow();
    	oneItem = borrowScene.findCopy(Integer.parseInt(copyId));
    	
    	// first checks if we found a copy and if it is borrowed
    	if (oneItem == null ||  oneItem.getCopy().getBrwId() > 0) {
    		JOptionPane.showMessageDialog(null, "Book is currently borrowed or doesn't exist...");
    		txCopyId.setText("");
    		return;
    	}
    	else {
    		oneItem = borrowScene.calculateDate(oneItem);
    		toBorrow.add(oneItem);
    		loadTable(toBorrow);
    		txCopyId.setText("");
    	}
    }
    
    
    // loads books into table
    public void loadTable(ObservableList<Borrow> toBorrow) {
 		columnTitle.setCellValueFactory(new PropertyValueFactory<Borrow, String>("title"));
 		columnCopyId.setCellValueFactory(new PropertyValueFactory<Borrow, String>("copyId"));
 		columnFromDate.setCellValueFactory(new PropertyValueFactory<Borrow, String>("fromDate"));
 		columnDueDate.setCellValueFactory(new PropertyValueFactory<Borrow, String>("dueDate"));
 		
 		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
 	    tableView.setItems(toBorrow);
 	}
    
    
    // deletes selected rows from table and database
    public void deleteButtonClicked(ActionEvent event) throws Exception {
    	ArrayList<Borrow> toDelete = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());
    	toDelete.forEach(row -> tableView.getItems().remove(row));
    }
    
    
    // borrows all books in the table
    public void borrowButtonClicked(ActionEvent event) throws Exception {
    	String readerId = txReaderId.getText();
    	
    	if (readerId.isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Invalid reader ID");
    		return;
    	}
    	
    	ArrayList<Borrow> toBorrow = new ArrayList<>(tableView.getItems());
    	borrowScene = new BorrowScene();
    	borrowScene.borrowBooks(toBorrow, readerId);
    	txReaderId.setText("");
    	tableView.getItems().clear();
    	JOptionPane.showMessageDialog(null, "Books were successfully borrowed.");
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
