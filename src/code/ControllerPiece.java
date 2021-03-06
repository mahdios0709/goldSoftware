package code;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
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

public class ControllerPiece implements Initializable {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    File file1=null;
  //  int idGoldType=1;
    Date nowDate;

    ObservableList<String> karaId= FXCollections.observableArrayList("24","22","18","14","10","9");
    ObservableList<String> gold= FXCollections.observableArrayList("ذهب جديد","ذهب مكسر");
    ObservableList piecesTable=FXCollections.observableArrayList();
    ObservableList goldTypesTable=FXCollections.observableArrayList();


    @FXML
    private Label codebarlabel;

    @FXML
    private Button addpiecebutton;

    @FXML
    private AnchorPane paneZoom;

    @FXML
    private ImageView imageZoom;

    @FXML
    private ComboBox<String> karaNumber;

    @FXML
    private ComboBox<String> goldCombo;

    @FXML
    private ComboBox<String> goldType;

    @FXML
    private TextField pieceNumber;

    @FXML
    private TextField pieceWeight;

    @FXML
    private CheckBox withStone;

    @FXML
    private CheckBox withPricePiece;

    @FXML
    private TextField stoneName;

    @FXML
    private TextField piecePrice;

    @FXML
    private AnchorPane stonePane;

    @FXML
    private AnchorPane stonePane1;

    @FXML
    private AnchorPane paneAddGoldType;

    @FXML
    private TextField stonePrice;

    @FXML
    private ImageView imagePiece;

    @FXML
    private ImageView imageCodeBar;

    @FXML
    private TableView<Piece> pieceTableView;

    @FXML
    private TableColumn<Piece, String> pieceNumberTable;

    @FXML
    private TableColumn<Piece, String> karaNumberTable;

    @FXML
    private TableColumn<Piece, String> goldTypeTable;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<Piece, String> pieceWeightTable;

    @FXML
    private TableColumn<Piece, String> stoneNameTable;

    @FXML
    private TableColumn<Piece, String> stonePriceTable;

    @FXML
    private TableColumn<Piece, String> pieceDateTable;

