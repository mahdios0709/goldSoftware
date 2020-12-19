package code;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ControllerActivation implements Initializable {

    @FXML
    private TextField nbrSerial;

    @FXML
    private TextField serialCode;

    @FXML
    void activate(ActionEvent event) {

      String tempSerial = serialCode.getText();
      if(tempSerial.isEmpty()||tempSerial.length()<10){
          new DialogOption().DialogOptionERROR("لم تقم بادراح السريال","خطـاء");
      }else{
          try {
              String serial=new MacAddress().securePWS();
              System.out.println(serial);

              if(tempSerial.equals(serial)){
                  try (Connection con = Connecter.getConnection(); Statement st = con.createStatement();  ) {
                      st.executeUpdate("INSERT INTO `serial`(`serialpc`) VALUES ('"+tempSerial+"')");
                      javax.swing.JOptionPane.showConfirmDialog((java.awt.Component)
                              null, "تم التفعيل" , "تهاني",
                               javax.swing.JOptionPane.DEFAULT_OPTION);
                      System.exit(1);
                      Platform.exit();
                  } catch (SQLException e) {

                      System.err.println(e);
                      System.exit(1);
                  }
              }else {
                  new DialogOption().DialogOptionERROR( "السريال خاطئ" , "خطاء");
              }

          } catch (NoSuchAlgorithmException e) {
              e.printStackTrace();
          }
      }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            nbrSerial.setText(new MacAddress().encrypt());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
