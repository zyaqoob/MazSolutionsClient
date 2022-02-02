/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Course;
import classes.Student;
import classes.User;
import classes.UserPrivilege;
import classes.UserStatus;
import crypto.Crypto;
import interfaces.CourseManager;
import interfaces.StudentManager;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.fxml.FXMLLoader;
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
import restful.StudentRESTClient;

/**
 * Controller class for students management view . It contains event handlers
 * and initialization code for the view defined in WindowStudentAdmin.fmxl file.
 *
 * @author Miguel Sanchez
 */
public class WindowStudentAdminController {

    /**
     * Label to go to Students stage.
     */
    @FXML
    private Label lblStudents;
    /**
     * Label to go to Teachers stage.
     */
    @FXML
    private Label lblTeachers;
    /**
     * Label to go to Subjects stage.
     */
    @FXML
    private Label lblSubjects;
    /**
     * Label to go to Courses stage.
     */
    @FXML
    private Label lblCourses;
    /**
     * Label to go to TeacherCourses stage.
     */
    @FXML
    private Label lblTeacherCourses;
    /**
     * Create Student data button.
     */
    @FXML
    private Button btnCreate;
    /**
     * Delete Student data button.
     */
    @FXML
    private Button btnDelete;
    /**
     * Print Student button to see the report.
     */
    @FXML
    private Button btnPrint;
    /**
     * Apply filter image view.
     */
    @FXML
    private ImageView ivSearch;
    /**
     * Press this image view to accept the Students creation.
     */
    @FXML
    private ImageView ivTick;
    /**
     * Press this image view to cancel the Students creation.
     */
    @FXML
    private ImageView ivX;
    /**
     * Students data table view.
     */
    @FXML
    private TableView<Student> tblStudents;
    /**
     * Students full name data table column.
     */
    @FXML
    private TableColumn<Student, String> tbcFullName;
    /**
     * Students course data table column.
     */
    @FXML
    private TableColumn<Student, String> tbcCourse;
    /**
     * Students year data table column.
     */
    @FXML
    private TableColumn<Student, Date> tbcYear;
    /**
     * Students email data table column.
     */
    @FXML
    private TableColumn<Student, String> tbcEmail;
    /**
     * Students telephone data table column.
     */
    @FXML
    private TableColumn<Student, String> tbcTelephone;
    /**
     * Students birth date data table column.
     */
    @FXML
    private TableColumn<Student, Date> tbcBirthDate;
    /**
     * Students Login data table column.
     */
    @FXML
    private TableColumn<Student, String> tbcLogin;
    /**
     * Filter choice box.
     */
    @FXML
    private ChoiceBox chbFilterStudents;
    /**
     * Filter text field.
     */
    @FXML
    private TextField tfSearch;
    /**
     * Students's data model.
     */
    private ObservableList<Student> studentsData;
    /**
     * Student manager to manage students.
     */
    private final StudentManager restStudents = (StudentManager) new RESTfulFactory().getRESTClient(RESTfulClientType.STUDENT);
    /**
     * Courses's data model.
     */
    private ObservableList<Course> coursesData;
    /**
     * Course manager to manage students.
     */
    private final CourseManager restCourses = (CourseManager) new RESTfulFactory().getRESTClient(RESTfulClientType.COURSE);
    /**
     * To work with stage methods.
     */
    public static Stage stageStudent = new Stage();

    private static final Logger LOGGER = Logger.getLogger("view");

