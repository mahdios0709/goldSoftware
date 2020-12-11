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
    private Pane pnlMarquee;

    @FXML
    private AnchorPane pnlMain;

    @FXML
    private Pane viewpane;



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


        Marquee marquee = new Marquee("This is the initialization content that has been chosen to appear...");
        marquee.setColor("white");
        marquee.setStyle("-fx-font: bold 20 arial;");
         marquee.setBoundsFrom(pnlMain);
        marquee.moveDownBy(7);
        marquee.setScrollDuration(18);

        pnlMarquee.getChildren().add(marquee);
        marquee.run();
    }
}
