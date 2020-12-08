package code;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.event.*;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ControllerPiece implements Initializable {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    File file1=null;
    int idGoldType=0;
    Date nowDate;

    ObservableList<String> karaId= FXCollections.observableArrayList("24","22","18","14","10","9");
    ObservableList piecesTable=FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> karaNumber;

    @FXML
    private ComboBox<String> goldType;

    @FXML
    private TextField pieceNumber;

    @FXML
    private TextField pieceWeight;

    @FXML
    private CheckBox withStone;

    @FXML
    private TextField stoneName;

    @FXML
    private AnchorPane stonePane;

    @FXML
    private AnchorPane paneAddGoldType;

    @FXML
    private TextField stonePrice;

    @FXML
    private ImageView imagePiece;

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
    private TableColumn<Piece, String> pieceDateTable;

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
            if (karaNumber.getSelectionModel().isEmpty()||goldType.getSelectionModel().isEmpty()||pieceNumber.getText().isEmpty()||pieceWeight.getText().isEmpty()||stoneName.getText().isEmpty()||stonePrice.getText().isEmpty()){
                // messege d'erreur please fill the gaps
            }else if(dejaExist==1){
                // messege d'erreur deja existe dans la base de donné
            }else{
                Calendar now=Calendar.getInstance();
                nowDate= Date.valueOf(now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+2)+"-"+now.get(Calendar.DATE));
                try{
                    if (file1!=null){
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("INSERT INTO `productmag`(`productCode`, `productImage`, `weight`, `prodcutBarCode`, `withMojawharat`, `mojawharatName`, `mojawharatPrice`, `idKara`, `idGoldType`, `Date`) VALUES (?,?,?,?,?,?,?,?,?,?)");
                        pst.setString(1,pieceNumber.getText());
                        FileInputStream fis1=new FileInputStream(file1);
                        pst.setBinaryStream(2,fis1,file1.length());
                        pst.setString(3,pieceWeight.getText());
                        pst.setString(4,"codebar");
                        pst.setInt(5,1);
                        pst.setString(6,stoneName.getText());
                        pst.setString(7,stonePrice.getText());
                        pst.setString(8,karaNumber.getValue());
                        pst.setInt(9,idGoldType);
                        pst.setDate(10,nowDate);
                        rs=pst.executeQuery();
                    }else{
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("INSERT INTO `productmag`(`productCode`, `weight`, `prodcutBarCode`, `withMojawharat`, `mojawharatName`, `mojawharatPrice`, `idKara`, `idGoldType`, `Date`) VALUES (?,?,?,?,?,?,?,?,?)");
                        pst.setString(1,pieceNumber.getText());
                        pst.setString(2,pieceWeight.getText());
                        pst.setString(3,"codebar");
                        pst.setInt(4,1);
                        pst.setString(5,stoneName.getText());
                        pst.setString(6,stonePrice.getText());
                        pst.setString(7,karaNumber.getValue());
                        pst.setInt(8,idGoldType);
                        pst.setDate(9,nowDate);
                        rs=pst.executeQuery();
                    }
                }catch (SQLException | FileNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        }else{
            if (karaNumber.getSelectionModel().isEmpty()||goldType.getSelectionModel().isEmpty()||pieceNumber.getText().isEmpty()||pieceWeight.getText().isEmpty()){
                // messege d'erreur please fill the gaps
            }else if(dejaExist==1){
                // messege d'erreur deja existe dans la base de donné
            }else{
                Calendar now=Calendar.getInstance();
                nowDate= Date.valueOf(now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+2)+"-"+now.get(Calendar.DATE));
                try{
                    if (file1!=null){
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("INSERT INTO `productmag`(`productCode`, `productImage`, `weight`, `prodcutBarCode`, `withMojawharat`, `idKara`, `idGoldType`, `Date`) VALUES (?,?,?,?,?,?,?,?,?,?)");
                        pst.setString(1,pieceNumber.getText());
                        FileInputStream fis1=new FileInputStream(file1);
                        pst.setBinaryStream(2,fis1,file1.length());
                        pst.setString(3,pieceWeight.getText());
                        pst.setString(4,"codebar");
                        pst.setInt(5,0);
                        pst.setString(6,karaNumber.getValue());
                        pst.setInt(7,idGoldType);
                        pst.setDate(8,nowDate);
                        rs=pst.executeQuery();
                    }else{
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("INSERT INTO `productmag`(`productCode`, `weight`, `prodcutBarCode`, `withMojawharat`, `idKara`, `idGoldType`, `Date`) VALUES (?,?,?,?,?,?,?,?,?)");
                        pst.setString(1,pieceNumber.getText());
                        pst.setString(2,pieceWeight.getText());
                        pst.setString(3,"codebar");
                        pst.setInt(4,0);
                        pst.setString(5,karaNumber.getValue());
                        pst.setInt(6,idGoldType);
                        pst.setDate(7,nowDate);
                        rs=pst.executeQuery();
                    }
                }catch (SQLException | FileNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

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
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
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
                try {
                    con = Connecter.getConnection();
                    pst = con.prepareStatement("SELECT * FROM `productmag`,`goldtype` WHERE productmag.idGoldType=goldtype.id AND productmag.id=?");
                    pst.setInt(1,idEdit);
                    rs=pst.executeQuery();
                    while(rs.next()){
                        if (rs.getInt("withMojawharat")==1){
                            withStone.setSelected(true);
                            pieceNumber.setText(rs.getString("productCode"));
                            stoneName.setText(rs.getString("mojawharatName"));
                            karaNumber.setValue(String.valueOf(rs.getInt("idKara")));
                            stonePrice.setText(String.valueOf(rs.getFloat("mojawharatPrice")));
                            pieceWeight.setText(String.valueOf(rs.getFloat("weight")));
                            Blob imagePieceBlob=(Blob) rs.getBlob("productImage");
                            if (imagePieceBlob!=null){
                                InputStream inputStream=imagePieceBlob.getBinaryStream();
                                Image image1=new Image(inputStream);
                                imagePiece.setImage(image1);

                            }else{
                                Image imagelogo=new Image("img/logo.png");
                                imagePiece.setImage(imagelogo);
                            }
                        }else{
                            withStone.setSelected(false);
                            pieceNumber.setText(rs.getString("productCode"));
                            stoneName.setText(null);
                            karaNumber.setValue(String.valueOf(rs.getInt("idKara")));
                            stonePrice.setText(null);
                            pieceWeight.setText(String.valueOf(rs.getFloat("weight")));
                            Blob imagePieceBlob=(Blob) rs.getBlob("productImage");
                            if (imagePieceBlob!=null){
                                InputStream inputStream=imagePieceBlob.getBinaryStream();
                                Image image1=new Image(inputStream);
                                imagePiece.setImage(image1);

                            }else{
                                Image imagelogo=new Image("img/logo.png");
                                imagePiece.setImage(imagelogo);
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
            karaNumber.getItems().clear();
            karaNumber.setItems(karaId);
            goldType.getItems().clear();
            goldType.setItems(karaId);
            withStone.setSelected(false);
            stoneName.clear();
            stonePrice.clear();
            file1=null;
            Image imagelogo=new Image("img/logo.png");
            imagePiece.setImage(imagelogo);
        }

    }

    @FXML
    void editPiece(ActionEvent event) {
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
            if (karaNumber.getSelectionModel().isEmpty()||goldType.getSelectionModel().isEmpty()||pieceNumber.getText().isEmpty()||pieceWeight.getText().isEmpty()||stoneName.getText().isEmpty()||stonePrice.getText().isEmpty()){
                // messege d'erreur please fill the gaps
            }else if(dejaExist==1){
                // messege d'erreur deja existe dans la base de donné
            }else{
                int index = pieceTableView.getSelectionModel().getSelectedIndex();
                int idEdit=pieceTableView.getItems().get(index).getId();

                Calendar now=Calendar.getInstance();
                nowDate= Date.valueOf(now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+2)+"-"+now.get(Calendar.DATE));
                try{
                    if (file1!=null){
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("UPDATE `productmag` SET `productCode`=?,`productImage`=?,`weight`=?,`prodcutBarCode`=?,`withMojawharat`=?,`mojawharatName`=?,`mojawharatPrice`=?,`idKara`=?,`idGoldType`=?,`date`=? WHERE `id`=?");
                        pst.setString(1,pieceNumber.getText());
                        FileInputStream fis1=new FileInputStream(file1);
                        pst.setBinaryStream(2,fis1,file1.length());
                        pst.setString(3,pieceWeight.getText());
                        pst.setString(4,"codebar");
                        pst.setInt(5,1);
                        pst.setString(6,stoneName.getText());
                        pst.setString(7,stonePrice.getText());
                        pst.setString(8,karaNumber.getValue());
                        pst.setInt(9,idGoldType);
                        pst.setDate(10,nowDate);
                        pst.setInt(11,idEdit);
                        rs=pst.executeQuery();
                        pst.close();

                    }else{
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("UPDATE `productmag` SET `productCode`=?,`weight`=?,`prodcutBarCode`=?,`withMojawharat`=?,`mojawharatName`=?,`mojawharatPrice`=?,`idKara`=?,`idGoldType`=?,`date`=? WHERE `id`=?");
                        pst.setString(1,pieceNumber.getText());
                        pst.setString(2,pieceWeight.getText());
                        pst.setString(3,"codebar");
                        pst.setInt(4,1);
                        pst.setString(5,stoneName.getText());
                        pst.setString(6,stonePrice.getText());
                        pst.setString(7,karaNumber.getValue());
                        pst.setInt(8,idGoldType);
                        pst.setDate(9,nowDate);
                        pst.setInt(10,idEdit);
                        rs=pst.executeQuery();
                        pst.close();

                    }
                }catch (SQLException | FileNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        }else{
            if (karaNumber.getSelectionModel().isEmpty()||goldType.getSelectionModel().isEmpty()||pieceNumber.getText().isEmpty()||pieceWeight.getText().isEmpty()){
                // messege d'erreur please fill the gaps
            }else if(dejaExist==1){
                // messege d'erreur deja existe dans la base de donné
            }else{
                int index = pieceTableView.getSelectionModel().getSelectedIndex();
                int idEdit=pieceTableView.getItems().get(index).getId();
                Calendar now=Calendar.getInstance();
                nowDate= Date.valueOf(now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+2)+"-"+now.get(Calendar.DATE));
                try{
                    if (file1!=null){
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("UPDATE `productmag` SET `productCode`=?,`productImage`=?,`weight`=?,`prodcutBarCode`=?,`withMojawharat`=?,`idKara`=?,`idGoldType`=?,`date`=? WHERE `id`=?");
                        pst.setString(1,pieceNumber.getText());
                        FileInputStream fis1=new FileInputStream(file1);
                        pst.setBinaryStream(2,fis1,file1.length());
                        pst.setString(3,pieceWeight.getText());
                        pst.setString(4,"codebar");
                        pst.setInt(5,0);
                        pst.setString(6,karaNumber.getValue());
                        pst.setInt(7,idGoldType);
                        pst.setDate(8,nowDate);
                        pst.setInt(9,idEdit);
                        rs=pst.executeQuery();
                        pst.close();

                    }else{
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("UPDATE `productmag` SET `productCode`=?,`weight`=?,`prodcutBarCode`=?,`withMojawharat`=?,`idKara`=?,`idGoldType`=?,`date`=? WHERE `id`=?)");
                        pst.setString(1,pieceNumber.getText());
                        pst.setString(2,pieceWeight.getText());
                        pst.setString(3,"codebar");
                        pst.setInt(4,0);
                        pst.setString(5,karaNumber.getValue());
                        pst.setInt(6,idGoldType);
                        pst.setDate(7,nowDate);
                        pst.setInt(8,idEdit);
                        rs=pst.executeQuery();
                        pst.close();
                    }
                }catch (SQLException | FileNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @FXML
    void refresh(ActionEvent event) {
        pieceNumber.clear();
        pieceWeight.clear();
        karaNumber.getItems().clear();
        karaNumber.setItems(karaId);
        goldType.getItems().clear();
        goldType.setItems(karaId);
        withStone.setSelected(false);
        stoneName.clear();
        stonePrice.clear();
        file1=null;
        Image imagelogo=new Image("img/logo.png");
        imagePiece.setImage(imagelogo);

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        karaNumber.setItems(karaId);
        Image imagelogo=new Image("img/logo.png");
        imagePiece.setImage(imagelogo);
        addToTable();
        pieceNumberTable.setCellValueFactory(new PropertyValueFactory<>("pieceNumber"));
        karaNumberTable.setCellValueFactory(new PropertyValueFactory<>("karaNumber"));
        goldTypeTable.setCellValueFactory(new PropertyValueFactory<>("goldType"));
        pieceWeightTable.setCellValueFactory(new PropertyValueFactory<>("pieceWeight"));
        stoneNameTable.setCellValueFactory(new PropertyValueFactory<>("stoneName"));
        stonePriceTable.setCellValueFactory(new PropertyValueFactory<>("stonePrice"));
        pieceDateTable.setCellValueFactory(new PropertyValueFactory<>("date"));
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

    public void vid(ActionEvent actionEvent) {
    }

    public void supp(ActionEvent actionEvent) {
    }

    public void add(ActionEvent actionEvent) {
    }

    public void selected(MouseEvent mouseEvent) {
    }
}
