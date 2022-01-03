package ex4_java_client;

public class Agent {
    private int id;
    private double value;
    private int src;
    private int dest;
    private double speed;
    private GeoLocation location;

    public Agent(int id, double value, int src, int dest, double speed, GeoLocation location) {
        this.id = id;
        this.value = value;
        this.src = src;
        this.dest = dest;
        this.speed = speed;
        this.location = location;
        //Update the new agent list to the current list.
    }

    public Agent(Agent agent) {
        this.id=agent.getId();
        this.value=agent.getValue();
        this.src=agent.getSrc();
        this.dest=agent.getDest();
        this.speed=agent.getSpeed();
        this.location= agent.getLocation();
    }

    public int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public int getSrc() {
        return src;
    }

    public int getDest() {
        return dest;
    }

    public double getSpeed() {
        return speed;
    }

    public GeoLocation getLocation() {
        return location;
    }
}
