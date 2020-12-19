/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author AbdElFateh
 */
public class FXLoader {
    @FXML
    private Pane viewpane;

    public Pane getpage(String fileName) throws FileNotFoundException {
        try {
            URL fileurl = Main.class.getResource("/src/fxml/" + fileName + ".fxml");
            if (fileurl == null) {
                throw new FileNotFoundException("FXML file can not found");
            }

            viewpane = FXMLLoader.load(fileurl);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return viewpane;
    }

    public Pane getpage2(String fileName) throws FileNotFoundException, IOException {
        FXMLLoader fx = new FXMLLoader(getClass().getResource("/fxml/" + fileName + ".fxml"));

        viewpane = fx.load();

        return viewpane;
    }

    public void LoadWin(ActionEvent event, String fileName, String title) throws IOException {

        FXMLLoader fx = new FXMLLoader(getClass().getResource("/fxml/" + fileName + ".fxml"));
        Parent root = (Parent) fx.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/CSS/Style.css");
        stage.setScene(scene);
       
        //stage.setFullScreen (true);

        stage.setResizable(false);
        stage.show();
        // if(!fileName.equals("DWI"))((Node)(event.getSource())).getScene().getWindow().hide();
    }


}
