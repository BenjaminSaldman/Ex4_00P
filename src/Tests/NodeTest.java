package Tests;


import ex4_java_client.GeoLocation;
import ex4_java_client.Location;
import ex4_java_client.Node;
import ex4_java_client.NodeData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void testGetKey() {
        NodeData n = new Node(35.19589389346247,32.10152879327731,0.0, 0);
        assertEquals(n.getKey(), 0);
        assertNotEquals(n.getKey(), 1);

        n = new Node(35.20319591121872,32.10318254621849,0.0, 1);
        assertEquals(n.getKey(), 1);
        assertNotEquals(n.getKey(), Integer.MIN_VALUE);

        n = new Node(35.19589389346247,32.10152879327731,0.0, Integer.MAX_VALUE);
        assertEquals(n.getKey(), Integer.MAX_VALUE);
        assertNotEquals(n.getKey(), 0);
    }

    @Test
    void testGetLocation() {
        NodeData n = new Node(35.19589389346247,32.10152879327731,0.0, 0);
        String s = n.getLocation().toString();
        GeoLocation gTrue = new Location(35.19589389346247,32.10152879327731,0.0);
        String sTrue = gTrue.toString();
        GeoLocation gFalse = new Location(35.20319591121872,32.10318254621849,0.0);
        String sFalse = gFalse.toString();
        assertEquals(s, sTrue);
        assertNotEquals(s, sFalse);

        n = new Node(35.20319591121872,32.10318254621849,0.0, 1);
        s = n.getLocation().toString();
        gTrue = new Location(35.20319591121872,32.10318254621849,0.0);
        sTrue = gTrue.toString();
        gFalse = new Location(35,32,0);
        sFalse = gFalse.toString();
        assertEquals(s, sTrue);
        assertNotEquals(s, sFalse);

        n = new Node(35.20752617756255,32.1025646605042,0.0, 2);
        s = n.getLocation().toString();
        gTrue = new Location(35.20752617756255,32.1025646605042,0.0);
        sTrue = gTrue.toString();
        gFalse = new Location(Double.MIN_VALUE,Double.MAX_VALUE,0.0);
        sFalse = gFalse.toString();
        assertEquals(s, sTrue);
        assertNotEquals(s, sFalse);
    }

    @Test
    void testSetLocation() {
        NodeData n = new Node(35.19589389346247,32.10152879327731,0.0, 0);
        GeoLocation g = new Location(35.20319591121872,32.10318254621849,0.0);
        String gStr = g.toString();
        String oldS = n.getLocation().toString();
        n.setLocation(g);
        String newS = n.getLocation().toString();
        assertEquals(gStr, newS);
        assertNotEquals(gStr, oldS);

        g = new Location(Double.MIN_VALUE,Double.MAX_VALUE,0.0);
        gStr = g.toString();
        n.setLocation(g);
        newS = n.getLocation().toString();
        assertEquals(gStr, newS);
        assertNotEquals(gStr, oldS);

        n = new Node(Double.MIN_VALUE,Double.MAX_VALUE,0.0, 0);
        g = new Location(35.20319591121872,32.10318254621849,0.0);
        gStr = g.toString();
        n.setLocation(g);
        newS = n.getLocation().toString();
        assertEquals(gStr, newS);
        assertNotEquals(gStr, oldS);
    }

    @Test
    void testGetWeight() {
        NodeData n = new Node(35.19589389346247,32.10152879327731,0.0, 0);
        assertEquals(n.getWeight(), 0);
        assertNotEquals(n.getWeight(), 1);

        n = new Node(Double.MIN_VALUE,Double.MAX_VALUE,0.0, 0);
        assertEquals(n.getWeight(), 0);
        assertNotEquals(n.getWeight(), Double.MAX_VALUE);

        n = new Node(1,1,0.0, 0);
        assertEquals(n.getWeight(), 0);
        assertNotEquals(n.getWeight(), 1);
    }

    @Test
    void testSetWeight() {
        NodeData n = new Node(35.19589389346247,32.10152879327731,0.0, 0);
        n.setWeight(5);
        assertEquals(n.getWeight(), 5);
        assertNotEquals(n.getWeight(), 0);

        n = new Node(Double.MIN_VALUE,Double.MAX_VALUE,0.0, 0);
        n.setWeight(0);
        assertEquals(n.getWeight(), 0);
        assertNotEquals(n.getWeight(), Double.MAX_VALUE);

        n = new Node(35.20319591121872,32.10318254621849,0.0, 1);
        n.setWeight(Double.MIN_VALUE);
        assertEquals(n.getWeight(), Double.MIN_VALUE);
        assertNotEquals(n.getWeight(), 0);
    }

    @Test
    void testGetInfo() {
        NodeData n = new Node(35.19589389346247,32.10152879327731,0.0, 0);
        String correct = "ID: "+n.getKey()+" The Location of this node is in: "+n.getLocation().toString()+ "Personal tag: ";
        String wrong = "Personal tag: " + ((int)n.getTag() - 1) + " The Location of this node is in: " + n.getLocation().toString()
                       + "ID: " + n.getKey();
        assertTrue(n.getInfo().equals( correct));
        assertNotEquals(n.getInfo(), wrong);

        n = new Node(35.20319591121872,32.10318254621849,0.0, 1);
        correct = "ID: "+n.getKey()+" The Location of this node is in: "+n.getLocation().toString()+ "Personal tag: ";
        wrong = "35.20319591121872,32.10318254621849,0.0, 1";
        assertTrue(n.getInfo().equals( correct));
        assertNotEquals(n.getInfo(), wrong);

        n = new Node(Double.MIN_VALUE,Double.MAX_VALUE,0.0, 2);
        correct = "ID: "+n.getKey()+" The Location of this node is in: "+n.getLocation().toString()+ "Personal tag: ";
        wrong = "ID: " + n.getKey() + " The Location of this node is in: " + n.getLocation().toString() +
                "Personal tag: " + n.getTag();
        assertEquals(n.getInfo(), correct);
        assertNotEquals(n.getInfo(), wrong);
    }

    @Test
    void testSetInfo() {
        NodeData n = new Node(35.19589389346247,32.10152879327731,0.0, 0);
        String wrong = n.getInfo();
        n.setInfo("35.19589389346247, 32.10152879327731, 0.0, 0");
        String correct = "35.19589389346247, 32.10152879327731, 0.0, 0";
        assertEquals(n.getInfo(), correct);
        assertNotEquals(n.getInfo(), wrong);

        n = new Node(35.20319591121872,32.10318254621849,0.0, 1);
        correct = n.getInfo();
        String c = "ID: "+n.getKey()+" The Location of this node is in: "+n.getLocation().toString()+ "Personal tag: ";
        n.setInfo("35.19589389346247, 32.10152879327731, 0.0, 0");
        wrong = n.getInfo();
        assertEquals(correct, c);
        assertNotEquals(wrong, c);

        n = new Node(35.19589389346247,32.10152879327731,0.0, 0);
        wrong = n.getInfo();
        n.setInfo("35.19589389346247, 32.10152879327731, 0.0, 0");
        c = "35.19589389346247, 32.10152879327731, 0.0, 0";
        correct = n.getInfo();
        assertEquals(correct, c);
        assertNotEquals(wrong, c);
    }

    @Test
    void testGetTag() {
        NodeData n = new Node(35.19589389346247,32.10152879327731,0.0, 0);
        assertEquals(n.getTag(),0);
        n.setTag(5);
        assertEquals(n.getTag(),5);
        n.setTag(15);
        assertEquals(n.getTag(),15);
    }

    @Test
    void testSetTag() {
        NodeData n = new Node(35.19589389346247,32.10152879327731,0.0, 0);
        n.setTag(5);
        assertEquals(n.getTag(), 5);
        assertNotEquals(n.getTag(), 6);

        n = new Node(Double.MIN_VALUE,Double.MAX_VALUE,0.0, 0);
        n.setTag(0);
        assertEquals(n.getTag(), 0);
        assertNotEquals(n.getTag(), Double.MAX_VALUE);

        n = new Node(35.20319591121872,32.10318254621849,0.0, 1);
        n.setTag(Integer.MAX_VALUE);
        assertEquals(n.getTag(), Integer.MAX_VALUE);
        assertNotEquals(n.getTag(), 0);
    }
}