package controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import security.LoginSecurity;

public class ManageBorrowingController {
     @FXML
    private JFXButton brtnIssue;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private JFXButton btnSearchMember;

    @FXML
    private JFXButton btnbtnSearchBook;

    @FXML
    private TableColumn<?, ?> colBookid;

    @FXML
    private TableColumn<?, ?> colIssueCondition;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private JFXComboBox<?> comboBox;

    @FXML
    private Label lblBookDetail;

    @FXML
    private Label lblMemberDetail;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<?> tblBorrow;

    @FXML
    private TextField txtBookId;

    @FXML
    private TextField txtBorrowId;

    @FXML
    private TextField txtMemberId;

    @FXML
    private TextField txtReturnDate;

    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException, Exception {
        goToHome(LoginSecurity.getInstance().getIsAdmin()); 
    }

    @FXML
    void btnIssueOnAction(ActionEvent event) {

    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearcchMemberOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchMemberOnAction(ActionEvent event) {

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
