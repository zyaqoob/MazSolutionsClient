/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.User;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import restful.UserRESTClient;

/**
 *
 * @author z332h
 */
public class PasswordRecoverController {

    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnAccept;
    @FXML
    private Button btnSignIn;
    @FXML
    private Label lblMax;
    @FXML
    private Text txtInfo;

    private Stage stage;

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Password recovery");
        stage.setResizable(false);
        btnAccept.setDisable(true);
        btnAccept.setOnAction(this::handleBtnAcceptAction);
        btnSignIn.setOnAction(this::handleSignIn);
        txtEmail.textProperty().addListener(this::textChanged);
        lblMax.setVisible(false);
        txtInfo.setVisible(false);
        stage.show();
    }

    public void handleBtnAcceptAction(ActionEvent action) {

        String email = txtEmail.getText();
        User user;
        if (checkEmailRegex(email)) {
            UserRESTClient restUser = new UserRESTClient();
            user = restUser.findUserByEmail_XML(new GenericType<User>() {
            }, email);
            if (!user.getEmail().isEmpty()) {
                txtEmail.setText("");
                txtInfo.setVisible(true);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Introduced Email does not belong to any account.", ButtonType.OK);
                alert.show();
            }

        } else {
            lblMax.setText("Not a valid email address");
            lblMax.setVisible(true);
        }

    }

    public void handleSignIn(ActionEvent action) {
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void textChanged(ObservableValue observable, String oldValue, String newValue) {
        if (!txtEmail.getText().trim().equals("")) {
            txtInfo.setVisible(false);
            btnAccept.setDisable(false);
        } else {
            btnAccept.setDisable(true);
        }
        // to check the limit of characters introduced in username and password fields.
        characterLimitArrived(txtEmail, lblMax);

    }

    private void characterLimitArrived(TextField textField, Label label) {

        //if textfield length is higher than 255 character, label will be visible to warn the user.
        if (textField.getText().trim().length() > 255) {
            label.setVisible(true);
            btnAccept.setDisable(true);
        } else {
            label.setVisible(false);
        }
    }

    private boolean checkEmailRegex(String email) {
        return email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    }
}
