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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import tableModel.HistoryTM;

public class MemberHistoryController implements Initializable {
    @FXML
    private JFXButton btnBack;

    @FXML
    private TableColumn<HistoryTM, String> colFineReason;

    @FXML
    private TableColumn<HistoryTM, String> colReturnDate;

    @FXML
    private TableColumn<HistoryTM, String> colBookId;

    @FXML
    private TableColumn<HistoryTM, String> colBorCondition;

    @FXML
    private TableColumn<HistoryTM, String> colBorrowDate;

    @FXML
    private TableColumn<HistoryTM, Double> colFines;

    @FXML
    private TableColumn<HistoryTM, String> colRetCondition;

    @FXML
    private Label lblDetail;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<HistoryTM> tblMemHistory;

    public static String MemberId;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        this.root.getChildren().clear();
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/ManageMembers.fxml"));
        this.root.getChildren().add(node);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBorCondition.setCellValueFactory(new PropertyValueFactory<>("borrowCondition"));
        colRetCondition.setCellValueFactory(new PropertyValueFactory<>("returnCondition"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colFines.setCellValueFactory(new PropertyValueFactory<>("fines"));
        colFineReason.setCellValueFactory(new PropertyValueFactory<>("finedReason"));

        loadTable();
    }

    private void loadTable() {
        ObservableList<HistoryTM> observableList = FXCollections.observableArrayList();
        HistoryTM tm = new HistoryTM("111", "", "2024-07-23", "2024-08-02", "Good", "Good", 50.5, "Late");
        observableList.add(tm);
        tblMemHistory.setItems(observableList);
    }

}
