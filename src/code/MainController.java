/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author AbdElFateh
 */

public class MainController implements Initializable {
    @FXML
    private BorderPane mainpane;

    @FXML
    private Pane viewpane;

    @FXML
    private Label phone;

    @FXML
    private Label mag;

    @FXML
    private Label dateFiled;

    @FXML
    private Label userName;

    @FXML
    void goldprice(ActionEvent event) throws IOException{
        FXLoader load = new FXLoader();
        viewpane = load.getpage2("gold");
        mainpane.setCenter(viewpane);
    }
    @FXML
    void facture(ActionEvent event) throws IOException {
        FXLoader load = new FXLoader();
        viewpane = load.getpage2("facture");
        mainpane.setCenter(viewpane);
    }
    @FXML
    void setting(ActionEvent event) throws IOException {
        FXLoader load = new FXLoader();
        viewpane = load.getpage2("config");
        mainpane.setCenter(viewpane);
    }

    @FXML
    void home(ActionEvent event) throws IOException{
        FXLoader load = new FXLoader();
        viewpane = load.getpage2("Main");
        mainpane.setCenter(viewpane);
    }

    @FXML
    void pieces(ActionEvent event) throws IOException{
        FXLoader load = new FXLoader();
        viewpane = load.getpage2("piece");
        mainpane.setCenter(viewpane);
    }

    @FXML
    void purchase(ActionEvent event) throws IOException{
        FXLoader load = new FXLoader();
        viewpane = load.getpage2("purchase");
        mainpane.setCenter(viewpane);

    }

    @FXML
    void exit(ActionEvent event) throws IOException {
        FXLoader fx = new FXLoader();
        ((Node) (event.getSource())).getScene().getWindow().hide();
        fx.LoadWin(event, "login", "Login");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userName.setText(Controller.getUserName());
        try {
            FXLoader load = new FXLoader();
            viewpane = load.getpage2("Main");
            mainpane.setCenter(viewpane);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }


        loadData();

    }

    private void loadData() {


        java.util.Locale locale = new Locale( "ar", "SA" ); // ( language code, country code
        DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, locale);
        dateFiled.setText(df.format(new Date()));

        try (Connection con = Connecter.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT  `magazinName`,`phoneNumber`  FROM `config`")) {

            while (rs.next()) {
                 mag.setText(rs.getString(1));
                phone.setText(rs.getString(2));
            }
        } catch (SQLException ex) {
            new DialogOption().DialogOptionERROR("حدث خطاء", "خطاء");        }
    }
}
