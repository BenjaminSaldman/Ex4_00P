package ex4_java_client;

/**
 * This Class represents a geographic location and implement's the methods of GeoLocation.
 */

public class Location implements GeoLocation {
    private double x, y, z; //Coordinates in the space.

    /**
     *
     * @return representative string.
     */
    @Override
    public String toString() {
        return ""+x+","+y+","+z+",";
    }

    public Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Location(GeoLocation location)
    {
        this.x=location.x();
        this.y= location.y();
        this.z= location.z();
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    /**
     *
     * @param g
     * @return the distance from another location.
     */
    @Override
    public double distance(GeoLocation g) {
        return Math.sqrt((Math.pow(x - g.x(), 2) + Math.pow(y - g.y(), 2) + Math.pow(z - g.z(), 2)));
    }
}
