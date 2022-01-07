package Tests;


import ex4_java_client.Edge;
import ex4_java_client.EdgeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    @Test
    void testGetSrc() {
        EdgeData e = new Edge(0, 16, 1.3118716362419698);
        assertEquals(e.getSrc(), 0);
        assertNotEquals(e.getSrc(), 16);

        e = new Edge(16, 0, 1.4418017651347552);
        assertEquals(e.getSrc(), 16);
        assertNotEquals(e.getSrc(), 1.4418017651347552);

        e = new Edge(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE + Integer.MAX_VALUE);
        assertEquals(e.getSrc(), Integer.MIN_VALUE);
        assertNotEquals(e.getSrc(), Integer.MIN_VALUE + Integer.MAX_VALUE);
    }

    @Test
    void testGetDest() {
        EdgeData e = new Edge(0, 16, 1.3118716362419698);
        assertEquals(e.getDest(), 16);
        assertNotEquals(e.getDest(), 0);

        e = new Edge(16, 0, 1.4418017651347552);
        assertEquals(e.getDest(), 0);
        assertNotEquals(e.getDest(), 1.4418017651347552);

        e = new Edge(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE + Integer.MAX_VALUE);
        assertEquals(e.getDest(), Integer.MAX_VALUE);
        assertNotEquals(e.getDest(), Integer.MIN_VALUE + Integer.MAX_VALUE);
    }

    @Test
    void testGetWeight() {
        EdgeData e = new Edge(0, 16, 1.3118716362419698);
        assertEquals(e.getWeight(), 1.3118716362419698);
        assertNotEquals(e.getWeight(), 1.3);

        e = new Edge(16, 0, 1.4418017651347552);
        assertEquals(e.getWeight(), 1.4418017651347552);
        assertNotEquals(e.getWeight(), 16);

        e = new Edge(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE + Integer.MAX_VALUE);
        assertEquals(e.getWeight(), Integer.MIN_VALUE + Integer.MAX_VALUE);
        assertNotEquals(e.getWeight(), Integer.MAX_VALUE);
    }

    @Test
    void testGetInfo() {
        EdgeData e = new Edge(0, 16, 1.3118716362419698);
        String correct = "Src: 0, Dest: 16, Weight: 1.3118716362419698";
        String wrong = "Src: 0.0, Dest: 16.0, Weight: 1.3118716362419698";
        assertEquals(e.getInfo(), correct);
        assertNotEquals(e.getInfo(), wrong);

        e = new Edge(0, 1, 1.232037506070033);
        correct = "Src: 0, Dest: 1, Weight: 1.232037506070033";
        wrong = "Src: 0, Dest: 1, Weight: 1.23203750607003";
        assertEquals(e.getInfo(), correct);
        assertNotEquals(e.getInfo(), wrong);

        e = new Edge(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        correct = "Src: " + Integer.MIN_VALUE + ", Dest: " + Integer.MAX_VALUE + ", Weight: 0.0";
        wrong = "Src: Integer.MIN_VALUE, Dest: Integer.MAX_VALUE, Weight: 0.0";
        assertEquals(e.getInfo(), correct);
        assertNotEquals(e.getInfo(), wrong);
    }

    @Test
    void testSetInfo() {
        EdgeData e = new Edge(0, 16, 1.3118716362419698);
        String wrong = e.getInfo();
        e.setInfo("0, 16, 1.3118716362419698");
        String correct = "0, 16, 1.3118716362419698";
        assertEquals(e.getInfo(), correct);
        assertNotEquals(e.getInfo(), wrong);

        e = new Edge(0, 1, 1.232037506070033);
        correct = e.getInfo();
        String c = "Src: 0, Dest: 1, Weight: 1.232037506070033";
        e.setInfo("0, 1, 1.232037506070033");
        wrong = e.getInfo();
        assertEquals(correct, c);
        assertNotEquals(wrong, c);

        e = new Edge(0, 16, 1.3118716362419698);
        wrong = e.getInfo();
        e.setInfo("0, 16, 1.3118716362419698");
        c = "0, 16, 1.3118716362419698";
        correct = e.getInfo();
        assertEquals(correct, c);
        assertNotEquals(wrong, c);
    }


}