package login;

import java.io.IOException;

import home.HomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import librarian.Librarian;
//import register.RegisterScene;


public class LoginController {

	LoginScene loginScene;
	Librarian librarian;
	
	String email;
	String password;
	
	@FXML
	private TextField emailTextField;
	
	@FXML
	private PasswordField passwordTextField;
	
    @FXML
    private Button loginButton;

    @FXML
    private Button createAccountButton;
    
    @FXML
    private Button loginAsEmployeeButton;
    
    
    // Checks information and tries to log in user
    public void loginButtonClicked(ActionEvent event) throws Exception {
    	
    	email = emailTextField.getText();
    	password = passwordTextField.getText();
    	
    	loginScene = new LoginScene();
    	
    	if (loginScene.readerLogIn(email,password) == true) {
    		Parent welcomeScene = FXMLLoader.load(getClass().getResource("/home/Home.fxml"));
    		Scene readerHome = new Scene(welcomeScene);
    		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

    		window.setScene(readerHome);
    		window.show();
    	} else {
    		passwordTextField.setText("");
    	}
    	
    	
    }
    
    // Transports user to registration screen
    public void createAccountButtonClicked(ActionEvent event) throws IOException {
    	
    	Parent welcomeScene = FXMLLoader.load(getClass().getResource("/register/Register.fxml"));
		Scene readerReg = new Scene(welcomeScene);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(readerReg);
		window.show();
    }
    
    // Logs in user as an employee
    public void loginAsEmployeeButtonClicked(ActionEvent event) throws IOException {
    	
    	/*Parent welcomeScene = FXMLLoader.load(getClass().getResource("/copy/CopyAdd.fxml"));
		Scene copyAdd = new Scene(welcomeScene);*/
		
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/home/Home.fxml"));
        Parent newScene = loader.load();
        Scene copyAdd = new Scene(newScene);
        
        librarian = new Librarian();
        librarian.setLoggedIn(true);
        
        HomeController newSceneController = loader.getController();
        newSceneController.employeeLogin();

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(copyAdd);
		window.show();
    }
    
}

