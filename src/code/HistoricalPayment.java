package code;

public class HistoricalPayment {
    private int idHistorical,idFacture;
    private String price,date;

    public HistoricalPayment(int idHistorical, int idFacture, String price, String date) {
        this.idHistorical = idHistorical;
        this.idFacture = idFacture;
        this.price = price;
        this.date = date;
    }

    public int getIdHistorical() {
        return idHistorical;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}
