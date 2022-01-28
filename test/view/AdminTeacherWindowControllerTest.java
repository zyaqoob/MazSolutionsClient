/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Teacher;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminTeacherWindowControllerTest extends ApplicationTest {

    private TableView<Teacher> tblTeachers;
    private TextField tfFilter;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void start(Stage stage) throws Exception {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WindowTeacherAdmin.fxml"));
            Parent root;
            root = (Parent) loader.load();
            AdminTeacherWindowController controller = loader.getController();
            controller.initStage(root);
            tblTeachers = lookup("#tblTeachers").query();
            tfFilter = lookup("#tfFilter").query();
        } catch (IOException ex) {
            Logger.getLogger(AdminTeacherWindowControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testFiler1() {
        tfFilter.setText("Lorea Mendieta Lastia");
        clickOn("#ivSearch");
        assertEquals("Not filtered by name correctly", 1, tblTeachers.getItems().size());
        assertTrue("The fullname value does not match with the one of the filter", tblTeachers.getItems().stream().
                filter(teacher -> teacher.getFullName().equalsIgnoreCase(tfFilter.getText())).count() > 0);
        resetTableValues();
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        tfFilter.setText("2dam");
        clickOn("#ivSearch");
        assertEquals("Not filtered by course correctly", 1, tblTeachers.getItems().size());
        assertTrue("The course value does not match with the one of the filter", tblTeachers.getItems().stream().
                filter(teacher -> teacher.getTeacherCourse().getName().equalsIgnoreCase(tfFilter.getText())).count() > 0);
        resetTableValues();
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        tfFilter.setText("lmendiet");
        clickOn("#ivSearch");
        assertEquals("Not filtered by username correctly", 1, tblTeachers.getItems().size());
        assertTrue("The username value does not match with the one of the filter", tblTeachers.getItems().stream().
                filter(teacher -> teacher.getLogin().equalsIgnoreCase(tfFilter.getText())).count() > 0);
        resetTableValues();
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        tfFilter.setText("1980-03-07");
        clickOn("#ivSearch");
        assertEquals("Not filtered by salary correctly", 1, tblTeachers.getItems().size());
        assertTrue("The username value does not match with the one of the filter", tblTeachers.getItems().stream().
                filter(teacher -> dateFormatter.format(teacher.getBirthDate()).equalsIgnoreCase(tfFilter.getText())).count() > 0);
        resetTableValues();
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        tfFilter.setText("2500");
        assertTrue("The username value does not match with the one of the filter", tblTeachers.getItems().stream().filter(teacher
                            -> Objects.equals(teacher.getSalary(), Float.valueOf(tfFilter.getText()))).count()>0);
        clickOn("#ivSearch");
        assertEquals("Not filtered by salary correctly", 2, tblTeachers.getItems().size());
    }

    public void resetTableValues() {
        clickOn("#chBox");
        clickOn("All");
        clickOn("#ivSearch");
        assertNotEquals("Table has no data: Cannot test.", tblTeachers.getItems().size(), 0);
        clickOn("#chBox");
    }
}
