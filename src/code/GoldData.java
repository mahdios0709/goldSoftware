package code;

public class GoldData {
    public int getIdKara() {
        return idKara;
    }

    public String getIdGoldType() {
        return idGoldType;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public void setIdKara(int idKara) {
        this.idKara = idKara;
    }

    public void setIdGoldType(String idGoldType) {
        this.idGoldType = idGoldType;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    int idKara;
    String idGoldType; String  date; String price;
    public GoldData(int idKara, String idGoldType, String date, String price) {
        this.idKara = idKara;
        this.idGoldType = idGoldType;
        this.date = date;
        this.price = price;
    }



}
