package statistics;


import java.util.ArrayList;

import javax.swing.JOptionPane;

import borrowing.Borrow;
import borrowing.BorrowScene;
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

public class StatisticsController {

    @FXML
    private Button finesButton;
    
    @FXML
    private Button borrowedButton;
    
    @FXML
    private Button returnedButton;
    
    @FXML
    private TableView<Statistics> tableView;

    @FXML
    private TableColumn<Statistics, String> columnEmail;

    @FXML
    private TableColumn<Statistics, String> columnValue;

    
    StatisticsScene statisticsScene;
    Librarian librarian;
    
    public void finesButtonClicked(ActionEvent event) throws Exception {
    	statisticsScene = new StatisticsScene();
    	ObservableList<Statistics> users = FXCollections.observableArrayList(statisticsScene.SumAllUsersFines());
    	loadTable(users);
    }
    
    public void borrowedButtonClicked(ActionEvent event) throws Exception {
    	statisticsScene = new StatisticsScene();
    	ObservableList<Statistics> users = FXCollections.observableArrayList(statisticsScene.FindUsersWithManyBooks());
    	loadTable(users);
    }
    
    public void returnedButtonClicked(ActionEvent event) throws Exception {
    	statisticsScene = new StatisticsScene();
    	ObservableList<Statistics> users = FXCollections.observableArrayList(statisticsScene.FindUsersWithReturnedBooks());
    	loadTable(users);
    }
    
    
    // loads values into table
    public void loadTable(ObservableList<Statistics> users) {
    	columnEmail.setCellValueFactory(new PropertyValueFactory<Statistics, String>("email"));
    	columnValue.setCellValueFactory(new PropertyValueFactory<Statistics, String>("value"));
 		
 		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
 	    tableView.setItems(users);
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
