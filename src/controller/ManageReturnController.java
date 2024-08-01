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

    private BorrowService borrowService = (BorrowService) ServiceFactory.getInstance().getService(ServiceType.BORROWING);
    private FinesService finesService = (FinesService) ServiceFactory.getInstance().getService(ServiceType.FINE);
    private BookService bookService = (BookService) ServiceFactory.getInstance().getService(ServiceType.BOOK);

    private ObservableList<ReturnTM> returnList = FXCollections.observableArrayList();

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

    //------------WHEN CLICK ON THE TAVLE UNSELECT THE CHECK BOXES----------------
    @FXML
    void tblIssueOnMouseClicked(MouseEvent event) {
        setUnselected();
    }
    
    //------------WHEN SELECT "DAMAGED" UNSELECT THE OTHER TWO----------------
    @FXML
    void cbDamagedOnAction(ActionEvent event) {
        if (tblIssue.getSelectionModel().getSelectedIndex()>=0) { //---CHECKING IF ANYTHING SELECTED ON THE TABLE
            cbGood.setSelected(false);
            cbLost.setSelected(false);
        } else {
           showDialog("Error", "Select a book..."); 
           setUnselected();
        }
        
    }

    //------------WHEN SELECT "GOOD" UNSELECT THE OTHER TWO----------------
    @FXML
    void cbGoodOnAction(ActionEvent event) {
        if (tblIssue.getSelectionModel().getSelectedIndex()>=0) { //---CHECKING IF ANYTHING SELECTED ON THE TABLE
            cbDamaged.setSelected(false);
            cbLost.setSelected(false);
            if (tblIssue.getSelectionModel().getSelectedItem().getIsuueCondition().equals("Damaged")) { //---TO PREVENT CHANGE BOOK CONDITION FROM "DAMAGED",
                showDialog("Error", "Issued a damaged book. Please check again...");               //TO "GOOD" WHILE RETURNING
                cbGood.setSelected(false);
            }
        } else {
           showDialog("Error", "Select a book..."); 
           setUnselected();
        }
        
    }


    //------------WHEN SELECT "LOST" UNSELECT THE OTHER TWO----------------
    @FXML
    void cbLostOnAction(ActionEvent event) {
        if (tblIssue.getSelectionModel().getSelectedIndex()>=0) { //---CHECKING IF ANYTHING SELECTED ON THE TABLE
            cbDamaged.setSelected(false);
            cbGood.setSelected(false);
        } else {
           showDialog("Error", "Select a book..."); 
           setUnselected();
        }
        
    }

    //------------CHECK IN THE BOOKS TO RETURN TABLE----------------
    @FXML
    void btnCheckedOnAction(ActionEvent event) throws ParseException {
        if (cbGood.isSelected() || cbDamaged.isSelected() || cbLost.isSelected()) {  //---CHECKING IF THE RETURN CONDITION IS SELECTED
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

    //------------GO BACK TO HOME PAGE----------------
    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException, Exception {
        goToHome(LoginSecurity.getInstance().getIsAdmin()); //---CHECKING THE LOGIN DETAIL
    }

    //------------UPDATE THE DATABASE AND RETURN BOOKS----------------
    @FXML
    void btnReturnOnAction(ActionEvent event) {
        try {
          ArrayList<Borrow_ReturnDetailDto> detailDtos = new ArrayList<>();
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          String returnDate = sdf.format(new Date()); //---GETTING TODAYS DATE AS ACTUAL RETURN DATE
          for (ReturnTM returnTM : returnList) {
            detailDtos.add(new Borrow_ReturnDetailDto(txtBorrowId.getText(), returnTM.getBookId(), null, returnTM.getReturnCondition(), returnDate, returnTM.getFines(), returnTM.getFinedReason()));
          }  
          String response = borrowService.update(detailDtos);
          if (response.equals("Success")) {
            String summery = getSummery();
            showDialog("Message", summery);
            returnList.clear();
            txtBorrowId.clear();
          } else {
            showDialog("Message", response);
          }
          
        } catch (Exception e) {
            showDialog("Error", "Error while returning books...");
        }
    }

    //------------SEARCHH THE BORROW ID----------------
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

    //------------CHECK THE ID AND LOAD THE ISSUED BOOKS CONTAINING IIN THE ID----------------
    private void loadTableBorrow(String borrowId){
        try {
            ArrayList<Borrow_ReturnDetailDto> detailDtos = borrowService.getDetail(borrowId);
            if (detailDtos == null) {  //---CHECK THE VALIDITY OF ID
                showDialog("Error", "Plese enter a valid ID...");
            } else {
                ObservableList<BorrowTM> observableList = FXCollections.observableArrayList();
                for (Borrow_ReturnDetailDto detailDto : detailDtos) {
                    if (detailDto.getReturnCondition().equals("PENDING")) { //---CHECKING IF THE BOOKS HAS BEEN ALREADY RETURNED
                        observableList.add(new BorrowTM(detailDto.getBookId(), detailDto.getBorrowCondition(), detailDto.getReturnDate()));
                    } 
                }
                if (observableList.isEmpty()) { //---PREVENT RETURN SAME BOOKS TWICE.
                    showDialog("Error", "All the books in this ID has been returned. \nPlease check again...");
                } else {
                    tblIssue.setItems(observableList);
                }
                
            }
        } catch (Exception e) {
            showDialog("Error", "Error while loading table...");
        }        
    }

    //------------ADDING BOOKS TO THE RETURN TABLE----------------
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

    //------------CALCULATING FINES----------------
    private Double getFines(BorrowTM borrowTM) {
        try {
            Double fines = 0.0;
            //====("LOST" AND "DAMAGED" FINES DEPENDING ON THE PRICE OF THE BOOK)===

            if (cbLost.isSelected()) { //---IF THE BOOK IS LOST, NOT ADDING ANY OTHER FINES
                Double bookPrice = bookService.get(borrowTM.getBookId()).getPrice();
                Double factor = finesService.get(1).getLost();  //---FINE IS SAVED AS A MULTIPLICATION FACTOR
                fines = bookPrice*factor;
                return fines;
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date day1 = sdf.parse(borrowTM.getReturnDate());
                Date day2 = new Date();
                Long dif = TimeUnit.DAYS.convert(day2.getTime()-day1.getTime(),TimeUnit.MILLISECONDS); //---CALCULATING DATE DIFFERENCE BETWEEN TODAYS DATE AND RETURN DATE GIVEN WHILE BORROWING
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

    //------------GETTING REASONS FOR THE FINES----------------
    private String getFinedReason(BorrowTM borrowTM) throws ParseException {
        String reason = "No Fine";
        
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

    //------------GETTING THE BOOK RETURNING CONDITION FROM CHECK BOXES----------------
    private String getCondtion() {
        
        if (cbDamaged.isSelected()) {
            return "Damaged";
        } else if (cbLost.isSelected()) {
            return "Lost";
        } else {
            return "Good";
        }
    }

    //------------GET RETURN SUMMERY----------------
    private String getSummery() {
        double total = 0.0;
        int count = returnList.size();
        for (ReturnTM returnTM : returnList) {
            total += returnTM.getFines();
        }
       
        String one = "Total books returned  :      " + count;
        String two = "\nTotal fines to pay    :      " + total;
        

        String summery = one + two;
        return summery;
        
    }

    //------------SET UNSELECTED THE CHECK BOXES----------------
    private void setUnselected() {
        cbGood.setSelected(false);
        cbLost.setSelected(false);
        cbDamaged.setSelected(false);
    }

}
