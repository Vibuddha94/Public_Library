package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import dto.MemberDto;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import service.ServiceFactory;
import service.ServiceFactory.ServiceType;
import service.custom.HistoryService;
import service.custom.MemberService;
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

    public static String id;

    HistoryService historyService = (HistoryService) ServiceFactory.getInstance().getService(ServiceType.HISTORY);
    MemberService memberService = (MemberService) ServiceFactory.getInstance().getService(ServiceType.MEMBER);
    
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
        loadMemberDetail();
    }

    // ------------GO BACK TO MEMBERS PAGE----------------
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        this.root.getChildren().clear();
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/ManageMembers.fxml"));
        this.root.getChildren().add(node);
    }

    // ------------DISPLAY THE MEMBER DETAILS ON A LABEL----------------
    private void loadMemberDetail() {
        try {
            MemberDto memberDto = memberService.get(id);
            lblDetail.setText(memberDto.getMemberId() + " | " + memberDto.getFirstName() + " " + memberDto.getLastName());
        } catch (Exception e) {
            showDialog("Error", "Error while loading member...");
        }
    }

    // ------------LOAD THE DATA TO THE TABLE----------------
    private void loadTable() {
        try {
            ObservableList<HistoryTM> observableList = FXCollections.observableArrayList();
            ArrayList<HistoryTM> historyTMs = historyService.getMemberHistory(id);
            for (HistoryTM historyTM : historyTMs) {
                observableList.add(historyTM);
            } 
            tblMemHistory.setItems(observableList);
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

}
