package ex4_java_client;

public class Pokemon {
    private double value;
    private int type;
    private GeoLocation location;
    boolean allocated;

    public Pokemon(double value, int type, GeoLocation location) {
        this.value = value;
        this.type = type;
        this.location = location;
        allocated=false;
    }

    public double getValue() {
        return value;
    }

    public int getType() {
        return type;
    }

    public GeoLocation getLocation() {
        return location;
    }
}
