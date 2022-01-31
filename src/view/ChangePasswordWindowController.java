/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.User;
import crypto.Crypto;
import interfaces.UserManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.GenericType;
import logic.RESTfulClientType;
import logic.RESTfulFactory;

/**
 * FXML Controller class
 *
 * @author Aitor
 */
public class ChangePasswordWindowController {

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfRepeatPassword;

    @FXML
    private TextField tfNewPassword;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblNewPassword;

    @FXML
    private Button btnAccept;

    private User user;

    private final UserManager USER_MANAGER = (UserManager) new RESTfulFactory().getRESTClient(RESTfulClientType.USER);

    public void setUser(User user) {
        this.user = user;
    }

    public void initStage(Parent root) {
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("SignIn");
        stage.setResizable(false);
        lblPassword.setVisible(false);
        lblNewPassword.setVisible(false);
        btnAccept.setDisable(true);
        btnAccept.setOnAction((ActionEvent action) -> {
            try {
                if (tfNewPassword.equals(tfRepeatPassword)) {
                    lblNewPassword.setText("Password does not match!");
                } else {
                    user.setPassword(tfPassword.getText());
                    user = USER_MANAGER.findUserByPassword_XML(new GenericType<User>() {
                    }, user.getLogin(), Crypto.cifrar(user.getPassword()), Crypto.cifrar(tfNewPassword.getText()));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Password changed correctly", ButtonType.OK);
                    alert.show();
                }
            } catch (NotAuthorizedException e) {
                lblPassword.setText("Incorrect password!");
                lblPassword.setVisible(true);
                btnAccept.setDisable(true);
            }
        });
        tfPassword.textProperty().addListener(this::textChanged);
        tfNewPassword.textProperty().addListener(this::textChanged);
        tfRepeatPassword.textProperty().addListener(this::textChanged);
        stage.show();
    }
    
    
    private void textChanged(Observable observable,String oldValue,String newValue){
         if (!tfPassword.getText().trim().equals("") && !tfNewPassword.getText().trim().equals("")
                    && !tfRepeatPassword.getText().trim().equals("")) {
                btnAccept.setDisable(false);
            } else {
                btnAccept.setDisable(true);
            }
            // to check the limit of characters introduced in username and password fields.
            characterLimitArrived(tfPassword, lblPassword);
            characterLimitArrived(tfNewPassword, lblNewPassword);
            characterLimitArrived(tfRepeatPassword, lblNewPassword);
    }
    private void characterLimitArrived(TextField textField, Label label) {

        //if textfield length is higher than 255 character, label will be visible to warn the user.
        if (textField.getText().length() > 255) {
            label.setText("Character limit arrived");
            label.setVisible(true);
            btnAccept.setDisable(true);
        } else {
            label.setVisible(false);
        }
    }
}
