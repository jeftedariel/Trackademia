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
import io.github.palexdev.materialfx.controls.MFXTextField;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author alexledezma
 */
public class RubrosController implements Initializable {

    public static int idGrupo;
    public static int courseId;

    private int idUsuario;

    private RubroDAO rdao = new RubroDAO();

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

    @FXML
    private MFXButton removeEvaluation;
    
    @FXML
    private PieChart evaluationsChart;
    
    @FXML
    private MFXButton addEvaluation;
    
    @FXML
    private MFXTextField name;
    
    @FXML
    private MFXTextField percentage;

    @FXML
    private Pane configEv;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logout.setOnMouseClicked(event -> {
            MenuController.initGui(logout);
        });

        setRubros(idGrupo);

        this.username.setText(UserSession.getInstance().getUserFullName());
        this.idUsuario = UserSession.getInstance().getIdUsuario();

        verRubros.setOnAction(event -> verActividades());

        removeEvaluation.setOnAction(event -> {
            removeEv();
        });
        setChart();
        
        addEvaluation.setOnAction(event-> {
            addEv(idGrupo);
        });
        
        name.setOnKeyReleased(event-> {
            addEvaluation.setDisable(!validateTextField(name, percentage));
        });
        percentage.setOnKeyReleased(event-> {
            addEvaluation.setDisable(!validateTextField(name, percentage));
        });
        
        configEv.setVisible(!isCollegeEvaluation());
    }
    
    private boolean isCollegeEvaluation(){
        return rdao.getCourseEvaluationType(RubrosController.courseId);
    }
    
    private boolean validateTextField(MFXTextField name, MFXTextField percentage){
        return (name.getText().matches("[a-zA-Z]+") && percentage.getText().matches("[0-9]+"));
    
    }
    
    private void verActividades() {

        Rubro seleccionado = tableView.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Alerts.show(Alert.AlertType.WARNING, "Warning", "Please, select an activity.");
            return;
        }

        int idRubro = seleccionado.getId_rubro();

        ActividadesController.initGui(logout, idRubro);
    }

    private void removeEv() {

        Rubro seleccionado = tableView.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            Alerts.show(Alert.AlertType.WARNING, "Warning", "Please, select an evaluation.");
            return;
        }

        rdao.removeEvaluation(seleccionado.getId_rubro());

        setRubros(idGrupo);
        setChart();
        Alerts.show(Alert.AlertType.INFORMATION, "Succes", "An evaluation has been deleted succesfully.");

        
    }
    
    private void addEv(int idGrupoEv){
        
        rdao.addEvaluation(name.getText(), Integer.valueOf(percentage.getText()), idGrupoEv);
        
        setRubros(idGrupo);
        setChart();
        Alerts.show(Alert.AlertType.INFORMATION, "Succes", "An evaluation has been added succesfully.");
    }
    
    private void setChart(){
        List<Rubro> rubrosList = rdao.getRubros(idGrupo);
        ObservableList<Rubro> rubros = FXCollections.observableArrayList(rubrosList);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        
        rubros.forEach(n-> {pieChartData.add(new PieChart.Data(n.getNombre(), n.getPonderacion()));});
        
        evaluationsChart.setData(pieChartData);
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

        List<Rubro> rubrosList = rdao.getRubros(idGrupo);

        ObservableList<Rubro> rubros = FXCollections.observableArrayList(rubrosList);
        tableView.setItems(rubros);
    }

    public static void initGui(ImageView img, int id_grupo, int courseId) {
        RubrosController.idGrupo = id_grupo;
        RubrosController.courseId= courseId;
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
