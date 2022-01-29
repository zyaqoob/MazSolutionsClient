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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import javafx.stage.Stage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminTeacherWindowControllerTest extends ApplicationTest {

    private TableView<Teacher> tblTeachers;
    private TextField tfFilter;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private ChoiceBox chBox;
    private TableColumn tbcFullName;

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
            chBox = lookup("#chBox").query();
        } catch (IOException ex) {
            // Logger.getLogger(AdminTeacherWindowControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void testA_InitialState() {
        verifyThat("#btnCreate", isEnabled());
        verifyThat("#ivTick", isInvisible());
        verifyThat("#ivX", isInvisible());
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#ivSearch", isVisible());
        verifyThat("#btnPrint", isEnabled());
        verifyThat("#tfFilter", isEnabled());
        verifyThat("#tfFilter", hasText(""));
        assertEquals("Choice box default value is incorrect", "Full Name", (String) chBox.getSelectionModel().getSelectedItem());
        verifyThat("#tblTeachers", isVisible());
        verifyThat("#tbcFullName", isVisible());
        verifyThat("#tbcUsername", isVisible());
        verifyThat("#tbcEmail", isVisible());
        verifyThat("#tbcBirthDate", isVisible());
        verifyThat("#tbcBirthDate", isVisible());
        verifyThat("#tbcSalary", isVisible());
    }

    @Test
    @Ignore
    public void testB_FilterSearch() {
        clickOn("#tfFiler");
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
        assertTrue("The birth date value does not match with the one of the filter", tblTeachers.getItems().stream().
                filter(teacher -> dateFormatter.format(teacher.getBirthDate()).equalsIgnoreCase(tfFilter.getText())).count() > 0);
        resetTableValues();
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        tfFilter.setText("2500");
        assertTrue("The salary value does not match with the one of the filter", tblTeachers.getItems().stream().filter(teacher
                -> Objects.equals(teacher.getSalary(), Float.valueOf(tfFilter.getText()))).count() > 0);
        clickOn("#ivSearch");
        assertEquals("Not filtered by salary correctly", 2, tblTeachers.getItems().size());
    }

    @Test
    public void testD_CreationOfANewTeacher() {
        int row = tblTeachers.getItems().size();
        clickOn("#btnCreate");
        assertEquals("Row not added on creation!", row + 1, tblTeachers.getItems().size());
        // tblTeachers.getSelectionModel().select(row + 1, tbcFullName);
        write("Federico Fernandez");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        write("ffernandez");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        write("fernandez");
        type(KeyCode.ENTER);
        verifyThat("Invalid email value", NodeMatchers.isVisible());
        clickOn("Aceptar");
        type(KeyCode.ENTER);
        write("fedefer@gmail.com");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        write("abcd");
        type(KeyCode.ENTER);
        verifyThat("Invalid telephone value", NodeMatchers.isVisible());
        clickOn("Aceptar");
        type(KeyCode.ENTER);
        write("676956879");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        type(KeyCode.TAB);
        write("3/3/1986");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        write("1000");
        type(KeyCode.ENTER);
        clickOn("#ivTick");
        assertTrue("Error while creating the teacher", tblTeachers.getItems().stream().filter(teacher -> teacher.getLogin().
                equalsIgnoreCase("ffernandez")).count() > 0);
        // type(KeyCode.ENTER);
        // Node cell=lookup(".table-row-cell").nth(0).query();
        //doubleClickOn(cell);
    }

    public void resetTableValues() {
        clickOn("#chBox");
        clickOn("All");
        clickOn("#ivSearch");
        assertNotEquals("Table has no data: Cannot test.", tblTeachers.getItems().size(), 0);
        clickOn("#chBox");
    }

}
