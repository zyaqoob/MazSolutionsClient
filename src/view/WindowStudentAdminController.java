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
import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
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
    private TableColumn<Student, String> tbcLogin;

    @FXML
    private ChoiceBox chbFilterStudents;

    @FXML
    private TextField tfSearch;

    private StudentRESTClient restStudents = new StudentRESTClient();
    private ObservableList<Student> studentsData;
    private CourseRESTClient restCourses = new CourseRESTClient();
    private ObservableList<Course> coursesData;

    public void initStage(Parent root) {

        try {
            studentsData = FXCollections.observableArrayList(restStudents.findAllStudents(new GenericType<List<Student>>() {
            }));
            coursesData = FXCollections.observableArrayList(restCourses.findAllCourses(new GenericType<List<Course>>() {
            }));
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Service unavailable" + e.getMessage(), ButtonType.OK);
            alert.show();

        }

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);

        tblStudents.setEditable(true);
        ivTick.setVisible(false);
        ivX.setVisible(false);
        btnDelete.setDisable(true);
        tbcFullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        tbcCourse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourse().getName()));
        tbcYear.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getYear()));
        tbcEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        tbcTelephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));
        tbcBirthDate.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getBirthDate()));
        tbcLogin.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getLogin()));

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

        tableCellInit();

        tblStudents.setItems(studentsData);
        btnCreate.setOnAction(this::create);
        btnDelete.setOnAction(this::delete);
        ivTick.setOnMouseClicked(this::accept);
        ivSearch.setOnMouseClicked(this::filterBySelectedValue);
        tfSearch.textProperty().addListener(this::textChanged);
        ivX.setOnMouseClicked(this::cancel);
        tblStudents.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChanged);
        stage.show();
    }

    public void create(ActionEvent action) {
        ivSearch.setVisible(false);
        Student student = new Student();
        Course course = new Course();
        student.setCourse(course);
        studentsData.add(student);
        tblStudents.getSelectionModel().select(studentsData.size() - 1);
        tblStudents.getFocusModel().focus(studentsData.size() - 1, tbcFullName);
        tblStudents.layout();
        tblStudents.edit(studentsData.size() - 1, tbcFullName);
        ivTick.setVisible(true);
        ivX.setVisible(true);
        btnCreate.setDisable(true);
    }

    public void delete(ActionEvent action) {

        Alert deleteConfirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirmationAlert.setHeaderText(null);
        deleteConfirmationAlert.setTitle("Confirmation");
        deleteConfirmationAlert.setContentText("Are you sure that you want to erase this student?");
        Optional<ButtonType> button = deleteConfirmationAlert.showAndWait();
        if (button.get() == ButtonType.OK) {
            try {
                restStudents.remove(String.valueOf(tblStudents.getSelectionModel().getSelectedItem().getIdUser()));
                studentsData = FXCollections.observableArrayList(restStudents.findAllStudents(new GenericType<List<Student>>() {
                }));
                tblStudents.setItems(studentsData);
                tblStudents.refresh();
            } catch (InternalServerErrorException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR, "Unexpected error ocurred while deleting the student.", ButtonType.OK);
                alert.show();
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

    }

    private void accept(MouseEvent event) {
        Student student = tblStudents.getSelectionModel().getSelectedItem();

        if (student != null) {
            try {
                student.setPrivilege(UserPrivilege.STUDENT);
                student.setStatus(UserStatus.ENABLED);
                student.setCourse(restCourses.findCourseByName(new GenericType<Course>() {
                }, student.getCourse().getName()));
                student.setPassword(Crypto.cifrar(generatePassword()));
                restStudents.create(student);
                ivTick.setVisible(false);
                ivX.setVisible(false);
                //restStudents.remove(String.valueOf(tblStudents.getSelectionModel().getSelectedItem().getIdUser()));
                studentsData = FXCollections.observableArrayList(restStudents.findAllStudents(new GenericType<List<Student>>() {
                }));
                tblStudents.setItems(studentsData);
                tblStudents.refresh();
                btnCreate.setDisable(false);
                ivSearch.setVisible(true);
            } catch (WebApplicationException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR, "Error while creating the user.\n" + e.getMessage(), ButtonType.OK);
                alert.show();

            }
        }
    }

    private void cancel(MouseEvent event) {
        studentsData = FXCollections.observableArrayList(restStudents.findAllStudents(new GenericType<List<Student>>() {
        }));
        tblStudents.setItems(studentsData);
        ivTick.setVisible(false);
        ivX.setVisible(false);
        btnCreate.setDisable(false);
        ivSearch.setVisible(true);
    }

    public void handleTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {

        if (newValue == null || btnCreate.isDisabled()) {
            btnDelete.setDisable(true);
        } else if (!ivTick.isVisible()) {
            btnDelete.setDisable(false);
        }

    }

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
                    // needs focus for proper working of esc/enter 
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

    public static String generatePassword() {
        String password;
        String charLow[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String charUpper[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String number[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        password = charLow[(int) (Math.random() * charLow.length)] + charUpper[(int) (Math.random() * charUpper.length)] + number[(int) (Math.random() * 9)] + charLow[(int) (Math.random() * charLow.length)] + charUpper[(int) (Math.random() * charUpper.length)] + number[(int) (Math.random() * 9)];
        return password;
    }

    public void filterBySelectedValue(MouseEvent event) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        ObservableList<Student> students = tblStudents.getItems();
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

    //this method observes the text changes
    public void textChanged(ObservableValue observable, Object oldValue, Object newValue) {

        if (tfSearch.getText().length() > 255) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "You have entered a value that is too large for this search.", ButtonType.OK);
            alert.show();
            //tfSearch.setText("");

        }

    }

}
