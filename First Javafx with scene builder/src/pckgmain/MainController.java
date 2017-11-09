/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pckgmain;

import database.DatabaseConnector;
import database.StudentDbUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Student;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import pckgCommon.AlertBox;

/**
 * FXML Controller class
 *
 * @author ashmeet
 */
public class MainController implements Initializable {
    
     private Connection conn = null;
    private OraclePreparedStatement pst = null;
    private OracleResultSet rs = null;
    Student student;
    StudentDbUtils std;
//    private ObservableList<Student> data;
    private File file;
    private FileChooser fileChooser;
    private InputStream is;
    FileInputStream fis;
//    private Stage window;
     private Image image;
    
    ObservableList<Student> data = FXCollections.observableArrayList();
    FilteredList<Student> filteredData = new FilteredList<>(data, e -> true);
    

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtRoll;
    @FXML
    private TextField txtGender;
    @FXML
    private TextField txtProgram;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private DatePicker typeDatePicker;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnUpdate;
    @FXML
    private AnchorPane nextstage;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colAddress;
    @FXML
    private TableColumn<?, ?> colPhone;
    @FXML
    private TableColumn<?, ?> colRoll;
    @FXML
    private TableColumn<?, ?> colGender;
    @FXML
    private TableColumn<?, ?> colProgram;
    @FXML
    private TableColumn<?, ?> colUsername;
    @FXML
    private TableColumn<?, ?> colPassword;
    @FXML
    private TableColumn<?, ?> colDateofbirth;
    @FXML
    private TextField txtSearchField;
    @FXML
    private Button btnbrowser;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnLoad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleNewAction(ActionEvent event) {
         clearFields();
    }

    private void handleCreateAction(ActionEvent event) throws FileNotFoundException, IOException {
        System.out.println("Date is : " + typeDatePicker.getEditor().getText());
        if (file != null) {
            fis = new FileInputStream(file);
            std = new StudentDbUtils();
            int id = std.getMaxId();
            id = id + 1;
            student = new Student();
            student.setId(id);
            student.setName(txtName.getText());
            student.setAddress(txtAddress.getText());
            student.setRoll_no(txtRoll.getText());
            student.setPhone_no(txtContact.getText());
            student.setGender(txtGender.getText());
             student.setProgram(txtProgram.getText());
            student.setUsername(txtUsername.getText());
            student.setPassword(txtPassword.getText());
            student.setDob(typeDatePicker.getEditor().getText());
            
            student.setFis(fis);
            
            if (std.createUser(student)) {
                clearFields();
                studentData();
                AlertBox.alert("Success", "Created Successfully ", "Record Saved ", "INFORMATION");
            }
        } else {
            AlertBox.alert("ERROR", "Upload Image ", "Please Select the image from the Device ", "ERROR");
        }
    
    }
    public void clearFields() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtRoll.clear();
        txtContact.clear();
        txtGender.clear();
        txtProgram.clear();
        txtUsername.clear();
        txtPassword.clear(); 
       //imgView.setImage(null);
    }

    @FXML
    private void handleUpdateAction(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            std = new StudentDbUtils();
            student = new Student();
            std.updateData(student);
            
            int id = Integer.parseInt(txtId.getText());
            student.setId(id);
            student.setName(txtName.getText());
            student.setAddress(txtAddress.getText());
            student.setRoll_no(txtRoll.getText());
            student.setPhone_no(txtContact.getText());
            student.setGender(txtGender.getText());
            student.setUsername(txtUsername.getText());
            student.setPassword(txtPassword.getText());
            
            std.updateData(student);
            studentData();
            clearFields();
        } else {
            AlertBox.alert("Error", "Cannot Update  ", "Cannot make update with empty ID ! ", "ERROR");
            
        }
        
    }
    


      private void studentData() {
        try {
            if ((conn = DatabaseConnector.databaseConnection()) != null) {
                String query = "SELECT * FROM  Student";
                pst = (OraclePreparedStatement) conn.prepareStatement(query);
                rs = (OracleResultSet) pst.executeQuery();
                while (rs.next()) {
                    int stdId = rs.getInt(1);
                    String stdName = rs.getString(2);
                    String stdAddress = rs.getString(3).toUpperCase();
                    String stdRoll = rs.getString(4).toUpperCase();
                    String stdContact = rs.getString(5).toUpperCase();
                    String stdGender = rs.getString(6).toUpperCase();
                    String stdUsername = rs.getString(7).toUpperCase();
                    String stdPassword = rs.getString(8).toUpperCase();
                    String stdDob = rs.getString(10);
                   

//                    System.out.println("ID " + stdId + " STd Name : " + stdName + " Address : " + stdAddress + " Roll : " + stdRoll);
                   // data.add(new Student(stdId, stdName, stdAddress, stdRoll, stdContact, stdGender,stdProgram, stdUsername, stdPassword, stdDob));
//                Double wage = rs.getDouble(10);
//                String stats = rs.getString(11).toUpperCase();
                } //end of while 
            }//end of if

        } catch (Exception e) {
        }
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        //name, address, phone_no, roll_no, gender, username, password
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colRoll.setCellValueFactory(new PropertyValueFactory<>("phone_no"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("roll_no"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("program"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colDateofbirth .setCellValueFactory(new PropertyValueFactory<>("Date of Birth"));
//        tableView.setItems(null);
    //    tableView.setItems(data);
        
    } //end of method 

    @FXML
    private void handleBrowseButton(ActionEvent event) {
    fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.gif", "*.png", "*.JPG")
        );
        Stage current = (Stage) btnNew.getScene().getWindow();
        file = fileChooser.showOpenDialog(current);
        
        if (file != null) {
            image = new Image(file.toURI().toString());
            //imgView.setImage(image);
        }
    }

   

    @FXML
    private void handleSaveAction(ActionEvent event) {
    }

    @FXML
    private void handleDeleteAction(ActionEvent event) {
             Integer id = Integer.parseInt(txtId.getText());

        StudentDbUtils std = new StudentDbUtils();
        System.out.println("ID : " + id.getClass());
        if (std.deleteUser(id)) {
            System.out.println("DELETED USer : " + id + " Successfully");
        }
    }

    @FXML
    private void handleLoadAction(ActionEvent event) {
        studentData();
    }

    @FXML
    private void handleSearchAction(KeyEvent event) {
txtSearchField.textProperty().addListener((observalbleValue, oldValue, newValue) -> {
            filteredData.setPredicate((Predicate<? super Student>) stdnt -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                Integer id = stdnt.getId();
                if (id.toString().contains(newValue)) {
                    return true;
                }
                if (stdnt.getName().toLowerCase().contains(lowerCaseFilter)) {
                    System.out.println("Student name : " + stdnt.getName());
                    return true;
    }
    

//                if (user.getLastName().toLowerCase().contains(lowerCaseFilter)) {
//                    return true;
//                }
                return false;
            });
        });
        SortedList<Student> sortedData = new SortedList<>(filteredData);
        System.out.println("Datas are : " + filteredData);
       // sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        //tableView.setItems(sortedData);
    }
}


