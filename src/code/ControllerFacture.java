package code;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ControllerFacture implements Initializable {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    ObservableList facturesTable=FXCollections.observableArrayList();
    ObservableList productsFacturesTable=FXCollections.observableArrayList();


    @FXML
    private TextField search;

    @FXML
    private TableView<Piece> pieceTableView;

    @FXML
    private TableColumn<Piece, String> pieceNumberTable;

    @FXML
    private TableColumn<Piece, String> karaNumberTable;

    @FXML
    private TableColumn<Piece, String> goldTypeTable;

    @FXML
    private TableColumn<Piece, String> pieceWeightTable;

    @FXML
    private TableColumn<Piece, String> stoneNameTable;

    @FXML
    private TableColumn<Piece, String> stonePriceTable;

    @FXML
    private TableColumn<Piece, String> piecePriceTable;



    @FXML
    private TableView<Facture> factureTableView;

    @FXML
    private TableColumn<Facture, String> factureNumberTable;

    @FXML
    private TableColumn<Facture, String> factureTypeTable;

    @FXML
    private TableColumn<Facture, String> clientNameTable;

    @FXML
    private TableColumn<Facture, String> clientNumberTable;

    @FXML
    private TableColumn<Facture, String> totalPriceTable;

    @FXML
    private TableColumn<Facture, String> factureDateTable;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addToTable();
        factureNumberTable.setCellValueFactory(new PropertyValueFactory<>("idFacture"));
        factureTypeTable.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        clientNameTable.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        clientNumberTable.setCellValueFactory(new PropertyValueFactory<>("clientNumber"));
        totalPriceTable.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));
        factureDateTable.setCellValueFactory(new PropertyValueFactory<>("dateFacture"));
        factureTableView.setItems(facturesTable);


        pieceNumberTable.setCellValueFactory(new PropertyValueFactory<>("pieceNumber"));
        karaNumberTable.setCellValueFactory(new PropertyValueFactory<>("karaNumber"));
        goldTypeTable.setCellValueFactory(new PropertyValueFactory<>("goldType"));
        pieceWeightTable.setCellValueFactory(new PropertyValueFactory<>("pieceWeight"));
        stoneNameTable.setCellValueFactory(new PropertyValueFactory<>("stoneName"));
        stonePriceTable.setCellValueFactory(new PropertyValueFactory<>("stonePrice"));
        piecePriceTable.setCellValueFactory(new PropertyValueFactory<>("stonePrice"));
        pieceTableView.setItems(productsFacturesTable);


    }

    private void addToTable() {
        facturesTable.clear();
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT * FROM `factures`,`clients` WHERE factures.idClient=clients.id ORDER BY factures.id DESC");
            rs= pst.executeQuery();
            while(rs.next()){
                facturesTable.add(new Facture(rs.getInt("id"),rs.getInt("idClient"),rs.getString("clientName"),rs.getString("clientNumber"),String.valueOf(rs.getDate("dateFacture")),rs.getString("priceF"),rs.getString("priceR"),rs.getString("paymentType")));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private Double CalculerPrixTotal(int anInt) {
        double prixTotal=0;
        return prixTotal;
    }

    @FXML
    private void addToTable2(MouseEvent event) {
        if (factureTableView.getSelectionModel().getSelectedIndex()>=0){
            productsFacturesTable.clear();
            try {
                con = Connecter.getConnection();
                pst = con.prepareStatement("SELECT * FROM `productsfact`,`productmag`,`goldtype` WHERE productsfact.idProductMag=productmag.id AND productmag.idGoldType=goldtype.id AND productsfact.idFacture=?");
                pst.setInt(1,factureTableView.getItems().get(factureTableView.getSelectionModel().getSelectedIndex()).getIdFacture());
                rs= pst.executeQuery();
                while(rs.next()){
                    if(rs.getDouble("price")==0){

                    }else {
                        productsFacturesTable.add(new Piece(rs.getInt("id"),rs.getInt("idGoldType"),rs.getFloat("weight"),rs.getString("productCode"),String.valueOf(rs.getInt("idKara")),rs.getString("goldType"),rs.getString("mojawharatName"),String.valueOf(rs.getFloat("mojawharatPrice")),String.valueOf(rs.getDate("date")),String.valueOf(rs.getDouble("price")+rs.getDouble("mojawharatPrice"))));

                    }
                }
                pst.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            productsFacturesTable.clear();
        }


    }

    public void printFacture(ActionEvent actionEvent) {
//        print repport
    }

    public void search(KeyEvent keyEvent) {
        String key=search.getText().trim();
        if (key.isEmpty()){
            productsFacturesTable.clear();
            addToTable();
        }else {
            productsFacturesTable.clear();
            facturesTable.clear();
            try {
                con = Connecter.getConnection();
                pst = con.prepareStatement("SELECT * FROM `factures`,`clients` WHERE factures.idClient=clients.id AND (factures.id LIKE '"+key+"' OR clients.clientName LIKE '%"+key+"%' OR clients.clientNumber LIKE '"+key+"')");
                rs=pst.executeQuery();
                while(rs.next()){
                    facturesTable.add(new Facture(rs.getInt("id"),rs.getInt("idClient"),rs.getString("clientName"),rs.getString("clientNumber"),String.valueOf(rs.getDate("dateFacture")),rs.getString("priceF"),rs.getString("priceR"),rs.getString("paymentType")));
                }
                pst.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
