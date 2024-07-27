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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import security.LoginSecurity;
import service.ServiceFactory;
import service.ServiceFactory.ServiceType;
import service.custom.CategoryService;
import service.custom.UserService;
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

    UserService service = (UserService) ServiceFactory.getInstance().getService(ServiceType.USER);
    CategoryService categoryService = (CategoryService) ServiceFactory.getInstance().getService(ServiceType.CATEGORY);

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

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
        colCatCode.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCatName.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadTable();
    }

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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
