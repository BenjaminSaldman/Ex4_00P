package ex4_java_client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class contains the GUI of the project.
 */
public class paint extends JFrame implements ActionListener {

    double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth(); //Dimension of screen.
    double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    PokemonContainer pokemons;
    AgentContainer agents;
    JLabel text; //game info.
    JButton stop; // stop button.
    Client client;
    panel panel_; // JPanel.
    DirectedWeightedGraphAlgorithms gr;

    public paint(Client client) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize((int) WIDTH, (int) HEIGHT);
        this.client = client;
        pokemons = new PokemonContainer();
        agents = new AgentContainer();
        pokemons.update(client.getPokemons());
        agents.update(client.getAgents());
        gr = new GraphAlgo();
        gr.load("temp.json");
        text = new JLabel();
        stop = new JButton("STOP");
        panel_ = new panel(gr.getGraph(), pokemons, agents);
        stop.setBounds((int) WIDTH - 250, (int) (HEIGHT / 2), 222, 20);
        text.setBounds((int) WIDTH - 250, (int) HEIGHT / 2 - 50, 222, 20);
        stop.addActionListener(this);
        add(stop);
        add(text);
        this.add(panel_);
        this.setResizable(true);
        this.setVisible(true);
        /**
         * Listener to the window resize.
         */
        this.getRootPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                WIDTH = paint.this.getWidth();
                HEIGHT = paint.this.getHeight();
                paint.this.setSize((int) WIDTH, (int) HEIGHT);
                paint.this.repaint();
                stop.setBounds((int) WIDTH - 250, (int) (HEIGHT / 2), 222, 20);
                text.setBounds((int) WIDTH - 250, (int) HEIGHT / 2 - 50, 222, 20);
            }
        });


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * Listener to the stop button.
         */
        if (e.getSource() == stop) {
            this.update();
            client.stop();
        }

    }

    /**
     * Updating the agent container, pokemon container and the screen.
     */
    public void update() {
        agents.update(client.getAgents());
        pokemons.update(client.getPokemons());
        panel_.update();
        panel_.repaint();
        JsonObject info = JsonParser.parseString(client.getInfo()).getAsJsonObject();
        int moves = info.get("GameServer").getAsJsonObject().get("moves").getAsInt();
        double grade = info.get("GameServer").getAsJsonObject().get("grade").getAsDouble();
        long time = Long.parseLong(client.timeToEnd()) / 1000;
        text.setText("TTL: " + time + " Grade: " + grade + " Moves: " + moves);
        this.repaint();

    }

    public class panel extends JPanel {
        /**
         * This class paints the pokemons, agents and the graph.
         */

        PokemonContainer p;
        AgentContainer a;
        DirectedWeightedGraph graph;
        LinkedList<Point2D> points;
        ArrayList<Integer> Id;

        public panel(DirectedWeightedGraph g, PokemonContainer p, AgentContainer a) {
            this.graph = g;
            this.p = p;
            this.a = a;
            points = new LinkedList<Point2D>();
            Iterator<NodeData> n = g.nodeIter();
            Id = new ArrayList<Integer>();
            while (n.hasNext()) {
                NodeData next = n.next();
                this.points.add(new Point2D.Double(next.getLocation().x(), next.getLocation().y()));
                Id.add(next.getKey());
            }
        }

        public void update() {
            p = pokemons;
            a = agents;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int prevID = 0; //ID of current node.
            int k = 0; //ID counter.
            int m = 0; //ID counter.
            for (Point2D p : points) {
                //Print all the nodes in black color.
                g.setColor(Color.BLACK);
                g.fillOval((int) (scale(p.getX(), minMax()[0], minMax()[1], 0, paint.this.WIDTH / 5 + paint.this.WIDTH / 2) - 5), (int) (scale(p.getY(), minMax()[2], minMax()[3], 0, paint.this.HEIGHT / 5 + paint.this.HEIGHT / 2) - 5), 10, 10);
                //Print the node ID
                String ID = Id.get(k++) + "";
                g.drawString(ID, (int) (scale(p.getX(), minMax()[0], minMax()[1], 0, paint.this.WIDTH / 5 + paint.this.WIDTH / 2)) + 10, (int) (scale(p.getY(), minMax()[2], minMax()[3], 0, paint.this.HEIGHT / 5 + paint.this.HEIGHT / 2) + 5));
                for (Point2D l : points) {
                    //Run from current node to all other nodes and check if there is an edge between the nodes.
                    if (l != p) {
                        if ((this.graph.getEdge(Id.get(prevID), Id.get(m)) != null) || graph.getEdge(Id.get(m), Id.get(prevID)) != null) {
                            double weight = 0;
                            if (graph.getEdge(Id.get(prevID), Id.get(m)) != null)
                                weight = graph.getEdge(Id.get(prevID), Id.get(m)).getWeight();
                            else
                                weight = graph.getEdge(Id.get(m), Id.get(prevID)).getWeight();
                            String w = weight + "";
                            g.drawLine((int) (scale(p.getX(), minMax()[0], minMax()[1], 0, paint.this.WIDTH / 5 + paint.this.WIDTH / 2)), (int) (scale(p.getY(), minMax()[2], minMax()[3], 0, paint.this.HEIGHT / 5 + paint.this.HEIGHT / 2)),
                                    (int) (scale(l.getX(), minMax()[0], minMax()[1], 0, paint.this.WIDTH / 5 + paint.this.WIDTH / 2)), (int) (scale(l.getY(), minMax()[2], minMax()[3], 0, paint.this.HEIGHT / 5 + paint.this.HEIGHT / 2)));
//                            g.drawString(w,((int) (scale(p.getX(), minMax()[0], minMax()[1], 0, GUI_graph.this.WIDTH / 5 + GUI_graph.this.WIDTH / 2)+(int) (scale(l.getX(), minMax()[0], minMax()[1], 0, GUI_graph.this.WIDTH / 5 + GUI_graph.this.WIDTH / 2))))/2,
//                                    ((int) (scale(p.getY(), minMax()[2], minMax()[3], 0, GUI_graph.this.HEIGHT / 5 + GUI_graph.this.HEIGHT / 2)+(int) (scale(l.getY(), minMax()[2], minMax()[3], 0, GUI_graph.this.HEIGHT / 5 + GUI_graph.this.HEIGHT / 2))))/2  ) ;
                        }

                    }
                    m++;
                }
                m = 0; //Node ID counter=0;
                prevID = k; //Current node for iteration.
            }
            g.setColor(Color.RED);
            for (int i = 0; i < p.p.size(); i++) {
                Pokemon po = p.p.get(i);
                Point2D p = new Point2D.Double(po.getLocation().x(), po.getLocation().y());
                g.fillOval((int) (scale(p.getX(), minMax()[0], minMax()[1], 0, paint.this.WIDTH / 5 + paint.this.WIDTH / 2) - 5), (int) (scale(p.getY(), minMax()[2], minMax()[3], 0, paint.this.HEIGHT / 5 + paint.this.HEIGHT / 2) - 5), 10, 10);
            }
            g.setColor(Color.BLUE);
            for (int i = 0; i < a.container.size(); i++) {
                Agent ag = a.container.get(i);
                Point2D p = new Point2D.Double(ag.getLocation().x(), ag.getLocation().y());
                g.fillOval((int) (scale(p.getX(), minMax()[0], minMax()[1], 0, paint.this.WIDTH / 5 + paint.this.WIDTH / 2) - 5), (int) (scale(p.getY(), minMax()[2], minMax()[3], 0, paint.this.HEIGHT / 5 + paint.this.HEIGHT / 2) - 5), 10, 10);
            }

        }


        /**
         * @param data
         * @param r_min
         * @param r_max
         * @param t_min
         * @param t_max
         * @return scaled location.
         */
        private double scale(double data, double r_min, double r_max,
                             double t_min, double t_max) {
            double res = ((data - r_min) / (r_max - r_min)) * (t_max - t_min) + t_min;

            if (res == 0)
                return res + 10;
            return res;


        }

        /**
         * @return array that contains the minimum X,Y and maximum X,Y among the nodes.
         */
        private double[] minMax() {
            double[] ans = new double[4];
            double xMax = 0, xMin = Double.MAX_VALUE, yMax = 0, yMin = Double.MAX_VALUE;
            for (Point2D p : points) {
                if (p.getX() < xMin) {
                    xMin = p.getX();
                    ans[0] = p.getX();
                }
                if (p.getX() > xMax) {
                    xMax = p.getX();
                    ans[1] = p.getX();
                }
                if (p.getY() < yMin) {
                    yMin = p.getY();
                    ans[2] = p.getY();
                }
                if (p.getY() > yMax) {
                    yMax = p.getY();
                    ans[3] = p.getY();
                }
            }
            return ans;
        }
    }
}
