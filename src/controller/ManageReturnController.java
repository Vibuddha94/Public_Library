package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

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
import tableModel.BorrowTM;
import tableModel.ReturnTM;

public class ManageReturnController implements Initializable{
    @FXML
    private JFXButton btnChecked;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnReturn;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXCheckBox cbDamaged;

    @FXML
    private JFXCheckBox cbGood;

    @FXML
    private JFXCheckBox cbLost;

    @FXML
    private TableColumn<ReturnTM, String> colFinedReason;

    @FXML
    private TableColumn<ReturnTM, Double> colFines;

    @FXML
    private TableColumn<BorrowTM, String> colIssueBookId;

    @FXML
    private TableColumn<BorrowTM, String> colIssueCondition;

    @FXML
    private TableColumn<BorrowTM, String> colRetuenDate;

    @FXML
    private TableColumn<ReturnTM, String> colReturnBookId;

    @FXML
    private TableColumn<ReturnTM, String> colReturnCondition;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<BorrowTM> tblIssue;

    @FXML
    private TableView<ReturnTM> tblReturn;

    @FXML
    private TextField txtBorrowId;

    @FXML
    void btnCheckedOnAction(ActionEvent event) {
        ObservableList<ReturnTM> observableList = FXCollections.observableArrayList();
        ReturnTM tm = new ReturnTM("111", "Damaged", "Damage", 50.0);
        observableList.add(tm);
        tblReturn.setItems(observableList);
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException, Exception {
        goToHome(LoginSecurity.getInstance().getIsAdmin()); 
    }

    @FXML
    void btnReturnOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        ObservableList<BorrowTM> observableList = FXCollections.observableArrayList();
        BorrowTM tm = new BorrowTM("111", "Good", "2024-08-05");
        observableList.add(tm);
        tblIssue.setItems(observableList);
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
        colIssueBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colIssueCondition.setCellValueFactory(new PropertyValueFactory<>("isuueCondition"));
        colRetuenDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        colReturnBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colReturnCondition.setCellValueFactory(new PropertyValueFactory<>("returnCondition"));
        colFines.setCellValueFactory(new PropertyValueFactory<>("fines"));
        colFinedReason.setCellValueFactory(new PropertyValueFactory<>("finedReason"));

    }
}
