package copy;

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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import librarian.Librarian;


public class CopyModifyController {

    @FXML
    private TextField txTitle;

    @FXML
    private TextField txAuthor;

    @FXML
    private TextField txIsbn;

    @FXML
    private Button btnSearch;
    
    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;
    
    @FXML
    private TableView<Copy> tableView;

    @FXML
    private TableColumn<Copy, String> colCopyId;

    @FXML
    private TableColumn<Copy, String> colCopyStatus;
    
    CopyDelete copyDelete;
    CopyModifyScene copyModifyScene;
    Librarian librarian;
    ObservableList<Copy> obsFoundCopies;
    
    
    // finds and displays book copies we wish to modify
    public void searchButtonClicked(ActionEvent event) throws Exception {
    	
    	ArrayList<Copy> foundCopies;
    	
    	String title = txTitle.getText();
    	String author = txAuthor.getText();
    	String isbn = txIsbn.getText();
    	
    	copyModifyScene = new CopyModifyScene();
    	foundCopies = copyModifyScene.getCopies(title, author, isbn);
    	
    	obsFoundCopies = FXCollections.observableArrayList(foundCopies);
    	loadTableData(obsFoundCopies);
    }
    
    
    // deletes selected rows from table and database
    public void deleteButtonClicked(ActionEvent event) throws Exception {
    	
    	// saves selected rows to an array list
    	ArrayList<Copy> toDelete = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());
    	
    	// deletes from database
    	copyDelete = new CopyDelete();
    	copyDelete.deleteSelected(toDelete);
    	
    	// deletes from table
    	toDelete.forEach(row -> tableView.getItems().remove(row));
    	JOptionPane.showMessageDialog(null, "Copies were successfully removed.");
    }
    
    
    // saves made changes to database
    public void saveButtonClicked(ActionEvent event) throws Exception {
    	
    	// saves all rows to an array list
    	ArrayList<Copy> toSave = new ArrayList<>(tableView.getItems());
    	
    	// saves all changes to database
    	copyModifyScene = new CopyModifyScene();
    	copyModifyScene.saveChanges(toSave);
    	JOptionPane.showMessageDialog(null, "Copies were successfully modified.");
    }
    
    
    // changes status of a selected copy
    public void changeCopyStatus(@SuppressWarnings("rawtypes") CellEditEvent edittedCell) {
    	Copy selected =  tableView.getSelectionModel().getSelectedItem();
        selected.setStatus(edittedCell.getNewValue().toString());
    }
    
    
    // loads info about copies to the table
	public void loadTableData(ObservableList<Copy> copies) {
		colCopyId.setCellValueFactory(new PropertyValueFactory<Copy, String>("copyId"));
		colCopyStatus.setCellValueFactory(new PropertyValueFactory<Copy, String>("status"));
        
        tableView.setItems(copies);
        tableView.setEditable(true);
        colCopyStatus.setCellFactory(TextFieldTableCell.forTableColumn());
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