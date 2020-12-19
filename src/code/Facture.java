package code;

public class Facture {
    private int idFacture, idClient;
    private String clientName,clientNumber,dateFacture;
    private String prixTotal,prixRest,paymentType;

    public Facture(int idFacture, int idClient, String clientName, String clientNumber, String dateFacture, String prixTotal, String prixRest, String paymentType) {
        this.idFacture = idFacture;
        this.idClient = idClient;
        this.clientName = clientName;
        this.clientNumber = clientNumber;
        this.dateFacture = dateFacture;
        this.prixTotal = prixTotal;
        this.prixRest = prixRest;
        this.paymentType = paymentType;
    }

    public String getPrixRest() {
        return prixRest;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public String getDateFacture() {
        return dateFacture;
    }

    public String getPrixTotal() {
        return prixTotal;
    }
}
