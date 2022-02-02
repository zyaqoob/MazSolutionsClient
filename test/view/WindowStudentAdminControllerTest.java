/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import application.ApplicationMaz;
import classes.Student;
import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import restful.StudentRESTClient;

/**
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WindowStudentAdminControllerTest extends ApplicationTest{
    
    private BorderPane studentsViewPane;
    private Label lblSearchBy;
    private Label lblStudents;
    private Label lblTeachers;
    private Label lblSubjects;
    private Label lblCourses;
    private Label lblTeacherCourses;
    private Button btnCreate;
    private Button btnDelete;
    private Button btnPrint;
    private ImageView ivSearch;
    private ImageView ivTick;
    private ImageView ivX;
    private TableView <Student> tblStudents;
    private ChoiceBox chbFilterStudents;
    private TextField tfSearch;
    
    private Button btnSignIn;
    private TextField txtUserName;
    private TextField txtPasswd;
    
    private final StudentRESTClient restStudents = new StudentRESTClient();
    private final ObservableList<Student> studentsData = FXCollections.observableArrayList(restStudents.findAllStudents(new GenericType<List<Student>>() {}));
    
    private static final String LIMIT_CHARACTERS="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ApplicationMaz.class);

    }

    //@Ignore
    @Test
    public void testB_initialInteraction(){
        
        clickOn("#txtUserName");
        write("admin");
        clickOn("#txtPasswd");
        write("uA2fN7");
        clickOn("#btnSignIn");
        verifyThat("#studentsViewPane", isVisible());
    }
    //@Ignore
    @Test
    public void testC_initialState() {
        
        verifyThat("#btnCreate", isEnabled());
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#ivTick", isInvisible());
        verifyThat("#ivX", isInvisible());
        verifyThat("#lblSearchBy", isEnabled());
        verifyThat("#chbFilterStudents", (ChoiceBox cb) -> cb.getSelectionModel().getSelectedIndex()==0);
        verifyThat("#tfSearch", hasText(""));
        verifyThat("#ivSearch", isVisible());
        verifyThat("#btnPrint", isEnabled());
    }
    //@Ignore
    @Test
    public void testD_choiceBoxFilterWorking() {
        
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        chbFilterStudents=lookup("#chbFilterStudents").query();
        tfSearch=lookup("#tfSearch").query();
        ivSearch=lookup ("#ivSearch").query();
        tblStudents=lookup ("#tblStudents").queryTableView();
        
        
        clickOn(ivSearch);
        assertNotEquals("Empty table.", tblStudents.getItems().size(), 0);
        
        //Test search by Full name
        clickOn(chbFilterStudents);
        clickOn("Student full name");
        clickOn(tfSearch);
        tfSearch.setText("Miguel Sanchez Herrero");
        clickOn(ivSearch);
        assertEquals("The filter is not working."
                , studentsData.stream().filter(u->u.getFullName().equals(tfSearch.getText())).count()
                , tblStudents.getItems().size());
        assertTrue("The filter is not working"
                , tblStudents.getItems().get(0).getFullName().equalsIgnoreCase(tfSearch.getText()));
        
        //Test search by Course
        clickOn(chbFilterStudents);
        clickOn("Reset");
        clickOn("#ivSearch");
        clickOn(chbFilterStudents);
        clickOn("Student courses");
        tfSearch.setText("2dam");
        clickOn(ivSearch);
        assertEquals("The filter is not working."
                , studentsData.stream().filter(u->u.getCourse().getName().equals(tfSearch.getText())).count()
                , tblStudents.getItems().size());
        assertTrue("The filter is not working"
                , tblStudents.getItems().get(0).getCourse().getName().equalsIgnoreCase(tfSearch.getText()));
        
        //Test search by Year
        clickOn(chbFilterStudents);
        clickOn("Reset");
        clickOn("#ivSearch");
        clickOn(chbFilterStudents);
        clickOn("Student year");
        tfSearch.setText("2021-09-16");
        clickOn(ivSearch);
        assertEquals("The filter is not working."
                , studentsData.stream().filter(u-> dateFormatter.format(u.getYear()).equals(tfSearch.getText())).count()
                , tblStudents.getItems().size());
        assertTrue("The filter is not working"
                , dateFormatter.format(tblStudents.getItems().get(0).getYear()).equalsIgnoreCase(tfSearch.getText()));
        
        //Test search by Email
        clickOn(chbFilterStudents);
        clickOn("Reset");
        clickOn("#ivSearch");
        clickOn(chbFilterStudents);
        clickOn("Student email");
        tfSearch.setText("msanchezherrero7@gmail.com");
        clickOn(ivSearch);
        assertEquals("The filter is not working."
                , studentsData.stream().filter(u->u.getEmail().equals(tfSearch.getText())).count()
                , tblStudents.getItems().size());
        assertTrue("The filter is not working"
                , tblStudents.getItems().get(0).getEmail().equalsIgnoreCase(tfSearch.getText()));
        
        //Test search by Telephone
        clickOn(chbFilterStudents);
        clickOn("Reset");
        clickOn("#ivSearch");
        clickOn(chbFilterStudents);
        clickOn("Student telephone");
        tfSearch.setText("691814720");
        clickOn(ivSearch);
        assertEquals("The filter is not working."
                , studentsData.stream().filter(u->u.getTelephone().equals(tfSearch.getText())).count()
                , tblStudents.getItems().size());
        assertTrue("The filter is not working"
                , tblStudents.getItems().get(0).getTelephone().equalsIgnoreCase(tfSearch.getText()));
        
        //Test search by Birth date
        clickOn(chbFilterStudents);
        clickOn("Reset");
        clickOn("#ivSearch");
        clickOn(chbFilterStudents);
        clickOn("Student birth date");
        tfSearch.setText("1985-05-27");
        clickOn(ivSearch);
        assertEquals("The filter is not working."
                , studentsData.stream().filter(u-> dateFormatter.format(u.getBirthDate()).equals(tfSearch.getText())).count()
                , tblStudents.getItems().size());
        assertTrue("The filter is not working"
                , dateFormatter.format(tblStudents.getItems().get(0).getBirthDate()).equalsIgnoreCase(tfSearch.getText()));
    }
    //@Ignore
    @Test
    public void testE_tfSearchMaxCharacterReached() {
        
        tfSearch=lookup("#tfSearch").query();
        clickOn("#tfSearch");
        tfSearch.setText(LIMIT_CHARACTERS);
        write("a");
        verifyThat("You have entered a value that is too large for this search.", NodeMatchers.isVisible());
        clickOn("Aceptar");
        
    }
    //@Ignore
    @Test
    public void testF_studentCreationIsWorking() {
        
        chbFilterStudents=lookup("#chbFilterStudents").query();
        tblStudents=lookup ("#tblStudents").queryTableView();
        tfSearch=lookup("#tfSearch").query();
        ivSearch=lookup ("#ivSearch").query();
        btnCreate=lookup ("#btnCreate").query();
        ivTick=lookup ("#ivTick").query();
        
        int rowCount = tblStudents.getItems().size();
        
        clickOn(btnCreate);       
        assertEquals("The row has not been added!!!",rowCount+1,tblStudents.getItems().size());
        write("Adama Traoré");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        type(KeyCode.TAB);
        write("16/2/2022");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        write("Mister Adama");
        type(KeyCode.ENTER);
        verifyThat("Invalid email format.", NodeMatchers.isVisible());      
        clickOn("Aceptar");
        type(KeyCode.ENTER);
        write("adamaAlBarca@gmail.com");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        write("adama");
        type(KeyCode.ENTER);
        verifyThat("Invalid telephone.", NodeMatchers.isVisible());
        clickOn("Aceptar");
        type(KeyCode.ENTER);
        write("666666666");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        type(KeyCode.TAB);
        write("3/3/1986");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        write("msanchez");
        type(KeyCode.ENTER);
        clickOn(ivTick);
        verifyThat("Error while creating the user.", NodeMatchers.isVisible());
        clickOn("Aceptar");
        type(KeyCode.ENTER);
        write("adamito");
        type(KeyCode.ENTER);
        clickOn(ivTick);
        assertTrue("Error while creating the student", tblStudents.getItems().stream().filter(s -> s.getFullName().
                equalsIgnoreCase("Adama Traoré")).count() > 0);
        
        
    }
    
    @Test
    public void testG_studentEditIsWorking() {
        
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        tblStudents=lookup ("#tblStudents").queryTableView();
        chbFilterStudents=lookup("#chbFilterStudents").query();
        tfSearch=lookup("#tfSearch").query();
        ivSearch=lookup ("#ivSearch").query();
        
        //get row count
        int rowCount=tblStudents.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        doubleClickOn("Adama Traoré");
        doubleClickOn("Adama Traoré");
        clickOn("Adama Traoré");
        write("Ansu Fati");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        doubleClickOn("2022-02-16");
        write("20/5/1966");
        type(KeyCode.ENTER);
        doubleClickOn("adamaAlBarca@gmail.com");
        doubleClickOn("adamaAlBarca@gmail.com");
        clickOn("adamaAlBarca@gmail.com");
        write("fuerzaFati@gmail.com");
        type(KeyCode.ENTER);
        doubleClickOn("666666666");
        doubleClickOn("666666666");
        clickOn("666666666");
        write("777777777");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);
        type(KeyCode.TAB);
        write("20/5/1966");
        type(KeyCode.ENTER);
        doubleClickOn("adamito");
        write("ansuFati");
        type(KeyCode.ENTER);
        clickOn(chbFilterStudents);
        clickOn("Reset");
        clickOn("#ivSearch");
        clickOn(chbFilterStudents);
        clickOn("Student full name");
        tfSearch.setText("Ansu Fati");
        clickOn(ivSearch);
        assertEquals("The row was not updated."
                , tblStudents.getItems().stream().filter(u->u.getFullName().equals(tfSearch.getText())).count()
                , tblStudents.getItems().size());
        assertTrue("The row was not updated."
                , tblStudents.getItems().get(0).getFullName().equalsIgnoreCase(tfSearch.getText()));
        
        assertTrue("The full name was not modified.", tblStudents.getItems().stream().
                filter(s -> s.getFullName().equalsIgnoreCase("Ansu Fati")).count() > 0);
        assertTrue("The year was not modified.", tblStudents.getItems().stream().
                filter(s -> dateFormatter.format(s.getYear()).equalsIgnoreCase("1966-05-20")).count() > 0);
        assertTrue("The email was not modified.", tblStudents.getItems().stream().
                filter(s -> s.getEmail().equalsIgnoreCase("fuerzaFati@gmail.com")).count() > 0);
        assertTrue("The telephone was not modified.", tblStudents.getItems().stream().
                filter(s -> s.getTelephone().equalsIgnoreCase("777777777")).count() > 0);
        assertTrue("The course was not modified.", tblStudents.getItems().stream().
                filter(s -> s.getCourse().getName().equalsIgnoreCase("2dam")).count() > 0);
        assertTrue("The borth date was not modified.", tblStudents.getItems().stream().
                filter(s -> dateFormatter.format(s.getBirthDate()).equalsIgnoreCase("1966-05-20")).count() > 0);
        assertTrue("The login was not modified.", tblStudents.getItems().stream().
                filter(s -> s.getLogin().equalsIgnoreCase("ansuFati")).count() > 0);
        
        
    }
    
    @Test
    public void testH_studentDeleteIsWorking() {
        
        tblStudents=lookup ("#tblStudents").queryTableView();
        chbFilterStudents=lookup("#chbFilterStudents").query();
        tfSearch=lookup("#tfSearch").query();
        ivSearch=lookup ("#ivSearch").query();
        btnDelete=lookup ("#btnDelete").query();
        clickOn(chbFilterStudents);
        clickOn("Reset");
        clickOn("#ivSearch");
        int rowCount=tblStudents.getItems().size();
        
        clickOn("Ansu Fati");
        clickOn(btnDelete);
        verifyThat("Are you sure that you want to erase this student?", NodeMatchers.isVisible());
        clickOn("Aceptar");
        
        assertEquals("Delete is not working.", rowCount-1,tblStudents.getItems().size());
        assertTrue("Teacher is still in the table", tblStudents.getItems().stream().
                filter(s -> s.getFullName().equalsIgnoreCase("Ansu Fati")).count() == 0);
        verifyThat("#btnDelete", isDisabled());
    }
    
    @Test
    public void testI_deleteWhileCreating() {
        
        btnCreate=lookup ("#btnCreate").query();
        btnDelete=lookup ("#btnDelete").query();
        clickOn(btnCreate);
        clickOn(btnDelete);
        verifyThat("Are you sure that you want to erase this student?", NodeMatchers.isVisible());
        clickOn("Aceptar");
        verifyThat("Unexpected error ocurred while deleting the student.", NodeMatchers.isVisible());
        clickOn("Aceptar");
    }
    
    @Test
    //@Ignore
    public void testJ_cancelOnDeleteUser() {
        tblStudents=lookup ("#tblStudents").queryTableView();
        btnDelete=lookup ("#btnDelete").query();
        //get row count
        int rowCount=tblStudents.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                        rowCount,0);
        //look for 1st row in table view and click it
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        verifyThat(btnDelete, isEnabled());
        clickOn(btnDelete);
        verifyThat("Are you sure that you want to erase this student?", NodeMatchers.isVisible());    
        clickOn("Cancelar");
        assertEquals("A row has been deleted!!!",rowCount,tblStudents.getItems().size());
    }
    
    @Test
    public void testK_EmptyStudentCreation() {
        btnDelete=lookup ("#btnCreate").query();
        ivTick=lookup ("#ivTick").query();
        clickOn(btnDelete);
        clickOn(ivTick);
        verifyThat("Error while creating the user.", NodeMatchers.isVisible());
        clickOn("Aceptar");
    }
    
    
    
}
