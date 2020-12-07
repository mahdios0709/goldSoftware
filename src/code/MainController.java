/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author AbdElFateh
 */
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class MainController implements Initializable {
    @FXML
    private BorderPane mainpane;




    @FXML
    private Pane viewpane;



    @FXML
    private Label User;

    @FXML
    void goldprice(ActionEvent event) throws IOException{
        FXLoader load = new FXLoader();
        viewpane = load.getpage2("gold");
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

        try {
            FXLoader load = new FXLoader();
            viewpane = load.getpage2("Main");
            mainpane.setCenter(viewpane);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
