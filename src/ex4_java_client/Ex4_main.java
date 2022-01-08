package ex4_java_client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Main program, creates the connections to the socket, run the algorithm and the GUI.
 */
public class Ex4_main {
    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        final double EPS=0.001; //Epsilon for distance.
        try {
            client.startConnection("127.0.0.1", 6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String graph = client.getGraph();
        String file = "temp.json";
        try (FileWriter File = new FileWriter(file)) {
            File.write(graph);
            File.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DirectedWeightedGraphAlgorithms gr = new GraphAlgo();
        gr.load(file); //Creating the graph.
        String poke = client.getPokemons();
        AgentContainer agents = new AgentContainer();
        PokemonContainer pokemons = new PokemonContainer();
        JsonObject info = JsonParser.parseString(client.getInfo()).getAsJsonObject();
        int size = info.get("GameServer").getAsJsonObject().get("agents").getAsInt();
        int save=size;
        pokemons.update(poke);
        /**
         * Allocating agents to the server near the most valued pokemons.
         */
        for(int i=0;i<pokemons.p.size() && size>0;i++,size--)
        {
            Pokemon po = pokemons.getMax();
            po.allocated=true;
            EdgeData edge = gr.getEd(po);
            client.addAgent("{\"id\":" + edge.getSrc() + "}");
        }
        /**
         * Default allocation.
         */
        while(size>0)
        {
            client.addAgent("{\"id\":0}");
            size--;
        }
        pokemons.setAllocFalse();
        agents.update(client.getAgents());
        /**
         * Update the agent container
         */
        for(int i=0;i<pokemons.p.size()&&save>0;i++)
        {
            Pokemon po = pokemons.getMax();
            EdgeData edge = gr.getEd(po);
            for(int j=0;j<agents.container.size();j++)
            {
                if(agents.container.get(j).getSrc()==edge.getSrc())
                {
                    agents.container.get(j).path.add(edge.getDest());
                    agents.container.get(j).target= po.getLocation();
                    pokemons.p.get(i).allocated=true;
                    save--;
                }
            }
        }

        int moves=0; // Allowed moves.
        int onlyOne=info.get("GameServer").getAsJsonObject().get("agents").getAsInt(); // 1 agent case.
        paint p=new paint(client); //GUI.
        client.start();
        while (client.isRunning().equals("true")) {
            /**
             * While the locations are not Empty.
             */
            while (agents.isRun())
            {
                for(int i=0;i<agents.container.size();i++)
                {
                    Agent agent=agents.container.get(i);
                    //If the agent's destination is -1, or he is not allocated continue.
                    if(agent.path.isEmpty() || agent.getDest()!=-1)
                        continue;
                    int next=agent.path.get(0);
                    agents.container.get(i).path.remove(0);
                    client.chooseNextEdge("{\"agent_id\":"+agent.getId()+", \"next_node_id\": " + next + "}");
                    if(agent.path.isEmpty()) {

                        client.move();
                        agents.update(client.getAgents());
                        agent=agents.container.get(i);
                        moves++;
                        double dist=agent.getLocation().distance(agent.target);
                        while (dist>EPS && agent.getDest()!=-1)
                        {
                            //sleep to avoid move() calls.
                            Thread.sleep((long) (100000*dist/2)); //100000*dist/2
                            agents.update(client.getAgents());
                            agent=agents.container.get(i);
                            dist=agent.getLocation().distance(agent.target);
                            client.move();
                            moves++;
                            p.update();
                        }
                        agents.container.get(i).isAllocated=false;
                        p.update();
                    }

                }
                if(moves>=9)
                {
                    Thread.sleep(1000);
                    moves=0;
                }
                client.move();
                moves++;
                agents.update(client.getAgents());
                p.update();

            }
            agents.update(client.getAgents());
            pokemons.update(client.getPokemons());
            size=info.get("GameServer").getAsJsonObject().get("agents").getAsInt();
            //Updating new pokemon to each agent.
            for(int i=0;i<pokemons.p.size()&&size>0;i++,size--)
            {
                Pokemon po = pokemons.getMax();
                EdgeData edge = gr.getEd(po);
                if(onlyOne==1)
                {

                    Agent agent=agents.container.get(0);
                    if(agent.isAllocated)
                        break;
                    List<NodeData>l=gr.shortestPath(agent.getSrc(), edge.getSrc(), agent.getSpeed());
                    for(NodeData n:l)
                        agents.container.get(0).path.add(n.getKey());
                    agents.container.get(0).path.add(edge.getDest());
                    agents.container.get(0).isAllocated=true;
                    agents.container.get(0).target= po.getLocation();
                    break;
                }
                double min=Double.MAX_VALUE;
                Agent curr=null;
                int index=0;
                for(int j=0;j<agents.container.size();j++)
                {
                    Agent agent=agents.container.get(j);
                    if(agent.isAllocated)
                        continue;
                    if(gr.shortestPathDist(agent.getSrc(), edge.getSrc(), agent.getSpeed())<min)
                    {
                        min=gr.shortestPathDist(agent.getSrc(), edge.getSrc(), agent.getSpeed());
                        curr=agent;
                        index=j;
                    }
                    if(curr == null)
                        break;
                    List<NodeData>l=gr.shortestPath(agent.getSrc(), edge.getSrc(), agent.getSpeed());
                    for(NodeData n:l)
                        curr.path.add(n.getKey());
                    curr.path.add(edge.getDest());
                    curr.isAllocated=true;
                    curr.target= po.getLocation();
                    agents.container.get(index).path=curr.path;
                    agents.container.get(index).isAllocated= curr.isAllocated;
                    agents.container.get(index).target=curr.target;

                }
            }



        }

    }
}

