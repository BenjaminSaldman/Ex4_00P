package ex4_java_client;

/**
 * This Class Represent an edge on graph, it implements the methods
 * of "EdgeData".
 */
public class Edge implements EdgeData {
    private int src,dst; //Src and dst node (id's).
    private double weight; //Weight of the node.
    private String info; //Representative String.
    public Edge(int src, int dst, double weight)
    {
        this.src=src;
        this.dst=dst;
        this.weight=weight;
        info = "Src: " + src +", Dest: " + dst + ", Weight: "+weight;
    }

    /**
     *
     * @return the src node id.
     */
    @Override
    public int getSrc() {
        return src;
    }

    /**
     *
     * @return the Destination node id.
     */
    @Override
    public int getDest() {
        return dst;
    }

    /**
     *
     * @return the weight of the Edge.
     */
    @Override
    public double getWeight() {
        return weight;
    }

    /**
     *
     * @return the info of the edge.
     */
    @Override
    public String getInfo() {
        return info ;
    }

    @Override
    public void setInfo(String s) {
        info=s;
    }

    @Override
    public int getTag() {
        return 0;
    }

    @Override
    public void setTag(int t) {
        return;
    }
}
