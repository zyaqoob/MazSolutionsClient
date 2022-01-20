/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Student;
import classes.Teacher;
import classes.TeacherCourse;
import classes.User;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.ws.rs.core.GenericType;
import restful.StudentRESTClient;
import restful.TeacherCourseRESTClient;
import restful.TeacherRESTClient;
import restful.UserRESTClient;

/**
 *
 * @author Miguel Sanchez
 */
public class WindowStudentAdminController {
    
    @FXML
    private Label lblStudents;
    
    @FXML
    private Label lblTeachers;
    
    @FXML
    private Label lblSubjects;
    
    @FXML
    private Label lblCourses;
    
    @FXML
    private Label lblTeacherCourses;
    
    @FXML
    private Button btnCreate;
    
    @FXML
    private Button btnDelete;
    
    @FXML
    private Button btnPrint;
    
    @FXML
    private ImageView ivSearch;
    
    @FXML
    private ImageView ivTick;
    
    @FXML
    private ImageView ivX;
    
    @FXML
    private TableView<Student> tblStudents;
    
    @FXML
    private TableColumn<Student, String> tbcFullName;
    
    @FXML
    private TableColumn<Student, String> tbcCourse;
    
    @FXML
    private TableColumn<Student, String> tbcYear;
    
    @FXML
    private TableColumn<Student, String> tbcEmail;
    
    @FXML
    private TableColumn<Student, String> tbcTelephone;
    
    @FXML
    private TableColumn<Student, Date> tbcBirthDate;
    
    
    private ObservableList<Student> studentsData;
   
    
    public void initStage(Parent root) {
        //LOGGER.info("Stage initiated");
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        
        tbcFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbcTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        tbcBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        tbcCourse.setCellValueFactory(new PropertyValueFactory<>("course"));        
        tblStudents.setEditable(true);
        ivTick.setVisible(false);
        ivX.setVisible(false);
        btnDelete.setDisable(true);
        StudentRESTClient rest = new StudentRESTClient();
        studentsData = FXCollections.observableArrayList(rest.findAllStudents(new GenericType<List<Student>>() {
        }));
        //teacherCourses = FXCollections.observableArrayList(new TeacherCourseRESTClient().findAllTeacherCourses(new GenericType<List<TeacherCourse>>() {
        //}));
        //Table column FullName editable with textField
        tbcFullName.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcFullName.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    ((User) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).setFullName(s.getNewValue());
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcCourse);
                    tblStudents.edit(s.getTablePosition().getRow(), tbcCourse);
                });
        tbcFullName.setOnEditCancel((CellEditEvent<Student, String> s) -> {
            tblStudents.refresh();
            btnCreate.setDisable(false);
        });
        //Table column Username editable with textField
        tbcCourse.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcCourse.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    ((User) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).setLogin(s.getNewValue());
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcYear);
                    tblStudents.edit(s.getTablePosition().getRow(), tbcYear);
                });
        //Table column Email editable with textField
        tbcYear.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcYear.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    ((User) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).setEmail(s.getNewValue());
                    //tblTeachers.getColumns().add(tbcEmail);
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcEmail);
                    tblStudents.edit(s.getTablePosition().getRow(), tbcEmail);
                });
        //Table column Telephone editable with textField
        tbcEmail.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcEmail.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                        ((User) s.getTableView().getItems().get(
                                s.getTablePosition().getRow())).setTelephone(s.getNewValue());
                        tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcTelephone);
                        tblStudents.edit(s.getTablePosition().getRow(), tbcTelephone);
                        // tblTeachers.getColumns().add(tbcTelephone);
                    
                });
        tbcTelephone.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcTelephone.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    if (!s.getNewValue().matches("[0-9]{9}")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid telephone value", ButtonType.OK);
                        alert.show();
                    } else {
                        ((User) s.getTableView().getItems().get(
                                s.getTablePosition().getRow())).setTelephone(s.getNewValue());
                        tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcTelephone);
                        tblStudents.edit(s.getTablePosition().getRow(), tbcTelephone);
                        // tblTeachers.getColumns().add(tbcTelephone);
                    }
                });
        /**
         * Callback<TableColumn<User, DatePicker>, TableCell<User, DatePicker>>
         * cellFactoryForDatePicker = (TableColumn<User, DatePicker> t) -> new
         * TextFieldTableCell(new StringConverter() {
         *
         * @Override public String toString(Object object) { return
         * object.toString(); // throw new UnsupportedOperationException("Not
         * supported yet."); //To change body of generated methods, choose Tools
         * | Templates. }
         *
         * @Override public Object fromString(String string) { return string;
         * //throw new UnsupportedOperationException("Not supported yet."); //To
         * change body of generated methods, choose Tools | Templates. } });*
         */
        Callback<TableColumn<Student, Date>, TableCell<Student, Date>> dateCellFactory
                = (TableColumn<Student, Date> param) -> new DatePickerCellStudent();
        tbcBirthDate.setCellFactory(dateCellFactory);
        tbcBirthDate.setOnEditCommit(
                (TableColumn.CellEditEvent<Student, Date> s) -> {
                    ((User) s.getTableView().getItems()
                            .get(s.getTablePosition().getRow()))
                            .setBirthDate(s.getNewValue());
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcCourse);
                    tblStudents.edit(s.getTablePosition().getRow(), tbcCourse);
                });
       /* tbcSalary.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcSalary.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    if (!t.getNewValue().matches("[0-9]?{5}")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid salary value", ButtonType.OK);
                        alert.show();
                    } else {
                        ((Teacher) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setSalary(Float.valueOf(t.getNewValue()));
                        tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcSalary);
                        // tblTeachers.getColumns().add(tbcTelephone);
                    }
                });*/
        ObservableList<String> name;
        //tbcCourse.setCellFactory(ChoiceBoxTableCell.forTableColumn(name));
        tblStudents.setItems(studentsData);
        btnCreate.setOnAction(this::creation);
        btnDelete.setOnAction(this::delete);
        
        stage.show();
    }
    
    public void creation(ActionEvent action) {
        studentsData.add(new Student());
        tblStudents.getSelectionModel().select(studentsData.size() - 1);
        tblStudents.getFocusModel().focus(studentsData.size() - 1, tbcFullName);
        tblStudents.edit(studentsData.size() - 1, tbcFullName);
        ivTick.setVisible(true);
        ivX.setVisible(true);
        btnCreate.setDisable(true);
    }
    public void delete(ActionEvent action){
        
    }
}