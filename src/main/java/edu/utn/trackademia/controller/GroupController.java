/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.controller;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author alexledezma
 */
public class GroupController implements Initializable {
    
    private int idUsuario;
    
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
            logout();
        });
        
        gdao = new GroupDAO();

        this.username.setText(UserSession.getInstance().getUserFullName());
        this.idUsuario = UserSession.getInstance().getIdUsuario();
        
        rubros.setOnAction(event ->  verRubros());
        
        setupTable(idUsuario);
    }
    
    private void logout() {
    try {
        Stage currentStage = (Stage) logout.getScene().getWindow();
        currentStage.close();
    } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    private void verRubros() {
    Grupo seleccionado = table.getSelectionModel().getSelectedItem();

    if (seleccionado == null) {
        mostrarAlerta("Warning", "Please, select a group.");
        return;
    }

    int idGrupo = seleccionado.getIdGrupo();

    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Rubros.fxml"));
        RubrosController rubrosController = new RubrosController(idGrupo);
        loader.setController(rubrosController);
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Rubros");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();  
    } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
    private void mostrarAlerta(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
    
}
