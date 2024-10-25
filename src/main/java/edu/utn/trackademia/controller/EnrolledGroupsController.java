/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.controller;

import edu.utn.trackademia.Trackademia;
import edu.utn.trackademia.dao.GroupDAO;
import edu.utn.trackademia.entities.EnrolledGroup;
import edu.utn.trackademia.entities.Grupo;
import edu.utn.trackademia.entities.UserSession;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author jefte
 */
public class EnrolledGroupsController implements Initializable {
    
    private static int idUsuario;
    
    private GroupDAO gdao;
    
    @FXML
    private Label username;
    
    @FXML
    private ImageView logout;
    
    @FXML
    private TableView<EnrolledGroup> table;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        logout.setOnMouseClicked(event -> {
            MenuController.initGui(logout);
        });
        
        gdao = new GroupDAO();

        this.username.setText(UserSession.getInstance().getUserFullName());
        this.idUsuario = UserSession.getInstance().getIdUsuario();
        
        
        setupTable(idUsuario);
    }
    
    private void setupTable(int idUsuario) {
        
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<EnrolledGroup, String> numeroGrupoColumn = new TableColumn<>("Group Number");
        numeroGrupoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().groupNumber())));
        numeroGrupoColumn.setComparator(Comparator.comparing(String::valueOf));
        
        TableColumn<EnrolledGroup, String> nombreCursoColumn = new TableColumn<>("Course");
        nombreCursoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().courseName())));
        nombreCursoColumn.setComparator(Comparator.comparing(String::valueOf));
        
        TableColumn<EnrolledGroup, String> teacherColumn = new TableColumn<>("Teacher");
        teacherColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().teacher())));
        teacherColumn.setComparator(Comparator.comparing(String::valueOf));
        
        TableColumn<EnrolledGroup, String> creditsColumn = new TableColumn<>("Credits ");
        creditsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().credits())));
        creditsColumn.setComparator(Comparator.comparing(String::valueOf));
        
        TableColumn<EnrolledGroup, String> scheduleColumn = new TableColumn<>("Schedule ");
        scheduleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().schedule())));
        scheduleColumn.setComparator(Comparator.comparing(String::valueOf));
        
        TableColumn<EnrolledGroup, String> classroomColumn = new TableColumn<>("Room number ");
        classroomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().classroom())));
        classroomColumn.setComparator(Comparator.comparing(String::valueOf));
        
        TableColumn<EnrolledGroup, String> urlColumn = new TableColumn<>("Url");
        urlColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().url())));
        urlColumn.setComparator(Comparator.comparing(String::valueOf));
        
        TableColumn<EnrolledGroup, String> platformColumn = new TableColumn<>("Platform");
        platformColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().platform())));
        platformColumn.setComparator(Comparator.comparing(String::valueOf));
        
        TableColumn<EnrolledGroup, String> hoursColumn = new TableColumn<>("Hours");
        hoursColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().hours())));
        hoursColumn.setComparator(Comparator.comparing(String::valueOf));
        
        table.getColumns().addAll(numeroGrupoColumn, nombreCursoColumn, teacherColumn, creditsColumn, scheduleColumn, classroomColumn, urlColumn, platformColumn, hoursColumn);
        
        GroupDAO gdao = new GroupDAO();
        ObservableList<EnrolledGroup> grupos = FXCollections.observableArrayList(gdao.getEnrolledGroups(idUsuario));
        table.setItems(grupos);
        
        System.out.println(gdao.hasAvailableGroups(idUsuario));
    }
    
    public static void initGui(ImageView img, int id_usuario) {
        EnrolledGroupsController.idUsuario = id_usuario;
        
        try {
            Parent root = FXMLLoader.load(Trackademia.class.getResource("/fxml/EnrolledGroups.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) img.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

    }
    
}
