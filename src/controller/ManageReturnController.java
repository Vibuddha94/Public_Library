package controller;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import dto.Borrow_ReturnDetailDto;
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
import service.custom.BookService;
import service.custom.BorrowService;
import service.custom.FinesService;
import tableModel.BorrowTM;
import tableModel.ReturnTM;

public class ManageReturnController implements Initializable{
    @FXML
    private JFXButton btnChecked;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnReturn;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXCheckBox cbDamaged;

    @FXML
    private JFXCheckBox cbGood;

    @FXML
    private JFXCheckBox cbLost;

    @FXML
    private TableColumn<ReturnTM, String> colFinedReason;

    @FXML
    private TableColumn<ReturnTM, Double> colFines;

    @FXML
    private TableColumn<BorrowTM, String> colIssueBookId;

    @FXML
    private TableColumn<BorrowTM, String> colIssueCondition;

    @FXML
    private TableColumn<BorrowTM, String> colRetuenDate;

    @FXML
    private TableColumn<ReturnTM, String> colReturnBookId;

    @FXML
    private TableColumn<ReturnTM, String> colReturnCondition;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<BorrowTM> tblIssue;

    @FXML
    private TableView<ReturnTM> tblReturn;

    @FXML
    private TextField txtBorrowId;

    BorrowService borrowService = (BorrowService) ServiceFactory.getInstance().getService(ServiceType.BORROWING);
    FinesService finesService = (FinesService) ServiceFactory.getInstance().getService(ServiceType.FINE);
    BookService bookService = (BookService) ServiceFactory.getInstance().getService(ServiceType.BOOK);

    private ObservableList<ReturnTM> returnList = FXCollections.observableArrayList();

    @FXML
    void tblIssueOnMouseClicked(MouseEvent event) {
        setUnselected();
    }
    
    @FXML
    void cbDamagedOnAction(ActionEvent event) {
        cbGood.setSelected(false);
        cbLost.setSelected(false);
    }

    @FXML
    void cbGoodOnAction(ActionEvent event) {
        cbDamaged.setSelected(false);
        cbLost.setSelected(false);
        if (tblIssue.getSelectionModel().getSelectedItem().getIsuueCondition().equals("Damaged")) {
            showDialog("Error", "Issued a damaged book. Please check again...");
            cbGood.setSelected(false);
        }
    }

    @FXML
    void cbLostOnAction(ActionEvent event) {
        cbDamaged.setSelected(false);
        cbGood.setSelected(false);
    }

    @FXML
    void btnCheckedOnAction(ActionEvent event) throws ParseException {
        if (cbGood.isSelected() || cbDamaged.isSelected() || cbLost.isSelected()) {
            BorrowTM borrowTM = tblIssue.getSelectionModel().getSelectedItem();
            Double fines = getFines(borrowTM);
            String finedReson = getFinedReason(borrowTM);
            String returnCondition = getCondtion();
            addToRetunTable(new ReturnTM(borrowTM.getBookId(), returnCondition, finedReson, fines));
            tblIssue.getItems().remove(tblIssue.getSelectionModel().getSelectedIndex());
            setUnselected();
        } else {
            showDialog("Error", "Select book condition");
        }
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException, Exception {
        goToHome(LoginSecurity.getInstance().getIsAdmin()); 
    }

    @FXML
    void btnReturnOnAction(ActionEvent event) {
        try {
          ArrayList<Borrow_ReturnDetailDto> detailDtos = new ArrayList<>();
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          String returnDate = sdf.format(new Date());
          for (ReturnTM returnTM : returnList) {
            detailDtos.add(new Borrow_ReturnDetailDto(txtBorrowId.getText(), returnTM.getBookId(), null, returnTM.getReturnCondition(), returnDate, returnTM.getFines(), returnTM.getFinedReason()));
          }  
          String response = borrowService.update(detailDtos);
          showDialog("Message", response);
          returnList.clear();
          txtBorrowId.clear();
        } catch (Exception e) {
            showDialog("Error", "Error while returning books...");
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        loadTableBorrow(txtBorrowId.getText());
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
        colIssueBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colIssueCondition.setCellValueFactory(new PropertyValueFactory<>("isuueCondition"));
        colRetuenDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        colReturnBookId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colReturnCondition.setCellValueFactory(new PropertyValueFactory<>("returnCondition"));
        colFines.setCellValueFactory(new PropertyValueFactory<>("fines"));
        colFinedReason.setCellValueFactory(new PropertyValueFactory<>("finedReason"));

    }

    private void loadTableBorrow(String borrowId){
        try {
            ArrayList<Borrow_ReturnDetailDto> detailDtos = borrowService.getDetail(borrowId);
            if (detailDtos == null) {
                showDialog("Error", "Plese enter a valid ID...");
            } else {
                ObservableList<BorrowTM> observableList = FXCollections.observableArrayList();
                for (Borrow_ReturnDetailDto detailDto : detailDtos) {
                    observableList.add(new BorrowTM(detailDto.getBookId(), detailDto.getBorrowCondition(), detailDto.getReturnDate()));
                }
                tblIssue.setItems(observableList);
            }
        } catch (Exception e) {
            showDialog("Error", "Error while loading table...");
        }        
    }

    private void addToRetunTable(ReturnTM returnTM){
        returnList.add(returnTM);
        tblReturn.setItems(returnList);
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

    private Double getFines(BorrowTM borrowTM) {
        try {
            Double fines = 0.0;
            
            if (cbLost.isSelected()) {
                Double bookPrice = bookService.get(borrowTM.getBookId()).getPrice();
                Double factor = finesService.get(1).getLost();
                fines = bookPrice*factor;
                return fines;
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date day1 = sdf.parse(borrowTM.getReturnDate());
                Date day2 = new Date();
                Long dif = TimeUnit.DAYS.convert(day2.getTime()-day1.getTime(),TimeUnit.MILLISECONDS);
                if (dif>0) {
                    Double latePerDay = finesService.get(1).getLate();
                    fines += latePerDay*dif;
                }

                if (borrowTM.getIsuueCondition().equals("Good") && cbDamaged.isSelected()) {
                    Double bookPrice = bookService.get(borrowTM.getBookId()).getPrice();
                    Double factor = finesService.get(1).getDamage();
                    fines += bookPrice*factor;
                } 

                return fines; 
            }      
        } catch (Exception e) {
            showDialog("Error", "Error while calculating fines...");
            return 0.0;
        }
        
    }

    private String getFinedReason(BorrowTM borrowTM) throws ParseException {
        String reason = "No Fines";
        
        if (cbLost.isSelected()) {
            return "Lost";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date day1 = sdf.parse(borrowTM.getReturnDate());
            Date day2 = new Date();
            Long dif = TimeUnit.DAYS.convert(day2.getTime()-day1.getTime(),TimeUnit.MILLISECONDS);
            if (dif>0) {
                if (borrowTM.getIsuueCondition().equals("Good") && cbDamaged.isSelected()) {
                    return "Late, Damaged";
                }
                return "Late";
            }
            if (borrowTM.getIsuueCondition().equals("Good") && cbDamaged.isSelected()) {
                return "Damaged";
            }

            return reason;
        }
    }

    private String getCondtion() {
        
        if (cbDamaged.isSelected()) {
            return "Damaged";
        } else if (cbLost.isSelected()) {
            return "Lost";
        } else {
            return "Good";
        }
    }

    private void setUnselected() {
        cbGood.setSelected(false);
        cbLost.setSelected(false);
        cbDamaged.setSelected(false);
    }

}
