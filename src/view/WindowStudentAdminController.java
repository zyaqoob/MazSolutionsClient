/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Course;
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
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
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
import javafx.scene.input.MouseEvent;
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
    private TableColumn<Student, Date> tbcYear;
    
    @FXML
    private TableColumn<Student, String> tbcEmail;
    
    @FXML
    private TableColumn<Student, String> tbcTelephone;
    
    @FXML
    private TableColumn<Student, Date> tbcBirthDate;
    
    
    private ObservableList<Student> studentsData;
    @FXML
    private ChoiceBox<?> chBox;
   
    
    public void initStage(Parent root) {
        //LOGGER.info("Stage initiated");
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        
               
        tblStudents.setEditable(true);
        ivTick.setVisible(false);
        ivX.setVisible(false);
        btnDelete.setDisable(true);
        StudentRESTClient rest = new StudentRESTClient();
        studentsData = FXCollections.observableArrayList(rest.findAllStudents(new GenericType<List<Student>>() {
        }));
        //tblStudents.setItems(studentsData);
        tbcFullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        tbcCourse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourse().getName()));
        tbcYear.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getYear()));
        tbcEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        tbcTelephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));
        tbcBirthDate.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getBirthDate()));
        
        //Table column FullName editable with textField
        tbcFullName.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcFullName.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    ((Student) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).setFullName(s.getNewValue());
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcCourse);
                    tblStudents.edit(s.getTablePosition().getRow(), tbcCourse);
                });
        //Table column Course editable with textField
        tbcCourse.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcCourse.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    ((Student) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).getCourse().setName(s.getNewValue());
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcYear);
                    tblStudents.edit(s.getTablePosition().getRow(), tbcYear);
                });
        tbcFullName.setOnEditCancel((CellEditEvent<Student, String> s) -> {
        });
        //Table column Year editable with textField
        Callback<TableColumn<Student, Date>, TableCell<Student, Date>> dateCellFactory
                = (TableColumn<Student, Date> param) -> new DatePickerCellStudent();
        tbcYear.setCellFactory(dateCellFactory);
        tbcYear.setOnEditCommit(
                (TableColumn.CellEditEvent<Student, Date> s) -> {
                    ((Student) s.getTableView().getItems()
                            .get(s.getTablePosition().getRow()))
                            .setYear(s.getNewValue());
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcEmail);
                    tblStudents.edit(s.getTablePosition().getRow(), tbcEmail);
                });
        
        //Table column Email editable with textField
        tbcEmail.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcEmail.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    ((Student) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).setEmail(s.getNewValue());
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcTelephone);
                    tblStudents.edit(s.getTablePosition().getRow(), tbcTelephone);
                });
        tbcFullName.setOnEditCancel((CellEditEvent<Student, String> s) -> {
        });
        
        //Table column Telephone editable with textField
        tbcTelephone.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcTelephone.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    ((Student) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).setTelephone(s.getNewValue());
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcBirthDate);
                    tblStudents.edit(s.getTablePosition().getRow(), tbcBirthDate);
                });
        tbcFullName.setOnEditCancel((CellEditEvent<Student, String> s) -> {
        });
        
        //Table column Birth Date editable with textField
        Callback<TableColumn<Student, Date>, TableCell<Student, Date>> dateCellFactory2
                = (TableColumn<Student, Date> param) -> new DatePickerCellStudent();
        tbcBirthDate.setCellFactory(dateCellFactory2);
        tbcBirthDate.setOnEditCommit(
                (TableColumn.CellEditEvent<Student, Date> s) -> {
                    ((Student) s.getTableView().getItems()
                            .get(s.getTablePosition().getRow()))
                            .setBirthDate(s.getNewValue());
                });
        
        
        tblStudents.setItems(studentsData);
        btnCreate.setOnAction(this::creation);
        btnDelete.setOnAction(this::delete);
        ivTick.setOnMouseClicked(this::accept);
        tblStudents.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChanged);
        stage.show();
    }
    
    public void creation(ActionEvent action) {
        Student student = new Student();
        Course course = new Course();
        student.setCourse(course);
        studentsData.add(student);
        tblStudents.getSelectionModel().select(studentsData.size() - 1);
        tblStudents.getFocusModel().focus(studentsData.size() - 1, tbcFullName);
        tblStudents.edit(studentsData.size() - 1, tbcFullName);
        ivTick.setVisible(true);
        ivX.setVisible(true);
        btnCreate.setDisable(true);
    }
    public void delete(ActionEvent action) {
        /*TeacherRESTClient teacherRESTClient = new TeacherRESTClient();
        TeacherCourseRESTClient teacherCourseRESTClient = new TeacherCourseRESTClient();
        tblTeachers.getSelectionModel().getSelectedItem().getTeacher();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation");
        alert.setContentText("You sure you want to erase this teacher?");
        Optional<ButtonType> button = alert.showAndWait();
        if (button.get() == ButtonType.OK) {
            teacherCourseRESTClient.remove(String.valueOf(tblTeachers.getSelectionModel().getSelectedItem().getIdTeacherCourse()));
            teacherRESTClient.remove(tblTeachers.getSelectionModel().getSelectedItem().getTeacher().getIdUser());
        }*/
    }

    private void accept(MouseEvent event) {
        /*Teacher teacher = tblTeachers.getSelectionModel().getSelectedItem().getTeacher();

        if (teacher != null) {
            teacher.setPrivilege(UserPrivilege.TEACHER);
            teacher.setStatus(UserStatus.ENABLED);
            TeacherRESTClient teacherRESTClient = new TeacherRESTClient();
            teacherRESTClient.create(teacher);
            tblTeachers.refresh();
        }*/
    }

    private void handleTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {
        /*if (newValue == null) {
            btnDelete.setDisable(true);
        } else {
            btnDelete.setDisable(false);
        }*/
    }
}