/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.controller;

import edu.utn.trackademia.Trackademia;
import edu.utn.trackademia.dao.ActividadDAO;
import edu.utn.trackademia.entities.Actividad;
import edu.utn.trackademia.entities.UserSession;
import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
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
public class ActividadesController implements Initializable {
    
    private int idUsuario;
    
    static private int idRubro;
    
     public ActividadesController(int idRubro) {
        this.idRubro = idRubro;
    }
    
    @FXML
    private Label username;
    
    @FXML
    private ImageView logout;

    @FXML
    private TableView<Actividad> tableView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      logout.setOnMouseClicked(event -> {
            MenuController.initGui(logout);
        });
        
      setActividades(idRubro);
      
      this.idUsuario = UserSession.getInstance().getIdUsuario();
      this.username.setText(UserSession.getInstance().getUserFullName());
      
    }
    
    private void setActividades(int idRubro) {
        
    tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    TableColumn<Actividad, Integer> idActividadColumn = new TableColumn<>("Actividad ID");
    idActividadColumn.setCellValueFactory(new PropertyValueFactory<>("id_actividad"));
    idActividadColumn.setComparator(Comparator.comparing(Integer::valueOf));

    TableColumn<Actividad, String> nombreActividadColumn = new TableColumn<>("Nombre Actividad");
    nombreActividadColumn.setCellValueFactory(new PropertyValueFactory<>("nombre_actividad"));
    nombreActividadColumn.setComparator(Comparator.comparing(String::valueOf));

    TableColumn<Actividad, String> descripcionColumn = new TableColumn<>("Descripción");
    descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    descripcionColumn.setComparator(Comparator.comparing(String::valueOf));

    TableColumn<Actividad, Integer> valorColumn = new TableColumn<>("Valor");
    valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
    valorColumn.setComparator(Comparator.comparing(Integer::valueOf));

    TableColumn<Actividad, LocalDate> fechaEntregaColumn = new TableColumn<>("Fecha de Entrega");
    fechaEntregaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha_entrega"));
    fechaEntregaColumn.setComparator(Comparator.comparing(LocalDate::from));

    tableView.getColumns().addAll(idActividadColumn, nombreActividadColumn, descripcionColumn, valorColumn, fechaEntregaColumn);

    ActividadDAO adao = new ActividadDAO();
    List<Actividad> actividadesList = adao.getActividades(idRubro);

    ObservableList<Actividad> actividades = FXCollections.observableArrayList(actividadesList);
    tableView.setItems(actividades);
    }
    
    public static void initGui(ImageView img, int id_Rubro) {
        ActividadesController.idRubro = id_Rubro;
        try {
            FXMLLoader loader = new FXMLLoader(Trackademia.class.getResource("/fxml/Actividades.fxml"));
            ActividadesController actividadesController = new ActividadesController(idRubro);
            loader.setController(actividadesController);
            
            Parent root = loader.load();
            
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) img.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
