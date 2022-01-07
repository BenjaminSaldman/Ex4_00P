package Tests;


import ex4_java_client.GeoLocation;
import ex4_java_client.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void testToString() {
        GeoLocation g = new Location(1,3,5);
        String correct = "1.0,3.0,5.0,";
        String wrong = "x=1.0, y=3.0, z=5.0,";
        assertEquals(correct, g.toString());
        assertNotEquals(wrong, g.toString());

        g = new Location(35.19589389346247,32.10152879327731,0.0);
        correct = "35.19589389346247,32.10152879327731,0.0,";
        wrong = "The Location of this node is in: " + g.toString();
        assertEquals(correct, g.toString());
        assertNotEquals(wrong, g.toString());

        g = new Location(Double.MIN_VALUE, Double.MAX_VALUE, 0);
        correct = "" + g.x() + "," + g.y() + "," +g.z() + ",";
        wrong = "x=Double.MIN_VALUE, y=Double.MAX_VALUE, z=0.0";
        assertEquals(correct, g.toString());
        assertNotEquals(wrong, g.toString());
    }

    @Test
    void testX() {
        GeoLocation g = new Location(1,3,5);
        assertEquals(g.x(), 1.0);
        assertNotEquals(g.x(), -1.0);

        g = new Location(35.19589389346247,32.10152879327731,0.0);
        assertEquals(g.x(), 35.19589389346247);
        assertNotEquals(g.x(), 35.195);

        g = new Location(Double.MIN_VALUE, Double.MAX_VALUE, 0);
        assertEquals(g.x(), Double.MIN_VALUE);
        assertNotEquals(g.x(), Double.MAX_VALUE);
    }

    @Test
    void testY() {
        GeoLocation g = new Location(1,3,5);
        assertEquals(g.y(), 3);
        assertNotEquals(g.y(), 1);

        g = new Location(35.19589389346247,32.10152879327731,0.0);
        assertEquals(g.y(), 32.10152879327731);
        assertNotEquals(g.y(), 35.19589389346247);

        g = new Location(Double.MIN_VALUE, Double.MAX_VALUE, 0);
        assertEquals(g.y(), Double.MAX_VALUE);
        assertNotEquals(g.y(), Double.MIN_VALUE);
    }

    @Test
    void testZ() {
        GeoLocation g = new Location(1,3,5);
        assertEquals(g.z(), 5);
        assertNotEquals(g.z(), 0);

        g = new Location(35.19589389346247,32.10152879327731,0.0);
        assertEquals(g.z(), 0);
        assertNotEquals(g.z(), null);

        g = new Location(Double.MIN_VALUE, Double.MAX_VALUE, 0);
        assertEquals(g.z(), 0);
        assertNotEquals(g.z(), Double.MIN_VALUE);
    }

    @Test
    void testDistance() {
        GeoLocation g = new Location(1,3,5);
        GeoLocation gNew = new Location(2,4,6);
        double dist = g.distance(gNew);
        assertEquals(dist, 1.7320508075688772);
        assertNotEquals(dist, 1.4142135623730951);

        g = new Location(35.19589389346247,32.10152879327731,0.0);
        gNew = new Location(35.20319591121872,32.10318254621849,0.0);
        dist = g.distance(gNew);
        assertEquals(dist, 0.007486946113275561);
        assertNotEquals(dist,  0.007487);

        g = new Location(35.19589389346247,32.10152879327731,1.0);
        gNew = new Location(35.20319591121872,32.10318254621849,0.0);
        dist = g.distance(gNew);
        assertEquals(dist, 1.000028026788301);
        assertNotEquals(dist,  0.007486946113275561);
    }
}