package code;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;

public class ControllerPiece {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    File file1=null;
    int idGoldType=0;

    @FXML
    private ChoiceBox<String> karaNumber;

    @FXML
    private ChoiceBox<String> goldType;

    @FXML
    private TextField pieceNumber;

    @FXML
    private TextField pieceWeight;

    @FXML
    private CheckBox withStone;

    @FXML
    private TextField stoneName;

    @FXML
    private TextField stonePrice;

    @FXML
    private ImageView imagePiece;

    @FXML
    void addPiece(ActionEvent event) {
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
                try{
                    if (file1!=null){
                        con = Connecter.getConnection();
                        pst = con.prepareStatement("INSERT INTO `productmag`(`productCode`, `productImage`, `weight`, `prodcutBarCode`, `withMojawharat`, `mojawharatName`, `mojawharatPrice`, `idKara`, `idGoldType`, `Date`) VALUES (?,?,?,?,?,?,?,?,?,?)");
                        pst.setString(1,pieceNumber.getText());
                        FileInputStream fis1=new FileInputStream(file1);
                        pst.setBinaryStream(2,fis1,file1.length());
                        pst.setString(3,pieceWeight.getText());
                        pst.setString(4,karaNumber.getValue());
                        pst.setString(5,"codebar");
                        pst.setInt(6,1);
                        pst.setString(7,stoneName.getText());
                        pst.setString(8,stonePrice.getText());
                        pst.setString(9,karaNumber.getValue());
                        pst.setInt(10,idGoldType);
                        rs=pst.executeQuery();
                    }
                }catch (SQLException | FileNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        }else{

        }

    }

    @FXML
    void checkWithStone(ActionEvent event) {

    }

    @FXML
    void deletePiece(ActionEvent event) {

    }

    @FXML
    void editPiece(ActionEvent event) {

    }

    @FXML
    void refresh(ActionEvent event) {

    }

    @FXML
    void showAddGoldType(ActionEvent event) {

    }

    @FXML
    void uploadImage(ActionEvent event) {

    }
}
