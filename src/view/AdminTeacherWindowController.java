/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Teacher;
import classes.TeacherCourse;
import classes.User;
import classes.UserPrivilege;
import classes.UserStatus;
import crypto.Crypto;
import static crypto.Crypto.generatePassword;
import interfaces.TeacherCourseManager;
import interfaces.TeacherManager;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import logic.RESTfulClientType;
import logic.RESTfulFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import restful.TeacherRESTClient;

/**
 *
 * @author Aitor
 */
/**
 * Class that controlls the WindowTeacherAdmin, setting the actions to do to the
 * widgets, and setting the values of the table.
 *
 * @author Aitor
 */
public class AdminTeacherWindowController {

    //Label that is used to go to the WindowTeacherStudent.
    @FXML
    private Label lblStudents;
    //Label that is used to go to the  WindowTeacherAdmin.
    @FXML
    private Label lblTeachers;

    @FXML
    private Label lblSubjects;

    @FXML
    private Label lblCourses;

    @FXML
    private Label lblTeacherCourses;
    //Button that creates a new empty teacher in the table.
    @FXML
    private Button btnCreate;
    //Button that deletes the selected teacher in the table.
    @FXML
    private Button btnDelete;
    //Button that prints a report with the table value.
    @FXML
    private Button btnPrint;
    //ImageView that is used to search info in the table
    @FXML
    private ImageView ivSearch;
    //ImageView that sends the new user to the server side.
    @FXML
    private ImageView ivTick;
    //ImageView that cancel the creation of the teacher.
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
    //Choice box that has the posible filter values.
    @FXML
    private ChoiceBox chBox;
    //TextField used to write the parameter to search in the filter.
    @FXML
    private TextField tfFilter;
    //ObservableList used to set the table values.
    private ObservableList<Teacher> teachers;

    public static Stage stage = new Stage();

    private User user;
    //ObservableList used to get the TeacherCourses name to the tbcCourse.
    private ObservableList<TeacherCourse> teacherCourses;
    //TeacherManager interface which will send the petitions about the teachers to the server side.
    private final TeacherManager teacherManager = (TeacherManager) new RESTfulFactory().getRESTClient(RESTfulClientType.TEACHER);
    ////TeacherCourseManager interface which will send the petitions about the teacher courses to the server side.
    private final TeacherCourseManager teacherCourseManager = (TeacherCourseManager) new RESTfulFactory().getRESTClient(RESTfulClientType.TEACHER_COURSE);

    private static final Logger LOGGER = Logger.getLogger("view");

