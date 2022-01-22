/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.ExamSession;
import classes.Student;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.ws.rs.core.GenericType;
import restful.ExamSessionRESTClient;

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
    private TableColumn<ExamSession, Calendar> tcDateStart;
    @FXML
    private TableColumn<ExamSession, Calendar> tcDateEnd;
    @FXML
    private TableColumn<ExamSession, String> tcMark;
    @FXML
    private Button btnRound;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnDelete;

    private ObservableList<ExamSession> examSessionData;

    public void initStage(Parent root) {
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Exam Sessions");
        stage.setResizable(false);

        // tblExamSession.setEditable(true);
        ivSearch.setVisible(false);
        ivSearch.setVisible(false);
        btnDelete.setDisable(true);
        btnCreate.setDisable(false);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm");

        examSessionData = FXCollections.observableArrayList(new ExamSessionRESTClient().findAllExamSession(new GenericType<List<ExamSession>>() {
        }));

        tcStudent.setCellValueFactory(new PropertyValueFactory("fullName"));
        tcStudent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getFullName()));
        //tcStudent.setCellFactory(column -> new TableCellChoiceBox());

        tcSubject.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExam().getSubject().getName()));
        tcExam.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExam().getExamStatement()));
        //  tcDateStart.setCellValueFactory(cellData -> new SimpleObjectProperty(dateFormat.format(cellData.getValue().getDateTimeStart().getTime())));
        tcDateStart.setCellFactory(new PropertyValueFactory("dateTimeStart"));
        tcDateEnd.setCellValueFactory(cellData -> new SimpleObjectProperty(dateFormat.format(cellData.getValue().getDateTimeEnd().getTime())));
        tcMark.setCellValueFactory(new PropertyValueFactory("mark"));
        tblExamSession.setItems(examSessionData);

        stage.show();

    }
    /*
        tcStudent.setCellValueFactory(new PropertyValueFactory("fullName"));
        tcSubject.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSessions().iterator().next().getExam().getSubject().getName()));
        tcExam.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSessions().iterator().next().getExam().getExamStatement()));
        tcDateStart.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getSessions().iterator().next().getDateTimeStart()));
        tcDateEnd.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getSessions().iterator().next().getDateTimeEnd()));
        tcMark.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSessions().iterator().next().getMark())));
        tblStudent.getItems().setAll(studentData);
    
    
    
    
    tcSubject.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExam().getSubject().getName()));
        tcExam.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getExam().getExamStatement()));
        tcStudent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getFullName()));
        tcDateStart.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getDateTimeStart()));
        tcDateEnd.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getDateTimeEnd()));
        tcMark.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMark())));
     */
}
