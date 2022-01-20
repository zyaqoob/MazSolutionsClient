/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Student;
import classes.User;
import restful.UserRESTClient;
import crypto.Crypto;
/*import exceptions.UserNotFoundException;
import exceptions.ConnectionRefusedException;
import exceptions.IncorrectPasswordException;
import exceptions.UserAlreadyExistException;*/
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Zeeshan Yaqoob
 */
/**
 * Class that manage the SignInWindow.
 * @author Aitor
 */
public class SignInController {

    // Logger to record the events and trace out errors.
    private static final Logger LOGGER = Logger.getLogger("application.SignInController");

    // Button to sign in to the application
    @FXML
    private Button btnSignIn;
    // Button to close the application
    @FXML
    private Button btnExit;
    // Password field for user to enter passwor
    @FXML
    private PasswordField txtPasswd;
    // TextField for user to enter username
    @FXML
    private TextField txtUserName;
    // If user is not registered, this hyperlink will get to user to sign up.
    @FXML
    private Hyperlink signUpLink;
    // lablel that will be visible if user reached to max character limit for password
    @FXML
    private Label lblPasswdMax;
    //lablel that will be visible if user reached to max character limit for username
    @FXML
    private Label lblUserMax;
    // Stage that will be used to initiate other windows
    private Stage stage;
    // An object of user to work with
    private User user;
    // An object of interface to ge implementation
    private final UserRESTClient userRest=new UserRESTClient();
    

    /**
     * Method that set the value of the stage.
     * @param primaryStage is principal window of application.
     */
    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
        LOGGER.info("Stage set");
    }

    /**
     * Method that iniciates the stage.
     * @param root base class
     * Scene is created, and defines the intial state of window.
     */
    public void initStage(Parent root) {
        LOGGER.info("Stage initiated");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("SignIn");
        stage.setResizable(false);
        btnSignIn.setDisable(true);
        btnExit.setOnAction(this::close);
        btnSignIn.setOnAction(this::signIn);
        txtUserName.textProperty().addListener(this::textChanged);
        txtPasswd.textProperty().addListener(this::textChanged);
        signUpLink.setOnAction(this::signUp);
        lblPasswdMax.setVisible(false);
        lblUserMax.setVisible(false);
        stage.show();

    }

    /**
     * Method that closes the stage.
     * @param action, to close the current window.
     */
    public void close(ActionEvent action) {
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        LOGGER.info("Stage closed");
    }

    /**
     * This method observe the username and password texts to manage the state of signIn button.
     * @param observable, object that has listener, being observed.
     * @param oldValue indicates the old value(could be default).
     * @param newValue indicates the newly introduced value.
     * 
     * 
     */
    public void textChanged(ObservableValue observable, String oldValue, String newValue) {
        if (!txtPasswd.getText().trim().equals("") && !txtUserName.getText().trim().equals("")) {
            btnSignIn.setDisable(false);
        } else {
            btnSignIn.setDisable(true);
        }
        // to check the limit of characters introduced in username and password fields.
        characterLimitArrived(txtPasswd, lblPasswdMax);
        characterLimitArrived(txtUserName, lblUserMax);
        LOGGER.info("Text changed");
    }

    /**
     * Method that get the information from window and make a call to the interface Signable depending on the action.
     * @param action
     *  
     */
    public void signIn(ActionEvent action) {       
        LOGGER.info("User sent for signIn");
        try {
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_SHOWING));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentWindowSinSeleccion.fxml"));
            Stage stageSignIn = new Stage();
            try {
                //Parent root = (Parent) loader.load();
                //StudentSinSeleccionController controller = loader.getController();
                //user=userRest.login_XML(new GenericType<User>(){},txtUserName.getText().trim(), txtPasswd.getText().trim()/*Crypto.cifrar(txtPasswd.getText())*/);
                //controller.setUser(user);
                //controller.setLabelText();
                //controller.setStage(stageSignIn);
                //controller.initStage(root);
                System.out.println(userRest.login_XML(new GenericType<User>(){}, "aitorrdg", "abcd*1234"));
            } catch (Exception ex) {
                //Alert alert = new Alert(Alert.AlertType.ERROR, "ERROR WHILE SIGNING UP", ButtonType.OK);
                ex.printStackTrace();
            }
            //Initialization of signedIn window.
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        /*} catch (UserNotFoundException | IncorrectPasswordException | ConnectionRefusedException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
            if(ex instanceof UserNotFoundException){
                txtUserName.requestFocus();
            }else if(ex instanceof IncorrectPasswordException){
                txtPasswd.requestFocus();
            }else{
                txtUserName.requestFocus();
            }*/
        } catch (Exception ex) {

           /*Alert alert = new Alert(Alert.AlertType.ERROR, "Unexpected Error Ocurred", ButtonType.OK);
            alert.show();*/
           ex.printStackTrace();
        }

    }

    /**
     * Method will initiate SignedUp window. 
     * @param action
     * 
     */
    public void signUp(ActionEvent action) {
        /*LOGGER.info("User sent for signUp");
        signableFactory = new SignableFactory();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_SHOWING));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUpWindow.fxml"));
        Stage stageSignUp = new Stage();
        try {
            Parent root = (Parent) loader.load();
            //SignUpController controller = loader.getController();
            //to get the implementation of signableFactory
            controller.setSignable(signableFactory.getSignableImplementation());
            controller.setStage(stageSignUp);
            controller.initStage(root);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "ERROR WHILE SIGNING UP", ButtonType.OK);
        }*/
    }

    /**
     * Method to check the character limit of a textfield.
     * @param textField, receives the textfield from textChanged method.
     * @param label, receives the label from the textChanged method.
     */
    private void characterLimitArrived(TextField textField, Label label) {
        LOGGER.info("character limit evaluation");
        
        //if textfield length is higher than 255 character, label will be visible to warn the user.
        if (textField.getText().length() > 255) {
            label.setVisible(true);
            btnSignIn.setDisable(true);
        } else {
            label.setVisible(false);
        }
    }
    
}