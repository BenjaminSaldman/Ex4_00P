package ex4_java_client;

public class Test {
    public static void main(String [] args)
    {
        DirectedWeightedGraphAlgorithms gr=new GraphAlgo();
        gr.load("temp.json");
        System.out.println(gr.shortestPathDist(28,14,2.0));
        System.out.println(gr.shortestPath(28,14,2.0));
    }
}
