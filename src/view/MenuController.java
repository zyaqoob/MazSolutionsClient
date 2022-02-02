/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import static view.AdminTeacherWindowController.stage;

/**
 * FXML Controller class
 *
 * @author Aitor
 */
public class MenuController extends MenuData implements Initializable{

    /**
     * Initializes the controller class.
     *
     * @param root
     */
    @FXML
    private MenuItem menuChangePassword;
    @FXML
    private MenuItem menuPersonalInfo;
    @FXML
    private MenuItem menuLogOut;
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menuChangePassword.setOnAction((ActionEvent a) -> {
            try {
                Parent root;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChangePasswordWindow.fxml"));
                root = (Parent) loader.load();
                ChangePasswordWindowController controller = loader.getController();
                controller.setUser(getUser());
                controller.initStage(root);
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        menuPersonalInfo.setOnAction((ActionEvent a) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Available in next updates", ButtonType.OK);
            alert.show();
        });
        menuLogOut.setOnAction((ActionEvent a) -> {
            Parent root;
            FXMLLoader loader;
            try {            
                
                stage.close();
                loader = new FXMLLoader(getClass().getResource("/view/SignInWindow.fxml"));
                root = (Parent) loader.load();
                SignInController signInController = loader.getController();
                signInController.initStage(root);
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }



}
