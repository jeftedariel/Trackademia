/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.controller;

import edu.utn.trackademia.Trackademia;
import edu.utn.trackademia.dao.GroupDAO;
import edu.utn.trackademia.entities.Grupo;
import edu.utn.trackademia.entities.UserSession;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
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
 * @author alexledezma
 */
public class GroupController implements Initializable {
    
    private static int idUsuario;
    
    private GroupDAO gdao;
    
    @FXML
    private Label username;
    
    @FXML
    private ImageView logout;
    
    @FXML
    private TableView<Grupo> table;
    
    @FXML
    private MFXButton rubros;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        logout.setOnMouseClicked(event -> {
            MenuController.initGui(logout);
        });
        
        gdao = new GroupDAO();

        this.username.setText(UserSession.getInstance().getUserFullName());
        this.idUsuario = UserSession.getInstance().getIdUsuario();
        
        rubros.setOnAction(event ->  verRubros());
        
        setupTable(idUsuario);
    }
    
    
    private void verRubros() {
    Grupo seleccionado = table.getSelectionModel().getSelectedItem();
    
    if (seleccionado == null) {
        Alerts.show(AlertType.WARNING,"Warning", "Please, select a group.");
        return;
    }

    int idGrupo = seleccionado.getIdGrupo();
    int courseId = Integer.valueOf(seleccionado.getCurso());
    RubrosController.initGui(logout, idGrupo, courseId);
    }
    
    
    private void setupTable(int idUsuario) {
        
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn<Grupo, String> numeroGrupoColumn = new TableColumn<>("Group Number");
        numeroGrupoColumn.setCellValueFactory(new PropertyValueFactory<>("numeroGrupo"));
        numeroGrupoColumn.setComparator(Comparator.comparing(String::valueOf));

        TableColumn<Grupo, String> numeroAulaColumn = new TableColumn<>("Room number");
        numeroAulaColumn.setCellValueFactory(new PropertyValueFactory<>("numeroAula"));
        numeroAulaColumn.setComparator(Comparator.comparing(String::valueOf));

        TableColumn<Grupo, String> horarioColumn = new TableColumn<>("Schedule");
        horarioColumn.setCellValueFactory(new PropertyValueFactory<>("horario"));
        horarioColumn.setComparator(Comparator.comparing(String::valueOf));

        TableColumn<Grupo, Integer> capacidadMaximaColumn = new TableColumn<>("Max Capacity");
        capacidadMaximaColumn.setCellValueFactory(new PropertyValueFactory<>("capacidadMaxima"));
        capacidadMaximaColumn.setComparator(Comparator.comparing(Integer::valueOf));

        TableColumn<Grupo, String> nombreCursoColumn = new TableColumn<>("Course");
        nombreCursoColumn.setCellValueFactory(new PropertyValueFactory<>("curso"));
        nombreCursoColumn.setComparator(Comparator.comparing(String::valueOf));

        TableColumn<Grupo, Integer> creditosColumn = new TableColumn<>("Credits");
        creditosColumn.setCellValueFactory(new PropertyValueFactory<>("creditos"));
        creditosColumn.setComparator(Comparator.comparing(Integer::valueOf));

        TableColumn<Grupo, Integer> horasLectivasColumn = new TableColumn<>("Hours");
        horasLectivasColumn.setCellValueFactory(new PropertyValueFactory<>("horas_lectivas"));
        horasLectivasColumn.setComparator(Comparator.comparing(Integer::valueOf));

        table.getColumns().addAll(numeroGrupoColumn, numeroAulaColumn, horarioColumn, capacidadMaximaColumn, nombreCursoColumn, creditosColumn, horasLectivasColumn);
        
        GroupDAO gdao = new GroupDAO();
        ObservableList<Grupo> grupos = FXCollections.observableArrayList(gdao.getGrupos(idUsuario));
        table.setItems(grupos);
        
        System.out.println(gdao.hasAvailableGroups(idUsuario));
    }
    
    public static void initGui(ImageView img, int id_usuario) {
        GroupController.idUsuario = id_usuario;
        
        try {
            Parent root = FXMLLoader.load(Trackademia.class.getResource("/fxml/Group.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) img.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

    }
    
}
