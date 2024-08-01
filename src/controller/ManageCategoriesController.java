package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import dto.CategoryDto;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import security.LoginSecurity;
import service.ServiceFactory;
import service.ServiceFactory.ServiceType;
import service.custom.CategoryService;
import tableModel.CatTM;

public class ManageCategoriesController implements Initializable {
     @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<CatTM, String> colCatCode;

    @FXML
    private TableColumn<CatTM, String> colCatName;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<CatTM> tblCategory;

    @FXML
    private TextField txtCatCode;

    @FXML
    private TextField txtCategory;

    CategoryService categoryService = (CategoryService) ServiceFactory.getInstance().getService(ServiceType.CATEGORY);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCatCode.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCatName.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadTable();
    }

    // ------------DELETE A CATEGORY----------------
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            String id = txtCatCode.getText();
            String response = categoryService.delete(id);
            showDialog("Message", response);
            clearForm();
            loadTable();
        } catch (Exception e) {
            showDialog("Error", "Error while deleting category...");
        }
    }

    // ------------GO BACK TO HOME PAGE----------------
    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException, Exception {
        goToHome(LoginSecurity.getInstance().getIsAdmin()); 
    }

    // ------------SAVE A NEW CATEGORY----------------
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            CategoryDto categoryDto = new CategoryDto(txtCatCode.getText(), txtCategory.getText());
            String response = categoryService.save(categoryDto);
            showDialog("Message", response);
            clearForm();
            loadTable();
        } catch (Exception e) {
            showDialog("Error", "Error whhile saving catagory...");
        }
    }

    // ------------UDATE A CATEGORY----------------
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            CategoryDto categoryDto = new CategoryDto(txtCatCode.getText(), txtCategory.getText());
            String response = categoryService.update(categoryDto);
            showDialog("Message", response);
            loadTable();
            clearForm();
        } catch (Exception e) {
            showDialog("Error", "Error while updating category...");
        }
    }

    // ------------LOAD THE DATA FROM CLICKED ROW----------------
    @FXML
    void tblCategoryOnMouseClicked(MouseEvent event) {
        CatTM catTM = tblCategory.getSelectionModel().getSelectedItem();
        txtCatCode.setText(catTM.getId());
        txtCategory.setText(catTM.getName());
    }

    // ------------INSERT DATA TO TE TABLE FROM DATABASE----------------
    private void loadTable() {
        try {
            ArrayList<CategoryDto> arrayList = categoryService.getAll();
            ObservableList<CatTM> observableList = FXCollections.observableArrayList();
            for (CategoryDto dto : arrayList) {
                CatTM tm = new CatTM(dto.getId(), dto.getName());
                observableList.add(tm);
            }
            tblCategory.setItems(observableList);
        } catch (Exception e) {
            showDialog("Error", "Error while loading table...");
            e.printStackTrace();
        }
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

    // ------------SHOW POP-UP DIALOGS----------------
    private void showDialog(String title, String content) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        ButtonType buttonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonType);
        dialog.setContentText(content);
        dialog.showAndWait();
    }

    // ------------CLEAR TEXT FIELDS----------------
    private void clearForm(){
        txtCatCode.clear();
        txtCategory.clear();
        root.requestFocus();
    }

}
