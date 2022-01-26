/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.User;
import crypto.Crypto;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
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
import static javafx.scene.input.KeyCode.I;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import restful.UserRESTClient;

/**
 *
 * @author z332h
 */
public class SignInController {

    @FXML
    private Label lblUserMax;
    @FXML
    private Label lblPasswdMax;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPasswd;
    @FXML
    private Button btnSignIn;
    @FXML
    private Hyperlink passwdRecoverLink;
    @FXML
    private Hyperlink signUpLink;

    private Stage stage;

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign In");
        stage.setResizable(false);
        btnSignIn.setDisable(true);
        btnSignIn.setOnAction(this::signIn);
        txtUserName.textProperty().addListener(this::textChanged);
        txtPasswd.textProperty().addListener(this::textChanged);
        signUpLink.setOnAction(this::signUp);
        passwdRecoverLink.setOnAction(this::handlePasswordRecoverAction);
        lblUserMax.setVisible(false);
        lblPasswdMax.setVisible(false);
        stage.show();

    }

    /**
     * Method that get the information from window and make a call to the
     * interface Signable depending on the action.
     *  
     * @param action
     *
     */
    public void signIn(ActionEvent action) {
        User user = new User();
        String username = txtUserName.getText();
        String password = txtPasswd.getText();
        // user.setFullName("Zeesha Yaqoob");
        // user.setEmail("z332han@gmail.com");
        // user.setTelephone("612569329");

        password = Crypto.cifrar(password);
        UserRESTClient rest = new UserRESTClient();
        user = rest.login_XML(User.class, username, password);
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserDetails.fxml"));
        Stage userDetailsStage = new Stage();
        try {
            Parent root = (Parent) loader.load();
            UserDetailsController controller = loader.getController();
            controller.setStage(userDetailsStage);
            controller.setUser(user);
            controller.initStage(root);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unexpected Error Ocurred", ButtonType.OK);
            alert.show();
        }

    }

    /**
     * Method will initiate SignedUp window.
     *
     * @param action
     *
     */
    public void signUp(ActionEvent action) {

    }

    /**
     * This method observe the username and password texts to manage the state
     * of signIn button.
     *
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

    }

    /**
     * Method to check the character limit of a textfield.
     *
     * @param textField, receives the textfield from textChanged method.
     * @param label, receives the label from the textChanged method.
     */
    private void characterLimitArrived(TextField textField, Label label) {

        //if textfield length is higher than 255 character, label will be visible to warn the user.
        if (textField.getText().length() > 255) {
            label.setVisible(true);
            btnSignIn.setDisable(true);
        } else {
            label.setVisible(false);
        }
    }

    public void handlePasswordRecoverAction(ActionEvent action) {
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/password_recover_window.fxml"));
        Stage passwordRecoverStage = new Stage();
        try {
            Parent root = (Parent) loader.load();
            PasswordRecoverController controller = loader.getController();
            controller.setStage(passwordRecoverStage);
            controller.initStage(root);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unexpected Error Ocurred", ButtonType.OK);
            alert.show();
        }
    }

}
