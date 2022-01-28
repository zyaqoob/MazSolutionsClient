/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import application.ApplicationMaz;
import classes.Student;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author 2dam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WindowStudentAdminControllerTest extends ApplicationTest{
    
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
    
    @Override 
    public void start(Stage stage) throws Exception {
        //start JavaFX application to be tested    
        new ApplicationMaz().start(stage);
        //lookup for some nodes to be used in testing
        
        btnCreate=lookup("#btnCreate").query();
        btnDelete=lookup("#btnDelete").query();
        btnPrint=lookup("#btnPrint").query();
        ivSearch=lookup ("#ivSearch").query();
        ivTick=lookup ("#ivTick").query();
        ivX=lookup ("#ivX").query();
        tblStudents=lookup ("#tblStudents").queryTableView();
        chbFilterStudents=lookup("#chbFilterStudents").query();
        tfSearch=lookup("#tfSearch").query();
    }
    
    @Test
    public void testA_initialInteraction(){
        
    }

    @Test
    public void testB_someMethod() {
    }
    
}
