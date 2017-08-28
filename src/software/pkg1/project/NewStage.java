/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.pkg1.project;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jhill
 */
public class NewStage {

    public static Stage newStage(Scene scene, String title) {
        Stage window = new Stage();

        scene.getStylesheets().add(Software1Project.class.getResource("project1.css").toExternalForm());
        window.setTitle(title);
        window.setScene(scene);
        window.show();

        return window;
    }

}
