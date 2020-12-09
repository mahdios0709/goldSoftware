/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author info-02
 */
public class Controller {

    @FXML
    private TextField userLogin;

    @FXML
    private PasswordField passLogin;

    @FXML
    private Label eText;

    private static   String UserName;

    public static String getUserName() {
        return UserName;
    }


    @FXML
     void login(ActionEvent event)  {
        toLogin(event);

    }

    private void toLogin(ActionEvent event) {

        String pass;
        UserName = userLogin.getText();
        pass = passLogin.getText();
        if (getUserName().isEmpty()) {
            eText.setText("Enter User Name");
            return;
        }
        if (pass.isEmpty()) {
            eText.setText("Enter Password");
            return;
        }

        try (Connection con = Connecter.getConnection(); Statement st = con.createStatement()) {

        ResultSet    rs = st.executeQuery("SELECT userName ,PASSWORD FROM gold.config where  (userName='" + getUserName() + "' And PASSWORD=SHA('" + pass + "'))");
            if (rs.next()) {
                    UserName = rs.getString("userName");
                    FXLoader fx = new FXLoader();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                     fx.LoadWin(event, "Fx", "Dashboard");

                rs.close();
           
            } else {
                eText.setText("اسم المستخدم او كلمة المرور خاطئة");
            }
        } catch (SQLException | IOException e) {

            System.err.println(e);
            System.exit(1);
        }
    }







}
