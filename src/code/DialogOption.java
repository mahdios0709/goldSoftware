/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class DialogOption {



    protected boolean DialogOptionCONFIRMATION(String ContentText, String Title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(Title);
        alert.setHeaderText(null);
        alert.setContentText(ContentText);
        //alert.showAndWait();
        Optional<ButtonType> option = alert.showAndWait();
       return option.get()==ButtonType.CANCEL;
    }

    protected void DialogOptionERROR(String ContentText, String Title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(Title);
        alert.setHeaderText(null);
        alert.setContentText(ContentText);
        alert.showAndWait();

    }

    protected void DialogOptionINFORMATION(String ContentText, String Title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(Title);
        alert.setHeaderText(null);
        alert.setContentText(ContentText);
        alert.showAndWait();

    }

    protected void DialogOptionWARNING(String ContentText, String Title) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(Title);
        alert.setHeaderText(null);
        alert.setContentText(ContentText);
        alert.showAndWait();
        //Optional<ButtonType> r = alert.showAndWait();
      // if (r.get() == ButtonType.CLOSE) ;

    }

}
