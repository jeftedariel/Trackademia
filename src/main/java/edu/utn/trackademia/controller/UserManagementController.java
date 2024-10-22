/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package edu.utn.trackademia.controller;

import edu.utn.trackademia.Trackademia;
import edu.utn.trackademia.dao.UserDAO;
import edu.utn.trackademia.entities.User;
import edu.utn.trackademia.entities.UserSession;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TablePosition;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jefte
 */
public class UserManagementController implements Initializable {

    @FXML
    private ImageView logout;

    @FXML
    private Label username;

    @FXML
    private MFXTableView table;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logout.setOnMouseClicked(event -> {
            MenuController.initGui(logout);
        });

        this.username.setText(UserSession.getInstance().getUserFullName());

        setupTable();
        table.autosizeColumnsOnInitialization();

        //table.getSelectionModel().setAllowsMultipleSelection(true);
        ObservableMap selectedCells = table.getSelectionModel().getSelection();

        selectedCells.addListener(new MapChangeListener() {
            @Override
            public void onChanged(Change c) {
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                Object val = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                System.out.println("Selected Value" + val);
            }
        });

    }

    private void setupTable() {
        MFXTableColumn<User> nameColumn = new MFXTableColumn<>("Name", true, Comparator.comparing(User::name));
        MFXTableColumn<User> surnameColumn = new MFXTableColumn<>("Surname", true, Comparator.comparing(User::surname));

        nameColumn.setRowCellFactory(person -> new MFXTableRowCell<>(User::name));
        surnameColumn.setRowCellFactory(person -> new MFXTableRowCell<>(User::surname));

        table.getTableColumns().addAll(nameColumn, surnameColumn);
        table.getFilters().addAll(
                new StringFilter<>("Name", User::name),
                new StringFilter<>("Surname", User::surname)
        );

        UserDAO udao = new UserDAO();

        ObservableList<User> users;
        users = FXCollections.observableArrayList(udao.getUsers().stream().toList());

        table.setItems(users);
    }
    
    
    public static void initGui(ImageView img){
    
        Parent root = null;
        try {
            root = FXMLLoader.load(Trackademia.class.getResource("/fxml/UserManagement.fxml"));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) img.getScene().getWindow();
        stage.setScene(scene);
    }

}
