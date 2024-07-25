package controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import security.LoginSecurity;

public class ManageReturnController {
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
    private TableColumn<?, ?> colFinedReason;

    @FXML
    private TableColumn<?, ?> colFines;

    @FXML
    private TableColumn<?, ?> colIssueBookId;

    @FXML
    private TableColumn<?, ?> colIssueCondition;

    @FXML
    private TableColumn<?, ?> colRetuenDate;

    @FXML
    private TableColumn<?, ?> colReturnBookId;

    @FXML
    private TableColumn<?, ?> colReturnCondition;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<?> tblIssue;

    @FXML
    private TableView<?> tblReturn;

    @FXML
    private TextField txtBorrowId;

    @FXML
    void btnCheckedOnAction(ActionEvent event) {

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
}
