/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package edu.utn.trackademia;

import edu.utn.trackademia.controller.SplashController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author jefte
 */
public class Trackademia extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SplashController.gui(stage);
    }

    public static void main(String[] args) {
        launch(args);

    }
}
