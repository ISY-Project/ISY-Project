package src.Telnet.Responses;

public enum MoveResponse {
    PLONS("Plons"),
    BOEM("Boem");

    private final String text;

    @Override
    public String toString() {
        return text;
    }

    MoveResponse(String string) {
        this.text = string;
    }
}
