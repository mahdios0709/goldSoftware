package code;

public class Facture {
    private int idFacture, idClient;
    private String clientName,clientNumber,dateFacture;
    private Double prixTotal;

    public Facture(int idFacture, int idClient, String clientName, String clientNumber, String dateFacture, Double prixTotal) {
        this.idFacture = idFacture;
        this.idClient = idClient;
        this.clientName = clientName;
        this.clientNumber = clientNumber;
        this.dateFacture = dateFacture;
        this.prixTotal = prixTotal;
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

    public Double getPrixTotal() {
        return prixTotal;
    }
}
