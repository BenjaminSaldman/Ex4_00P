package ex4_java_client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.util.*;

/////////////////////////////////////////////////////////////////////////////////////////////////////////
public class GraphAlgo implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph g;
    public static final double EPS=0.001;
    /**
     * @param src run Dijkstra algorithm on the graph.
     */
    private void Dijkstra(int src,double speed) {
        Iterator<NodeData> e = this.g.nodeIter();
        /**
         * Set all distances to infinity.
         */
        while (e.hasNext()) {
            e.next().setWeight(Double.MAX_VALUE);
        }
        /**
         * Create PriorityQueue that saves the nodes by distance.
         */
        PriorityQueue<NodeData> q = new PriorityQueue<>(new Comparator<NodeData>() {
            @Override
            public int compare(NodeData o1, NodeData o2) {
                return Double.compare(o1.getWeight(), o2.getWeight());
            }
        });
        //The distance of the source is 0.
        this.g.getNode(src).setWeight(0);
        q.add(this.g.getNode(src));
        while (!q.isEmpty()) {
            NodeData n = q.poll();  //get the next node with the minimal distance from src.
            Iterator<EdgeData> ed = this.g.edgeIter(n.getKey());
            while (ed.hasNext()) { //for every neighbor of n do relaxation if needed.
                EdgeData edge = ed.next();
                NodeData neighbor = this.g.getNode(edge.getDest());
                double alt = (n.getWeight() + edge.getWeight())/speed;
                if ((neighbor.getWeight())/speed > alt) {
                    neighbor.setWeight(alt);
                    neighbor.setTag(n.getKey());
                    if (!q.contains(neighbor))
                        q.add(neighbor);
                }

            }
        }
    }

    public GraphAlgo() {
        this.g = new DirectedGraph();
    }
    public GraphAlgo(DirectedWeightedGraph g){this.g=g;}

    @Override
    public void init(DirectedWeightedGraph g) {
        this.g = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.g;
    }

    @Override
    public DirectedWeightedGraph copy() {
        DirectedWeightedGraph g1 = new DirectedGraph(this.g);
        return g1;
    }
    /**
     * @param g Initialize the nodes status to not visited.
     */
    private void InitNotVisited(DirectedWeightedGraph g) {
        Iterator<NodeData> e = g.nodeIter();
        while (e.hasNext()) {
            NodeData n = e.next();
            n.setTag(0);
        }
    }

    /**
     * @return true if the graph is strongly connected.
     * Using BFS 2 times one on the original graph and the other to the transpose graph..
     */
    @Override
    public boolean isConnected() {
        if (this.g == null)
            return true;
        if (this.g.nodeSize() <= 1)
            return true;
        if (this.g.nodeSize() > this.g.edgeSize() + 1)
            return false;
        InitNotVisited(this.g);
        BFS(0, this.g);
        Iterator<NodeData> e = this.g.nodeIter();
        while (e.hasNext()) {
            NodeData n = e.next();
            if (n.getTag() == 0)
                return false;
        }
        DirectedWeightedGraph tr = transpose(this.g);
        InitNotVisited(tr);
        BFS(0, tr);
        e = tr.nodeIter();
        while (e.hasNext()) {
            NodeData n = e.next();
            if (n.getTag() == 0)
                return false;
        }
        return true;
    }

    /**
     * @param start
     * @param g     BFS searches on given graph.
     */
    private void BFS(int start, DirectedWeightedGraph g) {
        Queue<NodeData> q = new LinkedList<NodeData>();
        NodeData n = g.getNode(start);
        q.add(n);
        n.setTag(1);
        while (!q.isEmpty()) {
            n = q.poll();
            Iterator<EdgeData> e = g.edgeIter(n.getKey());
            while (e.hasNext()) {
                EdgeData edge = e.next();
                if (g.getNode(edge.getDest()).getTag() != 1) {
                    g.getNode(edge.getDest()).setTag(1);
                    q.add(g.getNode(edge.getDest()));
                }
            }
        }
    }

    /**
     * @param graph
     * @return the transpose graph.
     */
    private DirectedWeightedGraph transpose(DirectedWeightedGraph graph) {
        DirectedWeightedGraph tr = new DirectedGraph();
        Iterator<NodeData> e = graph.nodeIter();
        while (e.hasNext()) {
            tr.addNode(e.next());
        }
        e = tr.nodeIter();
        while (e.hasNext()) {
            NodeData n = e.next();
            Iterator<EdgeData> i = graph.edgeIter(n.getKey());
            while (i.hasNext()) {
                EdgeData ed = i.next();
                tr.connect(ed.getDest(), ed.getSrc(), ed.getWeight());
            }
        }
        return tr;
    }

    /**
     * @param src  - start node
     * @param dest - end (target) node
     * @return shortestPathDist between the source to the destination.
     */
    @Override
    public double shortestPathDist(int src, int dest,double speed) {
        if(src==dest)
            return 0;
        Dijkstra(src,speed);
        /**
         * The weight of the Node with the id "dest" is the distance of the path.
         */
        if (this.g.getNode(dest).getWeight() >= Double.MAX_VALUE) //No such path.
            return -1;
        return this.g.getNode(dest).getWeight();
    }

    /**
     * @param src  - start node
     * @param dest - end (target) node
     * @return shortestPath between src to dest.
     */
    @Override
    public List<NodeData> shortestPath(int src, int dest,double speed) {
        Dijkstra(src,speed);
        List<NodeData> ans = new LinkedList<>();
        if(src==dest)
        {
            ans.add(this.g.getNode(src));
            return ans;
        }
        if (this.g.getNode(dest).getWeight() == -1)
            return null;
        /**
         * The algorithm sets in every node tag the ID of his parent in the path.
         */
        NodeData reversed = this.g.getNode(dest);
        while (reversed != this.g.getNode(src)) {
            ans.add(0, reversed);
            reversed = this.g.getNode(reversed.getTag());
        }
        //ans.add(0, this.g.getNode(src));
        return ans;
    }

    /**
     * @return the center of the graph.
     */
    @Override
    public NodeData center() {
        double ave = 0;
        double min = Double.MAX_VALUE;
        NodeData ans = null;
        Iterator<NodeData> e = this.g.nodeIter();
        double currMax = 0;
        while (e.hasNext()) {
            NodeData curr = e.next();
            Iterator<NodeData> e2 = this.g.nodeIter();
            while (e2.hasNext()) {
                NodeData next = e2.next();
                ave = shortestPathDist(curr.getKey(), next.getKey(),1);
                if (ave > currMax) {
                    currMax = ave;
                }
            }
            if (currMax < min) {
                min = currMax;
                ans = curr;
            }
            currMax = 0;
        }
        return ans;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        if (cities.isEmpty())
            return null;
        List<NodeData> ans = new LinkedList<NodeData>();
        for (int i = 0; i < cities.size() - 1; i++) {
            NodeData curr = cities.get(i);
            NodeData next = cities.get(i + 1);
            List<NodeData> l = shortestPath(curr.getKey(), next.getKey(),1);
            for (NodeData n : l)
                ans.add(n);
        }

        return ans;
    }

    @Override
    public boolean save(String file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(g);
        JsonObject finalOutput = JsonFiler.WriteToJson(g);
        try (FileWriter File = new FileWriter(file)) {
            File.write(gson.toJson(finalOutput));
            File.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        try {
            this.g = JsonFiler.ReadFromJson(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }
    }

    @Override
    public boolean calc_edge(int p1, int p2, double x2, double y2) {
        double x1=this.g.getNode(p1).getLocation().x(),y1=this.g.getNode(p1).getLocation().y();
        double x3=this.g.getNode(p2).getLocation().x(),y3=this.g.getNode(p2).getLocation().y();
        double dist1=Math.sqrt((Math.pow((x2-x1),2)+Math.pow((y2-y1),2)));
        double dist2=Math.sqrt((Math.pow((x2-x3),2)+Math.pow((y2-y3),2)));
        if((dist1+dist2)-(this.g.getNode(p1).getLocation().distance(this.g.getNode(p2).getLocation()))<EPS)
            return true;
        return false;
    }

    @Override
    public EdgeData getEd(Pokemon pokemon) {
        Iterator<NodeData>e=this.g.nodeIter();
        int type=pokemon.getType();
        double x=pokemon.getLocation().x();
        double y=pokemon.getLocation().y();
        while (e.hasNext())
        {
            NodeData n=e.next();
            Iterator<EdgeData>ed=this.g.edgeIter(n.getKey());
            while(ed.hasNext())
            {
                EdgeData edge=ed.next();
                if(calc_edge(edge.getSrc(), edge.getDest(),x,y));
                {
                    int max=Math.max(edge.getSrc() ,edge.getDest());
                    int min=Math.min(edge.getSrc() ,edge.getDest());
                    if(type>=0)
                        return this.g.getEdge(min,max);
                    else
                        return this.g.getEdge(max,min);
                }
            }
        }
        return null;
    }


}
