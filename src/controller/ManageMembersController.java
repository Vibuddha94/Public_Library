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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import security.LoginSecurity;
import service.ServiceFactory;
import service.ServiceFactory.ServiceType;
import service.custom.MemberService;
import tableModel.MembersTM;

public class ManageMembersController implements Initializable {
    @FXML
    private JFXButton bbtnHome;

    @FXML
    private JFXButton btnDeelete;

    @FXML
    private JFXButton btnHistory;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<MembersTM, String> colAddress;

    @FXML
    private TableColumn<MembersTM, String> colContactNumber;

    @FXML
    private TableColumn<MembersTM, String> colDob;

    @FXML
    private TableColumn<MembersTM, String> colFirstName;

    @FXML
    private TableColumn<MembersTM, String> colLastName;

    @FXML
    private TableColumn<MembersTM, String> colMemberId;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<MembersTM> tblMembet;

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
    private TextField txtMemberId;

    MemberService memberService = (MemberService) ServiceFactory.getInstance().getService(ServiceType.MEMBER);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contNumber"));

        loadTable();
    }


    //------------GO BACK TO HOME PAGE----------------
    @FXML
    void bbtnHomeOnAction(ActionEvent event) throws IOException, Exception {
        goToHome(LoginSecurity.getInstance().getIsAdmin()); 
    }

    //------------DELETE EXISTING MEMBER----------------
    @FXML
    void btnDeeleteOnAction(ActionEvent event) {
        try {
            String memberId = txtMemberId.getText();
            String response = memberService.deleteUser(memberId);
            showDialog("Message", response);
            clearForm();
            loadTable();
        } catch (Exception e) {
            showDialog("Error", "Error while deleting member...");
        }
    }

    //------------CHECK SELECTED MEMBER'S BORROWING HISTORY----------------
    @FXML
    void btnHistoryOnAction(ActionEvent event) throws IOException {
        if (tblMembet.getSelectionModel().getSelectedIndex()>=0) { //---CHECKING ANY ITEM HAS BEEN SELECTED ON THE TABLE
            MemberHistoryController.id = txtMemberId.getText(); //---PASSING SELECTED MEMBER'S ID TO "MemberHistoryController"
            this.root.getChildren().clear();
            Parent node = FXMLLoader.load(this.getClass().getResource("/view/MemberHistory.fxml"));
            this.root.getChildren().add(node);
        } else {
            showDialog("Error", "Please select a member to see the history...");
        }
        
    }

    //------------SAVE A NEW MEMBER IN THE SYSTEM----------------
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            MemberDto memberDto = new MemberDto();
            setValueToDto(memberDto);
            String response = memberService.saveUser(memberDto);
            showDialog("Message", response);
            clearForm();
            loadTable();
        } catch (Exception e) {
            showDialog("Error", "Error while saving member...");
        }
    }

    //------------UPDATE EXISTING MEMBER'S DETAILS----------------
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            MemberDto memberDto = new MemberDto();
            setValueToDto(memberDto);
            String response = memberService.updateUser(memberDto);
            showDialog("Message", response);
            clearForm();
            loadTable();
        } catch (Exception e) {
            showDialog("Error", "Error while updating member");
        }
    }

    //------------GET THE DETAILS OF SELECTED MEMBER FROM TABLE----------------
    @FXML
    void tblMemberOnMouseClocked(MouseEvent event) {
        setValueFromTM(tblMembet.getSelectionModel().getSelectedItem());
    }

    //------------TO REFRESH THE PAGE AFTER SELECTING A MEMBER FROM THE TABLE. ID IS NOT EDITABLE. SO OTHERWISE WON'T BE ABLE TO REMOVE THE EXIXTING ID  AND ENTER NEW ONE----------------
    @FXML
    void txtMemberIdOnMouseClicked(MouseEvent event) {
        clearForm();
        loadTable();
    }

    //------------GETTING MEMBERS DATA FROM DATABASE----------------
    private void loadTable() {
        try {
            ObservableList<MembersTM> observableList = FXCollections.observableArrayList();
            ArrayList<MemberDto> memberDtos = memberService.getAll();
            for (MemberDto memberDto : memberDtos) {
                MembersTM tm = new MembersTM(memberDto.getMemberId(), memberDto.getFirstName(), memberDto.getLastName(), memberDto.getDob(), memberDto.getAddress(), memberDto.getContNumber());
                observableList.add(tm); 
            }
            loadMemberId(memberDtos.getLast().getMemberId()); //---GETTING LAST ID AND GENERATE THE NEXT
            tblMembet.setItems(observableList);
        } catch (Exception e) {
            showDialog("Error", "Error while loading table...");
            e.printStackTrace();
        }        
    }

    //------------GENERATE NEXT ID----------------
    private void loadMemberId(String memberId) {
            String[] split = memberId.split("MEM");
            int number = Integer.valueOf(split[1]);
            number++;
            String id = 10>number ? "MEM0000" + number : 100>number ? "MEM000" + number : 1000>number ? "MEM00" + number : 10000> number ? "MEM0" + number : "MEM" + number;
            txtMemberId.setText(id);
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

    // ------------SET VALUES TO THE TEXT FIELDS FROM A TM----------------
    private void setValueFromTM(MembersTM dto) {
        txtMemberId.setText(dto.getMemberId());
        txtFirstName.setText(dto.getFirstName());
        txtLastName.setText(dto.getLastName());
        txtDob.setText(dto.getDob());
        txtAddress.setText(dto.getAddress());
        txtContactNumber.setText(dto.getContNumber());
    }

    // ------------SET VALUES TO A DTO FROM TEXT FIELDS----------------
    private void setValueToDto(MemberDto dto) {
        dto.setMemberId(txtMemberId.getText());
        dto.setFirstName(txtFirstName.getText());
        dto.setLastName(txtLastName.getText());
        dto.setDob(txtDob.getText());
        dto.setAddress(txtAddress.getText());
        dto.setContNumber(txtContactNumber.getText());
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

    //------------CLEAR FORM----------------
    private void clearForm(){
        txtMemberId.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtDob.clear();
        txtAddress.clear();
        txtContactNumber.clear();
        txtFirstName.requestFocus();
    }

}
