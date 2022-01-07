package Tests;


import ex4_java_client.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphTest {

    @org.junit.jupiter.api.Test
    void testGetNode() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();
        a.load("G1.json");
        DirectedWeightedGraph g = a.getGraph();

        NodeData n = new Node(35.19589389346247,32.10152879327731,0.0, 0);
        assertEquals(g.getNode(0).getInfo(), n.getInfo());
        assertNotEquals(g.getNode(1).getInfo(), n.getInfo());

        n = g.getNode(1);
        assertEquals(g.getNode(1), n);
        assertNotEquals(g.getNode(0), n);

        n = new Node(35.20752617756255,32.1025646605042,0.0, 2);
        assertEquals(g.getNode(2).getKey(), n.getKey());
        assertNotEquals(g.getNode(2).getLocation(), n.getLocation());
    }

    @org.junit.jupiter.api.Test
    void testGetEdge() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();
        a.load("G1.json");
        DirectedWeightedGraph g = a.getGraph();

        EdgeData e = new Edge(0, 16, 1.3118716362419698);
        assertEquals(g.getEdge(0, 16).getWeight(), e.getWeight());
        assertNotEquals(g.getEdge(0,1).getWeight(), e.getWeight());

        e = g.getEdge(0,1);
        assertEquals(g.getEdge(0,1), e);
        assertNotEquals(g.getEdge(0, 16), e);

        e = new Edge(0,1,1.232037506070033);
        assertEquals(g.getEdge(0,16).getSrc(), e.getSrc());
        assertNotEquals(g.getEdge(0,16).getDest(), e.getDest());
    }

    @org.junit.jupiter.api.Test
    void testAddNode() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();
        a.load("G1.json");
        DirectedWeightedGraph g = a.getGraph();

        NodeData n = new Node(35.19071455528652,32.106235628571426,0.0, 30);
        g.addNode(n);
        assertEquals(g.nodeSize(), 18);
        assertNotEquals(g.nodeSize(), 17);

        for (int i = 1; i <= 10; i++) {
            n = new Node(i, i, i, i + 20);
            g.addNode(n);
        }
        assertEquals(g.nodeSize(), 27);
        assertNotEquals(g.nodeSize(), 10);

        NodeData node = new Node(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Integer.MAX_VALUE);
        g.addNode(node);
        assertEquals(g.nodeSize(), 28);
        assertNotEquals(g.nodeSize(), Integer.MAX_VALUE);
    }

    @org.junit.jupiter.api.Test
    void testConnect() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();
        a.load("G1.json");
        DirectedWeightedGraph g = a.getGraph();

        g.connect(0, 2, 1.5);
        assertEquals(g.edgeSize(), 37);
        assertNotEquals(g.edgeSize(), 36);

        for (int i = 1; i <= 5; i++) {
            g.connect(i, i + 2, i);
        }
        assertEquals(g.edgeSize(), 42);
        assertNotEquals(g.edgeSize(), 5);

        g.connect(0, 3, Double.MAX_VALUE);
        assertEquals(g.edgeSize(), 43);
        assertNotEquals(g.edgeSize(), Double.MAX_VALUE);
    }

    @org.junit.jupiter.api.Test
    void testNodeIter() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();
        a.load("G1.json");
        DirectedWeightedGraph g1 = a.getGraph();

        List<NodeData> l1 = new LinkedList<>();
        Iterator<NodeData> i1 = g1.nodeIter();
        while(i1.hasNext()) {
            l1.add(i1.next());
        }
        assertEquals(l1.size(), 17);
        assertNotEquals(l1.size(), 0);

        a.load("G2.json");
        DirectedWeightedGraph g2 = a.getGraph();
        List<NodeData> l2 = new LinkedList<>();
        Iterator<NodeData> i2 = g2.nodeIter();
        while(i2.hasNext()) {
            l2.add(i2.next());
        }
        assertEquals(l2.size(), 31);
        assertNotEquals(l2.size(), 17);

        a.load("G3.json");
        DirectedWeightedGraph g3 = a.getGraph();
        List<NodeData> l3 = new LinkedList<>();
        Iterator<NodeData> i3 = g3.nodeIter();
        while(i3.hasNext()) {
            l3.add(i3.next());
        }
        assertEquals(l3.size(), 48);
        assertNotEquals(l3.size(), 31);
    }

    @org.junit.jupiter.api.Test
    void testEdgeIter() {

    }

    @org.junit.jupiter.api.Test
    void testEdgeIterOfGivenNode() {

    }

    @org.junit.jupiter.api.Test
    void testRemoveNode() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();
        a.load("G1.json");
        DirectedWeightedGraph g = a.getGraph();

        g.removeNode(0);
        assertEquals(g.nodeSize(), 16);
        assertNotEquals(g.nodeSize(), 17);

        g.removeNode(2);
        assertEquals(g.edgeSize(), 26);
        assertNotEquals(g.edgeSize(), 36);

        NodeData n = new Node(35.18992916384181,32.1043092789916,0.0, 17);
        assertEquals(g.removeNode(n.getKey()), null);
        assertNotEquals(g.removeNode(n.getKey()), 17);
    }

    @org.junit.jupiter.api.Test
    void testRemoveEdge() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();
        a.load("G1.json");
        DirectedWeightedGraph g = a.getGraph();

        g.removeEdge(0, 16);
        assertEquals(g.edgeSize(), 35);
        assertNotEquals(g.edgeSize(), 36);

        g.connect(0, 16, 1.3118716362419698);
        g.removeEdge(1,2);
        g.removeEdge(2,1);
        g.removeEdge(2,3);
        g.removeEdge(2,6);
        assertEquals(g.edgeSize(), 32);
        assertNotEquals(g.edgeSize(),35);

        EdgeData e = new Edge(0, 16, 1.3118716362419698);
        assertEquals(g.removeEdge(e.getSrc(), e.getDest()).getInfo(), e.getInfo());
        assertNotEquals(g.removeEdge(e.getSrc(), e.getDest()), e);
    }

    @org.junit.jupiter.api.Test
    void testNodeSize() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();
        a.load("G1.json");
        DirectedWeightedGraph g = a.getGraph();

        NodeData n = new Node(35.19071455528652,32.106235628571426,0.0, 30);
        g.addNode(n);
        assertEquals(g.nodeSize(), 18);
        assertNotEquals(g.nodeSize(), 17);

        for (int i = 1; i <= 10; i++) {
            n = new Node(i, i, i, i + 20);
            g.addNode(n);
        }
        assertEquals(g.nodeSize(), 27);
        assertNotEquals(g.nodeSize(), 10);

        g.removeNode(0);
        assertEquals(g.nodeSize(), 26);
        assertNotEquals(g.nodeSize(), 17);
    }

    @org.junit.jupiter.api.Test
    void testEdgeSize() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();
        a.load("G1.json");
        DirectedWeightedGraph g = a.getGraph();

        g.connect(0, 2, 1.5);
        assertEquals(g.edgeSize(), 37);
        assertNotEquals(g.edgeSize(), 36);

        for (int i = 1; i <= 5; i++) {
            g.connect(i, i + 2, i);
        }
        assertEquals(g.edgeSize(), 42);
        assertNotEquals(g.edgeSize(), 5);

        g.removeEdge(1,2);
        g.removeEdge(2,1);
        assertEquals(g.edgeSize(), 40);
        assertNotEquals(g.edgeSize(),36);
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        DirectedWeightedGraphAlgorithms a = new GraphAlgo();
        a.load("G1.json");
        DirectedWeightedGraph g = a.getGraph();

        NodeData n = new Node(35.19071455528652,32.106235628571426,0.0, 30);
        String s = g.toString() + n.getInfo();
        g.addNode(n);
        assertEquals(g.toString(), s);
        assertNotEquals(g.toString(), n.getInfo());

        n = new Node(35.19786798547216,32.10151062016807,0.0, 31);
        String st = g.toString() + n.getInfo();
        g.addNode(n);
        assertEquals(g.toString(), st);
        assertNotEquals(g.toString(), s);
    }
}