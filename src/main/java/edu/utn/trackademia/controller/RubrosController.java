/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.utn.trackademia.controller;

import edu.utn.trackademia.dao.RubroDAO;
import edu.utn.trackademia.entities.Rubro;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class RubrosController implements Initializable{

    private int idGrupo;
    
    private RubroDAO rdao;
    
    public RubrosController(int idGrupo) {
        this.idGrupo = idGrupo;
    }
    
    @FXML
    private ImageView logout;
    
    @FXML
    private TableView tableView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logout.setOnMouseClicked(event -> {
            logout();
        });
        
        setRubros(idGrupo);

    }
    
    private void logout() {
    try {
        Stage currentStage = (Stage) logout.getScene().getWindow();
        currentStage.close();
    } catch (Exception e) {
        e.printStackTrace();
      }
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
    
}
