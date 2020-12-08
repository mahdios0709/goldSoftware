package code;

public class GoldTypes {
    private int id;
    private String goldType,abGoldType;

    public GoldTypes(int id, String goldType, String abGoldType) {
        this.id = id;
        this.goldType = goldType;
        this.abGoldType = abGoldType;
    }

    public int getId() {
        return id;
    }

    public String getGoldType() {
        return goldType;
    }

    public String getAbGoldType() {
        return abGoldType;
    }
}
