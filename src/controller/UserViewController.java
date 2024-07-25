package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import security.LoginSecurity;

public class UserViewController implements Initializable {
    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnOk;

    @FXML
    private JFXComboBox<String> comboBox;

    @FXML
    private Label lblName;

    @FXML
    private AnchorPane root;

    //------------LOGOUT FUNCTION----------------
    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        this.root.getChildren().clear();
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/Login.fxml"));
        this.root.getChildren().add(node);
    }

    //------------GO TO THE SELECTED INTERFACE----------------
    @FXML
    void btnOkOnAction(ActionEvent event) throws IOException {
        loadInterface(comboBox.getSelectionModel().getSelectedIndex());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list = FXCollections.observableArrayList("Manage Categories", "Manage Books", 
                "Manage Members", "Issue Books", "Return Books");  //------------ADD ITEMS TO COMBO BOX----------------

        comboBox.setItems((ObservableList<String>) list);

        try {
            lblName.setText(LoginSecurity.getInstance().getName());  //------------SET LOGGED IN USERS NAME----------------
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


     //------------LOAD THE INTERFACE ACCORDING TO THE COMBO BOX OUTPUT----------------
    private void loadInterface(int index) throws IOException {
        String nextInterface = switch (index) {
            case 0 -> "ManageCategories.fxml";
            case 1 -> "ManageBooks.fxml";
            case 2 -> "ManageMembers.fxml";
            case 3 -> "ManageBorrowing.fxml";
            case 4 -> "ManageReturn.fxml";
            default -> "Wrong Number";
        };

        if (nextInterface.equals("Wrong Number")) {
            showDialog("Error", "Select an Option");
        } else {
            this.root.getChildren().clear();
            Parent node = FXMLLoader.load(this.getClass().getResource("/view/" + nextInterface));
            this.root.getChildren().add(node);
        }
    }


    //------------SHOW POP-UP MESSAGE----------------
    private void showDialog(String title, String content) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        ButtonType buttonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonType);
        dialog.setContentText(content);
        dialog.showAndWait();
    }
}
