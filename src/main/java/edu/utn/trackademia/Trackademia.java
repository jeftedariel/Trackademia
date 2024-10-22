/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package edu.utn.trackademia;

import edu.utn.trackademia.controller.SplashController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author jefte
 */
public class Trackademia extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SplashController.initGui(stage);
    }

    public static void main(String[] args) {
        launch(args);

    }
}
