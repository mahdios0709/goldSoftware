package code;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class ControllerGoldDaily implements Initializable {



    @FXML
    private ComboBox<String> caliberFiled;

    @FXML
    private ComboBox<String> typleFiled;

    @FXML
    private TextField priceFiled;

    @FXML
    private TableView<GoldData> goldTable;

    @FXML
    private TableColumn<?, ?> caliber;

    @FXML
    private TableColumn<?, ?> type;

    @FXML
    private TableColumn<?, ?> price;

    @FXML
    private TableColumn<?, ?> dateGold;
    //private static int idGoldType;

    @FXML
    void insert(ActionEvent event) {
    String caliberTemp =  caliberFiled.getSelectionModel().getSelectedItem()+"";
    String  typeTemp =typleFiled.getSelectionModel().getSelectedItem()+"";
    String  priceTemp=  priceFiled.getText()+"";

    if (caliberTemp.isEmpty()||typeTemp.isEmpty()||priceTemp.isEmpty()) {
        new DialogOption().DialogOptionERROR("خطأ يرجى منكم ادخال كافة المعلومات", "تحذير");

    }

       try (Connection con = Connecter.getConnection(); Statement st = con.createStatement()) {
           ResultSet rs1 = st.executeQuery("select id FROM goldprices WHERE ((SELECT `goldType` FROM `goldtype` WHERE `goldType`='"+ typeTemp +"')='"+typeTemp+"' AND idKara="+caliberTemp+")");
                   if(rs1.next()){
                       st.executeUpdate("UPDATE `goldprices` SET   `date`=CURRENT_TIMESTAMP(), `price`="+priceTemp+" ");
                   }else {
                       st.executeUpdate("INSERT  IGNORE INTO `goldprices`( `idKara`, `idGoldType`, `date`, `price`) "
                               + "VALUES(" + caliberTemp + ",(SELECT `id` FROM `goldtype` WHERE `goldType`='" + typeTemp + "'),CURRENT_TIMESTAMP()," + priceTemp + ") ON DUPLICATE KEY UPDATE price=" + priceTemp + ";");
                   }
                   rs1.close();
           new DialogOption().DialogOptionINFORMATION("تم الاضافة بنجاح", "نجاح العملية");

           loadData();
        } catch (SQLException ex) {
            new DialogOption().DialogOptionERROR("حدث خطاء", "خطاء");
            System.err.println(""+ex);
        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> karaId= FXCollections.observableArrayList("24","22","18","14","10","9");
        caliberFiled.setItems(karaId);

        type.setCellValueFactory(new PropertyValueFactory<>("idGoldType"));
        caliber.setCellValueFactory(new PropertyValueFactory<>("idKara"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        dateGold.setCellValueFactory(new PropertyValueFactory<>("date"));


        loadData();
        typeGoldCombox();
    }

    private void loadData() {
        ObservableList<GoldData> data = FXCollections.observableArrayList();
        try (Connection con = Connecter.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT `idKara`, goldtype, `date`, `price` FROM `goldprices` INNER JOIN goldtype ON goldtype.id=goldprices.idGoldType ORDER BY idKara;")) {

            while (rs.next()) {
                data.add(new GoldData(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException ex) {
            new DialogOption().DialogOptionERROR("حدث خطاء", "خطاء");        }
        goldTable.setItems(data);
        assert goldTable.getItems() == data;


    }

    @FXML
    void isNmbr(KeyEvent event) {
        try {
           new BigDecimal(priceFiled.getText());
            priceFiled.setStyle(" -fx-border-color: #45CCB1");
            event.consume();
        }catch (NumberFormatException e){
            priceFiled.setStyle("-fx-border-color:#F66E84 ");
            new DialogOption().DialogOptionERROR("يقبل الارقام فقط ", "تححقق من الادخال");
        }
    }

    private void typeGoldCombox() {


        try (Connection con = Connecter.getConnection(); Statement st = con.createStatement()) {
            ResultSet rs;
            rs = st.executeQuery("SELECT  `goldType`FROM `goldtype`");

            while (rs.next()) {

                typleFiled.getItems().add(rs.getString(1));

            }

        } catch (SQLException ex) {
            System.err.println(""+ex);

        }


    }

}
