/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.ExamSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;

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

    @Override
    public void start(Stage stage) throws Exception {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ExamSessionWindow.fxml"));
            Parent root;
            root = (Parent) loader.load();
            ExamSessionController controller = loader.getController();
            controller.initStage(root);
            tblExamSession = lookup("#tblExamSession").query();
            txtFilters = lookup("#txtFilters").query();
            btnDelete = (Button) lookup("#btnDelete").query();
            btnCreate = (Button) lookup("#btnCreate").query();
            ivTick = (ImageView) lookup("#ivTick").query();
            ivCross = (ImageView) lookup("#ivCroos").query();
            ivSearch = (ImageView) lookup("#ivSearch").query();

        } catch (IOException ex) {
            Logger.getLogger(ExamSessionControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void windowControlls() {
        verifyThat("#btnCreate", isEnabled());
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#ivTick", isInvisible());
        verifyThat("#ivCross", isInvisible());
    }

    @Test
    public void checkFilters() {
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
        txtFilters.setText("11");
        clickOn("#ivSearch");
        verifyThat("Invalid value for mark, it should be between 0 to 10.", NodeMatchers.isVisible());
        clickOn("OK");
        txtFilters.setText("9");
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
}
