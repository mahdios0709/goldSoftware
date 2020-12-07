/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {
    private static Stage stage; // **Declare static Stage**
  //  private static final String macThisPc="F8-CA-B8-24-A1-5E";




    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/CSS/Style.css");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e -> Platform.exit());

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
 //       if(getMac!=new MacAddress()); return;
 //   new MacAddress(macThisPc);
     launch(args);

    }

}
