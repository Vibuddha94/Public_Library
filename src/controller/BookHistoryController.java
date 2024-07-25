package controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class BookHistoryController {
    @FXML
    private JFXButton btnBack;

    @FXML
    private TableColumn<?, ?> colBorrowCondition;

    @FXML
    private TableColumn<?, ?> colBorrowDate;

    @FXML
    private TableColumn<?, ?> colFinedReson;

    @FXML
    private TableColumn<?, ?> colFines;

    @FXML
    private TableColumn<?, ?> colMemberId;

    @FXML
    private TableColumn<?, ?> colReturnCondition;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private Label lblDetail;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<?> tblBookHistory;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        this.root.getChildren().clear();
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/ManageBooks.fxml"));
        this.root.getChildren().add(node);
    }
}
