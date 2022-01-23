/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Exam;
import classes.ExamSession;
import classes.Student;
import classes.Subject;
import interfaces.ExamSessionManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import restful.ExamRESTClient;
import restful.StudentRESTClient;
import restful.SubjectRESTClient;

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

   // private RESTfulFactory factory;

   // private ExamSessionManager manager;

    private ObservableList<ExamSession> examSessionData;

    private ObservableList<Student> studentData;

    private ObservableList<Exam> examData;

    private ObservableList<Subject> subjectData;

    public void initStage(Parent root) {
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Exam Sessions");
        stage.setResizable(false);

        tblExamSession.setEditable(true);
        tblExamSession.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChanged);
        ivSearch.setVisible(false);
        ivSearch.setVisible(false);
        btnDelete.setDisable(true);
        btnCreate.setDisable(false);
        ivTick.setDisable(true);
        
        ivCross.setDisable(true);
        btnCreate.setOnAction(this::handleCreationEvent);
        btnDelete.setDisable(true);
        btnDelete.setOnAction(this::handleDeleteEvent);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm");

      //  factory = new RESTfulFactory();

        // manager = (ExamSessionManager) factory.getRESTClient(RESTfulClientType.EXAM_SESSION);

        StudentRESTClient studentClient = (StudentRESTClient) factory.getRESTClient(RESTfulClientType.STUDENT);

        ExamRESTClient examClient = (ExamRESTClient) factory.getRESTClient(RESTfulClientType.EXAM);

        SubjectRESTClient subjectClient = (SubjectRESTClient) factory.getRESTClient(RESTfulClientType.SUBJECT);

        studentData = FXCollections.observableArrayList(studentClient.findAllStudents(new GenericType<List<Student>>() {
        }));

        subjectData = FXCollections.observableArrayList(subjectClient.findAllSubject(new GenericType<List<Subject>>() {
        }));

        examData = FXCollections.observableArrayList(examClient.findAllExam(new GenericType<List<Exam>>() {
        }));
        examSessionData = FXCollections.observableArrayList(manager.findAllExamSession(new GenericType<List<ExamSession>>() {
        }));

        tcSubject.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExam().getSubject().getName()));
        tcStudent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getFullName()));
        tcExam.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExam().getExamStatement()));
        //tcDateStart.setCellValueFactory(new PropertyValueFactory<>(dateFormat.format(("dateTimeStart"))));
        tcDateStart.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(cellData.getValue().getDateTimeStart().getTime())));
        tcDateEnd.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(cellData.getValue().getDateTimeEnd().getTime())));
        tcMark.setCellValueFactory(cellData -> new SimpleObjectProperty(String.valueOf(cellData.getValue().getMark())));

        //Table column subject
        ObservableList<String> subjectNames;
        List<String> sNames = new ArrayList<>();
        for (int i = 0; i < subjectData.size(); i++) {
            sNames.add(i, subjectData.get(i).getName());
        }
        subjectNames = FXCollections.observableArrayList(sNames);
        tcSubject.setCellFactory(ChoiceBoxTableCell.forTableColumn(subjectNames));

        //Table column exam
        ObservableList<String> examNames;
        List<String> eNames = new ArrayList<>();
        for (int i = 0; i < examData.size(); i++) {
            eNames.add(i, examData.get(i).getExamStatement());
        }
        examNames = FXCollections.observableArrayList(eNames);
        tcExam.setCellFactory(ChoiceBoxTableCell.forTableColumn(examNames));

        //Table column student
        ObservableList<String> studentNames;
        List<String> names = new ArrayList<>();
        for (int i = 0; i < studentData.size(); i++) {
            names.add(i, studentData.get(i).getFullName());
        }
        studentNames = FXCollections.observableArrayList(names);
        tcStudent.setCellFactory(ChoiceBoxTableCell.forTableColumn(studentNames));

        //Table column dateTimeStart
        tcDateStart.setCellFactory(TextFieldTableCell.<ExamSession>forTableColumn());
        tcDateStart.setOnEditCommit(
                (CellEditEvent<ExamSession, String> t) -> {

                    try {
                        //Calendar cal = Calendar.getInstance();
                        Calendar cal = new GregorianCalendar();
                        cal.setTime(dateFormat.parse(t.getNewValue()));

                        ((ExamSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDateTimeStart(cal);
                    } catch (ParseException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid value for mark, it should be between 0 to 10.", ButtonType.OK);
                        alert.show();
                        tblExamSession.refresh();

                    }

                });

        //Table column dateTimeEnd
        tcDateEnd.setCellFactory(TextFieldTableCell.<ExamSession>forTableColumn());
        tcDateEnd.setOnEditCommit(
                (CellEditEvent<ExamSession, String> t) -> {

                    try {
                        //Calendar cal = Calendar.getInstance();
                        Calendar cal = new GregorianCalendar();
                        cal.setTime(dateFormat.parse(t.getNewValue()));

                        ((ExamSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDateTimeEnd(cal);
                    } catch (ParseException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid value for mark, it should be between 0 to 10.", ButtonType.OK);
                        alert.show();
                        tblExamSession.refresh();
                    }

                });

        //Table Column mark.
        tcMark.setCellFactory(TextFieldTableCell.<ExamSession>forTableColumn());
        tcMark.setOnEditCommit(
                (CellEditEvent<ExamSession, String> t) -> {
                    if (!t.getNewValue().matches("\\d*") || Integer.parseInt(t.getNewValue().trim()) > 10) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid value for mark, it should be between 0 to 10.", ButtonType.OK);
                        alert.show();
                        ((ExamSession) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setMark(Integer.parseInt(t.getOldValue().trim()));
                        tblExamSession.refresh();
                    } else {
                        ((ExamSession) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setMark(Integer.parseInt(t.getNewValue().trim()));
                        tblExamSession.getSelectionModel().select(t.getTablePosition().getRow(), tcMark);
                        tblExamSession.edit(t.getTablePosition().getRow(), tcMark);
                    }
                });
        tcMark.setOnEditCancel((CellEditEvent<ExamSession, String> t) -> {
            tblExamSession.refresh();
        });
        
        ivTick.setOnMouseClicked(this::handleAcceptEvent);
        tblExamSession.setItems(examSessionData);
        stage.show();

    }

    private void handleTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue == null) {
            btnDelete.setDisable(true);
        } else {
            btnDelete.setDisable(false);
        }
    }

    private void handleCreationEvent(ActionEvent action) {
        ExamSession eSession = new ExamSession();
        eSession.setStudent(new Student());
        eSession.setExam(new Exam());
        eSession.getExam().setSubject(new Subject());
        eSession.setDateTimeEnd(new GregorianCalendar());
        eSession.setDateTimeStart(new GregorianCalendar());

        examSessionData.add(eSession);
        tblExamSession.getSelectionModel().select(examSessionData.size() - 1);
        tblExamSession.getFocusModel().focus(examSessionData.size() - 1, tcStudent);
        tblExamSession.edit(examSessionData.size() - 1, tcStudent);
        ivTick.setDisable(false);
        btnCreate.setDisable(true);

    }

    private void handleDeleteEvent(ActionEvent action) {
        ExamSessionManager manager = (ExamSessionManager) factory.getRESTClient(RESTfulClientType.EXAM_SESSION);
        tblExamSession.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> button = alert.showAndWait();
        if (button.get() == ButtonType.YES) {
            manager.remove(tblExamSession.getSelectionModel().getSelectedItem().getIdExamSession().toString());
            tblExamSession.refresh();
        }
    }

    private void handleAcceptEvent(MouseEvent event) {
        ExamSession examSession = tblExamSession.getSelectionModel().getSelectedItem();
        if (examSession.getDateTimeEnd() == null || examSession.getDateTimeStart() == null || examSession.getStudent() == null || examSession.getExam() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid value for mark, it should be between 0 to 10.", ButtonType.OK);
            alert.show();
        } else {
            
            tblExamSession.refresh();
        }
    }
}
