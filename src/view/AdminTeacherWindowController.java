/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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
import restful.TeacherCourseRESTClient;
import restful.TeacherRESTClient;
import restful.UserRESTClient;

/**
 *
 * @author Aitor
 */
public class AdminTeacherWindowController {
    
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
    private TableView<Teacher> tblTeachers;
    
    @FXML
    private TableColumn<Teacher, String> tbcFullName;
    
    @FXML
    private TableColumn<Teacher, String> tbcUsername;
    
    @FXML
    private TableColumn<Teacher, String> tbcEmail;
    
    @FXML
    private TableColumn<Teacher, String> tbcTelephone;
    
    @FXML
    private TableColumn<Teacher, Date> tbcBirthDate;
    
    @FXML
    private TableColumn<Teacher, String> tbcCourse;
    
    @FXML
    private TableColumn<Teacher, Float> tbcSalary;
    
    private ObservableList<Teacher> teachersData;
    
    private ObservableList<TeacherCourse> teacherCourses;
    
    public void initStage(Parent root) {
        //LOGGER.info("Stage initiated");
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("SignIn");
        stage.setResizable(false);
        
        tbcFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tbcUsername.setCellValueFactory(new PropertyValueFactory<>("login"));
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbcTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        tbcBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        tbcCourse.setCellValueFactory(new PropertyValueFactory<>("teacherCourses"));        
        tbcSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        tblTeachers.setEditable(true);
        ivTick.setVisible(false);
        ivX.setVisible(false);
        btnDelete.setDisable(true);
        TeacherRESTClient rest = new TeacherRESTClient();
        teachersData = FXCollections.observableArrayList(rest.findAllTeacher(new GenericType<List<Teacher>>() {
        }));
        teacherCourses = FXCollections.observableArrayList(new TeacherCourseRESTClient().findAllTeacherCourses(new GenericType<List<TeacherCourse>>() {
        }));
        //Table column FullName editable with textField
        tbcFullName.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcFullName.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setFullName(t.getNewValue());
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcUsername);
                    tblTeachers.edit(t.getTablePosition().getRow(), tbcUsername);
                });
        tbcFullName.setOnEditCancel((CellEditEvent<Teacher, String> t) -> {
            tblTeachers.refresh();
            btnCreate.setDisable(false);
        });
        //Table column Username editable with textField
        tbcUsername.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcUsername.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setLogin(t.getNewValue());
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcEmail);
                    tblTeachers.edit(t.getTablePosition().getRow(), tbcEmail);
                });
        //Table column Email editable with textField
        tbcEmail.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcEmail.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    ((User) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setEmail(t.getNewValue());
                    //tblTeachers.getColumns().add(tbcEmail);
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcTelephone);
                    tblTeachers.edit(t.getTablePosition().getRow(), tbcTelephone);
                });
        //Table column Telephone editable with textField
        tbcTelephone.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcTelephone.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    if (!t.getNewValue().matches("[0-9]{9}")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid telephone value", ButtonType.OK);
                        alert.show();
                    } else {
                        ((User) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setTelephone(t.getNewValue());
                        tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcBirthDate);
                        tblTeachers.edit(t.getTablePosition().getRow(), tbcBirthDate);
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
        Callback<TableColumn<Teacher, Date>, TableCell<Teacher, Date>> dateCellFactory
                = (TableColumn<Teacher, Date> param) -> new DatePickerCell();
        tbcBirthDate.setCellFactory(dateCellFactory);
        tbcBirthDate.setOnEditCommit(
                (TableColumn.CellEditEvent<Teacher, Date> t) -> {
                    ((User) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setBirthDate(t.getNewValue());
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcCourse);
                    tblTeachers.edit(t.getTablePosition().getRow(), tbcCourse);
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
        List<String> stringnames = new ArrayList<>();
        for (int i = 0; i < teacherCourses.size(); i++) {
            stringnames.add(i, teacherCourses.get(i).getName());
            //name.add(t.getName());
        }
        name = FXCollections.observableArrayList(stringnames);
        tbcCourse.setCellFactory(ChoiceBoxTableCell.forTableColumn(name));
        tblTeachers.setItems(teachersData);
        btnCreate.setOnAction(this::creation);
        btnDelete.setOnAction(this::delete);
        
        stage.show();
    }
    
    public void creation(ActionEvent action) {
        teachersData.add(new Teacher());
        tblTeachers.getSelectionModel().select(teachersData.size() - 1);
        tblTeachers.getFocusModel().focus(teachersData.size() - 1, tbcFullName);
        tblTeachers.edit(teachersData.size() - 1, tbcFullName);
        ivTick.setVisible(true);
        ivX.setVisible(true);
        btnCreate.setDisable(true);
    }
    public void delete(ActionEvent action){
        
    }
}
