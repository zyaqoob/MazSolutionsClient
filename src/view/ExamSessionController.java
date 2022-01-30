/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Course;
import classes.Exam;
import classes.ExamSession;
import classes.Student;
import classes.Subject;
import classes.User;
import interfaces.ExamManager;
import interfaces.ExamSessionManager;
import interfaces.StudentManager;
import interfaces.SubjectManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
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
 *
 * @author z332h
 */
public class ExamSessionController {

    @FXML
    private ChoiceBox chBoxFilters;
    @FXML
    private TextField txtFilters;
    @FXML
    private ImageView ivSearch;
    @FXML
    private ImageView ivTick;
    @FXML
    private ImageView ivCross;
    @FXML
    private Label lblCourse;
    @FXML
    private Label lblExam;
    @FXML
    private Label lblExamSession;
    @FXML
    private TableView<ExamSession> tblExamSession;
    @FXML
    private TableColumn<ExamSession, String> tcSubject;
    @FXML
    private TableColumn<ExamSession, String> tcExam;
    @FXML
    private TableColumn<ExamSession, String> tcStudent;
    @FXML
    private TableColumn<ExamSession, String> tcDateStart;
    @FXML
    private TableColumn<ExamSession, String> tcDateEnd;
    @FXML
    private TableColumn<ExamSession, String> tcMark;
    @FXML
    private Button btnRound;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnDelete;
    @FXML
    Button btnPrint;

    private DateFormat dateFormat;

    private RESTfulFactory factory;

    private ExamSessionManager examSessionManager;

    private ObservableList<ExamSession> examSessionData;

    private ObservableList<Student> studentData;

    private ObservableList<Exam> examData;

    private ObservableList<Subject> subjectData;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void initStage(Parent root) {
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Exam Sessions");
        stage.setResizable(false);
        stage.requestFocus();
        tblExamSession.setEditable(true);
        tblExamSession.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChanged);
        btnDelete.setDisable(true);
        btnCreate.setDisable(false);
        ivTick.setVisible(false);
        ivCross.setVisible(false);

        ivSearch.setOnMouseClicked(this::handleSearchEvent);
        txtFilters.setText("");
        btnPrint.setOnAction(this::handlePrintEvent);
        btnCreate.setOnAction(this::handleCreationEvent);
        btnDelete.setDisable(true);
        btnDelete.setOnAction(this::handleDeleteEvent);
        // btnRound.setText(user.getFullName().substring(0, 1));
        dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm");

        factory = new RESTfulFactory();
        examSessionManager = (ExamSessionManager) factory.getRESTClient(RESTfulClientType.EXAM_SESSION);

        StudentManager studentManager = (StudentRESTClient) factory.getRESTClient(RESTfulClientType.STUDENT);

        ExamManager examManager = (ExamManager) factory.getRESTClient(RESTfulClientType.EXAM);

        SubjectManager subjectManager = (SubjectManager) factory.getRESTClient(RESTfulClientType.SUBJECT);

        studentData = FXCollections.observableArrayList(studentManager.findAllStudents(new GenericType<List<Student>>() {
        }));

        subjectData = FXCollections.observableArrayList(subjectManager.findAllSubject(new GenericType<List<Subject>>() {
        }));

        examData = FXCollections.observableArrayList(examManager.findAllExam(new GenericType<List<Exam>>() {
        }));
        examSessionData = FXCollections.observableArrayList(examSessionManager.findAllExamSession(new GenericType<List<ExamSession>>() {
        }));

