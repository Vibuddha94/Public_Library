package controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import dto.BooksDto;
import dto.BorrowDto;
import dto.Borrow_ReturnDetailDto;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import security.LoginSecurity;
import service.ServiceFactory;
import service.ServiceFactory.ServiceType;
import service.custom.BookService;
import service.custom.BorrowService;
import service.custom.MemberService;
import tableModel.BorrowTM;

public class ManageBorrowingController implements Initializable {
     @FXML
    private JFXButton brtnIssue;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnRemove;

    @FXML
    private TableColumn<BorrowTM, String> colBookid;

    @FXML
    private TableColumn<BorrowTM, String> colIssueCondition;

    @FXML
    private TableColumn<BorrowTM, String> colReturnDate;

    @FXML
    private JFXComboBox<String> comboBox;

    @FXML
    private Label lblBookDetail;

    @FXML
    private Label lblMemberDetail;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<BorrowTM> tblBorrow;

    @FXML
    private TextField txtBookId;

    @FXML
    private TextField txtBorrowId;

    @FXML
    private TextField txtMemberId;

    @FXML
    private TextField txtReturnDate;

    BorrowService borrowService = (BorrowService) ServiceFactory.getInstance().getService(ServiceType.BORROWING);
    MemberService memberService = (MemberService) ServiceFactory.getInstance().getService(ServiceType.MEMBER);
    BookService bookService = (BookService) ServiceFactory.getInstance().getService(ServiceType.BOOK);
    ArrayList<Borrow_ReturnDetailDto> detailDtos = new ArrayList<>();
    ObservableList<BorrowTM> issueList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colBookid.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colIssueCondition.setCellValueFactory(new PropertyValueFactory<>("isuueCondition"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        loadComboBox();
        loadBorrowId();
    }

    //------------ADDING BOOKS TO ISSUE LIST AND TABLE----------------
    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (lblBookDetail.getText().equals("Book Not Found or Not Available") || lblBookDetail.getText().equals("")) {
            showDialog("Error", "Please enter a valid book ID");
        } else {
            addToTable(new BorrowTM(txtBookId.getText(), comboBox.getSelectionModel().getSelectedItem(), txtReturnDate.getText()));
            lblBookDetail.setText("");
            txtReturnDate.clear();
            txtBookId.clear();
            comboBox.setValue(null);
        }  
    }

    //------------GO BACK TO HOME PAGE----------------
    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException, Exception {
        goToHome(LoginSecurity.getInstance().getIsAdmin()); 
    }

    //------------ISSUE THE BOOKS IN THE LIST----------------
    @FXML
    void btnIssueOnAction(ActionEvent event) {
        try {
            for (BorrowTM borrowTM : issueList) {
                detailDtos.add(new Borrow_ReturnDetailDto(txtBorrowId.getText(), borrowTM.getBookId(), borrowTM.getIsuueCondition(), "PENDING", borrowTM.getReturnDate(), 0.0, "PENDING"));
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date()); //---SET ISSUE DATE AS TODAYS DATE
            BorrowDto borrowDto = new BorrowDto(txtBorrowId.getText(), date, txtMemberId.getText(), detailDtos);
            String response = borrowService.save(borrowDto);
            showDialog("Message", response);
            clearAll();
            tblBorrow.setItems(issueList);
            loadBorrowId();
        } catch (Exception e) {
            showDialog("Error", "Error while issuing books...");
        }
    }

    //------------REMOVE SELECTED ITEM FROM ISSUING TABLE----------------
    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        if (tblBorrow.getSelectionModel().getSelectedIndex()>=0) {
            issueList.remove(tblBorrow.getSelectionModel().getSelectedItem()); //REMOVE SELECTED ITEM FROM LIST
        } else {
            showDialog("Error", "Select a book to remove...");
        }
    }

    //------------LOAD MEMBER DETAILS WHEN CLICK ON THE BOOK ID TEXT FIELD----------------
    @FXML
    void txtBookIdOnMouseClicked(MouseEvent event) {
        try {
            MemberDto memberDto = memberService.get(txtMemberId.getText());
            if (memberDto != null) {
                lblMemberDetail.setText(memberDto.getMemberId() + " | " + memberDto.getFirstName() + " " + memberDto.getLastName());
            } else {
                lblMemberDetail.setText("Member Not Found");
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    //------------LOAD BOOK DETAILS WHEN CLICK ON THE RETURN DATE TEXT FIELD----------------
    @FXML
    void txtReturnDateOnMouseClicked(MouseEvent event) {
        try {
            BooksDto booksDto = bookService.get(txtBookId.getText());
            if (booksDto != null && booksDto.getStatus().equals("Available")) {
                lblBookDetail.setText(booksDto.getBookId() + " | " + booksDto.getName());
                comboBox.setValue(booksDto.getCondition());
            } else {
                lblBookDetail.setText("Book Not Found or Not Available");
                comboBox.setValue(null);
            }
        } catch (Exception e) {
            System.out.println("Error");
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

   //------------ADDING DATA TO COMBO BOX BOOK CONDITION----------------
    private void loadComboBox() {
        ObservableList<String> observableList = FXCollections.observableArrayList("Good", "Damaged","Lost");
        comboBox.setItems(observableList);
    }

    //------------ADDING BOOKS TO ISSING TABLE----------------
    private void addToTable(BorrowTM borrowTM) {
        issueList.add(borrowTM);
        tblBorrow.setItems(issueList);
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

    //------------GENERATE NEXT ID----------------
    private void loadBorrowId() {
        String memberId;
        try {
            memberId = borrowService.getAllBorrowings().getLast().getBorrowId(); //---GETTING LAST ID
            String[] split = memberId.split("BOR");
            int number = Integer.valueOf(split[1]);
            number++;
            String id = 10>number ? "BOR0000" + number : 100>number ? "BOR000" + number : 1000>number ? "BOR00" + number : 10000> number ? "BOR0" + number : "BOR" + number;
            txtBorrowId.setText(id);
        } catch (Exception e) {
            showDialog("Error", "Error while loading borrow ID...");
            e.printStackTrace();
        }
        
    }

    //------------CLEAR FORM----------------
    private void clearAll(){
        txtMemberId.clear();
        txtReturnDate.clear();
        txtBookId.clear();
        lblBookDetail.setText("");
        lblMemberDetail.setText("");
        issueList.clear();
        detailDtos.clear();
    }


}
