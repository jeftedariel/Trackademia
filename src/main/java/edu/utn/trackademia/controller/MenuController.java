/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.trackademia.controller;

import edu.utn.trackademia.dao.RoleDAO;
import edu.utn.trackademia.entities.UserSession;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author jefte
 */
public class MenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    RoleDAO rdao = new RoleDAO(); 
    
    
    @FXML
    private ImageView logout;
    
    @FXML
    private Label username;

    @FXML
    private TitledPane educational;
    
    @FXML
    private TitledPane administration;
    
    @FXML
    private MFXButton abrirMatricula;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logout.setOnMouseClicked(event -> {
            logout();
        });
        
        abrirMatricula.setOnAction(event -> openAcademicOffer());
        
        this.username.setText(UserSession.getInstance().getUserFullName());
        
        educational.setCollapsible(false);
        administration.setCollapsible(false);
        
        //Sets visible both panels (Administration & Educational) if permissions 'Show Educational' or 'Show Administration' are present
        educational.setVisible(rdao.getPermissions(UserSession.getInstance().getRole()).stream().anyMatch(n->n.name().equals("Show Educational")));
        administration.setVisible(rdao.getPermissions(UserSession.getInstance().getRole()).stream().anyMatch(n->n.name().equals("Show Administration")));
        
    }
    
    private void openAcademicOffer() {
        try {
            // Load the AcademicOffer.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/AcademicOffer.fxml"));
            
            // Create a new Stage for the Academic Offer window
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Academic Offer");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png"))); // Optional: Set the window icon
            stage.initStyle(StageStyle.DECORATED); // Use a decorated window style
            stage.show();

            // Optionally close the current window if desired
            Stage currentStage = (Stage) abrirMatricula.getScene().getWindow();
            currentStage.close(); // Uncomment this line if you want to close the current window
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception if loading fails
        }
    }

    public void logout() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.centerOnScreen();
        stage.setTitle("Trackademia");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
        Stage spStage = (Stage) logout.getScene().getWindow();
        spStage.close();
    }
}

