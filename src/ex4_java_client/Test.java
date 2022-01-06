package ex4_java_client;

import java.util.List;

public class Test {
    public static void main(String [] args)
    {
        DirectedWeightedGraphAlgorithms gr=new GraphAlgo();
        gr.load("temp.json");
//        System.out.println(gr.shortestPathDist(28,14,2.0));
//        System.out.println(gr.shortestPath(28,14,2.0));
        List<NodeData>l=gr.shortestPath(3,0,5);
        for(NodeData n:l)
            System.out.println(n.getKey());
        //System.out.println();
    }
}
