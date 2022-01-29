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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
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
    
  
    public void testA_variablesStart() throws Exception {
        //start JavaFX application to be tested    
        //new ApplicationMaz().start(stage);
        //lookup for some nodes to be used in testing
        lblSearchBy=lookup("#lblSearchBy").query();
        btnCreate=lookup("#btnCreate").query();
        btnDelete=lookup("#btnDelete").query();
        btnPrint=lookup("#btnPrint").query();
        
        ivTick=lookup ("#ivTick").query();
        ivX=lookup ("#ivX").query();
        
        
        
        studentsViewPane=lookup("#studentsViewPane").query();
        
        
        btnSignIn=lookup("#btnSignIn").query();
        txtUserName=lookup("#txtUserName").query();
        txtPasswd=lookup("#txtPasswd").query();
    }
    
    
    
    @Test
    public void testB_initialInteraction(){
        
        clickOn("#txtUserName");
        write("admin");
        clickOn("#txtPasswd");
        write("uA2fN7");
        clickOn("#btnSignIn");
        verifyThat("#studentsViewPane", isVisible());
    }
    
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
    
    @Test
    public void testD_choiceBoxFilterWorking() {
        
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        chbFilterStudents=lookup("#chbFilterStudents").query();
        tfSearch=lookup("#tfSearch").query();
        ivSearch=lookup ("#ivSearch").query();
        tblStudents=lookup ("#tblStudents").queryTableView();
        
        //Test search by Full name
        clickOn("#tfSearch");
        tfSearch.setText("Miguel Sanchez Herrero");
        clickOn(ivSearch);
        assertEquals("The filter is not working."
                , studentsData.stream().filter(u->u.getFullName().equals(tfSearch.getText())).count()
                , tblStudents.getItems().size());
        assertTrue("The filter is not working"
                , tblStudents.getItems().get(0).getFullName().equalsIgnoreCase(tfSearch.getText()));
        
        //Test search by Course
        clickOn("#chbFilterStudents");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        tfSearch.setText("2dam");
        clickOn(ivSearch);
        assertEquals("The filter is not working."
                , studentsData.stream().filter(u->u.getCourse().getName().equals(tfSearch.getText())).count()
                , tblStudents.getItems().size());
        assertTrue("The filter is not working"
                , tblStudents.getItems().get(0).getCourse().getName().equalsIgnoreCase(tfSearch.getText()));
        
        //Test search by Year
        clickOn("#chbFilterStudents");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        tfSearch.setText("2021-09-16");
        clickOn(ivSearch);
        assertEquals("The filter is not working."
                , studentsData.stream().filter(u-> dateFormatter.format(u.getYear()).equals(tfSearch.getText())).count()
                , tblStudents.getItems().size());
        assertTrue("The filter is not working"
                , dateFormatter.format(tblStudents.getItems().get(0).getYear()).equalsIgnoreCase(tfSearch.getText()));
        
        //Test search by Email
        clickOn("#chbFilterStudents");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        tfSearch.setText("msanchezherrero7@gmail.com");
        clickOn(ivSearch);
        assertEquals("The filter is not working."
                , studentsData.stream().filter(u->u.getEmail().equals(tfSearch.getText())).count()
                , tblStudents.getItems().size());
        assertTrue("The filter is not working"
                , tblStudents.getItems().get(0).getEmail().equalsIgnoreCase(tfSearch.getText()));
        
        //Test search by Telephone
        clickOn("#chbFilterStudents");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        tfSearch.setText("691814720");
        clickOn(ivSearch);
        assertEquals("The filter is not working."
                , studentsData.stream().filter(u->u.getTelephone().equals(tfSearch.getText())).count()
                , tblStudents.getItems().size());
        assertTrue("The filter is not working"
                , tblStudents.getItems().get(0).getTelephone().equalsIgnoreCase(tfSearch.getText()));
        
        //Test search by Birth date
        clickOn("#chbFilterStudents");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        tfSearch.setText("1985-05-27");
        clickOn(ivSearch);
        assertEquals("The filter is not working."
                , studentsData.stream().filter(u-> dateFormatter.format(u.getBirthDate()).equals(tfSearch.getText())).count()
                , tblStudents.getItems().size());
        assertTrue("The filter is not working"
                , dateFormatter.format(tblStudents.getItems().get(0).getBirthDate()).equalsIgnoreCase(tfSearch.getText()));
    }
    
    @Test
    public void testE_tfSearchMaxCharacterReached() {
        
        tfSearch=lookup("#tfSearch").query();
        clickOn("#tfSearch");
        tfSearch.setText(LIMIT_CHARACTERS);
        write("a");
        verifyThat("You have entered a value that is too large for this search.", NodeMatchers.isVisible());
        clickOn("Aceptar");
        
    }
    
    @Test
    public void testF_studentCreationIsWorking() {
    }
    
}