    /**
     * Method for initializing WindowStudentAdmin Stage.
     *
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) {
        try {
            // studentsData inizialization.
            studentsData = FXCollections.observableArrayList(restStudents.findAllStudents(new GenericType<List<Student>>() {
            }));
            // coursesData inizialization.
            coursesData = FXCollections.observableArrayList(restCourses.findAllCourses(new GenericType<List<Course>>() {
            }));
            Scene scene = new Scene(root);
            //Set stage properties
            stageStudent.setScene(scene);
            stageStudent.setTitle("Student Crud");
            stageStudent.setResizable(false);
            // Table and controls inizialization.
            tblStudents.setEditable(true);
            ivTick.setVisible(false);
            ivX.setVisible(false);
            btnDelete.setDisable(true);
            //Set factories for cell values in students table columns.
            tbcFullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
            tbcCourse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourse().getName()));
            tbcYear.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getYear()));
            tbcEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
            tbcTelephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));
            tbcBirthDate.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getBirthDate()));
            tbcLogin.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getLogin()));
            //Set filter options combo data model.
            ObservableList<String> name;
            List<String> stringnames = new ArrayList<>();
            stringnames.add("Reset");
            stringnames.add("Student full name");
            stringnames.add("Student courses");
            stringnames.add("Student year");
            stringnames.add("Student email");
            stringnames.add("Student telephone");
            stringnames.add("Student birth date");
            name = FXCollections.observableArrayList(stringnames);
            chbFilterStudents.setItems(name);
            chbFilterStudents.getSelectionModel().selectFirst();
            //Set cell factories.
            tableCellInit();
            //Set table model.
            tblStudents.setItems(studentsData);
            //Add property change listeners for controls
            btnCreate.setOnAction(this::create);
            btnDelete.setOnAction(this::delete);
            ivTick.setOnMouseClicked(this::accept);
            ivSearch.setOnMouseClicked(this::filterBySelectedValue);
            tfSearch.textProperty().addListener(this::textChanged);
            ivX.setOnMouseClicked(this::cancel);
            tblStudents.getSelectionModel().selectedItemProperty().addListener(this::tableSelectionChanged);
            lblTeachers.setOnMouseClicked(this::changeToTeacherWindow);
            btnPrint.setOnMouseClicked(this::printReport);
            //Show window.
            stageStudent.show();
        } catch (ClientErrorException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Service unavailable." + e.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    /**
     * Action event handler for create button. It creates a new student, set the
     * focus into the table and update the status of some buttons.
     *
     * @param action The ActionEvent object for the event.
     */
    public void create(ActionEvent action) {
        LOGGER.info("Creating user...");
        //Set ivSearch invisible.
        ivSearch.setVisible(false);
        //Create an student with a course.
        Student student = new Student();
        Course course = new Course();
        student.setCourse(course);
        //Add the student to the students data model.
        studentsData.add(student);
        //Set the focus in the correct cell.
        tblStudents.getSelectionModel().select(studentsData.size() - 1);
        tblStudents.getFocusModel().focus(studentsData.size() - 1, tbcFullName);
        tblStudents.layout();
        tblStudents.edit(studentsData.size() - 1, tbcFullName);
        //Set new values for buttons.
        ivTick.setVisible(true);
        ivX.setVisible(true);
        btnCreate.setDisable(true);
    }

    /**
     * Action event handler for delete button. It asks user for confirmation on
     * delete, sends delete message to the business logic tier and updates user
     * table view.
     *
     * @param action The ActionEvent object for the event.
     */
    public void delete(ActionEvent action) {

        try {
            LOGGER.info("Deleting user...");
            //Ask user for confirmation on delete.
            Alert deleteConfirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteConfirmationAlert.setHeaderText(null);
            deleteConfirmationAlert.setTitle("Confirmation");
            deleteConfirmationAlert.setContentText("Are you sure that you want to erase this student?");
            Optional<ButtonType> button = deleteConfirmationAlert.showAndWait();
            if (button.get() == ButtonType.OK) {
                //delete user from server side.
                restStudents.remove(String.valueOf(tblStudents.getSelectionModel().getSelectedItem().getIdUser()));
                //Updates studentsData value and removes selected item from table.
                studentsData = FXCollections.observableArrayList(restStudents.findAllStudents(new GenericType<List<Student>>() {
                }));
                tblStudents.setItems(studentsData);
                tblStudents.refresh();
            }
        } catch (InternalServerErrorException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Unexpected error ocurred while deleting the student.", ButtonType.OK);
            alert.show();
            //Update the value of the buttons if the delete fails.
            ivSearch.setVisible(true);
            ivX.setVisible(false);
            ivTick.setVisible(false);
            studentsData = FXCollections.observableArrayList(restStudents.findAllStudents(new GenericType<List<Student>>() {
            }));
            tblStudents.setItems(studentsData);
            tblStudents.refresh();
            btnCreate.setDisable(false);

        }

    }

