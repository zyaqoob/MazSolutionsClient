/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import application.ApplicationMaz;
import classes.Teacher;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.concurrent.TimeoutException;
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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
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
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private ChoiceBox chBox;

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ApplicationMaz.class);
    }

    @Test
    public void testA_SignIn() {
        clickOn("#txtUserName");
        write("admin");
        clickOn("#txtPasswd");
        write("uA2fN7");
        clickOn("#btnSignIn");
        verifyThat("#adminTeacherPane", isVisible());
    }

    @Test
    public void testB_InitialState() {
        chBox = lookup("#chBox").query();
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
    public void testC_FilterSearch() {
        tblTeachers = lookup("#tblTeachers").query();
        tfFilter = lookup("#tfFilter").query();
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
        assertEquals("Not filtered by birthdate correctly", 1, tblTeachers.getItems().size());
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
        tblTeachers = lookup("#tblTeachers").query();
        int row = tblTeachers.getItems().size();
        clickOn("#btnCreate");
        assertEquals("Row not added on creation!", row + 1, tblTeachers.getItems().size());
        // tblTeachers.getSelectionModel().select(row + 1, tbcFullName);
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#ivTick", isVisible());
        verifyThat("#ivX", isVisible());
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

    @Test
    public void testE_ModificationOfTheCreatedTeacher() {
        tblTeachers = lookup("#tblTeachers").query();
        doubleClickOn("Federico Fernandez");
        eraseText(20);
        write("Federico Garcia");
        type(KeyCode.ENTER);
        doubleClickOn("ffernandez");
        eraseText(20);
        write("fgarcia");
        type(KeyCode.ENTER);
        doubleClickOn("fedefer@gmail.com");
        doubleClickOn("fedefer@gmail.com");
        clickOn("fedefer@gmail.com");
        eraseText(1);
        write("fedegarcia@gmail.com");
        type(KeyCode.ENTER);
        doubleClickOn("676956879");
        eraseText(10);
        write("643923058");
        type(KeyCode.ENTER);
        doubleClickOn("1986-03-03");
        eraseText(20);
        write("2/1/1982");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        doubleClickOn("1000.0");
        eraseText(4);
        write("1200");
        type(KeyCode.ENTER);

        assertTrue("Error while modifying the fullname", tblTeachers.getItems().stream().
                filter(teacher -> teacher.getFullName().equalsIgnoreCase("Federico Garcia")).count() > 0);
        assertTrue("Error while modifying the username", tblTeachers.getItems().stream().
                filter(teacher -> teacher.getLogin().equalsIgnoreCase("fgarcia")).count() > 0);
        assertTrue("Error while modifying the email", tblTeachers.getItems().stream().
                filter(teacher -> teacher.getEmail().equalsIgnoreCase("fedegarcia@gmail.com")).count() > 0);
        assertTrue("Error while modifying the telephone", tblTeachers.getItems().stream().
                filter(teacher -> teacher.getTelephone().equalsIgnoreCase("643923058")).count() > 0);
        assertTrue("Error while modifying the course", tblTeachers.getItems().stream().
                filter(teacher -> teacher.getTeacherCourse().getName().equalsIgnoreCase("2dam")).count() > 0);
        assertTrue("Error while modifying the birthdate", tblTeachers.getItems().stream().
                filter(teacher -> dateFormatter.format(teacher.getBirthDate()).equalsIgnoreCase("1982-01-02")).count() > 0);
        assertTrue("Error while modifying the salary", tblTeachers.getItems().stream().
                filter(teacher -> Objects.equals(teacher.getSalary(), Float.valueOf("1200.0"))).count() > 0);
    }

    @Test
    //@Ignore
    public void testF_DeleteTheCreatedTeacher() {
        tblTeachers = lookup("#tblTeachers").query();
        int row = tblTeachers.getItems().size();
        clickOn("Federico Garcia");
        verifyThat("#btnDelete", isEnabled());
        verifyThat("#ivTick", isInvisible());
        verifyThat("#ivX", isInvisible());
        clickOn("#btnDelete");
        verifyThat("You sure you want to erase this teacher?", NodeMatchers.isVisible());
        clickOn("Aceptar");
        assertEquals("Teacher not deleted!", row - 1,tblTeachers.getItems().size());
        assertTrue("Teacher is still in the table", tblTeachers.getItems().stream().
                filter(teacher -> teacher.getFullName().equalsIgnoreCase("Federico Garcia")).count() == 0);
        verifyThat("#btnDelete", isDisabled());
    }
    @Test
    public void testG_CreateEmptyTeacher(){
        clickOn("#btnCreate");
        clickOn("#ivTick");
        verifyThat("Missing data in table cell",NodeMatchers.isVisible());
        clickOn("Aceptar");
    }
    public void resetTableValues() {
        clickOn("#chBox");
        clickOn("All");
        clickOn("#ivSearch");
        assertNotEquals("Table has no data: Cannot test.", tblTeachers.getItems().size(), 0);
        clickOn("#chBox");
    }

}
