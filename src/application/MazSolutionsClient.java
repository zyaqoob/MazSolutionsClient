/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.SignInController;
import view.WindowStudentAdminController;

/**
 *
 * @author z332h
 */
public class MazSolutionsClient extends Application {
    
    // Logger to record the events and trace out errors.
    private static final Logger LOGGER = Logger.getLogger("application.Main.class");

    /**
     * Method where the stage is shown.
     * @param primaryStage creation of stage to load our window application.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        LOGGER.info("App started");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignInWindow.fxml"));
        try {

            Parent root = (Parent) loader.load();
            SignInController controller = loader.getController();
            controller.setStage(primaryStage);
            controller.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
       /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/WindowStudentAdmin.fxml"));
       Parent root = (Parent) loader.load();
       WindowStudentAdminController controller = loader.getController();
       controller.initStage(root);*/
    }

    /**
     * Method that run the application.
     * @param args this method creates an instance of application and run it.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
