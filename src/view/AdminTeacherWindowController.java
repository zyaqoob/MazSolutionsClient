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
import crypto.Crypto;
import static crypto.Crypto.generatePassword;
import interfaces.TeacherCourseManager;
import interfaces.TeacherManager;
import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.GenericType;
import logic.RESTfulClientType;
import logic.RESTfulFactory;
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

    @FXML
    private ChoiceBox chBox;

    @FXML
    private TextField tfFilter;

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
        List<String> filterValues = new ArrayList<>();
        filterValues.add("Full Name");
        filterValues.add("Course");
        filterValues.add("Username");
        filterValues.add("Date");
        filterValues.add("Salary");
        filterValues.add("All");
        ObservableList<String> filterValuesForChoiceBox = FXCollections.observableList(filterValues);
        chBox.setItems(filterValuesForChoiceBox);
        chBox.getSelectionModel().selectFirst();
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
        setCellFactories();

        tblTeachers.setItems(teachers);
        //tblTeachers.setItems(teachersData);
        btnCreate.setOnAction(this::creation);
        btnDelete.setOnAction(this::delete);
        ivTick.setOnMouseClicked(this::accept);
        ivSearch.setOnMouseClicked(this::search);
        ivX.setOnMouseClicked((MouseEvent e) -> {
            teachers = FXCollections.observableArrayList(teacherManager.findAllTeacher(new GenericType<List<Teacher>>() {
            }));
            tblTeachers.setItems(teachers);
            ivTick.setVisible(false);
            ivX.setVisible(false);
            btnCreate.setDisable(false);
        });
        tblTeachers.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChanged);
        stage.show();
    }

    private void creation(ActionEvent action) {
        btnCreate.setDisable(true);
        Teacher teacher = new Teacher();
        TeacherCourse teacherCourse = new TeacherCourse();
        teacher.setTeacherCourse(teacherCourse);
        teachers.add(teacher);
        tblTeachers.getSelectionModel().select(teachers.size() - 1);
        tblTeachers.getFocusModel().focus(teachers.size() - 1, tbcFullName);
        tblTeachers.layout();
        tblTeachers.edit(teachers.size() - 1, tbcFullName);
        ivTick.setVisible(true);
        ivX.setVisible(true);
    }

    private void delete(ActionEvent action) {
        Teacher teacher = teacherManager.findTeachersByLogin(new GenericType<Teacher>() {
        }, tblTeachers.getSelectionModel().getSelectedItem().getLogin());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmation");
        alert.setContentText("You sure you want to erase this teacher?");
        Optional<ButtonType> button = alert.showAndWait();
        if (button.get() == ButtonType.OK) {
            new TeacherRESTClient().remove(teacher.getLogin());
            teachers = FXCollections.observableArrayList(teacherManager.findAllTeacher(new GenericType<List<Teacher>>() {
            }));
            tblTeachers.setItems(teachers);
            tblTeachers.refresh();
        }
    }

    private void search(MouseEvent event) {
        String filter = (String) chBox.getSelectionModel().getSelectedItem();
        ObservableList<Teacher> filteredTeachers = tblTeachers.getItems();
        switch (filter) {
            case "Full Name":
                teachers = FXCollections.observableArrayList(filteredTeachers.stream().filter(teacher -> teacher.getFullName()
                        .equalsIgnoreCase(tfFilter.getText())).map(teacher -> teacher).collect(Collectors.toList()));
                tblTeachers.setItems(teachers);
                break;
            case "Course":
                teachers = FXCollections.observableArrayList(filteredTeachers.stream().filter(teacher -> teacher.
                        getTeacherCourse().getName().equalsIgnoreCase(tfFilter.getText())).map(teacher -> teacher)
                        .collect(Collectors.toList()));
                tblTeachers.setItems(teachers);
                break;
            case "Username":
                teachers = FXCollections.observableArrayList(filteredTeachers.stream().filter(teacher -> teacher.getLogin()
                        .equalsIgnoreCase(tfFilter.getText())).map(teacher -> teacher).collect(Collectors.toList()));
                tblTeachers.setItems(teachers);
                break;
            case "Salary":
                if (tfFilter.getText().matches("[0-9]{3,4}")) {
                    teachers = FXCollections.observableArrayList(filteredTeachers.stream().filter(teacher
                            -> Objects.equals(teacher.getSalary(), Float.valueOf(tfFilter.getText())))
                            .map(teacher -> teacher).collect(Collectors.toList()));
                    tblTeachers.setItems(teachers);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid salary value", ButtonType.OK);
                    alert.show();
                }
                break;
            case "Date":
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                teachers = FXCollections.observableArrayList(filteredTeachers.stream().filter(teacher -> dateFormatter.
                        format(teacher.getBirthDate()).equalsIgnoreCase(tfFilter.getText())).map(teacher -> teacher).
                        collect(Collectors.toList()));
                tblTeachers.setItems(teachers);
                break;
            case "All":
                teachers = FXCollections.observableArrayList(teacherManager.findAllTeacher(new GenericType<List<Teacher>>() {
                }));
                tblTeachers.setItems(teachers);
                break;
        }
    }

    private void accept(MouseEvent event) {
        tblTeachers.getSelectionModel().select(teachers.size() - 1);
        try {
            Teacher teacher = tblTeachers.getSelectionModel().getSelectedItem();
            teacher.setPrivilege(UserPrivilege.TEACHER);
            teacher.setStatus(UserStatus.ENABLED);
            teacher.setLastPasswordChange(Calendar.getInstance());
            teacher.setPassword(Crypto.cifrar(generatePassword()));
            teacher.setTeacherCourse(teacherCourseManager.findTeacherCourseByName(new GenericType<TeacherCourse>() {
            }, teacher.getTeacherCourse().getName()));
            teacherManager.create(teacher);
            teachers = FXCollections.observableArrayList(teacherManager.findAllTeacher(new GenericType<List<Teacher>>() {
            }));
            tblTeachers.setItems(teachers);
            btnCreate.setDisable(false);
            ivTick.setVisible(false);
            ivX.setVisible(false);
            tblTeachers.refresh();
        } catch (InternalServerErrorException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Missing data in table cell", ButtonType.OK);
            alert.show();
        }
    }

    private void handleTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue == null || btnCreate.isDisabled()) {
            btnDelete.setDisable(true);
        } else if (!ivTick.isVisible()) {
            btnDelete.setDisable(false);
        }
    }

    private void setCellFactories() {
        //Comentario de celdas.
        tbcFullName.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcFullName.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    ((Teacher) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setFullName(t.getNewValue());
                    if (!ivTick.isVisible()) {
                        Teacher teacher = (Teacher) t.getTableView().getItems().get(
                                t.getTablePosition().getRow());
                        teacherManager.edit(teacher, String.valueOf(teacher.getIdUser()));
                        tblTeachers.refresh();
                    }
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcUsername);
                    tblTeachers.edit(t.getTablePosition().getRow(), tbcUsername);
                });
        //Table column Username editable with textField
        tbcUsername.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcUsername.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    ((Teacher) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setLogin(t.getNewValue());
                    if (!ivTick.isVisible()) {
                        Teacher teacher = ((Teacher) t.getTableView().getItems().get(
                                t.getTablePosition().getRow()));
                        teacherManager.edit(teacher, String.valueOf(teacher.getIdUser()));
                        tblTeachers.refresh();
                    }
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcEmail);
                    tblTeachers.edit(t.getTablePosition().getRow(), tbcEmail);
                });
        //Table column Email editable with textField
        tbcEmail.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcEmail.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    if (t.getNewValue().matches("[A-Za-z0-9._%+-]+@[a-z0-9.-]+.[A-Za-z]")) {
                        ((Teacher) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setEmail(t.getNewValue());
                        //tblTeachers.getColumns().add(tbcEmail);
                        if (!ivTick.isVisible()) {
                            Teacher teacher = ((Teacher) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()));
                            teacherManager.edit(teacher, String.valueOf(teacher.getIdUser()));
                            tblTeachers.refresh();
                        }
                        tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcTelephone);
                        tblTeachers.edit(t.getTablePosition().getRow(), tbcTelephone);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid email value", ButtonType.OK);
                        alert.show();
                    }
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
                        if (!ivTick.isVisible()) {
                            Teacher teacher = ((Teacher) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()));
                            teacherManager.edit(teacher, String.valueOf(teacher.getIdUser()));
                            tblTeachers.refresh();
                        }
                        tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcBirthDate);
                        tblTeachers.edit(t.getTablePosition().getRow(), tbcBirthDate);
                        // tblTeachers.getColumns().add(tbcTelephone);
                    }
                });
        Callback<TableColumn<Teacher, Date>, TableCell<Teacher, Date>> dateCellFactory
                = (TableColumn<Teacher, Date> param) -> new DatePickerCell();
        tbcBirthDate.setCellFactory(dateCellFactory);
        tbcBirthDate.setOnEditCommit(
                (TableColumn.CellEditEvent<Teacher, Date> t) -> {
                    ((Teacher) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setBirthDate(t.getNewValue());
                    if (!ivTick.isVisible()) {
                        Teacher teacher = ((Teacher) t.getTableView().getItems().get(
                                t.getTablePosition().getRow()));
                        teacherManager.edit(teacher, String.valueOf(teacher.getIdUser()));
                        tblTeachers.refresh();
                    }
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
                        if (!ivTick.isVisible()) {
                            Teacher teacher = ((Teacher) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()));
                            teacherManager.edit(teacher, String.valueOf(teacher.getIdUser()));
                            tblTeachers.refresh();
                        }
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
        tbcCourse.setCellFactory(cb -> new ChoiceBoxTableCell(name) {

            @Override
            public void startEdit() {
                super.startEdit();
                if (isEditing() && getGraphic() instanceof ChoiceBox) {
                    // needs focus for proper working of esc/enter 
                    getGraphic().requestFocus();
                    ((ChoiceBox<String>) getGraphic()).show();
                }
            }

        });
        tbcCourse.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {

                    ((Teacher) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setTeacherCourse(teacherCourseManager.findTeacherCourseByName(new GenericType<TeacherCourse>() {
                    }, t.getNewValue()));
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcSalary);
                    if (!ivTick.isVisible()) {
                        Teacher teacher = ((Teacher) t.getTableView().getItems().get(
                                t.getTablePosition().getRow()));
                        teacherManager.edit(teacher, String.valueOf(teacher.getIdUser()));
                        tblTeachers.refresh();
                    }
                    // tblTeachers.getColumns().add(tbcTelephone);
                });
    }
}
