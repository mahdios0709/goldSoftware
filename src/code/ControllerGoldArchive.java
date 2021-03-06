package code;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ControllerGoldArchive implements Initializable {
    @FXML
    private ComboBox<String> caliberFiled;

    @FXML
    private ComboBox<String> typeFiled;

    @FXML
    private DatePicker dateFiled;

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
    public void restrictDatePicker(DatePicker datePicker, LocalDate minDate, LocalDate maxDate) {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(minDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }else if (item.isAfter(maxDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }
    private void loadData() {
        ObservableList<GoldData> data = FXCollections.observableArrayList();
        try (Connection con = Connecter.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT `idKara`, `goldType`, `date`, `price`  FROM `goldarchive` ORDER BY date")) {

            while (rs.next()) {
                data.add(new GoldData(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException ex) {
//            new DialogOption().DialogOptionERROR("حدث خطاء", "خطاء");
        }
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
    @FXML
    void refresh(ActionEvent event) {
        typeFiled.getSelectionModel().clearSelection();
        caliberFiled.getSelectionModel().clearSelection();
        priceFiled.setText("");
        dateFiled.getEditor().clear();
        goldTable.getItems().clear();
        loadData();

    }

    @FXML
    void search(ActionEvent event) {
        String idKaraTemp=caliberFiled.getSelectionModel().getSelectedItem(), typeTemp=typeFiled.getSelectionModel().getSelectedItem(),priceTemp=priceFiled.getText();

        List<String> args =new ArrayList<String>();

        if(idKaraTemp!=null){  args.add("(`idKara` LIKE '%"+idKaraTemp+"%')");}
        if(typeTemp!=null) {    args.add("(`goldType` LIKE '%"+typeTemp+"%')");}
        if(!priceTemp.isEmpty()){args.add("(`price` LIKE '%"+priceTemp+"%')");}
        if(dateFiled.getValue()!=null){args.add("(`date` LIKE '%"+dateFiled.getValue()+"%')");}
         if(!args.isEmpty()) {
             goldTable.getItems().clear();
             String query = args.get(0);
             for (int i = 1; i < args.size(); i++) {
                 query = query + " AND " + args.get(i);
             }
             ObservableList<GoldData> data = FXCollections.observableArrayList();
             try (Connection con = Connecter.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT `idKara`, `goldType`, `date`, `price`  FROM `goldarchive` WHERE   " + query + " ORDER BY date")) {

                 while (rs.next()) {
                     data.add(new GoldData(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                 }
             } catch (SQLException ex) {
                 new DialogOption().DialogOptionERROR("حدث خطاء", "خطاء");
             }
             goldTable.setItems(data);
             assert goldTable.getItems() == data;
         }else{
             new DialogOption().DialogOptionINFORMATION("لم تقم بادخال اي من الحقول", "لم تدخل بيانات");
         }
    }
    private void typeGoldCombox() {


        try (Connection con = Connecter.getConnection(); Statement st = con.createStatement()) {
            ResultSet rs;
            rs = st.executeQuery("SELECT  `goldType`FROM `goldtype`");

            while (rs.next()) {

                typeFiled.getItems().add(rs.getString(1));

            }

        } catch (SQLException ex) {
            System.err.println(""+ex);

        }


    }

}
