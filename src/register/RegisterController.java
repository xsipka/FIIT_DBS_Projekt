package register;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
//import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController {
	
	// used for registration
	String firstName;
	String lastName;
	String password;
	String email;
	//String gender;
	//String phoneNum;
	
	RegisterScene registerScene;
	
    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private PasswordField passwordTextField;

    //@FXML
    //private RadioButton rbMale;

    //@FXML
    //private RadioButton rbFemale;

    //@FXML
    //private RadioButton rbOther;

    @FXML
    private TextField emailTextField;

    //@FXML
    //private TextField txPhoneNum;

    @FXML
    private Button registerButton;
    
    @FXML
    private Button loginButton;

    //@FXML
    //private TextField txEmailLog;

    //@FXML
    //private PasswordField txPasswdLog;

    //@FXML
    //private Button btnReaderLog;
    
    
    
    // assigns selected gender of  a new user
    /*public void assignGender(ActionEvent event) {
    	
    	if (rbMale.isSelected())
    		gender = "Male";
    	if (rbFemale.isSelected())
    		gender = "Female";
    	if (rbOther.isSelected())
    		gender = "Other";
    }*/
    
    
    // registers new reader
    public void registerButtonClicked(ActionEvent event) throws Exception {
    	
    	firstName = firstNameTextField.getText();
    	lastName = lastNameTextField.getText();
    	password = passwordTextField.getText();
    	email = emailTextField.getText();
    	//phoneNum = txPhoneNum.getText();
    	
    	registerScene = new RegisterScene();
    	
    	if (registerScene.checkRegData(firstName, lastName, email, password) == true) {
    		registerScene.addNewReader(firstName, lastName, email, password);
    		System.out.println("registracia uspesna...");
    		switchToHomescreen(event);
    	}
    }
    
    
    // switches to homescreen after successfull registration
    public void switchToHomescreen(ActionEvent event) throws IOException {
    	Parent welcomeScene = FXMLLoader.load(getClass().getResource("/home/Home.fxml"));
		Scene readerReg = new Scene(welcomeScene);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	
		window.setScene(readerReg);
		window.show();
    }
    
    
    public void loginButtonClicked(ActionEvent event) throws IOException {
    	Parent welcomeScene = FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
		Scene libReg = new Scene(welcomeScene);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(libReg);
		window.show();
    }
    
}
