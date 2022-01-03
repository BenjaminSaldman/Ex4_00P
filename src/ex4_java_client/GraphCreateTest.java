package ex4_java_client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphCreateTest {
    @Test
    void test1000Nodes()
    {
        DirectedWeightedGraph g=GraphCreate.create(1000,1);
        DirectedWeightedGraphAlgorithms gr=new GraphAlgo(g);
        gr.save("1000n.json");
        assertTrue(gr.isConnected());
    }
    @Test
    void test10000Nodes()
    {
        DirectedWeightedGraph g=GraphCreate.create(10000,2);
        DirectedWeightedGraphAlgorithms gr=new GraphAlgo(g);
        gr.save("10000n.json");
        assertTrue(gr.isConnected());
    }
    @Test
    void test100000Nodes()
    {
        DirectedWeightedGraph g=GraphCreate.create(100000,3);
        DirectedWeightedGraphAlgorithms gr=new GraphAlgo(g);
        gr.save("100000n.json");
        assertTrue(gr.isConnected());
    }


}