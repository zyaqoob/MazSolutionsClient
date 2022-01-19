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
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private TableView<TeacherCourse> tblTeachers;

    @FXML
    private TableColumn<TeacherCourse, String> tbcFullName;

    @FXML
    private TableColumn<TeacherCourse, String> tbcUsername;

    @FXML
    private TableColumn<TeacherCourse, String> tbcEmail;

    @FXML
    private TableColumn<TeacherCourse, String> tbcTelephone;

    @FXML
    private TableColumn<TeacherCourse, Date> tbcBirthDate;

    @FXML
    private TableColumn<TeacherCourse, String> tbcCourse;

    @FXML
    private TableColumn<TeacherCourse, String> tbcSalary;

    private ObservableList<TeacherCourse> teacherCourses;

    public void initStage(Parent root) {
        //LOGGER.info("Stage initiated");
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("SignIn");
        stage.setResizable(false);

        tblTeachers.setEditable(true);
        ivTick.setVisible(false);
        ivX.setVisible(false);
        btnDelete.setDisable(true);
        TeacherRESTClient rest = new TeacherRESTClient();
        teacherCourses = FXCollections.observableArrayList(new TeacherCourseRESTClient().findAllTeacherCourses(new GenericType<List<TeacherCourse>>() {
        }));
        tbcFullName.setCellValueFactory(cellData -> 
        new SimpleStringProperty(cellData.getValue().getTeacher().getFullName()));
        tbcUsername.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeacher().getLogin()));
        tbcEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeacher().getEmail()));
        tbcTelephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeacher().getTelephone()));
        tbcBirthDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTeacher().getBirthDate()));
        tbcSalary.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTeacher().getSalary())));
        tbcCourse.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        //Table column FullName editable with textField
        tbcFullName.setCellFactory(TextFieldTableCell.<TeacherCourse>forTableColumn());
        tbcFullName.setOnEditCommit(
                (CellEditEvent<TeacherCourse, String> t) -> {
                    ((TeacherCourse) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).getTeacher().setFullName(t.getNewValue());
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcUsername);
                    tblTeachers.edit(t.getTablePosition().getRow(), tbcUsername);
                });
        tbcFullName.setOnEditCancel((CellEditEvent<TeacherCourse, String> t) -> {
            tblTeachers.refresh();
            btnCreate.setDisable(false);
        });
        //Table column Username editable with textField
        tbcUsername.setCellFactory(TextFieldTableCell.<TeacherCourse>forTableColumn());
        tbcUsername.setOnEditCommit(
                (CellEditEvent<TeacherCourse, String> t) -> {
                    ((TeacherCourse) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).getTeacher().setLogin(t.getNewValue());
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcEmail);
                    tblTeachers.edit(t.getTablePosition().getRow(), tbcEmail);
                });
        //Table column Email editable with textField
        tbcEmail.setCellFactory(TextFieldTableCell.<TeacherCourse>forTableColumn());
        tbcEmail.setOnEditCommit(
                (CellEditEvent<TeacherCourse, String> t) -> {
                    ((TeacherCourse) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).getTeacher().setEmail(t.getNewValue());
                    //tblTeachers.getColumns().add(tbcEmail);
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcTelephone);
                    tblTeachers.edit(t.getTablePosition().getRow(), tbcTelephone);
                });
        //Table column Telephone editable with textField
        tbcTelephone.setCellFactory(TextFieldTableCell.<TeacherCourse>forTableColumn());
        tbcTelephone.setOnEditCommit(
                (CellEditEvent<TeacherCourse, String> t) -> {
                    if (!t.getNewValue().matches("[0-9]{9}")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid telephone value", ButtonType.OK);
                        alert.show();
                    } else {
                        ((TeacherCourse) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).getTeacher().setTelephone(t.getNewValue());
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
        Callback<TableColumn<TeacherCourse, Date>, TableCell<TeacherCourse, Date>> dateCellFactory
                = (TableColumn<TeacherCourse, Date> param) -> new DatePickerCell();
        tbcBirthDate.setCellFactory(dateCellFactory);
        tbcBirthDate.setOnEditCommit(
                (TableColumn.CellEditEvent<TeacherCourse, Date> t) -> {
                    ((TeacherCourse) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow())).getTeacher()
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
        tblTeachers.setItems(teacherCourses);
        //tblTeachers.setItems(teachersData);
        btnCreate.setOnAction(this::creation);
        btnDelete.setOnAction(this::delete);

        stage.show();
    }

    public void creation(ActionEvent action) {
        teacherCourses.add(new TeacherCourse());
        tblTeachers.getSelectionModel().select(teacherCourses.size() - 1);
        tblTeachers.getFocusModel().focus(teacherCourses.size() - 1, tbcFullName);
        tblTeachers.edit(teacherCourses.size() - 1, tbcFullName);
        ivTick.setVisible(true);
        ivX.setVisible(true);
        btnCreate.setDisable(true);
    }

    public void delete(ActionEvent action) {

    }
}
