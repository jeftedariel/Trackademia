/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.trackademia.controller;

import edu.utn.trackademia.dao.AcademicOfferDAO;
import edu.utn.trackademia.dao.MatriculaService;
import edu.utn.trackademia.entities.Grupo;
import edu.utn.trackademia.entities.UserSession;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;

/**
 * FXML Controller class
 *
 * @author jefte
 */
public class AcademicOfferControler implements Initializable {
    
    int idUsuario;
    
    private MatriculaService matriculaService;

    @FXML
    private ImageView logout;

    @FXML
    private Label username;
    
    @FXML
    private MFXButton matricular;

    @FXML
    private TableView<Grupo> table;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logout.setOnMouseClicked(event -> {
           logout();
    });

    matriculaService = new MatriculaService();    
        
    this.username.setText(UserSession.getInstance().getUserFullName());
    this.idUsuario = UserSession.getInstance().getIdUsuario();

    setupTable(idUsuario);
    
    matricular.setOnAction(event -> realizarMatricula());
    
    table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    private void realizarMatricula() {
    ObservableList<Integer> selectedIndices = table.getSelectionModel().getSelectedIndices();
  
    if (selectedIndices.isEmpty()) {
        mostrarAlerta("Advertencia", "Por favor, selecciona al menos un grupo para matricularte.");
        return;
    }

    ObservableList<Grupo> seleccionados = FXCollections.observableArrayList();

    for (Integer index : selectedIndices) {
        if (index >= 0 && index < table.getItems().size()) {
            Grupo grupo = table.getItems().get(index);
            seleccionados.add(grupo);
        }
    }

    // Time to matriculate
    try {
        int idUsuario = UserSession.getInstance().getIdUsuario(); 
        matriculaService.matricularEstudiante(idUsuario, seleccionados);
        mostrarAlerta("Éxito", "Te has matriculado correctamente en los grupos seleccionados.");
        logout();
    } catch (SQLException e) {
        e.printStackTrace();
        mostrarAlerta("Error", "No se pudo completar la matrícula. Intenta nuevamente.");
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
        
    TableColumn<Grupo, String> numeroGrupoColumn = new TableColumn<>("Número Grupo");
    numeroGrupoColumn.setCellValueFactory(new PropertyValueFactory<>("numeroGrupo"));
    numeroGrupoColumn.setComparator(Comparator.comparing(String::valueOf));

    TableColumn<Grupo, String> numeroAulaColumn = new TableColumn<>("Número Aula");
    numeroAulaColumn.setCellValueFactory(new PropertyValueFactory<>("numeroAula"));
    numeroAulaColumn.setComparator(Comparator.comparing(String::valueOf));

    TableColumn<Grupo, String> horarioColumn = new TableColumn<>("Horario");
    horarioColumn.setCellValueFactory(new PropertyValueFactory<>("horario"));
    horarioColumn.setComparator(Comparator.comparing(String::valueOf));

    TableColumn<Grupo, Integer> capacidadMaximaColumn = new TableColumn<>("Capacidad Máxima");
    capacidadMaximaColumn.setCellValueFactory(new PropertyValueFactory<>("capacidadMaxima"));
    capacidadMaximaColumn.setComparator(Comparator.comparing(Integer::valueOf));

    TableColumn<Grupo, String> nombreCursoColumn = new TableColumn<>("Curso");
    nombreCursoColumn.setCellValueFactory(new PropertyValueFactory<>("curso"));
    nombreCursoColumn.setComparator(Comparator.comparing(String::valueOf));

    TableColumn<Grupo, Integer> creditosColumn = new TableColumn<>("Créditos");
    creditosColumn.setCellValueFactory(new PropertyValueFactory<>("creditos"));
    creditosColumn.setComparator(Comparator.comparing(Integer::valueOf));

    TableColumn<Grupo, Integer> horasLectivasColumn = new TableColumn<>("Horas Lectivas");
    horasLectivasColumn.setCellValueFactory(new PropertyValueFactory<>("horas_lectivas"));
    horasLectivasColumn.setComparator(Comparator.comparing(Integer::valueOf));

    table.getColumns().addAll(numeroGrupoColumn, numeroAulaColumn, horarioColumn, capacidadMaximaColumn, nombreCursoColumn, creditosColumn, horasLectivasColumn);

      AcademicOfferDAO gdao = new AcademicOfferDAO();
      ObservableList<Grupo> grupos = FXCollections.observableArrayList(gdao.getGrupos(idUsuario));
      table.setItems(grupos);
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
