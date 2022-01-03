package ex4_java_client;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
/////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * This class Represent a directed graph by implementing the methods of
 * "DirectedWeightedGraph".
 */
public class DirectedGraph implements DirectedWeightedGraph {
    private HashMap<Integer, NodeData> nodes; //Graph nodes.
    private HashMap<Integer, HashMap<Integer, EdgeData>> edges; //The edges the comes out from the node.
    private int count; //Number of edge.
    private int MC; //The number of the changes that were done on the graph.

    public DirectedGraph() {
        edges = new HashMap<Integer, HashMap<Integer, EdgeData>>();
        nodes = new HashMap<Integer, NodeData>();
        count = 0;
        MC = 0;
    }

    public DirectedGraph(DirectedWeightedGraph graph) {
        edges = new HashMap<Integer, HashMap<Integer, EdgeData>>();
        nodes = new HashMap<Integer, NodeData>();
        this.count=0;
        this.MC=graph.getMC();
        Iterator<NodeData> e = graph.nodeIter();
        while (e.hasNext()) {
            NodeData temp = e.next();
            NodeData n = new Node(temp);
            this.addNode(n);
        }
        e= graph.nodeIter();
        while (e.hasNext())
        {
            NodeData temp=e.next();
            Iterator<EdgeData>ed=graph.edgeIter(temp.getKey());
            while (ed.hasNext()) {
                EdgeData edge = ed.next();
                this.connect(edge.getSrc(), edge.getDest(), edge.getWeight());

            }
        }
    }

    /**
     *
     * @param key - the node_id
     * @return the node with this specific id.
     */
    @Override
    public NodeData getNode(int key) {
        return nodes.get(key);
    }

    /**
     *
     * @param src
     * @param dest
     * @return the edge that represented by those nodes.
     */
    @Override
    public EdgeData getEdge(int src, int dest) {
        return edges.get(src).get(dest);
    }

    /**
     *
     * @param n
     * Adding new node to the graph.
     */
    @Override
    public void addNode(NodeData n) {
        nodes.put(n.getKey(), n);
        HashMap<Integer, EdgeData> neigbhors = new HashMap<Integer, EdgeData>();
        edges.put(n.getKey(), neigbhors);

    }

    /**
     *
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     * Adding new Edge to the graph.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if (src != dest) { //If src=dest nothing to be done.
            if (this.getNode(src) != null && this.getNode(dest) != null) {
                //Case 1: the edge doesn't exist, int this case create new edge.
                if (edges.get(src).get(dest) == null) {
                    EdgeData e = new Edge(src, dest, w);
                    edges.get(src).put(dest, e);
                    count++;
                    this.MC++;
                //Case 2: the edge exist but there's need to update the w.
                } else if (edges.get(src).get(dest).getWeight() != w) {
                    EdgeData e = new Edge(src, dest, w);
                    edges.get(src).replace(dest, e);
                    MC++;
                }
            }
        }
    }

    /**
     *
     * @return iterator on the nodes.
     */
    @Override
    public Iterator<NodeData> nodeIter() {
        return nodes.values().iterator();
    }

    /**
     *
     * @return iterator on the edge of the first node.
     */
    @Override
    public Iterator<EdgeData> edgeIter() {
        if (edges.isEmpty())
            return null;
        return edgeIter(0);
    }

    /**
     *
     * @param node_id
     * @return iterator of specific nodes edge.
     */
    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {

        return edges.get(node_id).values().iterator();
    }

    /**
     *
     * @param key
     * @return the node that removed.
     */
    @Override
    public NodeData removeNode(int key) {
        NodeData n = nodes.get(key);
        //Valid only if the node exist.
        if (n != null) {
            //Remove all the edges related to the node.
            //Here we delete all the edges that this node is the dest of them.
            Iterator<NodeData> k = nodeIter();
            while (k.hasNext()) {
                NodeData temp = k.next();
                if (edges.get(temp.getKey()).get(key) != null)
                    this.removeEdge(temp.getKey(), key);
            }
            //Here we delete all the edges that this node is the src of them.
            Iterator<EdgeData> e = edgeIter(key);
            while (e.hasNext()) {
                EdgeData temp = e.next();
                this.removeEdge(key, temp.getDest());
                e = edgeIter(key);
            }
            edges.remove(key, edges.get(key));
            nodes.remove(key, n);
            MC++;
        }
        return n;
    }

    /**
     *
     * @param src
     * @param dest
     * @return the edge that been removed.
     */
    @Override
    public EdgeData removeEdge(int src, int dest) {
        EdgeData e = this.getEdge(src, dest);
        if (e != null) {
            edges.get(src).remove(dest, e);
            count--;
            MC++;
        }
        return e;
    }

    /**
     *
     * @return the number of the nodes that in the graph.
     */
    @Override
    public int nodeSize() {
        return nodes.size();
    }

    /**
     *
     * @return the number of the edges that in the graph.
     */
    @Override
    public int edgeSize() {
        return count;
    }

    /**
     *
     * @return the mode count of the graph.
     */
    @Override
    public int getMC() {
        return MC;
    }

    /**
     *
     * @return representative String.
     */
    public String toString() {
        String ans = " ";
        Iterator<NodeData> e = nodeIter();
        while (e.hasNext()) {
            ans += e.next().getInfo();
        }

        return ans;
    }
}
