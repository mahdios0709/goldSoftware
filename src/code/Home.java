/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 *
 * @author AbdElFateh
 */
public class Home implements Initializable {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    Map<String, Integer> map = new HashMap<>();
    @FXML
    private BarChart<?, ?> barRealStat;
    @FXML
    private BarChart<?, ?> barCaliber;

    @FXML
    private ListView<Operation> dailyList;

    @FXML
    private Text pieceVenduPerMonth;

    @FXML
    private Label benefit;

    @FXML
    private Label benefitMonthly;

    @FXML
    private ComboBox<String> goldTypeCombo;
    @FXML
    private ComboBox<String>  caliberBox;

    @FXML
    private Text pieceNow;

    @FXML
    private Text pieceVendu;
    @FXML
    private Label txtTotalRec;

    @FXML
    private Label clientNumber;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> karaId= FXCollections.observableArrayList("24","22","18","14","10","9");
        caliberBox.getItems().addAll(karaId);
        loading();
        //loadList();
        fillCombo();


        //client number
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT COUNT(*) AS clientNumber FROM clients");
            rs= pst.executeQuery();
            while(rs.next()){
                clientNumber.setText(String.valueOf(rs.getInt("clientNumber")));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //piece Now
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT COUNT(*) AS pieceNow FROM `productmag` WHERE `vendu`=0");
            rs= pst.executeQuery();
            while(rs.next()){
                pieceNow.setText(String.valueOf(rs.getInt("pieceNow")));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //piece Vendu
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT COUNT(*) AS pieceVendu FROM `productmag` WHERE `vendu`=1");
            rs= pst.executeQuery();
            while(rs.next()){
                pieceVendu.setText(String.valueOf(rs.getInt("pieceVendu")));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //piece Vendu Per Month
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT COUNT(*) AS pieceVenduPerMonth FROM `productmag` WHERE `vendu`=1");
            rs= pst.executeQuery();
            while(rs.next()){
                pieceVenduPerMonth.setText(String.valueOf(rs.getInt("pieceVenduPerMonth")));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        txtTotalRec.setText(Integer.parseInt(pieceNow.getText())+Integer.parseInt(pieceVendu.getText())+"");


        //benefit Per day
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT SUM(price) AS benefit FROM `productsfact`,`factures` WHERE productsfact.idFacture=factures.id AND factures.dateFacture=CURRENT_DATE()");
            rs= pst.executeQuery();
            while(rs.next()){
                benefit.setText(String.valueOf(rs.getInt("benefit")));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }




        //benefit Per Month
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT SUM(price) AS benefitMonthly FROM `productsfact`,`factures` WHERE productsfact.idFacture=factures.id AND year(factures.dateFacture)=year(CURRENT_DATE()) AND month(factures.dateFacture)=month(CURRENT_DATE())");
            rs= pst.executeQuery();
            while(rs.next()){
                benefitMonthly.setText(String.valueOf(rs.getInt("benefitMonthly")));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        //  list of sold products today
     /*   dailyList.getItems().clear();
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT * FROM `productsfact`,`factures`,`productmag` WHERE productsfact.idProductMag=productmag.id AND productsfact.idFacture=factures.id AND factures.dateFacture=CURRENT_DATE()");
            rs= pst.executeQuery();
            while(rs.next()){
                dailyList.getItems().add("القطعة رقم : "+rs.getString("productCode"));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/



    }
    private void fillCombo() {

        goldTypeCombo.getItems().clear();
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT id,goldType FROM goldtype");
            rs= pst.executeQuery();
            while(rs.next()){
                goldTypeCombo.getItems().add(rs.getString(2));
                map.put(rs.getString(2), rs.getInt(1));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void bar() {
        XYChart.Series kara;
        int [] caliber={9,10,14,18,20,22};
        kara = new XYChart.Series();
        kara.setName("السعر بدلالة الزمن");
        barRealStat.getData().clear();
        String typeTemp=goldTypeCombo.getSelectionModel().getSelectedItem();
        XYChart.Series kara9 = new XYChart.Series();
        XYChart.Series kara10 = new XYChart.Series();
        XYChart.Series kara14 = new XYChart.Series();
        XYChart.Series kara18 = new XYChart.Series();
        XYChart.Series kara20 = new XYChart.Series();
        XYChart.Series kara22 = new XYChart.Series();
        kara9.setName("عيار 9");
        kara10.setName("عيار 10");
        kara14.setName("عيار 14");
        kara18.setName("عيار 18");
        kara20.setName("عيار 20");
        kara22.setName("عيار 22");
        try (Connection con = Connecter.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT  `idKara`, `price` FROM `goldprices` WHERE  idGoldType='"+map.get(typeTemp)+"'")) {
            while (rs.next()){
                switch (rs.getInt(1)){
                    case 9: kara9.getData().add(new XYChart.Data<>("عيار 9",rs.getBigDecimal(2)));break;
                    case 10: kara10.getData().add(new XYChart.Data<>("عيار 10",rs.getBigDecimal(2)));break;
                    case 14:    kara14.getData().add(new XYChart.Data<>("عيار 14",rs.getBigDecimal(2)));break;
                    case  18: kara18.getData().add(new XYChart.Data<>("عيار 18",rs.getBigDecimal(2)));break;
                    case  22:   kara20.getData().add(new XYChart.Data<>("عيار 22",rs.getBigDecimal(2)));break;
                    case  24:  kara22.getData().add(new XYChart.Data<>("عيار 24",rs.getBigDecimal(2)));break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }

        barRealStat.getData().addAll(kara9, kara10, kara18, kara20,kara22, kara14);

    }
    private void fitToBar(){
        barCaliber.getData().clear();
        String typeTemp=goldTypeCombo.getSelectionModel().getSelectedItem();
        String caliberTemp =caliberBox.getSelectionModel().getSelectedItem();
        if (typeTemp==null||caliberTemp==null){
            new DialogOption().DialogOptionWARNING("اختر من العنصرين", "تحديث");

        }else {

            XYChart.Series kara = new XYChart.Series();


            kara.setName("عيار بدلالة الزمن");
            try (Connection con = Connecter.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT  `date`, `price` FROM `goldarchive` WHERE  `goldType`='" + typeTemp + "' AND `idKara`=" + caliberTemp)) {
                while (rs.next()) {

                    kara.getData().add(new XYChart.Data<>(rs.getString(1), rs.getDouble(2)));
                }
                barCaliber.getData().add(kara);

            } catch (SQLException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }

    @FXML
    void fit(ActionEvent event) {
        bar();
    }

    @FXML
    void typeClick(ActionEvent event) {
        bar();
        fitToBar();
    }
    private void loading() {

            ObservableList<Operation> ObservableList = FXCollections.observableArrayList();
                ObservableList.add(new Operation("N°facture","N°piece"));

        try (Connection con = Connecter.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT idFacture,productCode FROM `productsfact`,`factures`,`productmag` WHERE productsfact.idProductMag=productmag.id AND productsfact.idFacture=factures.id AND factures.dateFacture=CURRENT_DATE()");) {
                while (rs.next()) {
                    ObservableList.add(new Operation(rs.getString(1), rs.getString(2)));
                }
                dailyList.setCellFactory(new Callback<ListView<Operation>, ListCell<Operation>>() {
                    @Override
                    public ListCell<Operation> call(ListView<Operation> ListeMois) {
                        return new CustomListCell();
                    }
                });
                dailyList.setItems(ObservableList);
                //  new Deduction().archivTotal(true);
            } catch (SQLException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
      class Operation {



        public String getm1() {
            return m1;
        }

        public String getm2() {
            return m2;
        }



        public void setm1(String m1) {
            this.m1 = m1;
        }

        public void setm2(String m2) {
            this.m2 = m2;
        }
        String m1, m2;

        public Operation(String m1, String m2) {
            this.m1 = m1;
            this.m2 = m2;

        }

    }

    class CustomListCell extends ListCell<Operation> {

        private HBox content;
        private final Text dateIn, montant;

        HBox hBox;

        public CustomListCell() {
            super();
            dateIn = new Text();
            //  statut = new Text();
            montant = new Text();
            hBox = new HBox(dateIn, montant);
            hBox.setSpacing(20);

        }

        @Override
        protected void updateItem(Operation item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) { // <== test for null item and empty parameter

                // statut.setText(item.);
                montant.setText(item.getm2());
                dateIn.setText(item.getm1());
                content = new HBox(new FontAwesomeIconView(FontAwesomeIcon.SHOPPING_CART).setStyleClass("iconComplet"), hBox);

                content.setSpacing(20);

                content.setStyle(" -fx-font-size:14px");
                content.setMinHeight(15);
                //  content.setAlignment(Pos.CENTER_LEFT);

                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }
    }



}
