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

import java.io.IOException;


public class Main extends Application {
    private static Stage stage; // **Declare static Stage**
  //  private static final String macThisPc="F8-CA-B8-24-A1-5E";

    private static  String naming="login";


    @Override
    public void start(Stage stage) throws Exception {

       Parent root = FXMLLoader.load(getClass().getResource("/fxml/"+naming+".fxml"));
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
     //check his code if ture run nrml else run serial

   // new MacAddress();
     /*   String serialNumber;
        int serialNumberLength = 16; // change this prefered length
        int splitInto = 4; // number of equal parts in the string output
        char separator = '-'; // can be any charactor even spaces

        serialNumber = new SerialNumberGenerator(serialNumberLength).split(splitInto,separator).generate();
        System.out.println(serialNumber);*/
      /*  String cipher1=new MacAddress().caesarCipherEncrypt("7AD0C42E1037");
        String cipher2=new MacAddress().caesarCipherEncrypt(cipher1);

        String cipher3= new MacAddress().encrypt("F8CAB824A15E");
        System.out.println("cipher 1 "+cipher1+"\n cipher 2 :"+cipher2);
       */

        try {
           if(new MacAddress().checking())
               naming="activation";
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch(args);

    }

    static void loading() throws IOException{

        Parent root = FXMLLoader.load(Main.class.getResource("/fxml/activation.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/CSS/Style.css");
        stage.setResizable(false);
        stage.setScene(scene);

        stage.setTitle("تفعيل");

        //stage.setFullScreen (true);

        stage.show();


    }

}
