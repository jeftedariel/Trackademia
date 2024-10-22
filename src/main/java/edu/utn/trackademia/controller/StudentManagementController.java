/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.trackademia.controller;

import edu.utn.trackademia.Trackademia;
import edu.utn.trackademia.dao.UserDAO;
import edu.utn.trackademia.entities.User;
import edu.utn.trackademia.entities.UserSession;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jefte
 */
public class StudentManagementController implements Initializable {

    @FXML
    private ImageView logout;

    @FXML
    private Label username;

    @FXML
    private TableView table;

    @FXML
    private MFXComboBox cbxStudents;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logout.setOnMouseClicked(event -> {
            MenuController.initGui(logout);
        });

        this.username.setText(UserSession.getInstance().getUserFullName());

        setupTable();

    }

    private void setupTable() {
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn<User, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().name()));

        TableColumn<User, String> surname = new TableColumn<>("Surname");
        surname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().surname()));
        
        TableColumn<User, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().email()));
        
        table.getColumns().addAll(name, surname, email);

        UserDAO uDao = new UserDAO();
        ObservableList<User> users = FXCollections.observableArrayList(uDao.getStudents());
        table.setItems(users);
    }

    public static void initGui(ImageView img) {

        Parent root = null;
        try {
            root = FXMLLoader.load(Trackademia.class.getResource("/fxml/StudentManagement.fxml"));

            Scene scene = new Scene(root);
            Stage stage = (Stage) img.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

}