        tcSubject.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExam().getSubject().getName()));
        tcStudent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getFullName()));
        tcExam.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExam().getExamStatement()));
        //tcDateStart.setCellValueFactory(new PropertyValueFactory<>(dateFormat.format(("dateTimeStart"))));
        tcDateStart.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(cellData.getValue().getDateTimeStart().getTime())));
        tcDateEnd.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(cellData.getValue().getDateTimeEnd().getTime())));
        tcMark.setCellValueFactory(cellData -> new SimpleObjectProperty(String.valueOf(cellData.getValue().getMark())));

        for (int i = 0; i < tblExamSession.getColumns().size(); i++) {
            if (!tblExamSession.getColumns().get(i).getText().equals(tcDateEnd.getText()) && !tblExamSession.getColumns().get(i).getText().equals(tcDateStart.getText())) {
                chBoxFilters.getItems().add(tblExamSession.getColumns().get(i).getText());
            }
        }
        chBoxFilters.getItems().add("Show all");
        chBoxFilters.setValue(chBoxFilters.getItems().get(0));

        //Table column subject
        ObservableList<String> subjectNames;
        List<String> sNames = new ArrayList<>();
        for (int i = 0; i < subjectData.size(); i++) {
            sNames.add(i, subjectData.get(i).getName());
        }
        subjectNames = FXCollections.observableArrayList(sNames);
        tcSubject.setCellFactory(ChoiceBoxTableCell.forTableColumn(subjectNames));
        tcSubject.setEditable(false);
        tcSubject.setOnEditCommit(this::handleTcSubjectEdit);

        //Table column exam
        ObservableList<String> examNames;
        List<String> eNames = new ArrayList<>();
        for (int i = 0; i < examData.size(); i++) {
            eNames.add(i, examData.get(i).getExamStatement());
        }
        examNames = FXCollections.observableArrayList(eNames);
        tcExam.setCellFactory(ChoiceBoxTableCell.forTableColumn(examNames));
        tcExam.setOnEditCommit(this::handleTcExamEdit);

        //Table column student
        ObservableList<String> studentNames;
        List<String> names = new ArrayList<>();
        for (int i = 0; i < studentData.size(); i++) {
            names.add(i, studentData.get(i).getFullName());
        }
        studentNames = FXCollections.observableArrayList(names);
        tcStudent.setCellFactory(ChoiceBoxTableCell.forTableColumn(studentNames));
        tcStudent.setOnEditCommit(this::handleTcStudentEdit);

        //Table column dateTimeStart
        tcDateStart.setCellFactory(TextFieldTableCell.<ExamSession>forTableColumn());
        tcDateStart.setOnEditCommit(this::handleTcDateStartEdit);

        // tcDateStart.setOnEditStart(this::handleTcDateStartEditStart);
        //Table column dateTimeEnd
        tcDateEnd.setCellFactory(TextFieldTableCell.<ExamSession>forTableColumn());
        tcDateEnd.setOnEditCommit(this::handleTcDateEndEdit);

        //Table Column mark.
        tcMark.setCellFactory(TextFieldTableCell.<ExamSession>forTableColumn());
        tcMark.setOnEditCommit(this::handleTcMarkEdit);

        ivTick.setOnMouseClicked(this::handleAcceptEvent);
        ivCross.setOnMouseClicked(this::handleCreateCancelEvent);
        tblExamSession.setItems(examSessionData);
        stage.show();

    }

    private void handleTcSubjectEdit(CellEditEvent<ExamSession, String> t) {

        ((ExamSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).getExam().getSubject().setName(t.getNewValue());
        for (int i = 0; i < subjectData.size(); i++) {
            if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getExam().getSubject().getName().equals(subjectData.get(i).getName())) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).getExam().setSubject(subjectData.get(i));
            }
        }
        if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getIdExamSession() != null) {
            ExamSession examSession = t.getTableView().getItems().get(t.getTablePosition().getRow());

            examSessionManager.edit(examSession, String.valueOf(examSession.getIdExamSession()));
        }
        tblExamSession.getSelectionModel().select(t.getTablePosition().getRow(), tcExam);
        tblExamSession.edit(t.getTablePosition().getRow(), tcExam);
        tcExam.setEditable(true);
    }

    private void handleTcStudentEdit(CellEditEvent<ExamSession, String> t) {

        ((ExamSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).getStudent().setFullName(t.getNewValue());
        for (int i = 0; i < studentData.size(); i++) {
            if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getStudent().getFullName().equals(studentData.get(i).getFullName())) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setStudent(studentData.get(i));
            }
        }
        if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getIdExamSession() != null) {
            ExamSession examSession = t.getTableView().getItems().get(t.getTablePosition().getRow());
            examSessionManager.edit(examSession, String.valueOf(examSession.getIdExamSession()));
        }
        tblExamSession.getSelectionModel().select(t.getTablePosition().getRow(), tcDateStart);
        tblExamSession.edit(t.getTablePosition().getRow(), tcDateStart);
    }

    private void handleTcExamEdit(CellEditEvent<ExamSession, String> t) {
        boolean found = false;
        String examStatement = t.getOldValue();
        try {
            ((ExamSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).getExam().setExamStatement(t.getNewValue());
            for (int i = 0; i < examData.size(); i++) {
                if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getExam().getExamStatement().equals(examData.get(i).getExamStatement())
                        && t.getTableView().getItems().get(t.getTablePosition().getRow()).getExam().getSubject().getName().equals(examData.get(i).getSubject().getName())) {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setExam(examData.get(i));
                    found = true;
                }
            }
            if (!found) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Exam for selected Subject.", ButtonType.OK);
                alert.show();
                ((ExamSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).getExam().setExamStatement(examStatement);
                tblExamSession.refresh();

            }
            if (found == true && t.getTableView().getItems().get(t.getTablePosition().getRow()).getIdExamSession() != null) {

                ExamSession examSession = t.getTableView().getItems().get(t.getTablePosition().getRow());
                examSessionManager.edit(examSession, String.valueOf(examSession.getIdExamSession()));
            }
            tblExamSession.getSelectionModel().select(t.getTablePosition().getRow(), tcStudent);
            tblExamSession.edit(t.getTablePosition().getRow(), tcStudent);
            if (((ExamSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).getExam().getSubject() == null) {

            }

        } catch (NullPointerException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please slect a Subject.", ButtonType.OK);
            alert.show();
            tblExamSession.refresh();
        } catch (Exception e) {
        }
    }

    private void handleTcDateStartEdit(CellEditEvent<ExamSession, String> t) {
        try {
            Calendar cal = new GregorianCalendar();
            cal.setTime(dateFormat.parse(t.getNewValue()));

            Calendar calOld = new GregorianCalendar();
            calOld.setTime(dateFormat.parse(t.getOldValue()));
            if (cal.after((t.getTableView().getItems().get(t.getTablePosition().getRow()).getDateTimeEnd()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Date Start should be before the Date End", ButtonType.OK);
                alert.show();
                tblExamSession.refresh();

            } else {
                ((ExamSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDateTimeStart(cal);
                if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getIdExamSession() != null) {
                    ExamSession examSession = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    examSessionManager.edit(examSession, String.valueOf(examSession.getIdExamSession()));
                }
                tblExamSession.getSelectionModel().select(t.getTablePosition().getRow(), tcDateEnd);
                tblExamSession.edit(t.getTablePosition().getRow(), tcDateEnd);
            }

        } catch (ParseException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid date format, it should be 'dd-MM-yyyy : hh:mm'.", ButtonType.OK);
            alert.show();
            tblExamSession.refresh();

        }
    }

    private void handleTcDateEndEdit(CellEditEvent<ExamSession, String> t) {
        try {
            //Calendar cal = Calendar.getInstance();
            Calendar cal = new GregorianCalendar();
            cal.setTime(dateFormat.parse(t.getNewValue()));
            if (cal.before((t.getTableView().getItems().get(t.getTablePosition().getRow()).getDateTimeEnd()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Date Start should be before the Date End", ButtonType.OK);
                alert.show();
                tblExamSession.refresh();

            } else {
                ((ExamSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDateTimeEnd(cal);
                if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getIdExamSession() != null) {
                    ExamSession examSession = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    examSessionManager.edit(examSession, String.valueOf(examSession.getIdExamSession()));
                }
            }

            tblExamSession.getSelectionModel().select(t.getTablePosition().getRow(), tcMark);
            tblExamSession.edit(t.getTablePosition().getRow(), tcMark);
        } catch (ParseException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid date format, it should be 'dd-MM-yyyy : hh:mm'.", ButtonType.OK);
            alert.show();
            tblExamSession.refresh();
        }
    }

    private void handleTcMarkEdit(CellEditEvent<ExamSession, String> t) {
        if (!t.getNewValue().matches("\\d*") || Integer.parseInt(t.getNewValue().trim()) > 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid value for mark, it should be between 0 to 10.", ButtonType.OK);
            alert.show();
            ((ExamSession) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setMark(Integer.parseInt(t.getOldValue().trim()));
            tblExamSession.refresh();
        } else {
            ((ExamSession) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setMark(Integer.parseInt(t.getNewValue().trim()));
            if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getIdExamSession() != null) {
                ExamSession examSession = t.getTableView().getItems().get(t.getTablePosition().getRow());
                examSessionManager.edit(examSession, String.valueOf(examSession.getIdExamSession()));
            }
            tblExamSession.getSelectionModel().select(t.getTablePosition().getRow(), tcMark);
            tblExamSession.edit(t.getTablePosition().getRow(), tcMark);
        }
    }

    private void handleTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue == null) {
            btnDelete.setDisable(true);
        } else {
            btnDelete.setDisable(false);
        }
    }

    private void handleCreationEvent(ActionEvent action) {
        tcSubject.setEditable(true);
        tcExam.setEditable(false);
        ExamSession eSession = new ExamSession();
        eSession.setStudent(new Student());
        eSession.getStudent().setCourse(new Course());
        eSession.setExam(new Exam());
        eSession.getExam().setSubject(new Subject());
        eSession.setDateTimeEnd(new GregorianCalendar());
        eSession.setDateTimeStart(new GregorianCalendar());

        examSessionData.add(eSession);
        tblExamSession.getSelectionModel().select(examSessionData.size() - 1);
        tblExamSession.getFocusModel().focus(examSessionData.size() - 1, tcSubject);
        tblExamSession.edit(examSessionData.size() - 1, tcSubject);
        ivTick.setVisible(true);
        ivCross.setVisible(true);
        btnCreate.setDisable(true);
        btnDelete.setDisable(true);

    }

    private void handleDeleteEvent(ActionEvent action) {

        tblExamSession.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the record.?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> button = alert.showAndWait();
        if (button.get() == ButtonType.YES) {

            examSessionManager.remove(tblExamSession.getSelectionModel().getSelectedItem().getIdExamSession().toString());
            examSessionData = FXCollections.observableArrayList(examSessionManager.findAllExamSession(new GenericType<List<ExamSession>>() {
            }));
            tblExamSession.setItems(examSessionData);

            tblExamSession.refresh();
        }
    }

    private void handleAcceptEvent(MouseEvent event) {
        try {

            ExamSession examSession = tblExamSession.getSelectionModel().getSelectedItem();
            Subject subject = null;

            for (int i = 0; i < studentData.size(); i++) {
                if (examSession.getStudent().getFullName().equals(studentData.get(i).getFullName())) {
                    examSession.setStudent(studentData.get(i));
                }
            }
            for (int i = 0; i < subjectData.size(); i++) {
                if (examSession.getExam().getSubject().getName().equals(subjectData.get(i).getName())) {
                    subject = subjectData.get(i);
                }
            }
            for (int i = 0; i < examData.size(); i++) {
                if (tblExamSession.getSelectionModel().getSelectedItem().getExam().getExamStatement().equals(examData.get(i).getExamStatement())) {
                    examSession.setExam(examData.get(i));
                    examSession.getExam().setSubject(subject);
                }
            }
            if (examSessionData.stream().anyMatch(e -> e.getExam().equals(examSession.getExam()) && e.getStudent().equals(examSession.getStudent()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "This student is already evaluvated, you can edit or delete the record", ButtonType.OK);
                alert.show();
            } else {
                examSessionManager.create(examSession);
                examSessionData = FXCollections.observableArrayList(examSessionManager.findAllExamSession(new GenericType<List<ExamSession>>() {
                }));
                tblExamSession.setItems(examSessionData);
                tblExamSession.refresh();
                btnCreate.setDisable(false);
            }

        } catch (Exception e) {
        }

    }

    private void handleCreateCancelEvent(MouseEvent event) {

        try {

            ExamSession selectedItem = tblExamSession.getSelectionModel().getSelectedItem();
            tblExamSession.getItems().remove(selectedItem);
            tblExamSession.getSelectionModel().clearSelection();
            tblExamSession.requestFocus();

            btnCreate.setDisable(false);

        } catch (Exception e) {
        }

    }

    private void handleSearchEvent(MouseEvent event) {
        ObservableList<ExamSession> examSessionFilteredData = null;

        for (int j = 0; j < tblExamSession.getColumns().size(); j++) {
            tblExamSession.getColumns().get(j).setVisible(true);
        }

        if (txtFilters.getText().trim().equals("")) {
            for (int j = 0; j < tblExamSession.getColumns().size(); j++) {
                if (!chBoxFilters.getValue().toString().equals(tblExamSession.getColumns().get(j).getText())) {
                    tblExamSession.getColumns().get(j).setVisible(false);
                }
            }
        } else {
            switch (chBoxFilters.getSelectionModel().getSelectedItem().toString()) {
                case "Subject":
                    examSessionFilteredData = FXCollections.observableArrayList(examSessionData.stream().
                            filter(e -> e.getExam().getSubject().getName().equalsIgnoreCase(txtFilters.getText().trim())).map(e -> e)
                            .collect(Collectors.toList()));
                    tblExamSession.setItems(examSessionFilteredData);
                    break;
                case "Exam":
                    examSessionFilteredData = FXCollections.observableArrayList(examSessionData.stream().
                            filter(e -> e.getExam().getExamStatement().equalsIgnoreCase(txtFilters.getText().trim())).map(e -> e)
                            .collect(Collectors.toList()));
                    tblExamSession.setItems(examSessionFilteredData);
                    break;
                case "Student":
                    examSessionFilteredData = FXCollections.observableArrayList(examSessionData.stream().
                            filter(e -> e.getStudent().getFullName().equalsIgnoreCase(txtFilters.getText().trim())).map(e -> e)
                            .collect(Collectors.toList()));
                    tblExamSession.setItems(examSessionFilteredData);
                    break;
                case "Mark":
                    if (!txtFilters.getText().matches("\\d*") || Integer.parseInt(txtFilters.getText().trim()) > 10) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid value for mark, it should be between 0 to 10.", ButtonType.OK);
                        alert.show();
                        txtFilters.setText("");
                    } else {

                        examSessionFilteredData = FXCollections.observableArrayList(examSessionData.stream().
                                filter(e -> e.getMark() == Integer.valueOf(txtFilters.getText().trim())).map(e -> e)
                                .collect(Collectors.toList()));
                        tblExamSession.setItems(examSessionFilteredData);
                        tblExamSession.refresh();
                    }
            }
        }
        if (chBoxFilters.getSelectionModel().getSelectedIndex() == 4) {
            for (int j = 0; j < tblExamSession.getColumns().size(); j++) {
                tblExamSession.getColumns().get(j).setVisible(true);
            }
            examSessionData = FXCollections.observableArrayList(examSessionManager.findAllExamSession(new GenericType<List<ExamSession>>() {
            }));
            tblExamSession.setItems(examSessionData);
            tblExamSession.refresh();
        }

    }

    private void handlePrintEvent(ActionEvent action) {

        try {

            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/view.ExamSessionReport.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource 
            //implementation 
            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<ExamSession>) this.tblExamSession.getItems());
            //Map of parameter to be passed to the report
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {

            /*
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error occured while printing report", ButtonType.OK);
            alert.show();*/
        }
    }
}
