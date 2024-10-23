/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.trackademia.controller;

import edu.utn.trackademia.Trackademia;
import edu.utn.trackademia.dao.UserDAO;
import edu.utn.trackademia.entities.Course;
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
public class StudentCoursesController implements Initializable {

    @FXML
    private ImageView logout;

    @FXML
    private Label username;

    @FXML
    private TableView table;

    @FXML
    private MFXComboBox cbxStudents;
    
    private static int student_id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logout.setOnMouseClicked(event -> {
            StudentManagementController.initGui(logout);
        });

        this.username.setText(UserSession.getInstance().getUserFullName());

        setupTable();

    }

    private void setupTable() {
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
       // String ,int ,int , String schedule
        
        TableColumn<Course, String> course_name = new TableColumn<>("Course");
        course_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().course_name()));

        TableColumn<Course, String> group_number = new TableColumn<>("Group");
        group_number.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().group_number())));

        TableColumn<Course, String> room_number = new TableColumn<>("Classroom");
        room_number.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().room_number())));
        
        
        TableColumn<Course, String> schedule = new TableColumn<>("Schedule");
        schedule.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().schedule()));
        
        table.getColumns().addAll(course_name, group_number, room_number,schedule );

        UserDAO uDao = new UserDAO();
        ObservableList<Course> courses = FXCollections.observableArrayList(uDao.getCourses(StudentCoursesController.student_id));
        table.setItems(courses);
    }

    public static void initGui(ImageView img, int student_id) {
        StudentCoursesController.student_id = student_id;
        
        Parent root = null;
        try {
            root = FXMLLoader.load(Trackademia.class.getResource("/fxml/StudentCourses.fxml"));

            Scene scene = new Scene(root);
            Stage stage = (Stage) img.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

}
