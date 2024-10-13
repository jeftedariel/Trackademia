package edu.utn.trackademia.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

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
    
    @FXML
    private Button loginButton;
    
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        loginButton.setOnAction(event -> menu());
    }    
    
    
    
    private void menu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace(); // Manejar la excepción de carga de FXML
        }
    }
    
}
