package code;


public class Piece {

    private int id,idGoldType;
    private Float pieceWeight;
    private String pieceNumber,karaNumber,goldType,stoneName,stonePrice,date,totalPriceOfPiece;

    public Piece(int id, int idGoldType, Float pieceWeight, String pieceNumber, String karaNumber, String goldType, String stoneName, String stonePrice, String date, String totalPriceOfPiece) {
        this.id = id;
        this.idGoldType = idGoldType;
        this.pieceWeight = pieceWeight;
        this.pieceNumber = pieceNumber;
        this.karaNumber = karaNumber;
        this.goldType = goldType;
        this.stoneName = stoneName;
        this.stonePrice = stonePrice;
        this.date = date;
        this.totalPriceOfPiece = totalPriceOfPiece;
    }

    public String getTotalPriceOfPiece() {
        return totalPriceOfPiece;
    }

    public int getId() {
        return id;
    }

    public int getIdGoldType() {
        return idGoldType;
    }

    public Float getPieceWeight() {
        return pieceWeight;
    }

    public String getPieceNumber() {
        return pieceNumber;
    }

    public String getKaraNumber() {
        return karaNumber;
    }

    public String getGoldType() {
        return goldType;
    }

    public String getStoneName() {
        return stoneName;
    }

    public String getStonePrice() {
        return stonePrice;
    }

    public String getDate() {
        return date;
    }
}
