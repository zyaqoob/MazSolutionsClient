/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Course;
import classes.Student;
import classes.UserPrivilege;
import classes.UserStatus;
import crypto.Crypto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.ws.rs.core.GenericType;
import restful.CourseRESTClient;
import restful.StudentRESTClient;

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
    
    @FXML
    private ChoiceBox <String> chbFilterStudentsByCourse;
    
    private final StudentRESTClient restStudents = new StudentRESTClient();
    private final ObservableList<Student> studentsData = FXCollections.observableArrayList(restStudents.findAllStudents(new GenericType<List<Student>>() {}));
    private final CourseRESTClient restCourses = new CourseRESTClient();
    private final ObservableList<Course> coursesData=FXCollections.observableArrayList(restCourses.findAllCourses(new GenericType<List<Course>>(){}));
    
    
   
    
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
        //coursesData = FXCollections.observableArrayList(rest.findAll(new GenericType<List<Course>>(){}));
        //tblStudents.setItems(studentsData);
        tbcFullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        tbcCourse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourse().getName()));
        tbcYear.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getYear()));
        tbcEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        tbcTelephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));
        tbcBirthDate.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getBirthDate()));
        
        /*ObservableList<String> name;
        List<String> stringnames = new ArrayList<>();
        for (int i = 0; i < coursesData.size(); i++) {
            stringnames.add(i, coursesData.get(i).getName());
        }
        name = FXCollections.observableArrayList(stringnames);
        chbFilterStudentsByCourse.setItems(name);*/
        
        checkStudentIsNull();
        
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
        //ExamSessionRESTClient restSessions = new ExamSessionRESTClient();
        //ObservableList<ExamSession> sessions=FXCollections.observableArrayList(restSessions.findAllExamSession(new GenericType<List<ExamSession>>(){}));
        //student.setSessions(sessions);
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
        
        //String email= tblStudents.getSelectionModel().getSelectedItem().getEmail();
        //Student student= restStudents.findStudentByEmail(new GenericType<Student>(){}, email);
        Alert deleteConfirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmationAlert.setHeaderText(null);
        deleteConfirmationAlert.setTitle("Confirmation");
        deleteConfirmationAlert.setContentText("Are you sure that you want to erase this student?");
        Optional<ButtonType> button = deleteConfirmationAlert.showAndWait();
        if (button.get() == ButtonType.OK) {
            restStudents.remove(String.valueOf(tblStudents.getSelectionModel().getSelectedItem().getIdUser()));
        }
        
    }

    private void accept(MouseEvent event) {
        Student student = tblStudents.getSelectionModel().getSelectedItem();

        if (student != null) {
            student.setPrivilege(UserPrivilege.STUDENT);
            student.setStatus(UserStatus.ENABLED);
            student.setCourse(restCourses.findCourseByName(new GenericType<Course>(){}, student.getCourse().getName()));
            student.setPassword(Crypto.cifrar(generatePassword()));
            restStudents.create(student);
            ivTick.setVisible(false);
            ivX.setVisible(false);
            tblStudents.refresh();
            btnCreate.setDisable(false);
        }
    }

    private void handleTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {
        
        if (newValue == null) {
            btnDelete.setDisable(true);
        } else {
            btnDelete.setDisable(false);
        }
        
    }
    
    private void checkStudentIsNull(){
        //Table column FullName editable with textField
        tbcFullName.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcFullName.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    ((Student) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).setFullName(s.getNewValue());
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcCourse);
                    if (!ivTick.isVisible()) {
                        Student student = ((Student) s.getTableView().getItems().get(
                                s.getTablePosition().getRow()));
                        new StudentRESTClient().edit(student, String.valueOf(student.getIdUser()));
                    }
                });
        
        
        
        //Table column Course editable with textField
        ObservableList<String> name;
        List<String> stringnames = new ArrayList<>();
        for (int i = 0; i < coursesData.size(); i++) {
            stringnames.add(i, coursesData.get(i).getName());
        }
        name = FXCollections.observableArrayList(stringnames);
        tbcCourse.setCellFactory(ChoiceBoxTableCell.forTableColumn(name));
        tbcCourse.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    ((Student) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).getCourse().setName(s.getNewValue());
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcYear);
                    if (((Student) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).getIdUser() != null) {
                        Student student = ((Student) s.getTableView().getItems().get(
                                s.getTablePosition().getRow()));
                        new StudentRESTClient().edit(student, String.valueOf(student.getIdUser()));
                    }
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
                    if (((Student) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).getIdUser() != null) {
                        Student student = ((Student) s.getTableView().getItems().get(
                                s.getTablePosition().getRow()));
                        new StudentRESTClient().edit(student, String.valueOf(student.getIdUser()));
                    }
                });
        
        //Table column Email editable with textField
        tbcEmail.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcEmail.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    ((Student) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).setEmail(s.getNewValue());
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcTelephone);
                    if (((Student) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).getIdUser() != null) {
                        Student student = ((Student) s.getTableView().getItems().get(
                                s.getTablePosition().getRow()));
                        new StudentRESTClient().edit(student, String.valueOf(student.getIdUser()));
                    }
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
                    if (((Student) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).getIdUser() != null) {
                        Student student = ((Student) s.getTableView().getItems().get(
                                s.getTablePosition().getRow()));
                        new StudentRESTClient().edit(student, String.valueOf(student.getIdUser()));
                    }
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
                    if (((Student) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).getIdUser() != null) {
                        Student student = ((Student) s.getTableView().getItems().get(
                                s.getTablePosition().getRow()));
                        new StudentRESTClient().edit(student, String.valueOf(student.getIdUser()));
                    }
                });
    }
    
    
    public static String generatePassword() {
        String password;
        String charLow[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String charUpper[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String number[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        password = charLow[(int) (Math.random() * charLow.length)] + charUpper[(int) (Math.random() * charUpper.length)] + number[(int) (Math.random() * 9)] + charLow[(int) (Math.random() * charLow.length)] + charUpper[(int) (Math.random() * charUpper.length)] + number[(int) (Math.random() * 9)];
        return password;
    }
    
}