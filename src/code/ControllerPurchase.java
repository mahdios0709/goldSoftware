package code;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ControllerPurchase implements Initializable {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    File file1=null;
    Date nowDate;

    ObservableList piecesTable= FXCollections.observableArrayList();
    ObservableList clientsTable= FXCollections.observableArrayList();
    ObservableList factureProductsTable= FXCollections.observableArrayList();

    @FXML
    private Label facturePrice;

    @FXML
    private Label imageUploaded;

    @FXML
    private TableView<Purchase> purchaseTableView;

    @FXML
    private TableColumn<Purchase, String> pieceNumberPurchaseTable;

    @FXML
    private TableColumn<Purchase, String> goldTypePurchaseTable;

    @FXML
    private TableColumn<Purchase, String> karaNumberPurchaseTable;

    @FXML
    private TableColumn<Purchase, String> pieceWeightPurchaseTable;

    @FXML
    private TableColumn<Purchase, String> pricePurchaseTable;

    @FXML
    private TableView<Client> clientsTableView;

    @FXML
    private TableColumn<Client, String> clientNameTable;

    @FXML
    private TableColumn<Client, String> phoneNumberTable;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField clientName;

    @FXML
    private TableView<Piece> pieceTableView;

    @FXML
    private TableColumn<Piece, String> stoneNameTable;

    @FXML
    private TableColumn<Piece, String> stonePriceTable;

    @FXML
    private TableColumn<Piece, String> pieceWeightTable;

    @FXML
    private TableColumn<Piece, String> karaNumberTable;

    @FXML
    private TableColumn<Piece, String> goldTypeTable;

    @FXML
    private TableColumn<Piece, String> pieceNumberTable;

    @FXML
    void confirmFacture(ActionEvent event) {
        int dejaExist=0;
        int size=0;
        int id=0;
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT * FROM `clients` WHERE `clientName`=? AND `clientNumber`=?");
            pst.setString(1,clientName.getText());
            pst.setString(2,phoneNumber.getText());
            rs=pst.executeQuery();
            while(rs.next()){
                size++;
                id=rs.getInt("id");
            }
            if (size>0){
                dejaExist=1;
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (purchaseTableView.getItems().size()<1){
            //              يرجى إظافة قطع لقائمة المشتريات
        }else if (clientName.getText().isEmpty()||phoneNumber.getText().isEmpty()||file1==null){
            //            يرجى ملء الفراغات
        }else if (dejaExist==1){
            Calendar now=Calendar.getInstance();
            nowDate= Date.valueOf(now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+2)+"-"+now.get(Calendar.DATE));
            try{
                con = Connecter.getConnection();
                pst = con.prepareStatement("INSERT INTO `factures`(`idClient`, `dateFacture`) VALUES (?,?)");
                pst.setInt(1,id);
                pst.setDate(2,nowDate);
                pst.execute();
                pst.close();
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            int idFacture=0;
            try {
                con = Connecter.getConnection();
                pst = con.prepareStatement("SELECT `id` FROM `factures` ORDER BY `id` DESC LIMIT 1");
                rs=pst.executeQuery();

                while(rs.next()){
                    idFacture=rs.getInt("id");
                }
                if (idFacture==0){
                    idFacture=1;
                }
                pst.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            for (int i=0;i<purchaseTableView.getItems().size();i++){
                try{
                    con = Connecter.getConnection();
                    pst = con.prepareStatement("INSERT INTO `productsfact`(`idProductMag`, `idFacture`, `price`) VALUES (?,?,?)");
                    pst.setInt(1,purchaseTableView.getItems().get(i).getId());
                    pst.setInt(2,idFacture);
                    pst.setDouble(3,purchaseTableView.getItems().get(i).getPrice());
                    pst.execute();
                    pst.close();
                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }else if (dejaExist==0){
            try{
                con = Connecter.getConnection();
                pst = con.prepareStatement("INSERT INTO `clients`(`clientName`, `clientNumber`, `clientCard`) VALUES (?,?,?)");
                pst.setString(1,clientName.getText());
                pst.setString(2,phoneNumber.getText());
                FileInputStream fis1=new FileInputStream(file1);
                pst.setBinaryStream(3,fis1,file1.length());
                pst.execute();
                pst.close();
            }catch (SQLException | FileNotFoundException throwables) {
                throwables.printStackTrace();
            }
            int idClient=0;
            try {
                con = Connecter.getConnection();
                pst = con.prepareStatement("SELECT `id` FROM `clients` ORDER BY `id` DESC LIMIT 1");
                rs=pst.executeQuery();

                while(rs.next()){
                    idClient=rs.getInt("id");
                }
                if (idClient==0){
                    idClient=1;
                }
                pst.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            Calendar now=Calendar.getInstance();
            nowDate= Date.valueOf(now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+2)+"-"+now.get(Calendar.DATE));
            try{
                con = Connecter.getConnection();
                pst = con.prepareStatement("INSERT INTO `factures`(`idClient`, `dateFacture`) VALUES (?,?)");
                pst.setInt(1,idClient);
                pst.setDate(2,nowDate);
                pst.execute();
                pst.close();
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            int idFacture=0;
            try {
                con = Connecter.getConnection();
                pst = con.prepareStatement("SELECT `id` FROM `factures` ORDER BY `id` DESC LIMIT 1");
                rs=pst.executeQuery();

                while(rs.next()){
                    idFacture=rs.getInt("id");
                }
                if (idFacture==0){
                    idFacture=1;
                }
                pst.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            for (int i=0;i<purchaseTableView.getItems().size();i++){
                try{
                    con = Connecter.getConnection();
                    pst = con.prepareStatement("INSERT INTO `productsfact`(`idProductMag`, `idFacture`, `price`) VALUES (?,?,?)");
                    pst.setInt(1,purchaseTableView.getItems().get(i).getId());
                    pst.setInt(2,idFacture);
                    pst.setDouble(3,purchaseTableView.getItems().get(i).getPrice());
                    pst.execute();
                    pst.close();
                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @FXML
    void addPieceToPurchase(ActionEvent event) {
        int index=pieceTableView.getSelectionModel().getSelectedIndex();
        if (index>=0){
            purchaseTableView.getItems().add(new Purchase(pieceTableView.getItems().get(index).getId(),pieceTableView.getItems().get(index).getPieceNumber(),pieceTableView.getItems().get(index).getGoldType(),pieceTableView.getItems().get(index).getKaraNumber(),String.valueOf(pieceTableView.getItems().get(index).getPieceWeight()),Double.valueOf(pieceTableView.getItems().get(index).getStonePrice())));
            factureProductsTable.add(new Purchase(pieceTableView.getItems().get(index).getId(),pieceTableView.getItems().get(index).getPieceNumber(),pieceTableView.getItems().get(index).getGoldType(),pieceTableView.getItems().get(index).getKaraNumber(),String.valueOf(pieceTableView.getItems().get(index).getPieceWeight()),Double.valueOf(pieceTableView.getItems().get(index).getStonePrice())));
        }
    }

    @FXML
    void deleteAllProducts(ActionEvent event) {
        factureProductsTable.clear();
        clientName.clear();
        phoneNumber.clear();
        facturePrice.setText("0");
    }

    @FXML
    void deleteOneProduct(ActionEvent event) {
        int index=purchaseTableView.getSelectionModel().getSelectedIndex();
        if (index>=0){
            purchaseTableView.getItems().remove(index);
            factureProductsTable.remove(index);
        }
    }

    @FXML
    void loadImage(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        file1=fileChooser.showOpenDialog(null);
        if (file1!=null){
            imageUploaded.setText("تم رفع الصورة بنجاح");
        }else{
            imageUploaded.setText("حدث خطأ أثناء رفع الصورة");
        }
    }

    @FXML
    void printFacture(ActionEvent event) {
//        print with jasper
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addToTable();
        pieceNumberTable.setCellValueFactory(new PropertyValueFactory<>("pieceNumber"));
        karaNumberTable.setCellValueFactory(new PropertyValueFactory<>("karaNumber"));
        goldTypeTable.setCellValueFactory(new PropertyValueFactory<>("goldType"));
        pieceWeightTable.setCellValueFactory(new PropertyValueFactory<>("pieceWeight"));
        stoneNameTable.setCellValueFactory(new PropertyValueFactory<>("stoneName"));
        stonePriceTable.setCellValueFactory(new PropertyValueFactory<>("stonePrice"));
        pieceTableView.setItems(piecesTable);

        addToTableClient();
        clientNameTable.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        phoneNumberTable.setCellValueFactory(new PropertyValueFactory<>("clientPhoneNumber"));
        clientsTableView.setItems(clientsTable);

        pieceNumberPurchaseTable.setCellValueFactory(new PropertyValueFactory<>("pieceNumber"));
        goldTypePurchaseTable.setCellValueFactory(new PropertyValueFactory<>("goldType"));
        karaNumberPurchaseTable.setCellValueFactory(new PropertyValueFactory<>("karaNumber"));
        pieceWeightPurchaseTable.setCellValueFactory(new PropertyValueFactory<>("pieceWeight"));
        pricePurchaseTable.setCellValueFactory(new PropertyValueFactory<>("price"));
        purchaseTableView.setItems(factureProductsTable);
    }


    private void addToTable() {
        piecesTable.clear();
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT * FROM `productmag`,`goldtype` WHERE productmag.idGoldType=goldtype.id");
            rs= pst.executeQuery();
            while(rs.next()){
                piecesTable.add(new Piece(rs.getInt("id"),rs.getInt("idGoldType"),rs.getFloat("weight"),rs.getString("productCode"),String.valueOf(rs.getInt("idKara")),rs.getString("goldType"),rs.getString("mojawharatName"),String.valueOf(rs.getFloat("mojawharatPrice")),String.valueOf(rs.getDate("date"))));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void addToTableClient() {
        clientsTable.clear();
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT * FROM `clients`");
            rs= pst.executeQuery();
            while(rs.next()){
                clientsTable.add(new Piece(rs.getInt("id"),rs.getInt("idGoldType"),rs.getFloat("weight"),rs.getString("productCode"),String.valueOf(rs.getInt("idKara")),rs.getString("goldType"),rs.getString("mojawharatName"),String.valueOf(rs.getFloat("mojawharatPrice")),String.valueOf(rs.getDate("date"))));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
