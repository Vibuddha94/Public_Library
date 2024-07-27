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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import security.LoginSecurity;
import tableModel.BooksTM;

public class ManageBooksController implements Initializable {
    
    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnHistory;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<BooksTM, String> colAuthor;

    @FXML
    private TableColumn<BooksTM, String> colBookId;

    @FXML
    private TableColumn<BooksTM, String> colCatagory;

    @FXML
    private TableColumn<BooksTM, String> colCondition;

    @FXML
    private TableColumn<BooksTM, String> colName;

    @FXML
    private TableColumn<BooksTM, Double> colPrice;

    @FXML
    private TableColumn<BooksTM, String> colStatus;

    @FXML
    private JFXComboBox<?> comboBoxCategory;

    @FXML
    private JFXComboBox<?> comboBoxCondition;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<BooksTM> tblBooks;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtBookId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtStatus;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnHistoryOnAction(ActionEvent event) throws IOException {
        this.root.getChildren().clear();
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/BookHistory.fxml"));
        this.root.getChildren().add(node);
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException, Exception {
        goToHome(LoginSecurity.getInstance().getIsAdmin());        
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

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
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colCatagory.setCellValueFactory(new PropertyValueFactory<>("catCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colCondition.setCellValueFactory(new PropertyValueFactory<>("condition"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        loadTable();
    }

    private void loadTable() {
        ObservableList<BooksTM> observableList = FXCollections.observableArrayList();
        BooksTM tm = new BooksTM("111", "ART", "aaaa bbbb", "Available", "Good", "ccc", 50.0);
        observableList.add(tm);
        tblBooks.setItems(observableList);
    }

}
