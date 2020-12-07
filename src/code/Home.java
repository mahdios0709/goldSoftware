/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 *
 * @author AbdElFateh
 */
public class Home implements Initializable {
/*
    @FXML
    private Label prime;

    @FXML
    private Label achat;

    @FXML
    private Label emprunt;

    @FXML
    private BarChart<?, ?> bar;

    @FXML
    private Label bdg;

    @FXML
    private Label creadit;
    @FXML
    private Label anneebdg;
    @FXML
    private PieChart pie;

    @FXML
    private Label date;
    @FXML
    private Label nbPrime;

    @FXML
    private Label nbAch;
    @FXML
    private Label paiement;
    @FXML
    private Label nbEmp;
     @FXML
    private Label remboursement;

    @FXML
    private Label nbrembors;
    @FXML
    private Label nbPayer;
    @FXML
    private Text txtTitulaire;

    @FXML
    private Text txtVacataire;

    @FXML
    private Text txtTotal;
    @FXML
    private Text txtTotalRec;
    @FXML
    private Label by;
    @FXML
    private ListView<situation_mensuel> ListeMois;
    private static final int idSo =Controller.getID_Societe();*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      //  loading();
        //loadList();

    }

    private void loading() {
       /* Double bdg1 = null,req=null, crd1 = null, prime1 = null, achat1 = null, emprunt1 = null, payer1 = null;
        try (Connection con = Connecter.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT  BUDGET,AnneeBudget,Date FROM servicesociaux.societe")) {
            if (rs.next()) {
                ///  bdg.setText(rs.getString("NOM_Soc"));
                bdg1 = rs.getDouble("BUDGET");
             //   crd1 = rs.getDouble("CREDIT");
                date.setText(rs.getString("Date"));
                anneebdg.setText(rs.getInt("AnneeBudget") + "");
                bdg.setText(bdg1+" DA");
              //  creadit.setText(crd1+"");

            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
         try (Connection con = Connecter.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM( SELECT IFNULL(COUNT(*),0), IFNULL(sum(MONTANT),0.0)FROM`achat` UNION ALL SELECT IFNULL(COUNT(*),0), IFNULL(sum(MONTANT),0.0) FROM`emprunt` UNION SELECT IFNULL(COUNT(*),0), IFNULL(sum(MONTANT),0.0) FROM`prime` UNION ALL SELECT IFNULL(COUNT(*),0), IFNULL(sum(MONTANT),0.0) FROM `payer` )tt LIMIT 4;")) {

            if (rs.next()) {
                nbAch.setText(rs.getInt(1)+"");
                achat1 = rs.getDouble(2);
                achat.setText(achat1+"");
                rs.next();
                 
                nbEmp.setText(rs.getInt(1)+"");
                emprunt1 = rs.getDouble(2);
                emprunt.setText(emprunt1+"");
                rs.next();
                
                nbPrime.setText(rs.getInt(1)+"");
                prime1 = rs.getDouble(2);
                prime.setText(prime1+"");
                rs.next();

                nbPayer.setText(rs.getInt(1)+"");
                payer1 = rs.getDouble(2);
                paiement.setText(payer1+"");
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (Connection con = Connecter.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT sum(Montant )FROM `situation_mensuel` WHERE id_societe=" + idSo)) {
            if (rs.next()) {
                req = rs.getDouble(1);
                txtTotalRec.setText(rs.getString(1)+" DA");
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
         try (Connection con = Connecter.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT IFNULL(`VALEUR`,0.0),IFNULL(`Nb`,0),IFNULL(count(`Nb`),0) FROM `rembou` WHERE `id_societe`=" + idSo)) {
            if (rs.next()) {
                remboursement.setText(rs.getString(1));
                nbrembors.setText(rs.getString(2));
                crd1=(bdg1+req+rs.getDouble(1))-(payer1+prime1+emprunt1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        creadit.setText(crd1+" DA");
        pie(bdg1, crd1);
        bar(achat1, prime1, emprunt1, payer1);
        try (Connection con = Connecter.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("Select t.ti FROM (SELECT count(*) as ti FROM employet WHERE TypePoste='titulaire' UNION ALL SELECT count(*) as ti FROM employet WHERE TypePoste='contractuel')t")) {
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
                txtTitulaire.setText(count + "");
                rs.next();
                count = count + rs.getInt(1);
                txtVacataire.setText(rs.getInt(1) + "");

            }

            txtTotal.setText(count + "");
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void pie(Double bdg, Double crd) {

        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Crédit", crd),
                        new PieChart.Data("Dépense", bdg - crd));
        pie.setData(pieChartData);
    }

    private void bar(Double  ach, Double pri, Double emp, Double payer1) {

        XYChart.Series achatXY = new XYChart.Series();
        XYChart.Series primeXY = new XYChart.Series();
        XYChart.Series empruntXY = new XYChart.Series();
        XYChart.Series payerXY = new XYChart.Series();
        achatXY.setName("Achat");
        primeXY.setName("Prime");
        empruntXY.setName("Emprunt");
        payerXY.setName("Paiement");
        achatXY.getData().add(new XYChart.Data<>("Achat",ach));
        primeXY.getData().add(new XYChart.Data<>("Prime", pri));
        empruntXY.getData().add(new XYChart.Data<>("Emprunt", emp));
        payerXY.getData().add(new XYChart.Data<>("Paiement", payer1));

        //  empruntXY.getData().add(new XYChart.Data<>("Emprunt", Double.parseDouble(emp)));
        bar.getData().addAll(achatXY, primeXY, empruntXY, payerXY);

    }

    private void loadList() {
        ObservableList<situation_mensuel> ObservableList = FXCollections.observableArrayList();
        try (Connection con = Connecter.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT DateMensuel,Statut,Montant FROM situation_mensuel ORDER BY DateMensuel DESC LIMIT 6");) {
            while (rs.next()) {
                ObservableList.add(new situation_mensuel(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
            ListeMois.setCellFactory(new Callback<ListView<situation_mensuel>, ListCell<situation_mensuel>>() {
                @Override
                public ListCell<situation_mensuel> call(ListView<situation_mensuel> ListeMois) {
                    return new CustomListCell();
                }
            });
            ListeMois.setItems(ObservableList);
          //  new Deduction().archivTotal(true);
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static class situation_mensuel {

        public String getDateM() {
            return dateM;
        }

        public String getStatut() {
            return statut;
        }

        public String getMontant() {
            return montant;
        }

        public void setDateM(String dateM) {
            this.dateM = dateM;
        }

        public void setStatut(String statut) {
            this.statut = statut;
        }

        public void setMontant(String montant) {
            this.montant = montant;
        }
        String dateM, statut, montant;

        public situation_mensuel(String dateM, String statut, String montant) {
            this.dateM = dateM;
            this.statut = statut;
            this.montant = montant;
        }

    }

    private class CustomListCell extends ListCell<situation_mensuel> {

        private HBox content;
        private final Text dateIn, montant;

        HBox hBox;

        public CustomListCell() {
            super();
            dateIn = new Text();
            //  statut = new Text();
            montant = new Text();
            hBox = new HBox(dateIn, montant);
            hBox.setSpacing(14);

        }

        @Override
        protected void updateItem(situation_mensuel item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && !empty) { // <== test for null item and empty parameter
                dateIn.setText(item.getDateM());
                // statut.setText(item.);
                montant.setText(item.getMontant() + " DA");
                switch (item.getStatut()) {
                    case "Complet":
                        content = new HBox(new FontAwesomeIconView(FontAwesomeIcon.HOURGLASS).setStyleClass("iconComplet"), hBox);
                        break;
                    case "Incomplet":
                        content = new HBox(new FontAwesomeIconView(FontAwesomeIcon.HOURGLASS_END).setStyleClass("iconInComplet"), hBox);
                        break;
                    default:

                        content = new HBox(new FontAwesomeIconView(FontAwesomeIcon.HOURGLASS_ALT).setStyleClass("iconNulle"), hBox);
                        break;
                }
                content.setSpacing(20);

                content.setStyle(" -fx-font-size:14px");
                content.setMinHeight(15);
                //  content.setAlignment(Pos.CENTER_LEFT);

                setGraphic(content);
            } else {
                setGraphic(null);
            }
        }*/
    }

}
