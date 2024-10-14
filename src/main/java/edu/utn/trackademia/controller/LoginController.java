package edu.utn.trackademia.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
import edu.utn.trackademia.dao.UserDAO;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jefte
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    UserDAO dao = new UserDAO();

    //FXML Components 
    @FXML
    private Button loginButton;
    @FXML
    private MFXProgressSpinner loadingSpinner;
    @FXML
    private MFXTextField email;
    @FXML
    private MFXPasswordField password;

    
    //Events initialization
    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        email.setOnKeyReleased(event -> validate());
        password.setOnKeyReleased(event -> validate());

        loginButton.setOnAction(event -> menu());
    }

    
    //Validation of data form
    private void validate() {
        loginButton.setDisable(!(email.getText().matches("[A-Za-z0-9\\._%+\\-]+@[A-Za-z0-9\\.\\-]+\\.[A-Za-z]{2,}") && password.getText().matches("[\\S]+")));
    }
    
    //If auth correct open menu as main stage
    private void menu() {
        loadingSpinner.setVisible(true);
        if (dao.authUser(email.getText(), password.getText())) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
                Scene scene = new Scene(loader.load());

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace(); 
            } finally{
                loadingSpinner.setVisible(false);
            }
        }
        loadingSpinner.setVisible(false);
    }

}
