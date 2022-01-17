/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
        stage.setTitle("Sign In");
        stage.setResizable(false);
        btnAccept.setOnAction(this::handleBtnAcceptAction);
        lblMax.setVisible(false);
        txtInfo.setVisible(false);
        stage.show();
    }

    public void handleBtnAcceptAction(ActionEvent action) {
        UserRESTClient restUser = new UserRESTClient();
       restUser.findUserByEmail_XML(User.class, txtEmail.getText());
    }
}
