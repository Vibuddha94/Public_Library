package controller;

import java.io.IOException;
import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar.ButtonData;
import security.LoginSecurity;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LoginController {

    @FXML
    private AnchorPane root;

     @FXML
    private JFXButton btnLogin;

    @FXML
    private PasswordField txtPasword;

    @FXML
    private TextField txtUserId;

    //------------CHECK THE VALIDITY OF THE ENTERED USER ID----------------
    @FXML
    void txtPasswordOnMouseClicked(MouseEvent event) throws Exception {
        String id = txtUserId.getText();
        boolean valid = LoginSecurity.getInstance().validateUserId(id);
        if (!valid) {
            showDialog("Error", "Enter valid User ID.");
            clearForm();
        }
    }


    //------------CHECK THE PASSWORD AND LOAD THE NEXT INTERFACE----------------
    @FXML
    void btnLoginOnAction(ActionEvent event) throws Exception {
        String password = txtPasword.getText();
        if (password.equals(LoginSecurity.getInstance().getPassword())) {
            goToHome(LoginSecurity.getInstance().getIsAdmin());
        } else {
            showDialog("Error", "Enter correct Password.");
            txtPasword.clear(); 
        }
    }


    //------------SHOW POP-UP MESSAGES----------------
    private void showDialog(String title, String content) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        ButtonType buttonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonType);
        dialog.setContentText(content);
        dialog.showAndWait();
    }


    //------------CLEAR THE TEXT FIELDS----------------
    private void clearForm(){
        txtPasword.clear();
        txtUserId.clear();
        txtUserId.requestFocus();
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
