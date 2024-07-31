package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import dto.BooksDto;
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
import service.custom.BookService;
import service.custom.CategoryService;
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
    private JFXComboBox<String> comboBoxCategory;

    @FXML
    private JFXComboBox<String> comboBoxCondition;

    @FXML
    private JFXComboBox<String> comboBoxStatus;


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

    BookService bookService = (BookService) ServiceFactory.getInstance().getService(ServiceType.BOOK);
    CategoryService categoryService = (CategoryService) ServiceFactory.getInstance().getService(ServiceType.CATEGORY);

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        try {
            String bookId = txtBookId.getText();
            String response = bookService.delete(bookId);
            showDialog("Message", response);
            comboBoxCategory.setValue("All");
        } catch (Exception e) {
            showDialog("Error", "Error while deleting book...");
        }
    }

    @FXML
    void btnHistoryOnAction(ActionEvent event) throws IOException {
        if (!comboBoxCategory.getSelectionModel().getSelectedItem().equals("All") && comboBoxCondition.getSelectionModel().getSelectedIndex()>=0) {
            BookHistoryController.bookID = txtBookId.getText();
            this.root.getChildren().clear();
            Parent node = FXMLLoader.load(this.getClass().getResource("/view/BookHistory.fxml"));
            this.root.getChildren().add(node);
        } else {
            showDialog("Error", "Please selct a book to see the history...");
        }
        
    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException, Exception {
        goToHome(LoginSecurity.getInstance().getIsAdmin());
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            BooksDto booksDto = new BooksDto();
            setValueFrom(booksDto);
            if (booksDto.getCatCode().equals("ALL")) {
                showDialog("Error", "Please select a catagory");
            } else {
                String response = bookService.save(booksDto);
                showDialog("Message", response);
                comboBoxCategory.setValue("All");
            }
        } catch (Exception e) {
            showDialog("Error", "Error while saving book...");
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            BooksDto booksDto = new BooksDto();
            setValueFrom(booksDto);
            String response = bookService.update(booksDto);
            showDialog("Message", response);
            comboBoxCategory.setValue("All");
        } catch (Exception e) {
            showDialog("Error", "Error while updating book...");
        }
    }

    @FXML
    void tblBooksOnMouseClicked(MouseEvent event) {
        setValueTo(tblBooks.getSelectionModel().getSelectedItem());
    }

    @FXML
    void cboxCategoryOnAction(ActionEvent event) {
        filterTable(getCatCode(comboBoxCategory.getSelectionModel().getSelectedItem()));
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

        loadCategoryCBox();
        comboBoxCategory.setValue("All");
        loadConditionCBox();
        loadStatusCBox();

    }

    private void loadStatusCBox() {
        ObservableList<String> status = FXCollections.observableArrayList("Available", "Issued","Lost");
        comboBoxStatus.setItems(status);
    }

    private void loadTable() {
        try {
            ObservableList<BooksTM> observableList = FXCollections.observableArrayList();
            ArrayList<BooksDto> booksDtos = bookService.getAll();
            for (BooksDto booksDto : booksDtos) {
                BooksTM tm = new BooksTM(booksDto.getBookId(), booksDto.getCatCode(), booksDto.getName(),
                        booksDto.getStatus(), booksDto.getCondition(), booksDto.getAuthor(), booksDto.getPrice());
                observableList.add(tm);
            }
            loadBookId(observableList.getLast().getBookId());
            tblBooks.setItems(observableList);
        } catch (Exception e) {
            showDialog("Error", "Error while loading table...");
        }
    }

    private void loadConditionCBox() {
        ObservableList<String> categories = FXCollections.observableArrayList("Good", "Damaged");
        comboBoxCondition.setItems(categories);
    }

    private void loadCategoryCBox() {
        try {
            ObservableList<String> categories = FXCollections.observableArrayList();
            ArrayList<CategoryDto> categoryDtos = categoryService.getAll();
            for (CategoryDto categoryDto : categoryDtos) {
                categories.add(categoryDto.getName());
            }
            comboBoxCategory.setItems(categories);
        } catch (Exception e) {
            showDialog("Error", "Error while loading categories...");
        }
    }

    // ------------LOAD THE HOME PAGE----------------
    private void goToHome(boolean isAdmin) throws IOException {
        if (isAdmin) {
            this.root.getChildren().clear();
            Parent node = FXMLLoader.load(this.getClass().getResource("/view/AdminView.fxml")); // ------------LOAD IF THE USER IS AN ADMIN----------------
            this.root.getChildren().add(node);
        } else {
            this.root.getChildren().clear();
            Parent node = FXMLLoader.load(this.getClass().getResource("/view/UserView.fxml")); // ------------LOAD IF THE USER IS A NON-ADMIN USER----------------                                                                              
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

    // ------------SET VALUES TO THE TEXT FIELDS FROM A TM----------------
    private void setValueTo(BooksTM tm) {
        txtBookId.setText(tm.getBookId());
        txtName.setText(tm.getName());
        txtAuthor.setText(tm.getAuthor());
        txtPrice.setText(String.valueOf(tm.getPrice()));
        comboBoxCategory.setValue(getCatName(tm.getCatCode()));
        comboBoxCondition.setValue(tm.getCondition());
        comboBoxStatus.setValue(tm.getStatus());
    }

    // ------------SET VALUES TO A DTO FROM TEXT FIELDS----------------
    private void setValueFrom(BooksDto dto) {
        dto.setBookId(txtBookId.getText());
        dto.setCatCode(getCatCode(comboBoxCategory.getSelectionModel().getSelectedItem()));
        dto.setName(txtName.getText());
        dto.setStatus(comboBoxStatus.getSelectionModel().getSelectedItem());
        dto.setCondition(comboBoxCondition.getSelectionModel().getSelectedItem());
        dto.setAuthor(txtAuthor.getText());
        dto.setPrice(Double.valueOf(txtPrice.getText()));
    }

    private String getCatCode(String selectedItem) {
        try {
            ArrayList<CategoryDto> categoryDtos = categoryService.getAll();
            for (CategoryDto categoryDto : categoryDtos) {
                if (categoryDto.getName().equals(selectedItem)) {
                    return categoryDto.getId();
                }
            }
            return null;
        } catch (Exception e) {
            showDialog("Error", "Error getting categories...");
            return null;
        }
    }

    private String getCatName(String catCode) {
        try {
            ArrayList<CategoryDto> categoryDtos = categoryService.getAll();
            for (CategoryDto categoryDto : categoryDtos) {
                if (categoryDto.getId().equals(catCode)) {
                    return categoryDto.getName();
                }
            }
            return null;
        } catch (Exception e) {
            showDialog("Error", "Error getting categories...");
            return null;
        }
    }

    private void filterTable(String category) { 
        try {
            if (category.equals("ALL")) {
                loadTable();
                clearForm();
            } else {
                ObservableList<BooksTM> observableList = FXCollections.observableArrayList();
                ArrayList<BooksDto> booksDtos = bookService.getAll();
                for (BooksDto booksDto : booksDtos) {
                    if (booksDto.getCatCode().equals(category)) {
                        BooksTM tm = new BooksTM(booksDto.getBookId(), booksDto.getCatCode(), booksDto.getName(),
                            booksDto.getStatus(), booksDto.getCondition(), booksDto.getAuthor(), booksDto.getPrice());
                        observableList.add(tm);
                    } 
                }
                tblBooks.setItems(observableList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showDialog("Error", "Error while loading table...");
        }
    }

    private void loadBookId(String bookId) {
        if (bookId == null) {
            txtBookId.setText("B00001");
        } else {
            String[] split = bookId.split("B");
            int number = Integer.valueOf(split[1]);
            number++;
            String id = 10>number ? "B0000" + number : 100>number ? "B000" + number : 1000>number ? "B00" + number : 10000> number ? "B0" + number : "B" + number;
            txtBookId.setText(id);
        }
    }

    private void clearForm(){
        txtName.clear();
        txtAuthor.clear();
        txtPrice.clear();
        comboBoxStatus.setValue(null);
        comboBoxCondition.setValue(null);
    }

}
