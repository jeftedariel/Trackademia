/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.trackademia.controller;

import edu.utn.trackademia.entities.UserSession;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author jefte
 */
public class UserManagementController implements Initializable {

    @FXML
    private ImageView logout;

    @FXML
    private Label username;

    @FXML
    private MFXTableView table;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<String> users = new ArrayList<String>();

        logout.setOnMouseClicked(event -> {
            logout();
        });

        this.username.setText(UserSession.getInstance().getUserFullName());

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
