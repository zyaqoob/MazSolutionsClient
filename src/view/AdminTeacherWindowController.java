/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Teacher;
import classes.TeacherCourse;
import classes.UserPrivilege;
import classes.UserStatus;
import interfaces.TeacherCourseManager;
import interfaces.TeacherManager;
import java.util.ArrayList;
import java.util.Calendar;
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
import logic.RESTfulClientType;
import logic.RESTfulFactory;
import restful.TeacherCourseRESTClient;
import restful.TeacherRESTClient;

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
    private TableColumn<Teacher, String> tbcSalary;

    private ObservableList<Teacher> teachers;

    private ObservableList<TeacherCourse> teacherCourses;

    private final TeacherManager teacherManager = (TeacherManager) new RESTfulFactory().getRESTClient(RESTfulClientType.TEACHER);

    private final TeacherCourseManager teacherCourseManager = (TeacherCourseManager) new RESTfulFactory().getRESTClient(RESTfulClientType.TEACHER_COURSE);

    public void initStage(Parent root) {
        //LOGGER.info("Stage initiated");
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("SignIn");
        stage.setResizable(false);
        teachers = FXCollections.observableArrayList(teacherManager.findAllTeacher(new GenericType<List<Teacher>>() {
        }));
        teacherCourses = FXCollections.observableArrayList(teacherCourseManager.findAllTeacherCourses(new GenericType<List<TeacherCourse>>() {
        }));
        tblTeachers.setEditable(true);
        ivTick.setVisible(false);
        ivX.setVisible(false);
        btnDelete.setDisable(true);
        TeacherRESTClient rest = new TeacherRESTClient();
        tbcFullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        tbcUsername.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogin()));
        tbcEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        tbcTelephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));
        tbcBirthDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBirthDate()));
        tbcSalary.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSalary())));
        tbcCourse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeacherCourse().getName()));
        //Table column FullName editable with textField
        tbcFullName.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcFullName.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    ((Teacher) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setFullName(t.getNewValue());
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcUsername);
                    tblTeachers.edit(t.getTablePosition().getRow(), tbcUsername);
                });
        //Table column Username editable with textField
        tbcUsername.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcUsername.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    ((Teacher) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setLogin(t.getNewValue());
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcEmail);
                    tblTeachers.edit(t.getTablePosition().getRow(), tbcEmail);
                });
        //Table column Email editable with textField
        tbcEmail.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcEmail.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    ((Teacher) t.getTableView().getItems().get(
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
                        ((Teacher) t.getTableView().getItems().get(
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
                    ((Teacher) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setBirthDate(t.getNewValue());
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcCourse);
                    tblTeachers.edit(t.getTablePosition().getRow(), tbcCourse);
                });
        tbcSalary.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcSalary.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    if (!t.getNewValue().matches("[0-9]{3,4}")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid salary value", ButtonType.OK);
                        alert.show();
                    } else {
                        ((Teacher) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setSalary(Float.valueOf(t.getNewValue()));
                        tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcSalary);
                        // tblTeachers.getColumns().add(tbcTelephone);
                    }
                });
        ObservableList<String> name;
        List<String> stringnames = new ArrayList<>();
        for (int i = 0; i < teacherCourses.size(); i++) {
            stringnames.add(i, teacherCourses.get(i).getName());
            //name.add(t.getName());
        }
        name = FXCollections.observableArrayList(stringnames);
        tbcCourse.setCellFactory(ChoiceBoxTableCell.forTableColumn(name));
        tbcCourse.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    
                    ((Teacher) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).getTeacherCourse().setName(t.getNewValue());
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcSalary);
                    // tblTeachers.getColumns().add(tbcTelephone);
                });
        tblTeachers.setItems(teachers);
        //tblTeachers.setItems(teachersData);
        btnCreate.setOnAction(this::creation);
        btnDelete.setOnAction(this::delete);
        ivTick.setOnMouseClicked(this::accept);
        tblTeachers.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChanged);
        stage.show();
    }

    public void creation(ActionEvent action) {
        Teacher teacher = new Teacher();
        TeacherCourse teacherCourse = new TeacherCourse();
        teacher.setTeacherCourse(teacherCourse);
        teachers.add(teacher);
        tblTeachers.getSelectionModel().select(teachers.size() - 1);
        tblTeachers.getFocusModel().focus(teachers.size() - 1, tbcFullName);
        tblTeachers.edit(teachers.size() - 1, tbcFullName);
        ivTick.setVisible(true);
        ivX.setVisible(true);
        btnCreate.setDisable(true);
    }

    public void delete(ActionEvent action) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation");
        alert.setContentText("You sure you want to erase this teacher?");
        Optional<ButtonType> button = alert.showAndWait();
        if (button.get() == ButtonType.OK) {
            teacherManager.remove(tblTeachers.getSelectionModel().getSelectedItem().getIdUser());
            tblTeachers.refresh();
        }
    }

    private void accept(MouseEvent event) {
        tblTeachers.getSelectionModel().select(teachers.size() - 1);
        Teacher teacher = tblTeachers.getSelectionModel().getSelectedItem();
        if (teacher != null) {
            teacher.setPrivilege(UserPrivilege.TEACHER);
            teacher.setStatus(UserStatus.ENABLED);
            teacher.setLastPasswordChange(Calendar.getInstance());
            teacher.setTeacherCourse(teacherCourseManager.findTeacherCourseByName(new GenericType<TeacherCourse>(){},teacher.getTeacherCourse().getName()));
            teacherManager.create(teacher);
            btnCreate.setDisable(true);
            ivTick.setVisible(false);
            ivX.setVisible(false);
            tblTeachers.refresh();
        }
    }

    private void handleTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue == null) {
            btnDelete.setDisable(true);
        } else {
            btnDelete.setDisable(false);
        }
    }
}