    /**
     * Action event handler for acept image view. It validates new user data,
     * send it to the business logic tier and updates user table view with new
     * user data.
     *
     * @param event The ActionEvent object for the event.
     */
    private void accept(MouseEvent event) {
        //Get the student from the table.
        Student student = tblStudents.getSelectionModel().getSelectedItem();
        if (student != null) {
            try {
                //Set the rest of the student information.
                student.setPrivilege(UserPrivilege.STUDENT);
                student.setStatus(UserStatus.ENABLED);
                //Find the course whit the name in the table and set it into the student.
                student.setCourse(restCourses.findCourseByName(new GenericType<Course>() {
                }, student.getCourse().getName()));
                student.setPassword(Crypto.cifrar(generatePassword()));
                //Send student data to business logic tier
                restStudents.create(student);
                //Update the image views status.
                ivTick.setVisible(false);
                ivX.setVisible(false);
                //Update the table whith all the students.
                studentsData = FXCollections.observableArrayList(restStudents.findAllStudents(new GenericType<List<Student>>() {
                }));
                tblStudents.setItems(studentsData);
                tblStudents.refresh();
                //Update the buttons status.
                btnCreate.setDisable(false);
                ivSearch.setVisible(true);
            } catch (WebApplicationException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR, "Error while creating the user.", ButtonType.OK);
                alert.show();

            }
        }
    }

    /**
     * Action event handler for cancel image view. It cancel the create action.
     *
     * @param event The ActionEvent object for the event.
     */
    private void cancel(MouseEvent event) {
        //Restart the students table and data values
        studentsData = FXCollections.observableArrayList(restStudents.findAllStudents(new GenericType<List<Student>>() {
        }));
        tblStudents.setItems(studentsData);
        //Update the buttons status.
        ivTick.setVisible(false);
        ivX.setVisible(false);
        btnCreate.setDisable(false);
        ivSearch.setVisible(true);
    }

    /**
     * This method observes the table selection changes. It enables or disables
     * buttons depending on selection state of the table to prevent posible
     * erros.
     *
     * @param observable, object that has listener, being observed.
     * @param oldValue indicates the old value(could be default).
     * @param newValue indicates the newly introduced value.
     */
    public void tableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {

        if (newValue == null || btnCreate.isDisabled()) {
            btnDelete.setDisable(true);
        } else if (!ivTick.isVisible()) {
            btnDelete.setDisable(false);
        }

    }

    /**
     * This method set the cell factories and manages the table edition.
     */
    public void tableCellInit() {
        //Table column FullName editable with textField
        tbcFullName.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcFullName.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    ((Student) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).setFullName(s.getNewValue());
                    if (!ivTick.isVisible()) {
                        Student student = ((Student) s.getTableView().getItems().get(
                                s.getTablePosition().getRow()));
                        if (student.getFullName() != null) {
                            new StudentRESTClient().edit(student, String.valueOf(student.getIdUser()));
                        }
                    }
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcCourse);
                    tblStudents.edit(s.getTablePosition().getRow(), tbcCourse);
                });

        //Table column Course editable with textField
        ObservableList<String> name;
        List<String> stringnames = new ArrayList<>();
        for (int i = 0; i < coursesData.size(); i++) {
            stringnames.add(i, coursesData.get(i).getName());
        }
        name = FXCollections.observableArrayList(stringnames);
        tbcCourse.setCellFactory(cb -> new ChoiceBoxTableCell(name) {
            @Override
            public void startEdit() {
                super.startEdit();
                if (isEditing() && getGraphic() instanceof ChoiceBox) {
                    getGraphic().requestFocus();
                    ((ChoiceBox<String>) getGraphic()).show();
                }
            }
        });
        tbcCourse.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    ((Student) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).getCourse().setName(s.getNewValue());
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcYear);
                    if (!ivTick.isVisible()) {
                        Student student = ((Student) s.getTableView().getItems().get(
                                s.getTablePosition().getRow()));
                        new StudentRESTClient().edit(student, String.valueOf(student.getIdUser()));
                    }
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcYear);
                    tblStudents.edit(s.getTablePosition().getRow(), tbcYear);
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
                    if (!ivTick.isVisible()) {
                        Student student = ((Student) s.getTableView().getItems().get(
                                s.getTablePosition().getRow()));
                        new StudentRESTClient().edit(student, String.valueOf(student.getIdUser()));
                    }
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcEmail);
                    tblStudents.edit(s.getTablePosition().getRow(), tbcEmail);
                });
        tbcYear.setOnEditCancel((TableColumn.CellEditEvent<Student, Date> s) -> {
            tblStudents.refresh();
        });

        //Table column Email editable with textField
        tbcEmail.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcEmail.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    if (s.getNewValue().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
                        ((Student) s.getTableView().getItems().get(
                                s.getTablePosition().getRow())).setEmail(s.getNewValue());
                        tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcTelephone);
                        if (!ivTick.isVisible()) {
                            Student student = ((Student) s.getTableView().getItems().get(
                                    s.getTablePosition().getRow()));
                            try {

                                new StudentRESTClient().edit(student, String.valueOf(student.getIdUser()));

                            } catch (WebApplicationException e) {

                                studentsData = FXCollections.observableArrayList(restStudents.findAllStudents(new GenericType<List<Student>>() {
                                }));
                                tblStudents.setItems(studentsData);
                                tblStudents.refresh();
                                Alert alert = new Alert(Alert.AlertType.ERROR, "This email is already registered.", ButtonType.OK);
                                alert.show();

                            }
                        }
                        tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcTelephone);
                        tblStudents.edit(s.getTablePosition().getRow(), tbcTelephone);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid email format.", ButtonType.OK);
                        alert.show();

                    }
                });

        //Table column Telephone editable with textField
        tbcTelephone.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcTelephone.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    if (s.getNewValue().matches("[0-9]{9}")) {
                        ((Student) s.getTableView().getItems().get(
                                s.getTablePosition().getRow())).setTelephone(s.getNewValue());
                        tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcBirthDate);
                        if (!ivTick.isVisible()) {
                            Student student = ((Student) s.getTableView().getItems().get(
                                    s.getTablePosition().getRow()));
                            new StudentRESTClient().edit(student, String.valueOf(student.getIdUser()));
                        }
                        tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcBirthDate);
                        tblStudents.edit(s.getTablePosition().getRow(), tbcBirthDate);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid telephone.", ButtonType.OK);
                        alert.show();
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
                    if (!ivTick.isVisible()) {
                        Student student = ((Student) s.getTableView().getItems().get(
                                s.getTablePosition().getRow()));
                        new StudentRESTClient().edit(student, String.valueOf(student.getIdUser()));
                    }
                    tblStudents.getSelectionModel().select(s.getTablePosition().getRow(), tbcLogin);
                    tblStudents.edit(s.getTablePosition().getRow(), tbcLogin);
                });
        tbcBirthDate.setOnEditCancel((TableColumn.CellEditEvent<Student, Date> s) -> {
            tblStudents.refresh();
        });

        //Table column FullName editable with textField
        tbcLogin.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        tbcLogin.setOnEditCommit(
                (CellEditEvent<Student, String> s) -> {
                    ((Student) s.getTableView().getItems().get(
                            s.getTablePosition().getRow())).setLogin(s.getNewValue());
                    if (!ivTick.isVisible()) {
                        Student student = ((Student) s.getTableView().getItems().get(
                                s.getTablePosition().getRow()));
                        try {

                            new StudentRESTClient().edit(student, String.valueOf(student.getIdUser()));

                        } catch (WebApplicationException e) {

                            studentsData = FXCollections.observableArrayList(restStudents.findAllStudents(new GenericType<List<Student>>() {
                            }));
                            tblStudents.setItems(studentsData);
                            tblStudents.refresh();
                            Alert alert = new Alert(Alert.AlertType.ERROR, "This login is already registered.", ButtonType.OK);
                            alert.show();

                        }
                    }
                });
    }

    /**
     * This method generates a random password.
     *
     * @return
     */
    public static String generatePassword() {
        String password;
        String charLow[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String charUpper[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String number[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        password = charLow[(int) (Math.random() * charLow.length)] + charUpper[(int) (Math.random() * charUpper.length)] + number[(int) (Math.random() * 9)] + charLow[(int) (Math.random() * charLow.length)] + charUpper[(int) (Math.random() * charUpper.length)] + number[(int) (Math.random() * 9)];
        return password;
    }

    /**
     * This method get the value of the choice box chbFilterStudents and the
     * text field tfSearch to apply the filter.
     *
     * @param event
     */
    public void filterBySelectedValue(MouseEvent event) {
        //Apply a formatter to convert the posibles Dates in the text field.
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        //Get the students in the table
        ObservableList<Student> students = tblStudents.getItems();
        //Analizes the value in the choice box and apply the filter.
        if (chbFilterStudents.getSelectionModel().getSelectedItem().equals("Student full name")) {
            studentsData = FXCollections.observableArrayList(students.stream()
                    .filter(s -> s.getFullName().equalsIgnoreCase(tfSearch.getText()))
                    .map(s -> s).collect(Collectors.toList()));
            tblStudents.setItems(studentsData);
        }
        if (chbFilterStudents.getSelectionModel().getSelectedItem().equals("Student course")) {
            studentsData = FXCollections.observableArrayList(students.stream()
                    .filter(s -> s.getCourse().getName().equalsIgnoreCase(tfSearch.getText()))
                    .map(s -> s).collect(Collectors.toList()));
            tblStudents.setItems(studentsData);
        }
        if (chbFilterStudents.getSelectionModel().getSelectedItem().equals("Student year")) {
            studentsData = FXCollections.observableArrayList(students.stream()
                    .filter(s -> dateFormatter.format(s.getYear()).equalsIgnoreCase(tfSearch.getText()))
                    .map(s -> s).collect(Collectors.toList()));
            tblStudents.setItems(studentsData);
        }
        if (chbFilterStudents.getSelectionModel().getSelectedItem().equals("Student email")) {
            studentsData = FXCollections.observableArrayList(students.stream()
                    .filter(s -> s.getEmail().equalsIgnoreCase(tfSearch.getText()))
                    .map(s -> s).collect(Collectors.toList()));
            tblStudents.setItems(studentsData);
        }
        if (chbFilterStudents.getSelectionModel().getSelectedItem().equals("Student telephone")) {
            studentsData = FXCollections.observableArrayList(students.stream()
                    .filter(s -> s.getTelephone().equalsIgnoreCase(tfSearch.getText()))
                    .map(s -> s).collect(Collectors.toList()));
            tblStudents.setItems(studentsData);
        }
        if (chbFilterStudents.getSelectionModel().getSelectedItem().equals("Student birth date")) {
            studentsData = FXCollections.observableArrayList(students.stream()
                    .filter(s -> dateFormatter.format(s.getBirthDate()).equalsIgnoreCase(tfSearch.getText()))
                    .map(s -> s).collect(Collectors.toList()));
            tblStudents.setItems(studentsData);
        }
        if (chbFilterStudents.getSelectionModel().getSelectedItem().equals("Reset")) {
            studentsData = FXCollections.observableArrayList(restStudents.findAllStudents(new GenericType<List<Student>>() {
            }));
            tblStudents.setItems(studentsData);
        }

    }

    /**
     * Method to check the character limit of the search text field.
     *
     * @param observable, object that has listener, being observed.
     * @param oldValue indicates the old value(could be default).
     * @param newValue indicates the newly introduced value.
     */
    public void textChanged(ObservableValue observable, Object oldValue, Object newValue) {

        if (tfSearch.getText().length() > 255) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You have entered a value that is too large for this search.", ButtonType.OK);
            alert.show();
        }

    }

    /**
     * Method that close the current stage, and open a Teacher window.
     *
     * @param event The ActionEvent object for the event.
     */
    public void changeToTeacherWindow(MouseEvent event) {

        LOGGER.info("Changing to teacher window.");
        stageStudent.fireEvent(new WindowEvent(stageStudent, WindowEvent.WINDOW_CLOSE_REQUEST));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WindowTeacherAdmin.fxml"));
        try {
            Parent root = (Parent) loader.load();
            AdminTeacherWindowController controller = loader.getController();
            controller.initStage(root);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "ERROR WHILE SIGNING UP", ButtonType.OK);
        }

    }
    /**
     * Action event handler for print button. It shows a JFrame containing a
     * report. This JFrame allows to print the report.
     *
     * @param event The ActionEvent object for the event.
     */
    public void printReport(MouseEvent event) {

        LOGGER.info("Beginning printing action...");
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/AdminStudentReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Student>) this.tblStudents.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(WindowStudentAdminController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error while loading the Report.", ButtonType.OK);
        }

    }

}
