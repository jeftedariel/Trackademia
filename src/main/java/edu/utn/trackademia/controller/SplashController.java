/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.trackademia.controller;

import edu.utn.trackademia.Trackademia;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
                        LoginController.gui(img);
                    }

                });

            } catch (InterruptedException e) {

            }
        }
    }

    public static void gui(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Trackademia.class.getResource("/fxml/Splash.fxml"));

            root.setStyle("-fx-background-color: transparent");
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Trackademia");
            stage.getIcons().add(new Image(Trackademia.class.getResourceAsStream("/assets/icon.png")));
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch(IOException e){
        }
    }

}
