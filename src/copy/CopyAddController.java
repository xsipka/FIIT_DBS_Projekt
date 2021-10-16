package copy;

import javax.swing.JOptionPane;

import home.HomeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import librarian.Librarian;

public class CopyAddController {

    @FXML
    private TextField txTitle;

    @FXML
    private TextField txAuthor;
    
    @FXML
    private TextField txIsbn;

    @FXML
    private TextField txCopyStatus;

    @FXML
    private Button btnAdd;
    
    CopyAddScene copyAddScene;
    Librarian librarian;
    
    // adds a book copy to the database
    public void addButtonClicked(ActionEvent event) throws Exception {
    	
    	String title = txTitle.getText();
    	String author = txAuthor.getText();
    	String isbn = txIsbn.getText();
    	String copyStatus = txCopyStatus.getText();
    	
    	copyAddScene = new CopyAddScene();
    	copyAddScene.addCopy(title, author, isbn, copyStatus);
    	System.out.println("pridana kniha " + title);
    	JOptionPane.showMessageDialog(null, title + " was added to the database.");
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
