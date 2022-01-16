/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.AdminTeacherWindowController;

/**
 *
 * @author z332h
 */
public class ApplicationMaz extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WindowTeacherAdmin.fxml"));
       Parent root = (Parent) loader.load();
       AdminTeacherWindowController a = loader.getController();
       a.initStage(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
