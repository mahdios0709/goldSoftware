package code;

public class Client {
    private int id;
    private String clientName,clientPhoneNumber;

    public Client(int id, String clientName, String clientPhoneNumber) {
        this.id = id;
        this.clientName = clientName;
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }
}
