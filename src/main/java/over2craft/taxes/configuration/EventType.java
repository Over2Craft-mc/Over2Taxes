package over2craft.taxes.configuration;

public enum EventType {

    onDeath("taxes.onDeath"),
    onCommand("taxes.onCommand");

    String value;

    EventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
