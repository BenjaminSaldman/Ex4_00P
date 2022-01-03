package ex4_java_client;
import java.util.Random;
public class GraphCreate {
    /**
     *
     * @param size
     * @param seed
     * @return random generated direct graph.
     */
    public static DirectedWeightedGraph create(int size,long seed)
    {
        Random rnd=new Random(seed);
        DirectedWeightedGraph g=new DirectedGraph();
        for(int i=0;i<size;i++)
        {
            g.addNode(new Node(rnd.nextDouble()*750,rnd.nextDouble()*750,rnd.nextDouble()*750,i));
        }
        for(int i=0;i<size;i++)
        {
            int rank=rnd.nextInt(1)+20;
            while(rank!=0)
            {
                int node=rnd.nextInt(size);
                if(node !=i)
                {
                    g.connect(i,node, rnd.nextDouble()*3.5);
                    rank--;
                }
            }
        }
        return g;
    }
}
