package controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UpdateFinesController {
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
    void btnHomeOnAction(ActionEvent event) throws IOException {
        this.root.getChildren().clear();
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/AdminView.fxml"));
        this.root.getChildren().add(node);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
       
    }
}
