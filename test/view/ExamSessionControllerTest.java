/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import application.ApplicationMaz;
import classes.ExamSession;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeoutException;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
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
import org.testfx.robot.Motion;

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
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm");

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ApplicationMaz.class);

    }

    @Test
    public void testA_initialInteraction() {

        clickOn("#txtUserName");
        write("zyaqoob");
        clickOn("#txtPasswd");
        write("zF0yB1");
        clickOn("#btnSignIn");
        verifyThat("#examSessionPane", isVisible());
    }

    @Test
    @Ignore
    public void testB_windowControlls() {

        verifyThat("#btnCreate", isEnabled());
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#ivTick", isInvisible());
        verifyThat("#ivCross", isInvisible());
        verifyThat("#ivSearch", isVisible());

    }

    @Test
    @Ignore
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
        txtFilters.setText("Zeeshan Yaqoob");
        clickOn("#ivSearch");
        assertTrue("Records matched to the given value",
                tblExamSession.getItems().stream().filter(e -> e.getStudent().getFullName()
                .equalsIgnoreCase(txtFilters.getText())).count() > 0);

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

    @Test
    public void testD_CheckDelete() {
        tblExamSession = lookup("#tblExamSession").query();
        loadAllDataInTable();
        int totalRows = tblExamSession.getItems().size();
        Node row = lookup(".table-row-cell").nth(totalRows-1).query();
        clickOn(row);
      //  clickOn("#btnCreate");
      //  assertEquals("The row has not been added", totalRows + 1, tblExamSession.getItems().size());
        verifyThat("#btnCreate", isEnabled());
        verifyThat("#ivTick", isInvisible());
        verifyThat("#ivCross", isInvisible());
        verifyThat("#btnDelete", isEnabled());
        clickOn("#btnDelete");
        verifyThat("Are you sure you want to delete the record.?", isVisible());
        clickOn("No");
        assertEquals("Row have been deleted", totalRows,tblExamSession.getItems().size());
        clickOn("#btnDelete");
        clickOn("Yes");
        assertEquals("Row have not been deleted correctly", totalRows-1,tblExamSession.getItems().size());
       

    }
}
