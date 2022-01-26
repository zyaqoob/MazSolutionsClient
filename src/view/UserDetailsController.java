/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.User;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author z332h
 */
public class UserDetailsController {

    @FXML
    private Label lblCourse;
    @FXML
    private Label lblExam;
    @FXML
    private Label lblExamSession;
    @FXML
    private Label lblName;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblBirthDate;
    @FXML
    private Label lblTelephone;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnCancel;
    
    private User user;
    
    private Stage stage;
    
    public void setUser(User user){
        this.user=user;
    }
    public void setStage(Stage primaryStage){
        this.stage=primaryStage;
    }
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign In");
        stage.setResizable(false);
        lblName.setText(user.getFullName());
       // lblBirthDate.setText(user.getBirthDate().toString());
        lblEmail.setText(user.getEmail());
        lblTelephone.setText(user.getTelephone());
        stage.show();

    }
}
