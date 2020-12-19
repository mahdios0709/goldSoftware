package code;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ControllerPurchase implements Initializable {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;
    File file1=null;


    ObservableList piecesTable= FXCollections.observableArrayList();
    ObservableList piecesTableFacil= FXCollections.observableArrayList();
    ObservableList clientsTable= FXCollections.observableArrayList();
    ObservableList factureProductsTable= FXCollections.observableArrayList();
    ObservableList historicalPricesTable=FXCollections.observableArrayList();


    @FXML
    private AnchorPane paneZoom;

    @FXML
    private ImageView imageZoom;

    @FXML
    private RadioButton fullpaymente;

    @FXML
    private Button confirmbutton;

    @FXML
    private Button printbutton;

    @FXML
    private Button closebutton;

    @FXML
    private Button deletebutton;

    @FXML
    private ImageView imageCard;

    @FXML
    private Label facturePrice;

    @FXML
    private Pane firstPricePane;

    @FXML
    private Label imageUploaded;

    @FXML
    private RadioButton parFacil;

    @FXML
    private TextField search;

    @FXML
    private TextField search2;

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
    private TextField priceAddFacil;

    @FXML
    private TextField firstPrice;

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
    private TableView<Facture> factureFacilTableView;

    @FXML
    private TableColumn<Facture, String> factureNumberFacil;

    @FXML
    private TableColumn<Facture, String> clientNameFacil;

    @FXML
    private TableColumn<Facture, String> clientPhoneFacil;

    @FXML
    private TableColumn<Facture, String> fullPriceFacil;

    @FXML
    private TableColumn<Facture, String> restPriceFacil;

    @FXML
    private TableColumn<Facture, String> factureDateFacil;

    @FXML
    private TableView<HistoricalPayment> historicalTableView;

    @FXML
    private TableColumn<HistoricalPayment, String> priceHistoricalFacil;

    @FXML
    private TableColumn<HistoricalPayment, String> dateHistoricalFacil;


    @FXML
    void confirmFacture() {

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
            new DialogOption().DialogOptionERROR("يرجى إظافة قطع لقائمة المشتريات","خطاء");

        }else if (clientName.getText().isEmpty()||phoneNumber.getText().isEmpty()){
            new DialogOption().DialogOptionERROR("ادخل جميع الحقول","خطاء");

        }else if (parFacil.isSelected() && firstPrice.getText().isEmpty()){
            new DialogOption().DialogOptionERROR("يرجى إدخال السعر الأولي","خطاء");

        }else if (dejaExist==1){
            try{
                con = Connecter.getConnection();

                if (parFacil.isSelected()){
                    pst = con.prepareStatement("INSERT INTO `factures`(`idClient`, `dateFacture`, `paymentType`, `priceF`, `priceR`) VALUES (?,CURRENT_TIMESTAMP(),?,?,?)");
                    pst.setInt(1,id);
                    pst.setString(2, "الدفع بالتقسيط");
                    pst.setDouble(3, Double.parseDouble(facturePrice.getText()));
                    pst.setDouble(4, Double.parseDouble(facturePrice.getText())-Double.parseDouble(firstPrice.getText()));
                    pst.execute();
                    pst.close();
                    int idFac=0;
                    con = Connecter.getConnection();
                    pst = con.prepareStatement("SELECT `id` FROM `factures` ORDER BY `id` DESC LIMIT 1");
                    rs=pst.executeQuery();

                    while(rs.next()){
                        idFac=rs.getInt("id");
                    }
                    if (idFac==0){
                        idFac=1;
                    }
                    pst.close();

                    con = Connecter.getConnection();
                    pst = con.prepareStatement("INSERT INTO `paymenthistorical`(`idFacture`, `price`, `date`) VALUES (?,?,CURRENT_TIMESTAMP())");
                    pst.setInt(1,idFac);
                    pst.setDouble(2, Double.parseDouble(firstPrice.getText()));
                    pst.execute();
                    pst.close();
                }else{
                    pst = con.prepareStatement("INSERT INTO `factures`(`idClient`, `dateFacture`, `paymentType`, `priceF`, `priceR`) VALUES (?,CURRENT_TIMESTAMP(),?,?,?)");
                    pst.setInt(1,id);
                    pst.setString(2, "الدفع كامل");
                    pst.setDouble(3, Double.parseDouble(facturePrice.getText()));
                    pst.setDouble(4, 0);
                    pst.execute();
                    pst.close();
                }
                new DialogOption().DialogOptionINFORMATION("تم إظافة بنجاح","نجاح العملية");

            }catch (SQLException throwables) {
                throwables.printStackTrace();
                new DialogOption().DialogOptionERROR("حدث خطأ أثناء الإظافة","خطاء");

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
                try{
                    con = Connecter.getConnection();
                    pst = con.prepareStatement("UPDATE `productmag` SET `vendu`=? WHERE `id`=?");
                    pst.setInt(1,1);
                    pst.setInt(2,purchaseTableView.getItems().get(i).getId());
                    pst.execute();
                    pst.close();
                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }else if (dejaExist==0){
            try{
                if (file1!=null){
                    con = Connecter.getConnection();
                    pst = con.prepareStatement("INSERT INTO `clients`(`clientName`, `clientNumber`, `clientCard`) VALUES (?,?,?)");
                    pst.setString(1,clientName.getText());
                    pst.setString(2,phoneNumber.getText());
                    FileInputStream fis1=new FileInputStream(file1);
                    pst.setBinaryStream(3,fis1,file1.length());
                }else{
                    con = Connecter.getConnection();
                    pst = con.prepareStatement("INSERT INTO `clients`(`clientName`, `clientNumber`) VALUES (?,?)");
                    pst.setString(1,clientName.getText());
                    pst.setString(2,phoneNumber.getText());

                }

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

            try{
                con = Connecter.getConnection();

                if (parFacil.isSelected()){
                    pst = con.prepareStatement("INSERT INTO `factures`(`idClient`, `dateFacture`, `paymentType`, `priceF`, `priceR`) VALUES (?,CURRENT_TIMESTAMP(),?,?,?)");
                    pst.setInt(1,idClient);
                    pst.setString(2, "الدفع بالتقسيط");
                    pst.setDouble(3, Double.parseDouble(facturePrice.getText()));
                    pst.setDouble(4, Double.parseDouble(facturePrice.getText())-Double.parseDouble(firstPrice.getText()));
                    pst.execute();
                    pst.close();
                    int idFac=0;
                    con = Connecter.getConnection();
                    pst = con.prepareStatement("SELECT `id` FROM `factures` ORDER BY `id` DESC LIMIT 1");
                    rs=pst.executeQuery();

                    while(rs.next()){
                        idFac=rs.getInt("id");
                    }
                    if (idFac==0){
                        idFac=1;
                    }
                    pst.close();

                    con = Connecter.getConnection();
                    pst = con.prepareStatement("INSERT INTO `paymenthistorical`(`idFacture`, `price`, `date`) VALUES (?,?,CURRENT_TIMESTAMP())");
                    pst.setInt(1,idFac);
                    pst.setDouble(2, Double.parseDouble(firstPrice.getText()));
                    pst.execute();
                    pst.close();
                }else{
                    pst = con.prepareStatement("INSERT INTO `factures`(`idClient`, `dateFacture`, `paymentType`, `priceF`, `priceR`) VALUES (?,CURRENT_TIMESTAMP(),?,?,?)");
                    pst.setInt(1,idClient);
                    pst.setString(2, "الدفع كامل");
                    pst.setDouble(3, Double.parseDouble(facturePrice.getText()));
                    pst.setDouble(4, 0);
                    pst.execute();
                    pst.close();
                }
                new DialogOption().DialogOptionINFORMATION("تم إظافة بنجاح","نجاح العملية");

            }catch (SQLException throwables) {
                throwables.printStackTrace();
                new DialogOption().DialogOptionERROR("حدث خطأ أثناء الإظافة","خطاء");

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
                try{
                    con = Connecter.getConnection();
                    pst = con.prepareStatement("UPDATE `productmag` SET `vendu`=? WHERE `id`=?");
                    pst.setInt(1,1);
                    pst.setInt(2,purchaseTableView.getItems().get(i).getId());
                    pst.execute();
                    pst.close();
                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }

        }
        addToTableClient();
        addToTable();
        factureProductsTable.clear();
        clientName.clear();
        phoneNumber.clear();
        facturePrice.setText("0.00");
    }



    @FXML
    void clientSelected(MouseEvent event) {
        int index = clientsTableView.getSelectionModel().getSelectedIndex();
        if (index>=0){
            clientName.setText(clientsTableView.getItems().get(index).getClientName());
            phoneNumber.setText(clientsTableView.getItems().get(index).getClientPhoneNumber());
        }

    }


    @FXML
    void addPieceToPurchase(ActionEvent event) {
        int dejaExist=0;
        int index=pieceTableView.getSelectionModel().getSelectedIndex();
        if (index>=0){
            for (int i=0;i<purchaseTableView.getItems().size();i++){
                if (purchaseTableView.getItems().get(i).getPieceNumber()==pieceTableView.getItems().get(index).getPieceNumber()){
                    dejaExist=1;
                }
            }
            if (dejaExist==0){
                purchaseTableView.getItems().add(new Purchase(pieceTableView.getItems().get(index).getId(),pieceTableView.getItems().get(index).getPieceNumber(),pieceTableView.getItems().get(index).getGoldType(),pieceTableView.getItems().get(index).getKaraNumber(),String.valueOf(pieceTableView.getItems().get(index).getPieceWeight()),Double.valueOf(pieceTableView.getItems().get(index).getStonePrice())));
            }else{
                new DialogOption().DialogOptionERROR("القطعة موجودة من قبل في قائمة المشتريات","خطاء");
            }

        }
        culculerPrixTotal();
    }


    @FXML
    void addPieceToPurchase2(KeyEvent event) {
        if (event.getText().contains("+")){
            int dejaExist=0;
            int index=pieceTableView.getSelectionModel().getSelectedIndex();
            if (index>=0){
                for (int i=0;i<purchaseTableView.getItems().size();i++){
                    if (purchaseTableView.getItems().get(i).getPieceNumber()==pieceTableView.getItems().get(index).getPieceNumber()){
                        dejaExist=1;
                    }
                }
                if (dejaExist==0){
                    purchaseTableView.getItems().add(new Purchase(pieceTableView.getItems().get(index).getId(),pieceTableView.getItems().get(index).getPieceNumber(),pieceTableView.getItems().get(index).getGoldType(),pieceTableView.getItems().get(index).getKaraNumber(),String.valueOf(pieceTableView.getItems().get(index).getPieceWeight()),Double.valueOf(pieceTableView.getItems().get(index).getStonePrice())));
                }else{
                    new DialogOption().DialogOptionERROR("القطعة موجودة من قبل في قائمة المشتريات","خطاء");
                }

            }
            culculerPrixTotal();
        }
    }


    @FXML
    void addPieceToPurchase3(MouseEvent event) {
        if (event.getClickCount()==2){
            int dejaExist=0;
            int index=pieceTableView.getSelectionModel().getSelectedIndex();
            if (index>=0){
                for (int i=0;i<purchaseTableView.getItems().size();i++){
                    if (purchaseTableView.getItems().get(i).getPieceNumber()==pieceTableView.getItems().get(index).getPieceNumber()){
                        dejaExist=1;
                    }
                }
                if (dejaExist==0){
                    purchaseTableView.getItems().add(new Purchase(pieceTableView.getItems().get(index).getId(),pieceTableView.getItems().get(index).getPieceNumber(),pieceTableView.getItems().get(index).getGoldType(),pieceTableView.getItems().get(index).getKaraNumber(),String.valueOf(pieceTableView.getItems().get(index).getPieceWeight()),Double.valueOf(pieceTableView.getItems().get(index).getStonePrice())));
                }else{
                    new DialogOption().DialogOptionERROR("القطعة موجودة من قبل في قائمة المشتريات","خطاء");
                }

            }
            culculerPrixTotal();
        }
    }

    private void culculerPrixTotal() {
        double prixTotal=0;
        if (purchaseTableView.getItems().size()>0){
            for (int k=0;k<purchaseTableView.getItems().size();k++){
                prixTotal=prixTotal+purchaseTableView.getItems().get(k).getPrice();
            }
            facturePrice.setText(String.valueOf(prixTotal));

        }else{
            facturePrice.setText("0.00");
        }
    }

    @FXML
    void deleteAllProducts(ActionEvent event) {
        factureProductsTable.clear();
        clientName.clear();
        phoneNumber.clear();
        facturePrice.setText("0.00");
    }

    @FXML
    void deleteOneProduct(ActionEvent event) {
        int index=purchaseTableView.getSelectionModel().getSelectedIndex();
        if (index>=0){
            purchaseTableView.getItems().remove(index);
        }
        culculerPrixTotal();
    }

    @FXML
    void deleteOneProduct2(KeyEvent event) {
        if (event.getText().contains("-")){
            int index=purchaseTableView.getSelectionModel().getSelectedIndex();
            if (index>=0){
                purchaseTableView.getItems().remove(index);
            }
            culculerPrixTotal();
        }
    }

    @FXML
    void deleteOneProduct3(MouseEvent event) {
        if (event.getClickCount()==2){
            int index=purchaseTableView.getSelectionModel().getSelectedIndex();
            if (index>=0){
                purchaseTableView.getItems().remove(index);
            }
            culculerPrixTotal();
        }
    }

    @FXML
    void loadImage(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        file1=fileChooser.showOpenDialog(null);
        if (file1!=null){
            imageUploaded.setText("تم رفع الصورة بنجاح");
            imageUploaded.setStyle(" -fx-text-fill: #00ff44");
        }else{
            imageUploaded.setText("حدث خطأ أثناء رفع الصورة");
            imageUploaded.setStyle(" -fx-text-fill: #ff0000");

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


        addToTableFacil();
        factureNumberFacil.setCellValueFactory(new PropertyValueFactory<>("idFacture"));
        clientNameFacil.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        clientPhoneFacil.setCellValueFactory(new PropertyValueFactory<>("clientNumber"));
        fullPriceFacil.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));
        restPriceFacil.setCellValueFactory(new PropertyValueFactory<>("prixRest"));
        factureDateFacil.setCellValueFactory(new PropertyValueFactory<>("dateFacture"));
        factureFacilTableView.setItems(piecesTableFacil);



        dateHistoricalFacil.setCellValueFactory(new PropertyValueFactory<>("date"));
        priceHistoricalFacil.setCellValueFactory(new PropertyValueFactory<>("price"));
        historicalTableView.setItems(historicalPricesTable);
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


    private void addToTableFacil() {
        piecesTableFacil.clear();
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT * FROM `factures`,`clients` WHERE factures.idClient=clients.id AND factures.paymentType='الدفع بالتقسيط'");
            rs= pst.executeQuery();
            while(rs.next()){
                piecesTableFacil.add(new Facture(rs.getInt("id"),rs.getInt("idClient"),rs.getString("clientName"),rs.getString("clientNumber"),String.valueOf(rs.getDate("dateFacture")),rs.getString("priceF"),rs.getString("priceR"),rs.getString("paymentType")));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    @FXML
    private void addToTableHistoric() {
        if (factureFacilTableView.getSelectionModel().getSelectedIndex()>=0){
            historicalPricesTable.clear();
            factureProductsTable.clear();
            try {
                con = Connecter.getConnection();
                pst = con.prepareStatement("SELECT * FROM `paymenthistorical` WHERE paymenthistorical.idFacture=?");
                pst.setInt(1,factureFacilTableView.getItems().get(factureFacilTableView.getSelectionModel().getSelectedIndex()).getIdFacture());
                rs= pst.executeQuery();
                while(rs.next()){
                    historicalPricesTable.add(new HistoricalPayment(rs.getInt("id"),rs.getInt("idFacture"),rs.getString("price"),rs.getString("date")));
                }
                pst.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                con = Connecter.getConnection();
                pst = con.prepareStatement("SELECT * FROM `factures`, `clients` WHERE factures.idClient=clients.id AND factures.id=?");
                pst.setInt(1,factureFacilTableView.getItems().get(factureFacilTableView.getSelectionModel().getSelectedIndex()).getIdFacture());
                rs= pst.executeQuery();
                while(rs.next()){
                    Blob imagePieceBlob=(Blob) rs.getBlob("clientCard");
                    if (imagePieceBlob!=null){
                        InputStream inputStream=imagePieceBlob.getBinaryStream();
                        Image image1=new Image(inputStream);
                        imageCard.setImage(image1);
                        imageZoom.setImage(image1);

                    }else{
                        Image imagelogo=new Image("img/logo.png");
                        imageCard.setImage(imagelogo);
                        imageZoom.setImage(imagelogo);
                    }
                }
                pst.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            //add product to the left
            try {
                con = Connecter.getConnection();
                pst = con.prepareStatement("SELECT * FROM `productsfact`, `productmag`, `goldtype` WHERE productsfact.idProductMag=productmag.id AND productmag.idGoldType=goldtype.id AND productsfact.idFacture=?");
                pst.setInt(1,factureFacilTableView.getItems().get(factureFacilTableView.getSelectionModel().getSelectedIndex()).getIdFacture());
                rs= pst.executeQuery();
                while(rs.next()){
                    factureProductsTable.add(new Purchase(rs.getInt("idProductMag"),rs.getString("productCode"),rs.getString("goldType"),rs.getString("idKara"),rs.getString("weight"),Double.valueOf(rs.getString("mojawharatPrice"))));
                }
                pst.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            Image imagelogo=new Image("img/logo.png");
            imageCard.setImage(imagelogo);
            imageZoom.setImage(imagelogo);
            historicalPricesTable.clear();
            factureProductsTable.clear();

        }


    }


    private void addToTableClient() {
        clientsTable.clear();
        try {
            con = Connecter.getConnection();
            pst = con.prepareStatement("SELECT * FROM `clients`");
            rs= pst.executeQuery();
            while(rs.next()){
                clientsTable.add(new Client(rs.getInt("id"),rs.getString("clientName"),rs.getString("clientNumber")));
            }
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void showPrice(ActionEvent actionEvent) {
        firstPricePane.setDisable(false);
    }

    public void hidePrice(ActionEvent actionEvent) {
        firstPricePane.setDisable(true);

    }

    public void facilityfacture(Event event) {
        factureFacilTableView.getSelectionModel().clearSelection();
        confirmbutton.setDisable(true);
        printbutton.setDisable(true);
        closebutton.setDisable(true);
        deletebutton.setDisable(true);
        priceAddFacil.clear();
        Image imagelogo=new Image("img/logo.png");
        imageCard.setImage(imagelogo);
        imageZoom.setImage(imagelogo);
        historicalPricesTable.clear();
        factureProductsTable.clear();
    }

    public void newfacture(Event event) {
        confirmbutton.setDisable(false);
        printbutton.setDisable(false);
        closebutton.setDisable(false);
        deletebutton.setDisable(false);
        factureProductsTable.clear();
        firstPrice.clear();
        phoneNumber.clear();
        clientName.clear();
        clientsTableView.getSelectionModel().clearSelection();
        pieceTableView.getSelectionModel().clearSelection();
        facturePrice.setText("0.00");
        file1=null;
        imageUploaded.setText("يرجى إختيار الصورة");
        imageUploaded.setStyle(" -fx-text-fill: #000000");




    }

    public void addHistoricalPrices(ActionEvent actionEvent) {
        if (priceAddFacil.getText().isEmpty()){
            new DialogOption().DialogOptionERROR("يرجى إدخال السعر المظاف","خطاء");

        } else if (factureFacilTableView.getSelectionModel().getSelectedIndex() < 0) {
            new DialogOption().DialogOptionERROR("يرجى إختيار الفاتورة","خطاء");
        } else if (Double.parseDouble(factureFacilTableView.getItems().get(factureFacilTableView.getSelectionModel().getSelectedIndex()).getPrixRest()) < Double.parseDouble(priceAddFacil.getText())) {
            new DialogOption().DialogOptionERROR("السعر المظاف أكبر من السعر المتبقي","خطاء");

        }else{
            try{
                con = Connecter.getConnection();
                pst = con.prepareStatement("INSERT INTO `paymenthistorical`(`idFacture`, `price`, `date`) VALUES (?,?,CURRENT_TIMESTAMP())");
                pst.setInt(1, factureFacilTableView.getItems().get(factureFacilTableView.getSelectionModel().getSelectedIndex()).getIdFacture());
                pst.setDouble(2, Double.parseDouble(priceAddFacil.getText()));
                pst.execute();
                pst.close();
                con = Connecter.getConnection();
                pst = con.prepareStatement("UPDATE `factures` SET `priceR`=`priceR`-? WHERE `id`=?");
                pst.setDouble(1, Double.parseDouble(priceAddFacil.getText()));
                pst.setInt(2, factureFacilTableView.getItems().get(factureFacilTableView.getSelectionModel().getSelectedIndex()).getIdFacture());
                pst.execute();
                pst.close();
                new DialogOption().DialogOptionINFORMATION("تم إظافة بنجاح","نجاح العملية");
                addToTableHistoric();
                addToTableFacil();
                priceAddFacil.clear();
            }catch (SQLException throwables) {
                throwables.printStackTrace();
                new DialogOption().DialogOptionERROR("حدث خطأ أثناء الإظافة","خطاء");

            }

        }
    }

    public void closeZoom(ActionEvent actionEvent) {
        paneZoom.setVisible(false);
    }

    public void zoomIn(MouseEvent mouseEvent) {
        paneZoom.setVisible(true);

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

    public void search2(KeyEvent keyEvent) {
        String key=search2.getText().trim();
        priceAddFacil.clear();
        Image imagelogo=new Image("img/logo.png");
        imageCard.setImage(imagelogo);
        imageZoom.setImage(imagelogo);
        historicalPricesTable.clear();
        factureProductsTable.clear();
        if (key.isEmpty()){
            piecesTableFacil.clear();
            addToTableFacil();
        }else {
            piecesTableFacil.clear();

            try {
                con = Connecter.getConnection();
                pst = con.prepareStatement("SELECT * FROM `factures`,`clients` WHERE factures.idClient=clients.id AND (factures.id LIKE '"+key+"' OR clients.clientName LIKE '%"+key+"%' OR clients.clientNumber LIKE '"+key+"')");
                rs=pst.executeQuery();
                while(rs.next()){
                    piecesTableFacil.add(new Facture(rs.getInt("id"),rs.getInt("idClient"),rs.getString("clientName"),rs.getString("clientNumber"),String.valueOf(rs.getDate("dateFacture")),rs.getString("priceF"),rs.getString("priceR"),rs.getString("paymentType")));
                }
                pst.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

}
