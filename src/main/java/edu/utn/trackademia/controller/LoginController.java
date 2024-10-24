package edu.utn.trackademia.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
import edu.utn.trackademia.Trackademia;
import edu.utn.trackademia.dao.UserDAO;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    
    @FXML
    private ImageView icon;

    //Events initialization
    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        email.setOnKeyReleased(event -> validate());
        password.setOnKeyReleased(event -> validate());

        loginButton.setOnAction(event -> menu());
    }

    //Validation of data form%+\\-]+@[
    private void validate() {
        loginButton.setDisable(!(email.getText().matches("[A-Za-z0-9\\._%+\\-]+@[A-Za-z0-9\\.\\-]+\\.[A-Za-z]{2,}") && password.getText().matches("[\\S]+")));
    }

    //If auth correct open menu as main stage
    private void menu() {
        loadingSpinner.setVisible(true);

        //Check and auth area ->  
        //Case textFields in blank or empty.
        if (email.getText().isBlank() || password.getText().isBlank()) {
            Alerts.show(AlertType.WARNING, "Warning", "Please enter your email and password to log in.");
            loadingSpinner.setVisible(false);
            return;
        }
        //Case incorrect email/password
        if (!dao.authUser(email.getText(), password.getText())) {
            Alerts.show(AlertType.ERROR, "Authentication Error", "Incorrect Email or Password");
            loadingSpinner.setVisible(false);
            return;
        }

        //If Everything is correct! :D 
        MenuController.initGui(icon);
        loadingSpinner.setVisible(false);
    }

    public static void initGui(ImageView img) {
        Parent root =null;
        try {
            root = FXMLLoader.load(Trackademia.class.getResource("/fxml/Login.fxml"));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.centerOnScreen();
        stage.setTitle("Trackademia");
        stage.getIcons().add(new Image(Trackademia.class.getResourceAsStream("/assets/icon.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
        Stage spStage = (Stage) img.getScene().getWindow();
        spStage.close();
    }
}