    /**
     * Method that initialize the starting state of the window teacher admin.
     *
     * @param root
     */
    public void initStage(Parent root) {
        LOGGER.info("Window Teacher Admin: Stage initiated");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Teacher Crud");
        stage.setResizable(false);
        try {
            //The values for the ObservableList teachers are charged by the method findAllTeacher of the teacherManager
            LOGGER.info("Window Teacher Admin: Getting teachers from the server");
            teachers = FXCollections.observableArrayList(teacherManager.findAllTeacher(new GenericType<List<Teacher>>() {
            }));
            //The values for the ObservableList teacherCourses are charged by the method findAllTeacherCourses of the teacherCourseManager
            LOGGER.info("Window Teacher Admin:Getting teacher courses from the server");
            teacherCourses = FXCollections.observableArrayList(teacherCourseManager.findAllTeacherCourses(new GenericType<List<TeacherCourse>>() {
            }));
        } catch (ClientErrorException e) {
            LOGGER.severe("Window Teacher Admin: Unable to connect with the server");
            Alert alert = new Alert(AlertType.ERROR, "Server Unavailable", ButtonType.OK);
            alert.show();
        }
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
        //The cellValueFactory is set for each table column, establishing the data that are going to have.
        tbcFullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        tbcUsername.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogin()));
        tbcEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        tbcTelephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));
        tbcBirthDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBirthDate()));
        tbcSalary.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSalary())));
        tbcCourse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeacherCourse().getName()));
        //Method to set the table columns editable-
        setCellFactories();
        tblTeachers.setItems(teachers);
        btnCreate.setOnAction(this::creation);
        btnDelete.setOnAction(this::delete);
        ivTick.setOnMouseClicked(this::accept);
        ivSearch.setOnMouseClicked(this::search);
        ivX.setOnMouseClicked((MouseEvent e) -> {
            LOGGER.info("Window teacher admin: Creation cancel");
            //This imageView will be visible when the admin is creating a teacher, if he decides to cancel it the values of the table will be restablish.
            teachers = FXCollections.observableArrayList(teacherManager.findAllTeacher(new GenericType<List<Teacher>>() {
            }));
            tblTeachers.setItems(teachers);
            ivTick.setVisible(false);
            ivX.setVisible(false);
            btnCreate.setDisable(false);
        });
        tblTeachers.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChanged);
        MenuData menuData = new MenuData();
        menuData.setStage(stage);
        tfFilter.textProperty().addListener(this::textChanged);
        lblStudents.setOnMouseClicked(this::goToStudentWindow);
        btnPrint.setOnAction((ActionEvent action) -> {
            try {
                //Setting the parameters for the report and showing it.
                LOGGER.info("Window Teacher Admin: Report showing");
                JasperReport report = JasperCompileManager.compileReport(AdminTeacherWindowController.class.getResourceAsStream("/report/AdminTeacherReport.jrxml"));
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource((Collection<Teacher>) tblTeachers.getItems());
                Map<String, Object> parameters = new HashMap<>();
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);
                JasperViewer jasperViewer = new JasperViewer(jasperPrint);
                jasperViewer.setVisible(true);
            } catch (JRException ex) {
                LOGGER.severe("Window Teacher Admin: Error while opening the report");
                Alert alert = new Alert(AlertType.ERROR, "ERROR WHILE OPENING THE REPORT", ButtonType.OK);
                alert.show();
            }
        });
        stage.show();
    }

    /**
     * Method that add a row to the table with an empty teacher. The focus will
     * be to the first column of the new row and will select it in edit mode.
     * The ivTick and the ivX will become visible and the button create/delete
     * disabled.
     *
     * @param action
     */
    private void creation(ActionEvent action) {
        LOGGER.info("Window Teacher Admin: Creating a new teacher");
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

    /**
     * Method that will delete the selected teacher of the table. And alert will
     * be shown for confirmation. After the remove the table values will be
     * restablished.
     *
     * @param action
     */
    private void delete(ActionEvent action) {
        try {
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
                LOGGER.info("Window Teacher Admin: Teacher Deleted");
                tblTeachers.setItems(teachers);
                tblTeachers.refresh();
            }
        } catch (InternalServerErrorException e) {
            LOGGER.severe("Window Teacher Admin: Error while deleting the teacher");
            Alert alert = new Alert(AlertType.ERROR, "Error while deleting", ButtonType.OK);
            alert.show();
        }
    }

    /**
     * Method that will apply the filter values for the table, updating the
     * showing data. Depending in the choice box selected value, the search will
     * be done in different table column. The method will search through all the
     * table items and if it found something it will show it to the user.
     *
     * @param event
     */
    private void search(MouseEvent event) {
        String filter = (String) chBox.getSelectionModel().getSelectedItem();
        ObservableList<Teacher> filteredTeachers = tblTeachers.getItems();
        LOGGER.info("Window Teacher Admin: Filtering table values");
        switch (filter) {
            case "Full Name":
                if (tfFilter.getText().trim().equals("") || ivTick.isVisible()) {
                    LOGGER.info("Window Teacher Admin: Error filtering, Empty text in search text field or trying to search during creation");
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Unexpected error ocurred while filtering", ButtonType.OK);
                    alert.show();
                } else {
                    teachers = FXCollections.observableArrayList(filteredTeachers.stream().filter(teacher -> teacher.getFullName()
                            .equalsIgnoreCase(tfFilter.getText())).map(teacher -> teacher).collect(Collectors.toList()));
                    tblTeachers.setItems(teachers);
                }
                break;

            case "Course":
                if (tfFilter.getText().trim().equals("") || ivTick.isVisible()) {
                    LOGGER.info("Window Teacher Admin: Error filtering, Empty text in search text field or trying to search during creation");
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Unexpected error ocurred while filtering", ButtonType.OK);
                    alert.show();
                } else {
                    teachers = FXCollections.observableArrayList(filteredTeachers.stream().filter(teacher -> teacher.
                            getTeacherCourse().getName().equalsIgnoreCase(tfFilter.getText())).map(teacher -> teacher)
                            .collect(Collectors.toList()));
                    tblTeachers.setItems(teachers);
                }
                break;
            case "Username":
                if (tfFilter.getText().trim().equals("") || ivTick.isVisible()) {
                    LOGGER.info("Window Teacher Admin: Error filtering, Empty text in search text field or trying to search during creation");
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Unexpected error ocurred while filtering", ButtonType.OK);
                    alert.show();
                } else {
                    teachers = FXCollections.observableArrayList(filteredTeachers.stream().filter(teacher -> teacher.getLogin()
                            .equalsIgnoreCase(tfFilter.getText())).map(teacher -> teacher).collect(Collectors.toList()));
                    tblTeachers.setItems(teachers);
                }
                break;
            case "Salary":
                if (tfFilter.getText().trim().equals("") || ivTick.isVisible()) {
                    LOGGER.info("Window Teacher Admin: Error filtering, Empty text in search text field or trying to search during creation");
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Unexpected error ocurred while filtering", ButtonType.OK);
                    alert.show();
                } else {
                    if (tfFilter.getText().matches("[0-9]{3,4}")) {
                        teachers = FXCollections.observableArrayList(filteredTeachers.stream().filter(teacher
                                -> Objects.equals(teacher.getSalary(), Float.valueOf(tfFilter.getText())))
                                .map(teacher -> teacher).collect(Collectors.toList()));
                        tblTeachers.setItems(teachers);
                    } else {
                        LOGGER.info("Window Teacher Admin: Error filtering, Empty text in search text field or trying to search during creation");
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid salary value", ButtonType.OK);
                        alert.show();
                    }
                }
                break;
            case "Date":
                if (tfFilter.getText().trim().equals("") || ivTick.isVisible()) {
                    LOGGER.info("Window Teacher Admin: Error filtering, Empty text in search text field or trying to search during creation");
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Unexpected error ocurred while filtering", ButtonType.OK);
                    alert.show();
                } else {
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                    teachers = FXCollections.observableArrayList(filteredTeachers.stream().filter(teacher -> dateFormatter.
                            format(teacher.getBirthDate()).equalsIgnoreCase(tfFilter.getText())).map(teacher -> teacher).
                            collect(Collectors.toList()));
                    tblTeachers.setItems(teachers);
                }
                break;
            case "All":
                if (ivTick.isVisible()) {
                    ivTick.setVisible(false);
                    ivX.setVisible(false);
                    btnCreate.setDisable(false);
                }
                teachers = FXCollections.observableArrayList(teacherManager.findAllTeacher(new GenericType<List<Teacher>>() {
                }));
                tblTeachers.setItems(teachers);
                break;
        }

    }

    /**
     * Method that will send the created teacher to the server side. If an error
     * ocurred it will get an http error code and an alert will be shown.
     *
     * @param event
     */
    private void accept(MouseEvent event) {
        tblTeachers.getSelectionModel().select(teachers.size() - 1);
        try {
            LOGGER.info("Window Teacher Admin: Sending new teacher to server");
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
        } catch (WebApplicationException e) {
            LOGGER.info("Window Teacher Admin: Unexpected error ocurred while creation\n"
                    + "Username/Email alredy in use or Missing Data");
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR, "Unexpected error ocurred while creation\n"
                    + "Username/Email alredy in use or Missing Data", ButtonType.OK);
            alert.show();
        }
    }

    /**
     * Listener that will enable/disable the btnDelete if the row is selected.
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void handleTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue == null || btnCreate.isDisabled()) {
            btnDelete.setDisable(true);
        } else if (!ivTick.isVisible()) {
            btnDelete.setDisable(false);
        }
    }

    /**
     * Method that set the cell factory for each table column allowing the user
     * to edit it.
     *
     */
    private void setCellFactories() {
        //Setting the cell factory for the table column Full name
        //The cell factory will be a TexFieldTableCell.
        //If the creation is active, at the time of commiting the edit the value will not be sent to the server to be updated.
        tbcFullName.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcFullName.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    ((Teacher) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setFullName(t.getNewValue());
                    if (!ivTick.isVisible()) {
                        try {
                            LOGGER.info("Window Teacher Admin: Sending value of fullname to server to be updated");
                            Teacher teacher = (Teacher) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow());
                            teacherManager.edit(teacher, String.valueOf(teacher.getIdUser()));
                            tblTeachers.refresh();
                        } catch (InternalServerErrorException e) {
                            LOGGER.severe("Window Teacher Admin: Unexpected error ocurred during modifying the fullname");
                            Alert alert = new Alert(AlertType.ERROR, "Unexpected error ocurred during modifying");
                            alert.show();
                            teachers = FXCollections.observableArrayList(teacherManager.findAllTeacher(new GenericType<List<Teacher>>() {
                            }));
                            tblTeachers.setItems(teachers);
                            tblTeachers.refresh();
                        }
                    }
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcUsername);
                    tblTeachers.edit(t.getTablePosition().getRow(), tbcUsername);
                });
        //Setting the cell factory for the table column Username
        //The cell factory will be a TexFieldTableCell.
        //If the creation is active, at the time of commiting the edit the value will not be sent to the server to be updated.
        tbcUsername.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcUsername.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    ((Teacher) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setLogin(t.getNewValue());
                    if (!ivTick.isVisible()) {
                        try {
                            LOGGER.info("Window Teacher Admin: Sending value of username to server to be updated");
                            Teacher teacher = ((Teacher) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()));
                            teacherManager.edit(teacher, String.valueOf(teacher.getIdUser()));
                            tblTeachers.refresh();
                        } catch (InternalServerErrorException e) {
                            LOGGER.severe("Window Teacher Admin: Unexpected error ocurred during modifying the username");
                            Alert alert = new Alert(AlertType.ERROR, "Login is already registered");
                            alert.show();
                            teachers = FXCollections.observableArrayList(teacherManager.findAllTeacher(new GenericType<List<Teacher>>() {
                            }));
                            tblTeachers.setItems(teachers);
                            tblTeachers.refresh();
                        }
                    }
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcEmail);
                    tblTeachers.edit(t.getTablePosition().getRow(), tbcEmail);
                });
        //Setting the cell factory for the table column Email
        //The cell factory will be a TexFieldTableCell.
        //If the creation is active, at the time of commiting the edit the value will not be sent to the server to be updated.
        //If the value doesn't match with the pattern an alert will be shown.
        tbcEmail.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcEmail.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    if (t.getNewValue().matches("[A-Za-z0-9._%+-]+@[a-z0-9.-]+.[A-Za-z]")) {
                        LOGGER.info("Window Teacher Admin: Sending value of email to server to be updated");
                        ((Teacher) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setEmail(t.getNewValue());
                        if (!ivTick.isVisible()) {
                            try {
                                LOGGER.info("Window Teacher Admin: Sending value of email to server to be updated");
                                Teacher teacher = ((Teacher) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow()));
                                teacherManager.edit(teacher, String.valueOf(teacher.getIdUser()));
                                tblTeachers.refresh();
                            } catch (InternalServerErrorException e) {
                                LOGGER.severe("Window Teacher Admin: Email is already registered");
                                Alert alert = new Alert(AlertType.ERROR, "Email is already registered");
                                alert.show();
                                teachers = FXCollections.observableArrayList(teacherManager.findAllTeacher(new GenericType<List<Teacher>>() {
                                }));
                                tblTeachers.setItems(teachers);
                                tblTeachers.refresh();
                            }
                        }
                        tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcTelephone);
                        tblTeachers.edit(t.getTablePosition().getRow(), tbcTelephone);
                    } else {
                        LOGGER.info("Window Teacher Admin: Invalid value for email");
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid email value", ButtonType.OK);
                        alert.show();
                    }
                });
        //Setting the cell factory for the table column telephone
        //The cell factory will be a TexFieldTableCell.
        //If the creation is active, at the time of commiting the edit the value will not be sent to the server to be updated.
        //If the value doesn't match with the telephone pattern an alert will be shown.
        tbcTelephone.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcTelephone.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    if (!t.getNewValue().matches("[0-9]{9}")) {
                        LOGGER.info("Window Teacher Admin: Invalid value for telephone");
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid telephone value", ButtonType.OK);
                        alert.show();
                    } else {
                        ((Teacher) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setTelephone(t.getNewValue());
                        if (!ivTick.isVisible()) {
                            try {
                                LOGGER.info("Window Teacher Admin: Sending value of telphone to server to be updated");
                                Teacher teacher = ((Teacher) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow()));
                                teacherManager.edit(teacher, String.valueOf(teacher.getIdUser()));
                                tblTeachers.refresh();
                            } catch (InternalServerErrorException e) {
                                LOGGER.severe("Window Teacher Admin: Unexpected error ocurred during modifying the telephone");
                                Alert alert = new Alert(AlertType.ERROR, "Unexpected error ocurred during modifying");
                                alert.show();
                            }
                            tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcBirthDate);
                            tblTeachers.edit(t.getTablePosition().getRow(), tbcBirthDate);
                            // tblTeachers.getColumns().add(tbcTelephone);
                        }
                    }
                });
        //Setting the cell factory for the table column birthDate
        //The cell factory will be a TexFieldTableCell.
        //If the creation is active, at the time of commiting the edit the value will not be sent to the server to be updated.
        //This cellFactory is set with an instance of the DatePickerCell class.Which set the functioning of startEdit,updateItem,cancelEdit.
        Callback<TableColumn<Teacher, Date>, TableCell<Teacher, Date>> dateCellFactory
                = (TableColumn<Teacher, Date> param) -> new DatePickerCell();
        tbcBirthDate.setCellFactory(dateCellFactory);
        tbcBirthDate.setOnEditCommit(
                (TableColumn.CellEditEvent<Teacher, Date> t) -> {
                    ((Teacher) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setBirthDate(t.getNewValue());
                    if (!ivTick.isVisible()) {
                        try {
                            LOGGER.info("Window Teacher Admin: Sending value of birthDate to server to be updated");
                            Teacher teacher = ((Teacher) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()));
                            teacherManager.edit(teacher, String.valueOf(teacher.getIdUser()));
                            tblTeachers.refresh();
                        } catch (InternalServerErrorException e) {
                            LOGGER.severe("Window Teacher Admin: Unexpected error ocurred during modifying the birthDate");
                            Alert alert = new Alert(AlertType.ERROR, "Unexpected error ocurred during modifying");
                            alert.show();
                        }
                    }
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcCourse);
                    tblTeachers.edit(t.getTablePosition().getRow(), tbcCourse);
                });
        //Setting the cell factory for the table column salary
        //The cell factory will be a TexFieldTableCell.
        //If the creation is active, at the time of commiting the edit the value will not be sent to the server to be updated.
        //If the value doesn't match with the salary pattern an alert will be shown.
        tbcSalary.setCellFactory(TextFieldTableCell.<Teacher>forTableColumn());
        tbcSalary.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {
                    if (!t.getNewValue().matches("[0-9]{3,4}")) {
                        LOGGER.info("Window Teacher Admin: Invalid value for salary");
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid salary value", ButtonType.OK);
                        alert.show();
                    } else {
                        ((Teacher) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setSalary(Float.valueOf(t.getNewValue()));
                        if (!ivTick.isVisible()) {
                            LOGGER.info("Window Teacher Admin: Sending value of email to server to be updated");
                            try {
                                Teacher teacher = ((Teacher) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow()));
                                teacherManager.edit(teacher, String.valueOf(teacher.getIdUser()));
                                tblTeachers.refresh();
                            } catch (InternalServerErrorException e) {
                                LOGGER.severe("Window Teacher Admin: Unexpected error ocurred during modifying the salary");
                                Alert alert = new Alert(AlertType.ERROR, "Unexpected error ocurred during modifying");
                                alert.show();
                            }
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
        //Parameters for the ChoiceBoxTableCell established.
        name = FXCollections.observableArrayList(stringnames);
        tbcCourse.setCellFactory(cb -> new ChoiceBoxTableCell(name) {

            @Override
            public void startEdit() {
                //Overriding the start method of the ChoiceBoxTableCell,
                //so when start editing the graphic of the choice box with the values will be shown.
                super.startEdit();
                if (isEditing() && getGraphic() instanceof ChoiceBox) {
                    // needs focus for proper working of esc/enter 
                    getGraphic().requestFocus();
                    ((ChoiceBox<String>) getGraphic()).show();
                }
            }

        });
        //Setting the cell factory for the table column course
        //The cell factory will be a TexFieldTableCell.
        //If the creation is active, at the time of commiting the edit the value will not be sent to the server to be updated.
        tbcCourse.setOnEditCommit(
                (CellEditEvent<Teacher, String> t) -> {

                    ((Teacher) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setTeacherCourse(teacherCourseManager.findTeacherCourseByName(new GenericType<TeacherCourse>() {
                    }, t.getNewValue()));
                    tblTeachers.getSelectionModel().select(t.getTablePosition().getRow(), tbcSalary);
                    if (!ivTick.isVisible()) {
                        try {
                            LOGGER.info("Window Teacher Admin: Sending value of course to server to be updated");
                            Teacher teacher = ((Teacher) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow()));
                            teacherManager.edit(teacher, String.valueOf(teacher.getIdUser()));
                            tblTeachers.refresh();
                        } catch (InternalServerErrorException e) {
                            LOGGER.severe("Window Teacher Admin: Unexpected error ocurred during modifying the salary");
                            Alert alert = new Alert(AlertType.ERROR, "Unexpected error ocurred during modifying");
                            alert.show();
                        }
                    }
                });
    }
    /**
     * Listener that controls that the text field for filter has not reach the maximun character limit, if it does an alert will be shown.
     * @param observable
     * @param oldValue
     * @param newValue 
     */
    private void textChanged(Observable observable, String oldValue, String newValue) {
        if (tfFilter.getText().length() > 255) {
            Alert alert = new Alert(AlertType.ERROR, "Maximun character limit arrived");
            alert.show();
        }
    }
    /**
     * Method that close this window and open the window student admin.
     * @param event 
     */
    private void goToStudentWindow(MouseEvent event) {
        try {
            Parent root;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WindowStudentAdmin.fxml"));
            root = (Parent) loader.load();
            WindowStudentAdminController controller = loader.getController();
            controller.initStage(root);
            stage.close();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Method that return the user.
     * @return user
     */
    public User getUser() {
        return user;
    }
    /**
     * Method that set the value of the user.
     * @param user 
     */
    public void setUser(User user) {
        this.user = user;
    }
}
