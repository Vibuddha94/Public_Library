package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import dto.UserDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import service.ServiceFactory;
import service.ServiceFactory.ServiceType;
import service.custom.UserService;
import tableModel.UserTM;

public class ManageUserController implements Initializable {
    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXCheckBox cbIsAdmin;

    @FXML
    private TableColumn<UserTM, String> colAddress;

    @FXML
    private TableColumn<UserTM, String> colContactNumber;

    @FXML
    private TableColumn<UserTM, String> colDob;

    @FXML
    private TableColumn<UserTM, String> colFirstName;

    @FXML
    private TableColumn<UserTM, String> colLastName;

    @FXML
    private TableColumn<UserTM, String> colPassword;

    @FXML
    private TableColumn<UserTM, String> colUserId;

    @FXML
    private TableColumn<UserTM, Boolean> colIsAdmin;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContactNumber;

    @FXML
    private TextField txtDob;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserId;

    @FXML
    private AnchorPane root;

    private UserService userService = (UserService) ServiceFactory.getInstance().getService(ServiceType.USER);

    // ------------DELETE AN USER----------------
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            String userId = txtUserId.getText();
            if (userId.equals("ADMIN")) {
                showDialog("System Error", "Can't delete the Admin");
                clear();
            } else {
                String response = userService.deleteUser(userId);
                clear();
                loadTable();
                showDialog("Message", response);
            }
        } catch (Exception e) {
            showDialog("Error", "Error while deleting user...");
        }
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        this.root.getChildren().clear();
        Parent node = FXMLLoader.load(this.getClass().getResource("/view/AdminView.fxml"));
        this.root.getChildren().add(node);
    }

    // ------------SAVE NEW USER----------------
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            UserDto userDto = new UserDto();
            setValueTo(userDto);
            String response = userService.saveUser(userDto);
            clear();
            loadTable();
            showDialog("Message", response);
        } catch (Exception e) {
            showDialog("Error", "Error while saving user...");
        }
    }

    // ------------UPDATE AN EXISTING USER----------------
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            UserDto userDto = new UserDto();
            setValueTo(userDto);
            String response = userService.updateUser(userDto);
            clear();
            loadTable();
            showDialog("Message", response);
        } catch (Exception e) {
            showDialog("Error", "Error while updating user...");
        }
    }

    // ------------LOAD THE DATA FROM CLICKED ROW----------------
    @FXML
    void tblUserOnMouseClicked(MouseEvent event) {
        UserTM dto = tblUser.getSelectionModel().getSelectedItem();
        setValueFrom(dto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colIsAdmin.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));

        loadTable();
    }

    // ------------IMPORT THE DATA TO THE TABLE FROM DATABASE----------------
    private void loadTable() {
        try {
            ArrayList<UserDto> userDtos = userService.getAll();
            ObservableList<UserTM> observableList = FXCollections.observableArrayList();

            for (UserDto userDto : userDtos) {
                UserTM tm = new UserTM(userDto.getUserId(), userDto.getFirstName(), userDto.getLastName(),
                        userDto.getDob(), userDto.getAddress(), userDto.getContactNumber(), userDto.getPassword(),
                        userDto.getIsAdmin());
                observableList.add(tm);
            }

            loadUserId(userDtos.get(userDtos.size() - 1).getUserId());
            tblUser.setItems(observableList);

        } catch (Exception e) {
            ButtonType buttonType = new ButtonType("OK", ButtonData.OK_DONE);
            new Alert(Alert.AlertType.ERROR, "Error while loading table...", buttonType);
        }
    }

    // ------------SET VALUES TO THE TEXT FIELDS FROM A DTO----------------
    private void setValueFrom(UserTM dto) {
        txtUserId.setText(dto.getUserId());
        txtFirstName.setText(dto.getFirstName());
        txtLastName.setText(dto.getLastName());
        txtDob.setText(dto.getDob());
        txtAddress.setText(dto.getAddress());
        txtContactNumber.setText(dto.getContactNumber());
        txtPassword.setText(dto.getPassword());
        cbIsAdmin.setSelected(dto.getIsAdmin());
    }

    // ------------SET VALUES TO A DTO FROM TEXT FIELDS----------------
    private void setValueTo(UserDto dto) {
        dto.setUserId(txtUserId.getText());
        dto.setFirstName(txtFirstName.getText());
        dto.setLastName(txtLastName.getText());
        dto.setDob(txtDob.getText());
        dto.setAddress(txtAddress.getText());
        dto.setContactNumber(txtContactNumber.getText());
        dto.setPassword(txtPassword.getText());
        dto.setIsAdmin(cbIsAdmin.isSelected());
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

    // ------------CLEAR THE FORM----------------
    private void clear() {
        txtUserId.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtDob.clear();
        txtAddress.clear();
        txtContactNumber.clear();
        txtPassword.clear();
        cbIsAdmin.setSelected(false);
        ;
    }

    // ------------LOAD THE NEXT USER ID AUTOMATICALLY----------------
    private void loadUserId(String id) {
        if (id.equals("ADMIN")) {
            txtUserId.setText("PLU001");
        } else {
            String[] split = id.split("PLU");
            int number = Integer.valueOf(split[1]);
            number++;
            txtUserId.setText(number >= 100 ? "PLU" + number : number < 10 ? "PLU00" + number : "PLU0" + number);
        }
    }

}
