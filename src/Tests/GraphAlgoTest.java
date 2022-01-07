package Tests;


import ex4_java_client.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphAlgoTest {

    @Test
    void init() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();
        a.load("G1.json");

        DirectedWeightedGraph g1 = a.getGraph();
        a.init(g1);
        DirectedWeightedGraph gTag1 = a.getGraph();
        assertEquals(g1, gTag1);

        a.load("G2.json");
        DirectedWeightedGraph g2 = a.getGraph();
        a.init(g2);
        DirectedWeightedGraph gTag2 = a.getGraph();
        assertEquals(g2, gTag2);

        a.load("G3.json");
        DirectedWeightedGraph g3 = a.getGraph();
        a.init(g3);
        DirectedWeightedGraph gTag3 = a.getGraph();
        assertEquals(g3, gTag3);

        assertNotEquals(g2,gTag1);
        assertNotEquals(g3,gTag1);
        assertNotEquals(g1,gTag2);
        assertNotEquals(g3,gTag2);
        assertNotEquals(g1,gTag3);
        assertNotEquals(g2,gTag3);
    }

    @Test
    void getGraph() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();
        a.load("G1.json");

        DirectedWeightedGraph g1 = a.getGraph();
        a.init(g1);
        DirectedWeightedGraph gTag1 = a.getGraph();
        assertEquals(g1, gTag1);

        a.load("G2.json");
        DirectedWeightedGraph g2 = a.getGraph();
        a.init(g2);
        DirectedWeightedGraph gTag2 = a.getGraph();
        assertEquals(g2, gTag2);
        assertNotEquals(g2,gTag1);
        assertNotEquals(g1,gTag2);

        a.load("G3.json");
        DirectedWeightedGraph g3 = a.getGraph();
        a.init(g3);
        DirectedWeightedGraph gTag3 = a.getGraph();
        assertEquals(g3, gTag3);
        assertNotEquals(g3,gTag1);
        assertNotEquals(g3,gTag2);
        assertNotEquals(g1,gTag3);
        assertNotEquals(g2,gTag3);
    }

    @Test
    void copy() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();
        a.load("G1.json");
        DirectedWeightedGraph g1 = a.copy();
        DirectedWeightedGraph g2 = a.copy();
        Iterator<NodeData> e1=g1.nodeIter();
        Iterator<NodeData> e2=g2.nodeIter();
        while(e2.hasNext() && e1.hasNext())
        {
            NodeData n1=e1.next();
            NodeData n2=e2.next();
            assertTrue(n1.getKey()==n1.getKey());
            assertTrue(n1.getLocation().toString().equals(n2.getLocation().toString()));
            Iterator<EdgeData>a1=g1.edgeIter(n1.getKey());
            Iterator<EdgeData>a2=g2.edgeIter(n2.getKey());
            while(a1.hasNext() && a2.hasNext())
            {
                EdgeData ed1=a1.next();
                EdgeData ed2=a2.next();
                assertTrue(ed1.getInfo().equals(ed2.getInfo()));
            }
        }
        assertEquals(g1.nodeSize(), g2.nodeSize());
        assertEquals(g1.edgeSize(), g2.edgeSize());
        g2=a.getGraph();
        e1=g1.nodeIter();
        e2=g2.nodeIter();
        while(e2.hasNext() && e1.hasNext())
        {
            NodeData n1=e1.next();
            NodeData n2=e2.next();
            assertTrue(n1.getKey()==n1.getKey());
            assertTrue(n1.getLocation().toString().equals(n2.getLocation().toString()));
            Iterator<EdgeData>a1=g1.edgeIter(n1.getKey());
            Iterator<EdgeData>a2=g2.edgeIter(n2.getKey());
            while(a1.hasNext() && a2.hasNext())
            {
                EdgeData ed1=a1.next();
                EdgeData ed2=a2.next();
                assertTrue(ed1.getInfo().equals(ed2.getInfo()));
            }
        }
        assertEquals(g1.nodeSize(), g2.nodeSize());
        assertEquals(g1.edgeSize(), g2.edgeSize());

    }

    @Test
    void isConnected() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();

        a.load("G1.json");
        DirectedWeightedGraph g1 = a.getGraph();
        assertTrue(a.isConnected());
        g1.removeNode(11);
        g1.removeNode(15);
        assertFalse(a.isConnected());

        a.load("G2.json");
        DirectedWeightedGraph g2 = a.getGraph();
        assertTrue(a.isConnected());
        NodeData n = new Node(35,32, 0, 31);
        g2.addNode(n);
        g2.connect(30, 31, 1);
        g2.connect(31, 30, 2);
        assertTrue(a.isConnected());


        a.load("G3.json");
        DirectedWeightedGraph g3 = a.getGraph();
        assertTrue(a.isConnected());
        g3.removeEdge(13,14);
        assertFalse(a.isConnected());
    }

    @Test
    void shortestPathDist() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();

        a.load("G1.json");
        double s = a.shortestPathDist(1,4,1.0);
        assertEquals(s, 4.11869441873162);
        assertNotEquals(s, 6.675);

        a.load("G3.json");
        DirectedWeightedGraph g3 = a.getGraph();
        g3.removeEdge(13,14);
        s = a.shortestPathDist(0,47,1.0);
        assertEquals(s, -1);
        assertNotEquals(s, 17.146479706314675);

        a.load("G2.json");
        DirectedWeightedGraph g2 = a.getGraph();
        g2.removeEdge(6,7);
        s = a.shortestPathDist(6,7,1.0);
        assertEquals(s, 6.98836522308555);
        assertNotEquals(s, 1.237565124536135);
    }

    @Test
    void shortestPath() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();

        a.load("G1.json");
        DirectedWeightedGraph g1 = a.getGraph();
        List<NodeData> l1 = a.shortestPath(1, 4,1.0);
        List<NodeData> l2 = new LinkedList<NodeData>();
        //l2.add(g1.getNode(1));
        l2.add(g1.getNode(2));
        l2.add(g1.getNode(3));
        l2.add(g1.getNode(4));
        List<NodeData> l3 = new LinkedList<NodeData>();
        l3.add(g1.getNode(1));
        l3.add(g1.getNode(2));
        l3.add(g1.getNode(6));
        l3.add(g1.getNode(5));
        l3.add(g1.getNode(4));
        assertEquals(l1, l2);
        assertNotEquals(l1, l3);

        a.load("G3.json");
        DirectedWeightedGraph g3 = a.getGraph();
        g3.removeEdge(13,14);
        l1 = a.shortestPath(3, 30,1.0);
        assertNotEquals(a.shortestPathDist(3,30,1.0), 5.10225682606953);
        a.load("G2.json");
        DirectedWeightedGraph g2 = a.getGraph();
        g2.removeEdge(2,6);
        l1 = a.shortestPath(2, 6,1.0);
        l2.clear();
        //l2.add(g2.getNode(2));
        l2.add(g2.getNode(3));
        l2.add(g2.getNode(4));
        l2.add(g2.getNode(5));
        l2.add(g2.getNode(6));
        l3.clear();
        l3.add(g2.getNode(2));
        l3.add(g2.getNode(6));
        assertEquals(l1, l2);
        assertNotEquals(l1, l3);
    }

    @Test
    void center() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();

        a.load("G1.json");
        DirectedWeightedGraph g1 = a.getGraph();
        assertEquals(a.center(), g1.getNode(8));
        assertNotEquals(a.center(), g1.getNode(7));

        a.load("G2.json");
        DirectedWeightedGraph g2 = a.getGraph();
        assertEquals(a.center(), g2.getNode(0));
        assertNotEquals(a.center(), g2.getNode(30));

        a.load("G3.json");
        DirectedWeightedGraph g3 = a.getGraph();
        assertEquals(a.center(), g3.getNode(40));
        assertNotEquals(a.center(), g3.getNode(0));
    }

    @Test
    void tsp() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}