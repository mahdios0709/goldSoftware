package code;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class controllerConfig implements Initializable {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    File file1=null;


    @FXML
    private TextField nomMag;

    @FXML
    private ImageView imageLogo;

    @FXML
    private TextField adresseMag;

    @FXML
    private PasswordField password;

    @FXML
    private TextField nmbrPhone;

    @FXML
    private TextField user;

    @FXML
    private TextField nomChef;

    @FXML
    void insert(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        file1=fileChooser.showOpenDialog(null);
        if (file1!=null){
            try {
                BufferedImage bufferedImage= ImageIO.read(file1);
                Image image= SwingFXUtils.toFXImage(bufferedImage,null);
                imageLogo.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @FXML
    void save(ActionEvent event) {
        try {
            con = Connecter.getConnection();
            if (file1!=null){
                pst = con.prepareStatement("UPDATE `config` SET `userName`=?,`password`=?,`magazinName`=?,`bossName`=?,`magazinImage`=?,`phoneNumber`=?,`address`=? WHERE 1");
                pst.setString(1,user.getText());
                pst.setString(2,password.getText());
                pst.setString(3,nomMag.getText());
                pst.setString(4,nomChef.getText());
                FileInputStream fis1=new FileInputStream(file1);
                pst.setBinaryStream(5,fis1);
                pst.setString(6,nmbrPhone.getText());
                pst.setString(7,adresseMag.getText());
            }else{
                pst = con.prepareStatement("UPDATE `config` SET `userName`=?,`password`=SHA(?),`magazinName`=?,`bossName`=?,`phoneNumber`=?,`address`=? WHERE 1");
                pst.setString(1,user.getText());
                pst.setString(2,password.getText());
                pst.setString(3,nomMag.getText());
                pst.setString(4,nomChef.getText());
                pst.setString(5,nmbrPhone.getText());
                pst.setString(6,adresseMag.getText());
            }
            new DialogOption().DialogOptionINFORMATION("تم تعديل البيانات بنجاح","معلومات البرنامج");

            pst.execute();
            pst.close();
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
            new DialogOption().DialogOptionERROR("حدث خطأ يرجى التأكد من البيانات أو الإتصال بالمبرمج","خطاء");

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT * FROM `config` WHERE 1");
            rs=pst.executeQuery();
            while(rs.next()){
                Blob imageLogoBlob= rs.getBlob("magazinImage");
                user.setText(rs.getString("userName"));
                password.setText(rs.getString("password"));
                nomMag.setText(rs.getString("magazinName"));
                nomChef.setText(rs.getString("bossName"));
                if (imageLogoBlob!=null){
                    InputStream inputStream=imageLogoBlob.getBinaryStream();
                    Image image1=new Image(inputStream);
                    imageLogo.setImage(image1);

                }else{
                    Image imagelogo=new Image("img/logo.png");
                    imageLogo.setImage(imagelogo);
                }

                nmbrPhone.setText(rs.getString("phoneNumber"));
                adresseMag.setText(rs.getString("address"));
            }
            pst.execute();
            pst.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
