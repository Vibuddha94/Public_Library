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
import javafx.scene.layout.AnchorPane;
import security.LoginSecurity;
import tableModel.MembersTM;

public class ManageMembersController implements Initializable {
    @FXML
    private JFXButton bbtnHome;

    @FXML
    private JFXButton btnDeelete;

    @FXML
    private JFXButton btnHistory;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<MembersTM, String> colAddress;

    @FXML
    private TableColumn<MembersTM, String> colContactNumber;

    @FXML
    private TableColumn<MembersTM, String> colDob;

    @FXML
    private TableColumn<MembersTM, String> colFirstName;

    @FXML
    private TableColumn<MembersTM, String> colLastName;

    @FXML
    private TableColumn<MembersTM, String> colMemberId;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<MembersTM> tblMembet;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtDob;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtMemberId;

    @FXML
    void bbtnHomeOnAction(ActionEvent event) throws IOException, Exception {
        goToHome(LoginSecurity.getInstance().getIsAdmin()); 
    }

    @FXML
    void btnDeeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnHistoryOnAction(ActionEvent event) throws IOException {
        this.root.getChildren().clear();
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/MemberHistory.fxml"));
        this.root.getChildren().add(node);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    //------------LOAD THE HOME PAGE----------------
    private void goToHome(boolean isAdmin) throws IOException{
        if (isAdmin) {
            this.root.getChildren().clear();
            Parent node = FXMLLoader.load(this.getClass().getResource("/view/AdminView.fxml")); //------------LOAD IF THE USER IS AN ADMIN----------------
            this.root.getChildren().add(node);
        } else {
            this.root.getChildren().clear();
            Parent node = FXMLLoader.load(this.getClass().getResource("/view/UserView.fxml")); //------------LOAD IF THE USER IS A NON-ADMIN USER----------------
            this.root.getChildren().add(node);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contNumber"));

        loadTable();
    }

    private void loadTable() {
        ObservableList<MembersTM> observableList = FXCollections.observableArrayList();
        MembersTM tm = new MembersTM("000", "aaa", "bbb", "1994-02-20", "matara", "0112356989");

        observableList.add(tm);

        tblMembet.setItems(observableList);
    }
}
