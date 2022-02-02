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
import java.util.Date;
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
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.InternalServerErrorException;
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

    //Choice box for the filters
    @FXML
    private ChoiceBox chBoxFilters;

    //Textfield for adding text to filters.
    @FXML
    private TextField txtFilters;

    //Imageview that woulb be use to apply filter.
    @FXML
    private ImageView ivSearch;

    //Imageview that would be use to add record.
    @FXML
    private ImageView ivTick;

    //Imageview incase of user want to cancel the process of adding the record
    @FXML
    private ImageView ivCross;

    //Course label for left menu
    @FXML
    private Label lblCourse;

    //Exam label for left menu
    @FXML
    private Label lblExam;

    //Label for exam session for left menu.
    @FXML
    private Label lblExamSession;

    //TableView that would be used to display exam session data.
    @FXML
    private TableView<ExamSession> tblExamSession;

    //Subject column
    @FXML
    private TableColumn<ExamSession, String> tcSubject;

    //Exam Column
    @FXML
    private TableColumn<ExamSession, String> tcExam;

    //Student Column
    @FXML
    private TableColumn<ExamSession, String> tcStudent;

    //Date time start column
    @FXML
    private TableColumn<ExamSession, String> tcDateStart;

    //Date time end column
    @FXML
    private TableColumn<ExamSession, String> tcDateEnd;

    //Mark column
    @FXML
    private TableColumn<ExamSession, String> tcMark;

    //Round button on the top right side
    @FXML
    private Button btnRound;

    //Button that would be use to create a new record.
    @FXML
    private Button btnCreate;

    //Button that would be use to delete the record.
    @FXML
    private Button btnDelete;

    //Button that would be use to print report.
    @FXML
    Button btnPrint;

    //An object of date format for fomatting and parsing dates.
    private DateFormat dateFormat;

    //An obejct of restful factory to get client type.
    private RESTfulFactory factory;

    //An object of exam session manager.(interface)
    private ExamSessionManager examSessionManager;

    //Observable list to which exam session data would be fetched
    private ObservableList<ExamSession> examSessionData;

    //Observable list to which students data would be fetched
    private ObservableList<Student> studentData;

    //Observable list to which exams data would be fetched
    private ObservableList<Exam> examData;

    //Observable list to which subject data would be fetched
    private ObservableList<Subject> subjectData;

    //An object of user that will be recieved after signing correctly.
    private User user;
    
    public static Stage stageExam=new Stage();

    /**
     * Method that return a user.
     *
     * @return User.
     */
    public User getUser() {
        return user;
    }

    /**
     * Method that set the received user..
     *
     * @param user. User that is received after signing in.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Method that initiates the stage.
     *
     * @param root base class. Scene is created and difines initial state of
     * stage.
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stageExam.setScene(scene);
        stageExam.setTitle("Exam Sessions");
        stageExam.setResizable(false);
        stageExam.requestFocus();
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
        btnRound.setText(user.getFullName().substring(0, 1));
        dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm aa");

        factory = new RESTfulFactory();
        examSessionManager = (ExamSessionManager) factory.getRESTClient(RESTfulClientType.EXAM_SESSION);

        StudentManager studentManager = (StudentRESTClient) factory.getRESTClient(RESTfulClientType.STUDENT);

        ExamManager examManager = (ExamManager) factory.getRESTClient(RESTfulClientType.EXAM);

        SubjectManager subjectManager = (SubjectManager) factory.getRESTClient(RESTfulClientType.SUBJECT);

        try {
            //Fetching student data.
            studentData = FXCollections.observableArrayList(studentManager.findAllStudents(new GenericType<List<Student>>() {
            }));
            //Fetching subject data.
            subjectData = FXCollections.observableArrayList(subjectManager.findAllSubject(new GenericType<List<Subject>>() {
            }));
            //Fetching exam data
            examData = FXCollections.observableArrayList(examManager.findAllExam(new GenericType<List<Exam>>() {
            }));
            //Fetching exam session data.
            examSessionData = FXCollections.observableArrayList(examSessionManager.findAllExamSession(new GenericType<List<ExamSession>>() {
            }));
            
        } catch (InternalServerErrorException e) {
            //In case of error occurs in server, exception is thrown.
            Alert alert = new Alert(Alert.AlertType.ERROR, "An unexpected error occured", ButtonType.OK);
            alert.show();

        }
        //Setting cell data value.
        tcSubject.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExam().getSubject().getName()));
        tcStudent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getFullName()));
        tcExam.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExam().getExamStatement()));
        tcDateStart.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(cellData.getValue().getDateTimeStart().getTime())));
        tcDateEnd.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(cellData.getValue().getDateTimeEnd().getTime())));
        tcMark.setCellValueFactory(cellData -> new SimpleObjectProperty(String.valueOf(cellData.getValue().getMark())));
        
        
        //Adding filters to choice box of filters.
        for (int i = 0; i < tblExamSession.getColumns().size(); i++) {
            if (!tblExamSession.getColumns().get(i).getText().equals(tcDateEnd.getText())
                    && !tblExamSession.getColumns().get(i).getText().equals(tcDateStart.getText())) {
                chBoxFilters.getItems().add(tblExamSession.getColumns().get(i).getText());
            }
        }
        chBoxFilters.getItems().add("Show all");
        chBoxFilters.getSelectionModel().selectFirst();
        
        
        //Adding subject to subject column cell choice box.
        //Table column subject setting cell value factory to choice box, and setting it on editcommit.
        ObservableList<String> subjectNames;
        List<String> sNames = new ArrayList<>();
        for (int i = 0; i < subjectData.size(); i++) {
            sNames.add(i, subjectData.get(i).getName());
        }
        subjectNames = FXCollections.observableArrayList(sNames);
        tcSubject.setCellFactory(ChoiceBoxTableCell.forTableColumn(subjectNames));
        tcSubject.setOnEditCommit(this::handleTcSubjectEdit);
        tcSubject.setEditable(false);
        
        //Adding exams to exam column cell choice box.
        //Table column exam setting cell value factory to choice box, and setting it on editcommit.
        ObservableList<String> examNames;
        List<String> eNames = new ArrayList<>();
        for (int i = 0; i < examData.size(); i++) {
            eNames.add(i, examData.get(i).getExamStatement());
        }
        examNames = FXCollections.observableArrayList(eNames);
        tcExam.setCellFactory(ChoiceBoxTableCell.forTableColumn(examNames));
        tcExam.setOnEditCommit(this::handleTcExamEdit);
        
        //Adding Studnets to student column cell choice box.
        //Table column student setting cell value factory to choice box., and setting it on editcommit.
        ObservableList<String> studentNames;
        List<String> names = new ArrayList<>();
        for (int i = 0; i < studentData.size(); i++) {
            names.add(i, studentData.get(i).getFullName());
        }
        studentNames = FXCollections.observableArrayList(names);
        tcStudent.setCellFactory(ChoiceBoxTableCell.forTableColumn(studentNames));
        tcStudent.setOnEditCommit(this::handleTcStudentEdit);
        
        
        //Table column dateTimeStart setting cell value factory to text field, and setting it on editcommit.
        tcDateStart.setCellFactory(TextFieldTableCell.<ExamSession>forTableColumn());
        tcDateStart.setOnEditCommit(this::handleTcDateStartEdit);

        //Table column dateTimeEnd setting cell value factory to text field, and setting it on editcommit.
        tcDateEnd.setCellFactory(TextFieldTableCell.<ExamSession>forTableColumn());
        tcDateEnd.setOnEditCommit(this::handleTcDateEndEdit);

        //Table Column mark.setting cell value factory to text field, and setting it on editcommit.
        tcMark.setCellFactory(TextFieldTableCell.<ExamSession>forTableColumn());
        tcMark.setOnEditCommit(this::handleTcMarkEdit);
        
        //Image view green tick set on mouse click.
        ivTick.setOnMouseClicked(this::handleAcceptEvent);
        
        //Image view red cross set on mouse click.
        ivCross.setOnMouseClicked(this::handleCreateCancelEvent);
        
        //Loading exam session data in table.
        tblExamSession.setItems(examSessionData);
        //Stage is shown.
        stageExam.show();

    }

    /**
     * Method that handles to subject edit.
     * @param t event that rises after clicking on the subject cell.
     */
    private void handleTcSubjectEdit(CellEditEvent<ExamSession, String> t) {
        
        tcExam.setEditable(true);
        try {
            //Changed subject searched form subject data to update the record.
            ((ExamSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).getExam().getSubject().setName(t.getNewValue());
            for (int i = 0; i < subjectData.size(); i++) {
                if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getExam().getSubject().getName().equals(subjectData.get(i).getName())) {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).getExam().setSubject(subjectData.get(i));
                }
            }
            if (!ivTick.isVisible()) {
                //If image view tick is invisble, that we would be existing recod and record will get updated.
                try {
                    ExamSession examSession = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    examSessionManager.edit(examSession, String.valueOf(examSession.getIdExamSession()));
                } catch (InternalServerErrorException e) {
                    //In case of error occurs in server while updaing the record
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error while submiting edited record", ButtonType.OK);
                    alert.show();
                }

            }
            //After submitting and hitting enter edit mode will be transefered to exam cell
            tblExamSession.getSelectionModel().select(t.getTablePosition().getRow(), tcExam);
            
            tblExamSession.edit(t.getTablePosition().getRow(), tcExam);
        } catch (Exception e) {
            //In case of error occurs an exception is thrwon
            Alert alert = new Alert(Alert.AlertType.ERROR, "An unexpected error occured", ButtonType.OK);
            alert.show();
        }

    }
    
    /**
     * Method that handle edit of exam cell
     * @param t event that rises after clicking on exam cell.
     */
    private void handleTcExamEdit(CellEditEvent<ExamSession, String> t) {
        boolean found = false;
        String examStatement = t.getOldValue();
        Exam exam;
        try {
            ((ExamSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).getExam().setExamStatement(t.getNewValue());
             //Changed Exam searched form exam data and confirmation that change exam does belong to the selected subject to update the record.
            for (int i = 0; i < examData.size(); i++) {
                if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getExam().getExamStatement().equals(examData.get(i).getExamStatement())
                        && t.getTableView().getItems().get(t.getTablePosition().getRow()).getExam().getSubject().getName().equals(examData.get(i).getSubject().getName())) {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setExam(examData.get(i));
                    found = true;
                }
            }
            if (!found) {
               //in case of changed exam does not belong to selected subject alert is shown
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Exam for selected Subject.", ButtonType.OK);
                alert.show();
                t.consume();
                ((ExamSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).getExam().setExamStatement(examStatement);
                tblExamSession.refresh();
                found = false;

            } else if (found == true && !ivTick.isVisible()) {
                try {
                    //If everything is correct record will get updated.
                    ExamSession examSession = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    examSessionManager.edit(examSession, String.valueOf(examSession.getIdExamSession()));
                } catch (InternalServerErrorException e) {
                    //In case of error occurs in server while updaing the record
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error while submiting edited record", ButtonType.OK);
                    alert.show();
                }

            }
            //After commiting edit and hitting enter student cell will go to edit mode.
            tblExamSession.getSelectionModel().select(t.getTablePosition().getRow(), tcStudent);
            tblExamSession.edit(t.getTablePosition().getRow(), tcStudent);
        } catch (NullPointerException ex) {
            //In case of subject is null
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a Subject.", ButtonType.OK);
            alert.show();
            tblExamSession.refresh();
        } catch (Exception e) {
            //In case of an unexpected error occured.
            Alert alert = new Alert(Alert.AlertType.ERROR, "An unexpected error occured", ButtonType.OK);
            alert.show();
        }
    }
    
    /**
     * Method that handles student cell edit
     * @param t event that rises after clicking on student cell to edit.
     */
    private void handleTcStudentEdit(CellEditEvent<ExamSession, String> t) {
        try {
            ((ExamSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).getStudent().setFullName(t.getNewValue());
             //Changed student searched form student data to update the record.
            for (int i = 0; i < studentData.size(); i++) {
                if (t.getTableView().getItems().get(t.getTablePosition().getRow()).getStudent().getFullName().equals(studentData.get(i).getFullName())) {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).setStudent(studentData.get(i));
                }
            }
            if (!ivTick.isVisible()) {
                //If image view tick is invisble, that we would be existing recod and record will get updated.
                try {
                    ExamSession examSession = t.getTableView().getItems().get(t.getTablePosition().getRow());
                    examSessionManager.edit(examSession, String.valueOf(examSession.getIdExamSession()));
                } catch (InternalServerErrorException e) {
                    ///In case of error occurs in server while updaing the record
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error while submiting edited record", ButtonType.OK);
                    alert.show();
                }

            }
            
             //After submitting and hitting enter edit mode will be transefered to edate start cell
            tblExamSession.getSelectionModel().select(t.getTablePosition().getRow(), tcDateStart);
            tblExamSession.edit(t.getTablePosition().getRow(), tcDateStart);
        } catch (Exception e) {
            //in case of an error occurs.
            Alert alert = new Alert(Alert.AlertType.ERROR, "An unexpected error occured", ButtonType.OK);
            alert.show();
        }

    }

    

    private void handleTcDateStartEdit(CellEditEvent<ExamSession, String> t) {
        try {
            //An object of calendar. so that newly entered value parsed and formatted.
            Calendar cal = new GregorianCalendar();
            cal.setTime(dateFormat.parse(t.getNewValue()));
            Date date = dateFormat.parse(t.getNewValue());
            cal.setTime(dateFormat.parse(dateFormat.format(date)));
            //Date end to compare with newly entered date
            Calendar calendar = t.getTableView().getItems().get(t.getTablePosition().getRow()).getDateTimeEnd();
           
            if (calendar.getTime().before(date)) {
                //If new date is after date end, error is shown
                Alert alert = new Alert(Alert.AlertType.ERROR, "Date Start should be before the Date End", ButtonType.OK);
                alert.show();
                tblExamSession.refresh();

            } else {
                
                ((ExamSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDateTimeStart(cal);
                if (!ivTick.isVisible()) {
                    
                    //If image view tick is invisble, that we would be existing recod and record will get updated.
                    try {
                        ExamSession examSession = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        examSessionManager.edit(examSession, String.valueOf(examSession.getIdExamSession()));
                    } catch (InternalServerErrorException e) {
                        //In case of error occurs in server while updaing the record
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Error while submiting edited record", ButtonType.OK);
                        alert.show();
                    }

                }
                //After submitting and hitting enter edit mode will be transefered to date end cell
                tblExamSession.getSelectionModel().select(t.getTablePosition().getRow(), tcDateEnd);
                tblExamSession.edit(t.getTablePosition().getRow(), tcDateEnd);
            }

        } catch (ParseException ex) {
            //In case of error occurs while parsing the date, exception is thrown.
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid date format, it should be 'dd-MM-yyyy : hh:mm AM/PM'.", ButtonType.OK);
            alert.show();
            tblExamSession.refresh();

        }
    }

    private void handleTcDateEndEdit(CellEditEvent<ExamSession, String> t) {
        try {
            //An object of calendar. so that newly entered value parsed and formatted.
            Calendar cal = new GregorianCalendar();
            Date date = dateFormat.parse(t.getNewValue());
            cal.setTime(dateFormat.parse(dateFormat.format(date)));
            //Date start to compare with newly entered date
            Calendar calendar = t.getTableView().getItems().get(t.getTablePosition().getRow()).getDateTimeStart();

            if (calendar.getTime().after(date)) {
                 //If new date is before date start, error is shown
                Alert alert = new Alert(Alert.AlertType.ERROR, "Date End should be after the Date Start", ButtonType.OK);
                alert.show();
                tblExamSession.refresh();

            } else {
                ((ExamSession) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDateTimeEnd(cal);
                if (!ivTick.isVisible()) {
                     //If image view tick is invisble, that we would be existing recod and record will get updated.
                    try {
                        ExamSession examSession = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        examSessionManager.edit(examSession, String.valueOf(examSession.getIdExamSession()));
                    } catch (InternalServerErrorException e) {
                        //In case of error occurs in server while updaing the record
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Error while submiting edited record", ButtonType.OK);
                        alert.show();
                    }

                }
            }
            //After submitting and hitting enter edit mode will be transefered to mark cell
            tblExamSession.getSelectionModel().select(t.getTablePosition().getRow(), tcMark);
            tblExamSession.edit(t.getTablePosition().getRow(), tcMark);
        } catch (ParseException ex) {
            //In case of error occurs while parsing the date, exception is thrown.
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid date format, it should be 'dd-MM-yyyy : hh:mm AM/PM'.", ButtonType.OK);
            alert.show();
            tblExamSession.refresh();
        }
    }

    private void handleTcMarkEdit(CellEditEvent<ExamSession, String> t) {
        try {
            //Confirmation that value entered for mark only contains digits and its value is between 0 to 10, both included.
            if (!t.getNewValue().matches("\\d*") || Integer.parseInt(t.getNewValue().trim()) > 10) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid value for mark, it should be between 0 to 10.", ButtonType.OK);
                alert.show();
                ((ExamSession) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setMark(Integer.parseInt(t.getOldValue().trim()));
                tblExamSession.refresh();
            } else {
                ((ExamSession) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setMark(Integer.parseInt(t.getNewValue().trim()));
                if (!ivTick.isVisible()) {
                    //If image view tick is invisble, that we would be existing recod and record will get updated.
                    try {
                        ExamSession examSession = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        examSessionManager.edit(examSession, String.valueOf(examSession.getIdExamSession()));
                    } catch (InternalServerErrorException e) {
                        //In case of error occurs in server while updaing the record
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Error while submiting edited record", ButtonType.OK);
                        alert.show();
                    }
                }
                tblExamSession.getSelectionModel().select(t.getTablePosition().getRow(), tcMark);
                tblExamSession.edit(t.getTablePosition().getRow(), tcMark);
            }
        } catch (Exception e) {
            //In case of error occurs, an exception is thrown
            Alert alert = new Alert(Alert.AlertType.ERROR, "An unexpected error occured while editing record", ButtonType.OK);
            alert.show();
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
        try {
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
            tblExamSession.layout();
            tblExamSession.edit(examSessionData.size() - 1, tcSubject);
            ivTick.setVisible(true);
            ivCross.setVisible(true);
            btnCreate.setDisable(true);
            btnDelete.setDisable(true);
            tcSubject.setEditable(true);
            tcExam.setEditable(false);
        } catch (Exception e) {
        }

    }

    private void handleDeleteEvent(ActionEvent action) {
        try {
            tblExamSession.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the record.?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> button = alert.showAndWait();
            if (button.get() == ButtonType.YES) {

                examSessionManager.remove(tblExamSession.getSelectionModel().getSelectedItem().getIdExamSession().toString());
                //examSessionManager.remove(new GenericType<ExamSession>(){}, tblExamSession.getSelectionModel().getSelectedItem().getIdExamSession().toString());
                examSessionData = FXCollections.observableArrayList(examSessionManager.findAllExamSession(new GenericType<List<ExamSession>>() {
                }));
                tblExamSession.setItems(examSessionData);

                tblExamSession.refresh();
            }
        } catch (InternalServerErrorException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error while deleting ExamSession record", ButtonType.OK);
            alert.show();
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

            examSessionManager.create(examSession);
            examSessionData = FXCollections.observableArrayList(examSessionManager.findAllExamSession(new GenericType<List<ExamSession>>() {
            }));
            tblExamSession.setItems(examSessionData);
            tblExamSession.refresh();
            btnCreate.setDisable(false);

        } catch (InternalServerErrorException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error while creating ExamSession record", ButtonType.OK);
            alert.show();
        }

    }

    private void handleCreateCancelEvent(MouseEvent event) {

        try {

            ExamSession selectedItem = tblExamSession.getSelectionModel().getSelectedItem();
            tblExamSession.getItems().remove(selectedItem);
            tblExamSession.getSelectionModel().clearSelection();
            tblExamSession.requestFocus();

            btnCreate.setDisable(false);
            ivTick.setVisible(false);
            ivCross.setVisible(false);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "An error occured while canceling the creation of record", ButtonType.OK);
            alert.show();
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
                    if (!txtFilters.getText().matches("\\d*") || Integer.parseInt(txtFilters.getText().trim()) > 10 || Integer.parseInt(txtFilters.getText().trim()) < 0) {
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
            try {
                examSessionData = FXCollections.observableArrayList(examSessionManager.findAllExamSession(new GenericType<List<ExamSession>>() {
                }));
            } catch (InternalServerErrorException e) {
            }

            tblExamSession.setItems(examSessionData);
            tblExamSession.refresh();
        }

    }

    private void handlePrintEvent(ActionEvent action) {
        try {

            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/report/ExamSessionReport.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource 
            //implementation 
            ObservableList<ExamSessionReportAuxClass> examSessionReportData = FXCollections.observableArrayList();
            ExamSessionReportAuxClass eSessionReport;
            for (int i = 0; i < tblExamSession.getItems().size(); i++) {
                eSessionReport = new ExamSessionReportAuxClass();
                eSessionReport.setIdExamSession(tblExamSession.getItems().get(i).getIdExamSession());
                eSessionReport.setDateTimeStart(dateFormat.format(tblExamSession.getItems().get(i).getDateTimeStart().getTime()));
                eSessionReport.setDateTimeEnd(dateFormat.format(tblExamSession.getItems().get(i).getDateTimeEnd().getTime()));
                eSessionReport.setMark(tblExamSession.getItems().get(i).getMark());
                eSessionReport.setExam(tblExamSession.getItems().get(i).getExam());
                eSessionReport.getExam().setSubject(tblExamSession.getItems().get(i).getExam().getSubject());
                eSessionReport.setStudent(tblExamSession.getItems().get(i).getStudent());
                examSessionReportData.add(eSessionReport);
            }

            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<ExamSessionReportAuxClass>) examSessionReportData);
            //Map of parameter to be passed to the report
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with data
            dataItems.getData();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.

            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "An error occured while creating report", ButtonType.OK);
            alert.show();
        }
    }

}
