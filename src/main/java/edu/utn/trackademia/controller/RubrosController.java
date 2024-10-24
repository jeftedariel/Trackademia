/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.controller;

import edu.utn.trackademia.Trackademia;
import edu.utn.trackademia.dao.RubroDAO;
import edu.utn.trackademia.entities.Rubro;
import edu.utn.trackademia.entities.UserSession;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
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
import javafx.scene.control.Alert;
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
public class RubrosController implements Initializable {

    static private int idGrupo;

    private int idUsuario;

    private RubroDAO rdao;

    public RubrosController(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    @FXML
    private Label username;
    
    @FXML
    private ImageView logout;

    @FXML
    private TableView<Rubro> tableView;
    
    @FXML
    private MFXButton verRubros;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logout.setOnMouseClicked(event -> {
            MenuController.initGui(logout);
        });

        setRubros(idGrupo);
        
        this.username.setText(UserSession.getInstance().getUserFullName());
        this.idUsuario = UserSession.getInstance().getIdUsuario();
        
        verRubros.setOnAction(event -> verActividades());
    }
    
    private void verActividades() {
        
    Rubro seleccionado = tableView.getSelectionModel().getSelectedItem();

    if (seleccionado == null) {
        Alerts.show(Alert.AlertType.WARNING,"Warning", "Please, select an activity.");
        return;
    }

    int idRubro = seleccionado.getId_rubro();

    ActividadesController.initGui(logout, idRubro);
    }

    private void setRubros(int idGrupo) {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn<Rubro, Integer> idRubroColumn = new TableColumn<>("Rubro ID");
        idRubroColumn.setCellValueFactory(new PropertyValueFactory<>("id_rubro"));
        idRubroColumn.setComparator(Comparator.comparing(Integer::valueOf));

        TableColumn<Rubro, String> nombreRubroColumn = new TableColumn<>("Rubro Name");
        nombreRubroColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        nombreRubroColumn.setComparator(Comparator.comparing(String::valueOf));

        TableColumn<Rubro, Integer> ponderacionColumn = new TableColumn<>("Ponderation");
        ponderacionColumn.setCellValueFactory(new PropertyValueFactory<>("ponderacion"));
        ponderacionColumn.setComparator(Comparator.comparing(Integer::valueOf));

        tableView.getColumns().addAll(idRubroColumn, nombreRubroColumn, ponderacionColumn);

        RubroDAO rdao = new RubroDAO();
        List<Rubro> rubrosList = rdao.getRubros(idGrupo);

        ObservableList<Rubro> rubros = FXCollections.observableArrayList(rubrosList);
        tableView.setItems(rubros);
    }

    public static void initGui(ImageView img, int id_grupo) {
        RubrosController.idGrupo = id_grupo;

        try {
            //Parent root = FXMLLoader.load(Trackademia.class.getResource("/fxml/Rubros.fxml"));

            FXMLLoader loader = new FXMLLoader(Trackademia.class.getResource("/fxml/Rubros.fxml"));
            RubrosController rubrosController = new RubrosController(idGrupo);
            loader.setController(rubrosController);
            
            Parent root = loader.load();
            
            
            Scene scene = new Scene(root);
            Stage stage = (Stage) img.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }

}
