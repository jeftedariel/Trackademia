/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.utn.trackademia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jefte
 */
public class Trackademia extends Application  {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        stage.setTitle("Trackademia");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
        
    }
}
