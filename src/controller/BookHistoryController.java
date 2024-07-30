package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import dto.BooksDto;
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
import service.custom.BookService;
import service.custom.HistoryService;
import tableModel.HistoryTM;

public class BookHistoryController implements Initializable {
    @FXML
    private JFXButton btnBack;

    @FXML
    private TableColumn<HistoryTM, String> colBorrowCondition;

    @FXML
    private TableColumn<HistoryTM, String> colBorrowDate;

    @FXML
    private TableColumn<HistoryTM, String> colFinedReson;

    @FXML
    private TableColumn<HistoryTM, Double> colFines;

    @FXML
    private TableColumn<HistoryTM, String> colMemberId;

    @FXML
    private TableColumn<HistoryTM, String> colReturnCondition;

    @FXML
    private TableColumn<HistoryTM, String> colReturnDate;

    @FXML
    private Label lblDetail;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<HistoryTM> tblBookHistory;

    public static String bookID;

    HistoryService historyService = (HistoryService) ServiceFactory.getInstance().getService(ServiceType.HISTORY);
    BookService bookService = (BookService) ServiceFactory.getInstance().getService(ServiceType.BOOK);

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        this.root.getChildren().clear();
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/ManageBooks.fxml"));
        this.root.getChildren().add(node);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colBorrowCondition.setCellValueFactory(new PropertyValueFactory<>("borrowCondition"));
        colReturnCondition.setCellValueFactory(new PropertyValueFactory<>("returnCondition"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colFines.setCellValueFactory(new PropertyValueFactory<>("fines"));
        colFinedReson.setCellValueFactory(new PropertyValueFactory<>("finedReason"));

        loadTable();
        loadBookDetail();
    }

    private void loadBookDetail() {
        try {
            BooksDto booksDto = bookService.get(bookID);
            lblDetail.setText(booksDto.getBookId() + " | " + booksDto.getName());
        } catch (Exception e) {
            showDialog("Error", "Error while loading book details...");
        }
    }

    private void loadTable() {

        try {
            ObservableList<HistoryTM> observableList = FXCollections.observableArrayList();
            ArrayList<HistoryTM> historyTMs = historyService.getBookHistory(bookID);
            for (HistoryTM historyTM : historyTMs) {
                observableList.add(historyTM);
            } 
            tblBookHistory.setItems(observableList);
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
