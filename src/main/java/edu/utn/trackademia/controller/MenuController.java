/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.trackademia.controller;

import edu.utn.trackademia.Trackademia;
import edu.utn.trackademia.dao.AcademicOfferDAO;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    private Pane userManagement;

    @FXML
    private Pane enrollCourse;
    
    @FXML
    private MFXButton abrirGrupo;
    
    @FXML
    private Pane enrolledCourses;
    
    @FXML
    private Pane courseGroups;
    
    @FXML
    private PieChart test;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logout.setOnMouseClicked(event -> {
            LoginController.initGui(logout);
        });

        enrollCourse.setOnMouseClicked(event -> openAcademicOffer());

        this.username.setText(UserSession.getInstance().getUserFullName());
        
        enrolledCourses.setOnMouseClicked(event ->  EnrolledGroupsController.initGui(logout, UserSession.getInstance().getIdUsuario()));
        courseGroups.setOnMouseClicked(event -> GroupController.initGui(logout,UserSession.getInstance().getIdUsuario()));

        userManagement.setOnMouseClicked(event -> {
            userManagement();
        });

        educational.setCollapsible(false);
        administration.setCollapsible(false);

        //Sets visible both panels (Administration & Educational) if permissions 'Show Educational' or 'Show Administration' are present
        educational.setVisible(rdao.getPermissions(UserSession.getInstance().getRole()).stream().anyMatch(n -> n.name().equals("Show Educational")));
        if (!rdao.getPermissions(UserSession.getInstance().getRole()).stream().anyMatch(n -> n.name().equals("Show Educational"))) {
            administration.setLayoutY(74);
        }
        administration.setVisible(rdao.getPermissions(UserSession.getInstance().getRole()).stream().anyMatch(n -> n.name().equals("Show Administration")));
    }

    public void openAcademicOffer() {
        Parent root = null;
        AcademicOfferDAO gdao = new AcademicOfferDAO();
        if (!gdao.hasAvailableGroups(UserSession.getInstance().getIdUsuario())) {
            Alerts.show(Alert.AlertType.INFORMATION, "Info", "There's no available enrollments");
            return;
        }
        AcademicOfferController.initGui(logout);

    }

    public void userManagement() {
        StudentManagementController.initGui(logout);
        
    }

    public static void initGui(ImageView img) {
        try {
            FXMLLoader loader = new FXMLLoader(Trackademia.class.getResource("/fxml/Menu.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) img.getScene().getWindow();

            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
