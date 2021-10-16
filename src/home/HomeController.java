package home;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import librarian.Librarian;
import reader.Reader;
import reservation.ReservationController;

public class HomeController {
	
	Reader reader;
	Librarian librarian;
	
	@FXML
	private Label hello;
	
	@FXML
	private Button addBookButton;
	
	@FXML
	private Button modifyBookButton;
	
	@FXML
	private Button borrowButton;
	
	@FXML
	private Button returnBooksButton;
	
    @FXML
    private Button reservationButton;
    
    @FXML
    private Button statisticsButton;
	
	
	// Transports user to book search screen
	public void findBookButtonClicked(ActionEvent event) throws IOException {
    	
    	Parent newScene = FXMLLoader.load(getClass().getResource("/book/BookFind.fxml"));
		Scene bookSearch = new Scene(newScene);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(bookSearch);
		window.show();
    }
	
	public void addBookButtonClicked(ActionEvent event) throws IOException {
    	
		Parent newScene = FXMLLoader.load(getClass().getResource("/copy/CopyAdd.fxml"));
		Scene bookAdd = new Scene(newScene);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(bookAdd);
		window.show();
    }
	
	public void modifyBookButtonClicked(ActionEvent event) throws IOException {
    	
		Parent newScene = FXMLLoader.load(getClass().getResource("/copy/CopyModify.fxml"));
		Scene bookModify = new Scene(newScene);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(bookModify);
		window.show();
    }
	
	public void borrowButtonClicked(ActionEvent event) throws IOException {
    	
		Parent newScene = FXMLLoader.load(getClass().getResource("/borrowing/Borrow.fxml"));
		Scene bookBorrow = new Scene(newScene);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(bookBorrow);
		window.show();
    }
	
	public void returnBooksButtonClicked(ActionEvent event) throws IOException {
    	
		Parent newScene = FXMLLoader.load(getClass().getResource("/returning/Return.fxml"));
		Scene bookBorrow = new Scene(newScene);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(bookBorrow);
		window.show();
    }

	public void reservationButtonClicked(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservation/Reservation.fxml"));
        Parent newScene = loader.load();
        Scene reservation = new Scene(newScene);
		
        reader = new Reader();
        
		if (reader.getId() == 0) {        
	        ReservationController newSceneController = loader.getController();
	        newSceneController.loggedAsEmployee();
		}
		
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(reservation);
		window.show();
    }
	
	public void statisticsButtonClicked(ActionEvent event) throws IOException {
    	
		Parent newScene = FXMLLoader.load(getClass().getResource("/statistics/Statistics.fxml"));
		Scene bookBorrow = new Scene(newScene);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(bookBorrow);
		window.show();
    }
	
	
	public void logoutButtonClicked(ActionEvent event) throws IOException {
    	
		Parent newScene = FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
		Scene bookSearch = new Scene(newScene);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		librarian = new Librarian();
        librarian.setLoggedIn(false);
        
		window.setScene(bookSearch);
		window.show();
    }
	
	public void employeeLogin() {
		hello.setText("Hello employee");
		addBookButton.setDisable(false);
		modifyBookButton.setDisable(false);
		borrowButton.setDisable(false);
		returnBooksButton.setDisable(false);
		statisticsButton.setDisable(false);
	}
	
}