    public void calculateNumberPiece(){

        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT `id` FROM `productmag` ORDER BY `id` DESC LIMIT 1");
            rs=pst.executeQuery();
            while(rs.next()){
                if (String.valueOf(rs.getInt("id")+1).length()==1){
                    pieceNumber.setText("00000"+String.valueOf(rs.getInt("id")+1));

                }else if (String.valueOf(rs.getInt("id")+1).length()==2){
                    pieceNumber.setText("0000"+String.valueOf(rs.getInt("id")+1));

                }else if (String.valueOf(rs.getInt("id")+1).length()==3){
                    pieceNumber.setText("000"+String.valueOf(rs.getInt("id")+1));

                }else if (String.valueOf(rs.getInt("id")+1).length()==4){
                    pieceNumber.setText("00"+String.valueOf(rs.getInt("id")+1));

                }else if (String.valueOf(rs.getInt("id")+1).length()==5){
                    pieceNumber.setText("0"+String.valueOf(rs.getInt("id")+1));

                }else{
                    pieceNumber.setText(String.valueOf(rs.getInt("id")+1));

                }
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML
    void addPiece(javafx.event.ActionEvent event) {
        int dejaExist=0;
        int size=0;
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT * FROM `productmag` WHERE `productCode`=?");
            pst.setString(1,pieceNumber.getText());
            rs=pst.executeQuery();
            while(rs.next()){
                size++;
            }
            if (size>0){
                dejaExist=1;
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (withStone.isSelected()){

            if (karaNumber.getSelectionModel().isEmpty()||goldCombo.getSelectionModel().isEmpty()||goldType.getSelectionModel().isEmpty()||pieceNumber.getText().isEmpty()||pieceWeight.getText().isEmpty()||stoneName.getText().isEmpty()||stonePrice.getText().isEmpty()){
                 new DialogOption().DialogOptionERROR("ادخل جميع الحقول","خطاء");
            }else if(dejaExist==1){
                new DialogOption().DialogOptionERROR("رقم القطعة موجودة","خطاء");
            }else{
                String codbare="hello";
              //  Calendar now=Calendar.getInstance();
               // nowDate= Date.valueOf(now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+2)+"-"+now.get(Calendar.DATE));
                try{
                    if (file1!=null){
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("INSERT INTO `productmag`(`productCode`, `productImage`, `weight`, `prodcutBarCode`, `withMojawharat`, `mojawharatName`, `mojawharatPrice`, `idKara`, `idGoldType`, `Date`, `price`, `norc`) VALUES (?,?,?,?,?,?,?,?,(SELECT id from goldtype WHERE goldType=?),CURRENT_TIMESTAMP(),?,?)");
                        pst.setString(1,pieceNumber.getText());
                        FileInputStream fis1=new FileInputStream(file1);
                        pst.setBinaryStream(2,fis1,file1.length());
                        pst.setString(3,pieceWeight.getText());
                        pst.setString(4,"codbare");
                        pst.setInt(5,1);
                        pst.setString(6,stoneName.getText());
                        pst.setString(7,stonePrice.getText());
                        pst.setString(8,karaNumber.getValue());
                        pst.setString(9,goldType.getSelectionModel().getSelectedItem());
                        if (withPricePiece.isSelected()){
                            pst.setDouble(10, Double.parseDouble(piecePrice.getText()));

                        }else{
                            pst.setDouble(10, 0);

                        }
                        pst.setString(11,goldCombo.getSelectionModel().getSelectedItem());
                       // pst.setDate(10,nowDate);
                        pst.execute();
                        pst.close();

                    }else{
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("INSERT INTO `productmag`(`productCode`, `weight`, `prodcutBarCode`, `withMojawharat`, `mojawharatName`, `mojawharatPrice`, `idKara`, `idGoldType`, `Date`, `price`, `norc`) VALUES (?,?,?,?,?,?,?,(SELECT id from goldtype WHERE goldType=?),CURRENT_TIMESTAMP(),?,?)");
                        pst.setString(1,pieceNumber.getText());
                        pst.setString(2,pieceWeight.getText());
                        pst.setString(3,"codebar");
                        pst.setInt(4,1);
                        pst.setString(5,stoneName.getText());
                        pst.setString(6,stonePrice.getText());
                        pst.setString(7,karaNumber.getValue());
                        pst.setString(8,goldType.getSelectionModel().getSelectedItem());
                        if (withPricePiece.isSelected()){
                            pst.setDouble(9, Double.parseDouble(piecePrice.getText()));

                        }else{
                            pst.setDouble(9, 0);

                        }
                        pst.setString(10, goldCombo.getSelectionModel().getSelectedItem());
                       // pst.setDate(9,nowDate);
                        pst.execute();
                        pst.close();

                    }
                    new DialogOption().DialogOptionINFORMATION("تم الإظافة بنجاح","إظافة");

                }catch (SQLException | FileNotFoundException throwables) {
                    throwables.printStackTrace();
                    new DialogOption().DialogOptionERROR("حدث خطأ يرجى الإتصال بالمبرمج","خطاء");

                }
            }
        }else{
            if (karaNumber.getSelectionModel().isEmpty()||goldCombo.getSelectionModel().isEmpty()||goldType.getSelectionModel().isEmpty()||pieceNumber.getText().isEmpty()||pieceWeight.getText().isEmpty()){
                // messege d'erreur please fill the gaps
                new DialogOption().DialogOptionERROR("ادخل جميع الحقول","خطاء");

            }else if(dejaExist==1){
                // messege d'erreur deja existe dans la base de donné
                new DialogOption().DialogOptionERROR("رقم القطعة موجودة","خطاء");

            }else{
        //        Calendar now=Calendar.getInstance();
            //    nowDate= Date.valueOf(now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+2)+"-"+now.get(Calendar.DATE));
                try{
                    if (file1!=null){
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("INSERT INTO `productmag`(`productCode`, `productImage`, `weight`, `prodcutBarCode`, `withMojawharat`, `idKara`, `idGoldType`, `date`, `price`, `norc`) VALUES (?,?,?,?,?,?,(SELECT id from goldtype WHERE goldType=?),CURRENT_TIMESTAMP(),?,?)");
                        pst.setString(1,pieceNumber.getText());
                        FileInputStream fis1=new FileInputStream(file1);
                        pst.setBinaryStream(2,fis1,file1.length());
                        pst.setString(3,pieceWeight.getText());
                        pst.setString(4,"codebar");
                        pst.setInt(5,0);
                        pst.setString(6,karaNumber.getValue());
                        pst.setString(7,goldType.getSelectionModel().getSelectedItem());
                        if (withPricePiece.isSelected()){
                            pst.setDouble(8, Double.parseDouble(piecePrice.getText()));

                        }else{
                            pst.setDouble(8, 0);

                        }
                        pst.setString(9, goldCombo.getSelectionModel().getSelectedItem());
                      //  pst.setDate(8,nowDate);

                        pst.execute();
                        pst.close();

                    }else{
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("INSERT INTO `productmag`(`productCode`, `weight`, `prodcutBarCode`, `withMojawharat`, `idKara`, `idGoldType`, `Date`, `price`, `norc`) VALUES (?,?,?,?,?,(SELECT id from goldtype WHERE goldType=?),CURRENT_TIMESTAMP(),?,?)");
                        pst.setString(1,pieceNumber.getText());
                        pst.setString(2,pieceWeight.getText());
                        pst.setString(3,"codebar");
                        pst.setInt(4,0);
                        pst.setString(5,karaNumber.getValue());
                        pst.setString(6,goldType.getSelectionModel().getSelectedItem());
                        if (withPricePiece.isSelected()){
                            pst.setDouble(7, Double.parseDouble(piecePrice.getText()));

                        }else{
                            pst.setDouble(7, 0);

                        }
                        pst.setString(8, goldCombo.getSelectionModel().getSelectedItem());
                        pst.execute();
                        pst.close();
                    }
                    new DialogOption().DialogOptionINFORMATION("تم الإظافة بنجاح","إظافة");
                    pieceNumber.clear();
                    pieceWeight.clear();
                    karaNumber.getSelectionModel().clearSelection();
                    goldType.getSelectionModel().clearSelection();
                    goldCombo.getSelectionModel().clearSelection();
                    withPricePiece.setSelected(false);
                    addpiecebutton.setDisable(false);
                    codebarlabel.setText("XXXXXX");
                    withStone.setSelected(false);
                    stoneName.clear();
                    piecePrice.clear();
                    stonePrice.clear();
                    file1=null;
                    Image imagelogo=new Image("img/logo.png");
                    imagePiece.setImage(imagelogo);
                    imageZoom.setImage(imagelogo);
                    calculateNumberPiece();
                }catch (SQLException | FileNotFoundException throwables) {
                    throwables.printStackTrace();
                    new DialogOption().DialogOptionERROR("رقم القطعة موجودة","خطاء");

                }
            }
        }
        addToTable();

    }

    @FXML
    void checkWithStone(ActionEvent event) {
        if (withStone.isSelected()){
            stonePane.setDisable(false);
            //stoneName.setDisable(false);
          //  stonePrice.setDisable(false);
        }else{
            stonePane.setDisable(true);
         //   stoneName.setDisable(true);
         //   stonePrice.setDisable(true);
        }
    }

    @FXML
    void checkWithPrice(ActionEvent event) {
        if (withPricePiece.isSelected()){
            stonePane1.setDisable(false);
            //stoneName.setDisable(false);
          //  stonePrice.setDisable(false);
        }else{
            stonePane1.setDisable(true);
         //   stoneName.setDisable(true);
         //   stonePrice.setDisable(true);
        }
    }

    @FXML
    void deletePiece(ActionEvent event) {
        int index=pieceTableView.getSelectionModel().getSelectedIndex();
        if (index>=0){
            int idDelete=pieceTableView.getItems().get(index).getId();
            if (idDelete>0){
                try {
                    con = Connecter.getConnection();
                    pst = con.prepareStatement("DELETE FROM `productmag` WHERE `id`=?");
                    pst.setInt(1,idDelete);
                    pst.execute();
                    pst.close();
                    new DialogOption().DialogOptionINFORMATION("تم الحذف بنجاح","حذف");
                    pieceNumber.clear();
                    pieceWeight.clear();
                    karaNumber.getSelectionModel().clearSelection();
                    goldType.getSelectionModel().clearSelection();
                    goldCombo.getSelectionModel().clearSelection();
                    piecePrice.clear();
                    withPricePiece.setSelected(false);
                    addpiecebutton.setDisable(false);
                    codebarlabel.setText("XXXXXX");
                    withStone.setSelected(false);
                    stoneName.clear();
                    stonePrice.clear();
                    file1=null;
                    Image imagelogo=new Image("img/logo.png");
                    imagePiece.setImage(imagelogo);
                    imageZoom.setImage(imagelogo);
                    calculateNumberPiece();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    new DialogOption().DialogOptionINFORMATION("حدث خطأ يرجى الإتصال بالمبرمج","خطأ");

                }
                idDelete=0;
                addToTable();

            }
        }


    }



    @FXML
    void pieceSelected(MouseEvent event) {
        int index = pieceTableView.getSelectionModel().getSelectedIndex();


        if (index>=0){
            int idEdit=pieceTableView.getItems().get(index).getId();
            if (idEdit>0){
                addpiecebutton.setDisable(true);
                try {
                    con = Connecter.getConnection();
                    pst = con.prepareStatement("SELECT * FROM `productmag`,`goldtype` WHERE productmag.idGoldType=goldtype.id AND productmag.id=?");
                    pst.setInt(1,idEdit);
                    rs=pst.executeQuery();
                    while(rs.next()){

                        if (rs.getInt("price")==0){
                            withPricePiece.setSelected(false);
                            piecePrice.clear();
                            stonePane1.setDisable(true);
                        }else{
                            withPricePiece.setSelected(true);
                            piecePrice.setText(rs.getString("price"));
                            stonePane1.setDisable(false);

                        }
                        if (rs.getInt("withMojawharat")==1){
                            stonePane.setDisable(false);
                            withStone.setSelected(true);
                            pieceNumber.setText(rs.getString("productCode"));
                            stoneName.setText(rs.getString("mojawharatName"));
                            goldType.setValue(rs.getString("goldType"));
                            goldCombo.setValue(rs.getString("norc"));
                            karaNumber.setValue(String.valueOf(rs.getInt("idKara")));
                            stonePrice.setText(String.valueOf(rs.getFloat("mojawharatPrice")));
                            pieceWeight.setText(String.valueOf(rs.getFloat("weight")));
                            Blob imagePieceBlob=(Blob) rs.getBlob("productImage");
                            if (imagePieceBlob!=null){
                                InputStream inputStream=imagePieceBlob.getBinaryStream();
                                Image image1=new Image(inputStream);
                                imagePiece.setImage(image1);
                                imageZoom.setImage(image1);

                            }else{
                                Image imagelogo=new Image("img/logo.png");
                                imagePiece.setImage(imagelogo);
                                imageZoom.setImage(imagelogo);
                            }
                        }else{
                            stonePane.setDisable(true);
                            withStone.setSelected(false);
                            pieceNumber.setText(rs.getString("productCode"));
                            stoneName.setText(null);
                            goldType.setValue(rs.getString("goldType"));
                            goldCombo.setValue(rs.getString("norc"));

                            karaNumber.setValue(String.valueOf(rs.getInt("idKara")));
                            stonePrice.setText(null);
                            pieceWeight.setText(String.valueOf(rs.getFloat("weight")));
                            Blob imagePieceBlob=(Blob) rs.getBlob("productImage");
                            if (imagePieceBlob!=null){
                                InputStream inputStream=imagePieceBlob.getBinaryStream();
                                Image image1=new Image(inputStream);
                                imagePiece.setImage(image1);
                                imageZoom.setImage(image1);

                            }else{
                                Image imagelogo=new Image("img/logo.png");
                                imagePiece.setImage(imagelogo);
                                imageZoom.setImage(imagelogo);
                            }
                        }
                    }
                    pst.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            }
        }else{
            pieceNumber.clear();
            pieceWeight.clear();
            karaNumber.getSelectionModel().clearSelection();
            goldType.getSelectionModel().clearSelection();
            goldCombo.getSelectionModel().clearSelection();
            piecePrice.clear();
            withPricePiece.setSelected(false);
            addpiecebutton.setDisable(false);
            codebarlabel.setText("XXXXXX");
            withStone.setSelected(false);
            stoneName.clear();
            stonePrice.clear();
            file1=null;
            Image imagelogo=new Image("img/logo.png");
            imagePiece.setImage(imagelogo);
            imageZoom.setImage(imagelogo);
            calculateNumberPiece();
        }
            codebarlabel.setText(pieceNumber.getText());


    }

    @FXML
    void editPiece(ActionEvent event) {
        int dejaExist=0;
        int size=0;
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT * FROM `productmag` WHERE `productCode`=? AND id!=?");
            pst.setString(1,pieceNumber.getText());
            pst.setInt(2,pieceTableView.getItems().get(pieceTableView.getSelectionModel().getSelectedIndex()).getId());
            rs=pst.executeQuery();
            while(rs.next()){
                size++;
            }
            if (size>0){
                dejaExist=1;
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (withStone.isSelected()){
            if (karaNumber.getSelectionModel().isEmpty()||goldCombo.getSelectionModel().isEmpty()||goldType.getSelectionModel().isEmpty()||pieceNumber.getText().isEmpty()||pieceWeight.getText().isEmpty()||stoneName.getText().isEmpty()||stonePrice.getText().isEmpty()){
                // messege d'erreur please fill the gaps
                new DialogOption().DialogOptionERROR("ادخل جميع الحقول","خطاء");

            }else if(dejaExist==1){
                // messege d'erreur deja existe dans la base de donné
                new DialogOption().DialogOptionERROR("رقم القطعة موجود من قبل","خطاء");

            }else{
                int index = pieceTableView.getSelectionModel().getSelectedIndex();
                int idEdit=pieceTableView.getItems().get(index).getId();

                 try{
                    if (file1!=null){
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("UPDATE `productmag` SET `productCode`=?,`productImage`=?,`weight`=?,`prodcutBarCode`=?,`withMojawharat`=?,`mojawharatName`=?,`mojawharatPrice`=?,`idKara`=?,`idGoldType`=(SELECT id from goldtype WHERE goldType=?),`date`=CURRENT_TIMESTAMP(),`price`=?,`norc`=? WHERE `id`=?");
                        pst.setString(1,pieceNumber.getText());
                        FileInputStream fis1=new FileInputStream(file1);
                        pst.setBinaryStream(2,fis1,file1.length());
                        pst.setString(3,pieceWeight.getText());
                        pst.setString(4,"codebar");
                        pst.setInt(5,1);
                        pst.setString(6,stoneName.getText());
                        pst.setString(7,stonePrice.getText());
                        pst.setString(8,karaNumber.getValue());
                        pst.setString(9,goldType.getSelectionModel().getSelectedItem());
                        if (withPricePiece.isSelected()){
                            pst.setDouble(10, Double.parseDouble(piecePrice.getText()));

                        }else{
                            pst.setDouble(10, 0);

                        }
                        pst.setString(11,goldCombo.getSelectionModel().getSelectedItem());
//                        pst.setDate(10,nowDate);,`price`=?,`norc`=?
                        pst.setInt(12,idEdit);
                        pst.execute();
                        pst.close();

                    }else{
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("UPDATE `productmag` SET `productCode`=?,`weight`=?,`prodcutBarCode`=?,`withMojawharat`=?,`mojawharatName`=?,`mojawharatPrice`=?,`idKara`=?,`idGoldType`=(SELECT id from goldtype WHERE goldType=?),`date`=CURRENT_TIMESTAMP(),`price`=?,`norc`=? WHERE `id`=?");
                        pst.setString(1,pieceNumber.getText());
                        pst.setString(2,pieceWeight.getText());
                        pst.setString(3,"codebar");
                        pst.setInt(4,1);
                        pst.setString(5,stoneName.getText());
                        pst.setString(6,stonePrice.getText());
                        pst.setString(7,karaNumber.getValue());
                        pst.setString(8,goldType.getSelectionModel().getSelectedItem());
                        if (withPricePiece.isSelected()){
                            pst.setDouble(9, Double.parseDouble(piecePrice.getText()));

                        }else{
                            pst.setDouble(9, 0);

                        }
                        pst.setString(10,goldCombo.getSelectionModel().getSelectedItem());
//                        pst.setDate(9,nowDate);
                        pst.setInt(11,idEdit);
                        pst.execute();
                        pst.close();

                    }
                     new DialogOption().DialogOptionINFORMATION("تم التعديل بنجاح","تعديل");karaNumber.getSelectionModel().clearSelection();
                     pieceNumber.clear();
                     pieceWeight.clear();
                     karaNumber.getSelectionModel().clearSelection();
                     goldType.getSelectionModel().clearSelection();
                     goldCombo.getSelectionModel().clearSelection();
                     piecePrice.clear();
                     withPricePiece.setSelected(false);
                     withStone.setSelected(false);
                     addpiecebutton.setDisable(false);
                     codebarlabel.setText("XXXXXX");
                     stoneName.clear();
                     stonePrice.clear();
                     file1=null;
                     Image imagelogo=new Image("img/logo.png");
                     imagePiece.setImage(imagelogo);
                     imageZoom.setImage(imagelogo);
                     calculateNumberPiece();
                 }catch (SQLException | FileNotFoundException throwables) {
                    throwables.printStackTrace();
                     new DialogOption().DialogOptionERROR("حدث خطأ يرجى الإتصال بالمبرمج","خطاء");

                 }
            }
        }else{
            if (karaNumber.getSelectionModel().isEmpty()||goldCombo.getSelectionModel().isEmpty()||goldType.getSelectionModel().isEmpty()||pieceNumber.getText().isEmpty()||pieceWeight.getText().isEmpty()){
                // messege d'erreur please fill the gaps
                new DialogOption().DialogOptionERROR("ادخل جميع الحقول","خطاء");
            }else if(dejaExist==1){
                // messege d'erreur deja existe dans la base de donné
                new DialogOption().DialogOptionERROR("رقم القطعة موجود من قبل","خطاء");
            }else{
                int index = pieceTableView.getSelectionModel().getSelectedIndex();
                int idEdit=pieceTableView.getItems().get(index).getId();
                try{
                    if (file1!=null){
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("UPDATE `productmag` SET `productCode`=?,`productImage`=?,`weight`=?,`prodcutBarCode`=?,`withMojawharat`=?,`idKara`=?,`idGoldType`=(SELECT id from goldtype WHERE goldType=?),`date`=CURRENT_TIMESTAMP(),`price`=?,`norc`=? WHERE `id`=?");
                        pst.setString(1,pieceNumber.getText());
                        FileInputStream fis1=new FileInputStream(file1);
                        pst.setBinaryStream(2,fis1,file1.length());
                        pst.setString(3,pieceWeight.getText());
                        pst.setString(4,"codebar");
                        pst.setInt(5,0);
                        pst.setString(6,karaNumber.getValue());
                        pst.setString(7,goldType.getSelectionModel().getSelectedItem());
                        if (withPricePiece.isSelected()){
                            pst.setDouble(8, Double.parseDouble(piecePrice.getText()));

                        }else{
                            pst.setDouble(8, 0);

                        }
                        pst.setString(9,goldCombo.getSelectionModel().getSelectedItem());
//                        pst.setDate(8,nowDate);
                        pst.setInt(10,idEdit);
                        pst.execute();

                        pst.close();

                    }else{
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("UPDATE `productmag` SET `productCode`=?,`weight`=?,`prodcutBarCode`=?,`withMojawharat`=?,`idKara`=?,`idGoldType`=(SELECT id from goldtype WHERE goldType=?),`date`=CURRENT_TIMESTAMP(),`price`=?,`norc`=? WHERE `id`=?");
                        pst.setString(1,pieceNumber.getText());
                        pst.setString(2,pieceWeight.getText());
                        pst.setString(3,"codebar");
                        pst.setInt(4,0);
                        pst.setString(5,karaNumber.getValue());
                        pst.setString(6,goldType.getSelectionModel().getSelectedItem());
                        if (withPricePiece.isSelected()){
                            pst.setDouble(7, Double.parseDouble(piecePrice.getText()));

                        }else{
                            pst.setDouble(7, 0);

                        }
                        pst.setString(8,goldCombo.getSelectionModel().getSelectedItem());
//                        pst.setDate(7,nowDate);
                        pst.setInt(9,idEdit);
                        pst.execute();
                        pst.close();
                    }
                   new DialogOption().DialogOptionINFORMATION("تم التعديل بنجاح","تعديل");
                    pieceNumber.clear();
                    pieceWeight.clear();
                    karaNumber.getSelectionModel().clearSelection();
                    goldType.getSelectionModel().clearSelection();
                    goldCombo.getSelectionModel().clearSelection();
                    piecePrice.clear();
                    withPricePiece.setSelected(false);

                    withStone.setSelected(false);
                   stoneName.clear();
                   stonePrice.clear();
                    addpiecebutton.setDisable(false);
                    codebarlabel.setText("XXXXXX");
                    file1=null;
                   Image imagelogo=new Image("img/logo.png");
                   imagePiece.setImage(imagelogo);
                    imageZoom.setImage(imagelogo);
                    calculateNumberPiece();
               }catch (SQLException | FileNotFoundException throwables) {
                    throwables.printStackTrace();
                   new DialogOption().DialogOptionERROR("حدث خطأ يرجى الإتصال بالمبرمج","خطاء");

               }
            }
        }
        addToTable();


    }

    

    @FXML
    void refresh(ActionEvent event) {
        addpiecebutton.setDisable(false);
        pieceNumber.clear();
        pieceWeight.clear();
        karaNumber.getSelectionModel().clearSelection();
        goldType.getSelectionModel().clearSelection();
        goldCombo.getSelectionModel().clearSelection();
        piecePrice.clear();
        codebarlabel.setText("XXXXXX");
        withPricePiece.setSelected(false);
        withStone.setSelected(false);
        stoneName.clear();
        stonePrice.clear();
        file1=null;
        Image imagelogo=new Image("img/logo.png");
        imagePiece.setImage(imagelogo);
        imageZoom.setImage(imagelogo);
        calculateNumberPiece();
    }

    @FXML
    void showAddGoldType(ActionEvent event) {
        paneAddGoldType.setVisible(true);
    }

    @FXML
    void hide(ActionEvent event) {
        paneAddGoldType.setVisible(false);
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        file1=fileChooser.showOpenDialog(null);
        if (file1!=null){
            try {
                BufferedImage bufferedImage= ImageIO.read(file1);
                Image image= SwingFXUtils.toFXImage(bufferedImage,null);
                imagePiece.setImage(image);
                imageZoom.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        karaNumber.setItems(karaId);
        goldCombo.setItems(gold);
        Image imagelogo=new Image("img/logo.png");
        imagePiece.setImage(imagelogo);
        imageZoom.setImage(imagelogo);
        calculateNumberPiece();
        Image imagelogo2=new Image("img/codebar.png");
        imageCodeBar.setImage(imagelogo2);
        addToTable();
        pieceNumberTable.setCellValueFactory(new PropertyValueFactory<>("pieceNumber"));
        karaNumberTable.setCellValueFactory(new PropertyValueFactory<>("karaNumber"));
        goldTypeTable.setCellValueFactory(new PropertyValueFactory<>("goldType"));
        pieceWeightTable.setCellValueFactory(new PropertyValueFactory<>("pieceWeight"));
        stoneNameTable.setCellValueFactory(new PropertyValueFactory<>("stoneName"));
        stonePriceTable.setCellValueFactory(new PropertyValueFactory<>("stonePrice"));
        pieceDateTable.setCellValueFactory(new PropertyValueFactory<>("date"));
        pieceTableView.setItems(piecesTable);

        addToTable2();
        val.setCellValueFactory(new PropertyValueFactory<>("goldType"));
        mot.setCellValueFactory(new PropertyValueFactory<>("abGoldType"));
        table.setItems(goldTypesTable);

        fillCombo();
    }

    private void fillCombo() {
        goldType.getItems().clear();
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT goldType FROM goldtype");
            rs= pst.executeQuery();
            while(rs.next()){
                goldType.getItems().add(rs.getString(1));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void addToTable() {
        piecesTable.clear();
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT * FROM `productmag`,`goldtype` WHERE productmag.idGoldType=goldtype.id AND productmag.vendu=0");
            rs= pst.executeQuery();
            while(rs.next()){
                if(rs.getDouble("price")==0){

                }else {
                    piecesTable.add(new Piece(rs.getInt("id"),rs.getInt("idGoldType"),rs.getFloat("weight"),rs.getString("productCode"),String.valueOf(rs.getInt("idKara")),rs.getString("goldType"),rs.getString("mojawharatName"),String.valueOf(rs.getFloat("mojawharatPrice")),String.valueOf(rs.getDate("date")),String.valueOf(rs.getDouble("price")+rs.getDouble("mojawharatPrice"))));

                }
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML
    private TableView<GoldTypes> table;

    @FXML
    private TableColumn<GoldTypes, String> mot;

    @FXML
    private TableColumn<GoldTypes, String> val;

    @FXML
    private TextField objF;

    @FXML
    private TextField valeurF;

    @FXML

    public void vid(ActionEvent actionEvent) {
        objF.setText(null);
        valeurF.setText(null);
    }

    public void supp(ActionEvent actionEvent) {
        int index=table.getSelectionModel().getSelectedIndex();
        if (index>=0){
            int idDelete=table.getItems().get(index).getId();
            if (idDelete>0){
                try {
                    con = Connecter.getConnection();
                    pst = con.prepareStatement("DELETE FROM `goldtype` WHERE `id`=?");
                    pst.setInt(1,idDelete);
                    pst.execute();
                    pst.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                idDelete=0;
                addToTable2();
                fillCombo();

            }
        }
    }


    public void add(ActionEvent actionEvent) {
        int dejaExist=0;
        int size=0;
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT * FROM `goldtype` WHERE `goldType`=? OR `abGoldType`=?");
            pst.setString(1,valeurF.getText());
            pst.setString(2,objF.getText());
            rs=pst.executeQuery();
            while(rs.next()){
                size++;
            }
            if (size>0){
                dejaExist=1;
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (valeurF.getText().isEmpty()||objF.getText().isEmpty()){
            // messege d'erreur please fill the gaps
        }else if(dejaExist==1){
            // messege d'erreur deja existe dans la base de donné
        }else{
            try{
                con = Connecter.getConnection();
                pst = con.prepareStatement("INSERT INTO `goldtype`(`goldType`, `abGoldType`) VALUES (?,?)");
                pst.setString(1,valeurF.getText());
                pst.setString(2,objF.getText());
                pst.execute();
                pst.close();
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        objF.setText(null);
        valeurF.setText(null);
        addToTable2();
        fillCombo();


    }

    private void addToTable2() {
        goldTypesTable.clear();
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT * FROM `goldtype`");
            rs= pst.executeQuery();
            while(rs.next()){
                goldTypesTable.add(new GoldTypes(rs.getInt("id"),rs.getString("goldType"),rs.getString("abGoldType")));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void closeZoom(ActionEvent actionEvent) {
        paneZoom.setVisible(false);
    }

    public void zoomIn(MouseEvent mouseEvent) {
        paneZoom.setVisible(true);

    }

    public void printBarcode(ActionEvent actionEvent) {

    }

    public void search(KeyEvent keyEvent) {
        String key=search.getText().trim();
        if (key.isEmpty()){
            piecesTable.clear();
            addToTable();
        }else {
            piecesTable.clear();
            try {
                con = Connecter.getConnection();
                pst = con.prepareStatement("SELECT * FROM `productmag`,`goldtype` WHERE productmag.idGoldType=goldtype.id AND productmag.vendu=0 AND productmag.productCode LIKE '%"+key+"%'");
                rs=pst.executeQuery();
                while(rs.next()){
                    if(rs.getDouble("price")==0){

                    }else {
                        piecesTable.add(new Piece(rs.getInt("id"),rs.getInt("idGoldType"),rs.getFloat("weight"),rs.getString("productCode"),String.valueOf(rs.getInt("idKara")),rs.getString("goldType"),rs.getString("mojawharatName"),String.valueOf(rs.getFloat("mojawharatPrice")),String.valueOf(rs.getDate("date")),String.valueOf(rs.getDouble("price")+rs.getDouble("mojawharatPrice"))));

                    }
                }
                pst.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
