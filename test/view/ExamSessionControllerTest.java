/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import application.ApplicationMaz;
import classes.ExamSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExamSessionControllerTest extends ApplicationTest {

    private TableView<ExamSession> tblExamSession;
    private TextField txtFilters;
    private Button btnCreate;
    private Button btnDelete;
    private ImageView ivTick;
    private ImageView ivCross;
    private ImageView ivSearch;
    private TextField txtUserName;
    private TextField txtPasswd;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm");
    private final String OVER_SIZE_TEXT = "XXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXX";

    @BeforeClass

    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ApplicationMaz.class);

    }
    @Test
    @Ignore
    public void testAA_serverOff(){
        clickOn("#txtUserName");
        write("zyaqoob");
        clickOn("#txtPasswd");
        write("zF0yB1");
        clickOn("#btnSignIn");
        verifyThat("Unable to connect to server right now, try again later after a while", isVisible());
        clickOn("OK");
    }
    @Test
    public void testA_initialInteraction() {
        txtUserName=lookup("#txtUserName").query();
        txtPasswd=lookup("#txtPasswd").query();
        clickOn(txtUserName);
        txtUserName.setText(OVER_SIZE_TEXT);
        verifyThat("#lblUserMax", isVisible());
        doubleClickOn("#txtUserName");
        type(KeyCode.BACK_SPACE);
        verifyThat("#lblUserMax", isInvisible());
        clickOn(txtPasswd);
        txtPasswd.setText(OVER_SIZE_TEXT);
         verifyThat("#lblPasswdMax", isVisible());
        doubleClickOn(txtPasswd);
        type(KeyCode.BACK_SPACE);
        verifyThat("#lblPasswdMax", isInvisible());
        clickOn("#txtUserName");
        write("zyaqoob");
        clickOn("#txtPasswd");
        write("zF0yB1");
        clickOn("#btnSignIn");
        verifyThat("#examSessionPane", isVisible());
    }

    @Test
    public void testB_windowControlls() {

        verifyThat("#btnCreate", isEnabled());
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#ivTick", isInvisible());
        verifyThat("#ivCross", isInvisible());
        verifyThat("#ivSearch", isVisible());

    }

    @Test

    public void testC_CheckFilters() {
        tblExamSession = lookup("#tblExamSession").query();
        txtFilters = lookup("#txtFilters").query();
        btnDelete = (Button) lookup("#btnDelete").query();
        btnCreate = (Button) lookup("#btnCreate").query();
        ivTick = (ImageView) lookup("#ivTick").query();
        ivCross = (ImageView) lookup("#ivCroos").query();
        ivSearch = (ImageView) lookup("#ivSearch").query();

        //Subjct Search
        txtFilters.setText("PMD");
        clickOn("#ivSearch");
        assertTrue("No record matched to the given value",
                tblExamSession.getItems().stream().filter(e -> e.getExam()
                .getSubject().getName().equalsIgnoreCase(txtFilters.getText())).count() == 0);
        txtFilters.setText("Acceso a Datos");
        clickOn("#ivSearch");
        assertTrue("Rocords matched to the given value",
                tblExamSession.getItems().stream().filter(e -> e.getExam().getSubject().getName().equals(txtFilters.getText())).count() > 0);

        //Exam Search
        loadAllDataInTable();
        clickOn("#chBoxFilters");
        type(KeyCode.UP);
        type(KeyCode.UP);
        type(KeyCode.UP);
        txtFilters.setText("4th Exam Statement");
        clickOn("#ivSearch");
        assertTrue("No record matched to the given value",
                tblExamSession.getItems().stream().filter(e -> e.getExam().getExamStatement()
                .equalsIgnoreCase(txtFilters.getText())).count() == 0);
        txtFilters.setText("Third Exam Statement");
        clickOn("#ivSearch");
        assertTrue("Records matched to the given value",
                tblExamSession.getItems().stream().filter(e -> e.getExam().getExamStatement()
                .equalsIgnoreCase(txtFilters.getText())).count() > 0);

        //Student Search
        loadAllDataInTable();
        clickOn("#chBoxFilters");
        type(KeyCode.UP);
        type(KeyCode.UP);
        txtFilters.setText("Zeshan Yaqoob");
        clickOn("#ivSearch");
        assertTrue("No record matched to the given value",
                tblExamSession.getItems().stream().filter(e -> e.getStudent().getFullName()
                .equalsIgnoreCase(txtFilters.getText())).count() == 0);
        txtFilters.setText("Miquel Sanchez Herrero");
        clickOn("#ivSearch");
        assertTrue("Records matched to the given value",
                tblExamSession.getItems().size()>0);

        //Mark Search
        loadAllDataInTable();
        clickOn("#chBoxFilters");
        type(KeyCode.UP);
        txtFilters.setText("dhg");
        type(KeyCode.ENTER);
        clickOn("#ivSearch");
        verifyThat("Invalid value for mark, it should be between 0 to 10.", NodeMatchers.isVisible());
        clickOn("OK");
        txtFilters.setText("9");
        clickOn("#ivSearch");
        assertTrue("No record matched to the given value",
                tblExamSession.getItems().stream().filter(e -> e.getMark()
                == Integer.valueOf(txtFilters.getText())).count() == 0);
        txtFilters.setText("5");
        clickOn("#ivSearch");
        assertTrue("No record matched to the given value",
                tblExamSession.getItems().stream().filter(e -> e.getMark()
                == Integer.valueOf(txtFilters.getText())).count() > 0);
    }

    public void loadAllDataInTable() {
        clickOn("#chBoxFilters");
        clickOn("Show all");
        clickOn("#ivSearch");
    }

   /* @Test
    public void testD_CheckDelete() {
        tblExamSession = lookup("#tblExamSession").query();
        loadAllDataInTable();
        int totalRows = tblExamSession.getItems().size();
        Node row = lookup(".table-row-cell").nth(totalRows - 1).query();
        clickOn(row);

        verifyThat("#btnCreate", isEnabled());
        verifyThat("#ivTick", isInvisible());
        verifyThat("#ivCross", isInvisible());
        verifyThat("#btnDelete", isEnabled());
        clickOn("#btnDelete");
        verifyThat("Are you sure you want to delete the record.?", isVisible());
        clickOn("No");
        assertEquals("Row have been deleted", totalRows, tblExamSession.getItems().size());
        clickOn("#btnDelete");
        clickOn("Yes");
        assertEquals("Row have not been deleted correctly", totalRows - 1, tblExamSession.getItems().size());

    }*/

    @Test

    public void testE_checkCreate() {
        tblExamSession = lookup("#tblExamSession").query();
        loadAllDataInTable();
        int totalRows = tblExamSession.getItems().size();
        clickOn("#btnCreate");
        assertEquals("The row has not been added", totalRows + 1, tblExamSession.getItems().size());
        Node row = lookup(".table-row-cell").nth(totalRows).query();
        Node cellSubject = lookup("#tcSubject").nth(totalRows + 1).query();
        doubleClickOn(cellSubject);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        Node cellExam = lookup("#tcExam").nth(totalRows + 1).query();
        doubleClickOn(cellExam);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat("Invalid Exam for selected Subject.", isVisible());
        clickOn("OK");
        cellExam = lookup("#tcExam").nth(totalRows + 1).query();
        doubleClickOn(cellExam);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        verifyThat("Invalid Exam for selected Subject.", isVisible());
        clickOn("OK");
        cellExam = lookup("#tcExam").nth(totalRows + 1).query();
        doubleClickOn(cellExam);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        Node cellStudent = lookup("#tcStudent").nth(totalRows + 1).query();
        doubleClickOn(cellStudent);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        type(KeyCode.BACK_SPACE);
        write("sdjfh");
        type(KeyCode.ENTER);
        verifyThat("Invalid date format, it should be 'dd-MM-yyyy : hh:mm AM/PM'.", isVisible());
        clickOn("OK");
        Node cellDateStart = lookup("#tcDateStart").nth(totalRows + 1).query();
        clickOn(cellDateStart);
        type(KeyCode.BACK_SPACE);
        write("14-01-2022 : 12:10 AM");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        type(KeyCode.BACK_SPACE);
        write("sdjfh43");
        type(KeyCode.ENTER);
        verifyThat("Invalid date format, it should be 'dd-MM-yyyy : hh:mm AM/PM'.", isVisible());
        clickOn("OK");
        Node cellDateEnd = lookup("#tcDateEnd").nth(totalRows + 1).query();
        clickOn(cellDateEnd);
        type(KeyCode.BACK_SPACE);
        write("13-01-2022 : 12:10 PM");
        type(KeyCode.ENTER);
        verifyThat("Date End should be after the Date Start", isVisible());
        clickOn("OK");
        cellDateEnd = lookup("#tcDateEnd").nth(totalRows + 1).query();
        clickOn(cellDateEnd);
        type(KeyCode.BACK_SPACE);
        write("14-01-2022 : 02:10 PM");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        type(KeyCode.BACK_SPACE);
        write("dnjk<");
        type(KeyCode.ENTER);
        verifyThat("Invalid value for mark, it should be between 0 to 10.", isVisible());
        clickOn("OK");
        Node cellMark = lookup("#tcMark").nth(totalRows + 1).query();
        clickOn(cellMark);
        doubleClickOn(cellMark);
        write("5");
        type(KeyCode.ENTER);
        clickOn("#ivTick");
        assertEquals("Record has not been added correctly", totalRows + 1, tblExamSession.getItems().size());

    }

    @Test
    public void testF_checkEdit() {
        tblExamSession = lookup("#tblExamSession").query();
        loadAllDataInTable();
        clickOn("#tcStudent");
        int totalRows = tblExamSession.getItems().size();
        Node row = lookup(".table-row-cell").nth(totalRows).query();
        Node cellDateStart = lookup("#tcDateStart").nth(totalRows).query();
        doubleClickOn(cellDateStart);
        type(KeyCode.BACK_SPACE);
        write("15-01-2022 : 03:10 AM");
        type(KeyCode.ENTER);
        verifyThat("Date Start should be before the Date End", isVisible());
        clickOn("OK");
        cellDateStart = lookup("#tcDateStart").nth(totalRows).query();
        clickOn(cellDateStart);
        type(KeyCode.BACK_SPACE);
        write("13-01-2022 : 12:10 PM");
        type(KeyCode.ENTER);
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm aa");
        /*assertTrue("Error while updating date Start", tblExamSession.getItems().
                stream().filter(e -> e.getStudent().getFullName().equalsIgnoreCase("Zeeshan Yaqoob")
                        && e.getDateTimeStart().equals(dateFormat.format("13-01-2022 : 12:10 PM"))).count() > 0);}*/
        type(KeyCode.ENTER);
        type(KeyCode.BACK_SPACE);
        write("12-01-2022 : 12:10 PM");
        type(KeyCode.ENTER);
        verifyThat("Date End should be after the Date Start", isVisible());
        clickOn("OK");
        Node cellDateEnd = lookup("#tcDateEnd").nth(totalRows).query();
        clickOn(cellDateEnd);
        type(KeyCode.BACK_SPACE);
        write("13-01-2022 : 03:10 PM");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        type(KeyCode.BACK_SPACE);
        write("dnjk<");
        type(KeyCode.ENTER);
        verifyThat("Invalid value for mark, it should be between 0 to 10.", isVisible());
        clickOn("OK");
        Node cellMark = lookup("#tcMark").nth(totalRows).query();
        doubleClickOn(cellMark);
        write("6");
        type(KeyCode.ENTER);
    }

    @Test
    public void testG_checkDelete() {
        tblExamSession = lookup("#tblExamSession").query();
        int totalRows = tblExamSession.getItems().size();
        tblExamSession.getSelectionModel().selectLast();
        clickOn("#btnDelete");
        verifyThat("Are you sure you want to delete the record.?", isVisible());
        clickOn("No");
        assertEquals("Row got deleted incorrectly", totalRows, tblExamSession.getItems().size());
        clickOn("#btnDelete");
        verifyThat("Are you sure you want to delete the record.?", isVisible());
        clickOn("Yes");
        assertEquals("Row did not get deleted, an error occured", totalRows - 1, tblExamSession.getItems().size());
        verifyThat("#btnDelete", isDisabled());
    }

    @Test

    public void testH_checkReport() {
        verifyThat("#btnPrint", isEnabled());
        clickOn("#btnPrint");
    }

}
