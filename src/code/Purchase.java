package code;

public class Purchase {
    private int id;
    private String pieceNumber,goldType,karaNumber,pieceWeight;
    private Double price;

    public Purchase(int id, String pieceNumber, String goldType, String karaNumber, String pieceWeight, Double price) {
        this.id = id;
        this.pieceNumber = pieceNumber;
        this.goldType = goldType;
        this.karaNumber = karaNumber;
        this.pieceWeight = pieceWeight;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getPieceNumber() {
        return pieceNumber;
    }

    public String getGoldType() {
        return goldType;
    }

    public String getKaraNumber() {
        return karaNumber;
    }

    public String getPieceWeight() {
        return pieceWeight;
    }

    public Double getPrice() {
        return price;
    }
}
