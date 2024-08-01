package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import dto.FinesDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import service.ServiceFactory;
import service.ServiceFactory.ServiceType;
import service.custom.FinesService;
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

    FinesService finesService = (FinesService) ServiceFactory.getInstance().getService(ServiceType.FINE);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colLate.setCellValueFactory(new PropertyValueFactory<>("late"));
        colDamage.setCellValueFactory(new PropertyValueFactory<>("damage"));
        colLost.setCellValueFactory(new PropertyValueFactory<>("lost"));

        loadTable();
        
    }

    // ------------GO BACK TO HOME PAGE----------------
    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        this.root.getChildren().clear();
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/AdminView.fxml"));
        this.root.getChildren().add(node);
    }

    // ------------UPDATING FINES----------------
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
       try {
        FinesDto finesDto = new FinesDto(1, Double.valueOf(txtLate.getText()), Double.valueOf(txtDamage.getText()), Double.valueOf(txtLost.getText()));
        String response = finesService.updateUser(finesDto);
        showDialog("Message", response);
        loadTable();
        clearForm();
       } catch (Exception e) {
        showDialog("Error", "Error while updating fines...");
        e.printStackTrace();
       }
    }

    // ------------GETTING THE DATA FROM TABLE TO TEXT FIELDS----------------
    @FXML
    void tblFineClickOnAction(MouseEvent event) {
        FinesTM finesTM = tblFines.getSelectionModel().getSelectedItem();
        txtLost.setText(finesTM.getLost().toString());
        txtLate.setText(finesTM.getLate().toString());
        txtDamage.setText(finesTM.getDamage().toString());
    }

    // ------------LOAD THE DATA TO THE TABLE----------------
    private void loadTable() {
        try {
            FinesDto finesDto = finesService.get(1);
            ObservableList<FinesTM> observableList = FXCollections.observableArrayList();
            FinesTM tm = new FinesTM(finesDto.getLate(), finesDto.getDamage(), finesDto.getLost());
            observableList.add(tm);
            tblFines.setItems(observableList);
        } catch (Exception e) {
            showDialog("Error", "Error while loading table...");
        }
        
    }

    // ------------SHOW POP-UP DIALOGS----------------
    private void showDialog(String title, String content) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        ButtonType buttonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonType);
        dialog.setContentText(content);
        dialog.showAndWait();
    }

    // ------------CLEAR FORM----------------
    private void clearForm(){
        txtDamage.clear();
        txtLate.clear();
        txtLost.clear();
    }
}
