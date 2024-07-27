package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tableModel.FinesTM;

public class UpdateFinesController implements Initializable {
    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtDamage;

    @FXML
    private TextField txtLate;

    @FXML
    private TextField txtLost;

    @FXML
    private TableColumn<FinesTM, Double> colDamage;

    @FXML
    private TableColumn<FinesTM, Double> colLate;

    @FXML
    private TableColumn<FinesTM, Double> colLost;

    @FXML
    private TableView<FinesTM> tblFines;

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        this.root.getChildren().clear();
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/AdminView.fxml"));
        this.root.getChildren().add(node);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
       
    }

    @FXML
    void tblFineClickOnAction(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colLate.setCellValueFactory(new PropertyValueFactory<>("late"));
        colDamage.setCellValueFactory(new PropertyValueFactory<>("damage"));
        colLost.setCellValueFactory(new PropertyValueFactory<>("lost"));

        loadTable();
        
    }

    private void loadTable() {
        ObservableList<FinesTM> observableList = FXCollections.observableArrayList();
        FinesTM tm = new FinesTM(25.0, 0.5, 1.0);
        observableList.add(tm);
        tblFines.setItems(observableList);
    }
}
