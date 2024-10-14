/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.trackademia.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jefte
 */
public class SplashController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ImageView img;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new SplashScreen().start();

    }

    class SplashScreen extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
                        } catch (IOException e) {
                            System.out.println("Error: "+ e);
                        }

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Trackademia");
                        stage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icon.png")));
                        stage.setScene(scene);
                        stage.setResizable(false);
                        
                        stage.show();
                        Stage spStage = (Stage) img.getScene().getWindow();
                        spStage.close();
                    }

                });

            } catch (InterruptedException e) {

            }
        }
    }

}
