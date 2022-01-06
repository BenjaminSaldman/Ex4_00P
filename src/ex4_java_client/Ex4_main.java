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

public class Ex4_main {
    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        final double EPS=0.001;
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
        gr.load(file);
        String age = client.getAgents();
        String poke = client.getPokemons();
        AgentContainer agents = new AgentContainer();
        PokemonContainer pokemons = new PokemonContainer();
        JsonObject info = JsonParser.parseString(client.getInfo()).getAsJsonObject();
        int size = info.get("GameServer").getAsJsonObject().get("agents").getAsInt();
        int save=size;
        //long time=Long.parseLong(client.timeToEnd());
        pokemons.update(poke);
        ArrayList<Pokemon> p = pokemons.p;
        ArrayList<Agent> a = agents.container;
        while (!pokemons.isEmpty() && size>0) {

            Pokemon po = pokemons.getMax();
            po.allocated=true;
            EdgeData edge = gr.getEd(po);
            client.addAgent("{\"id\":" + edge.getSrc() + "}");
            --size;
        }
        while(size>0)
        {
            client.addAgent("{\"id\":0}");
            size--;
        }
        pokemons.setAllocFalse();
        agents.update(client.getAgents());

        for(int i=0;i<p.size()&&save>0;i++)
        {
            Pokemon po = pokemons.getMax();
            EdgeData edge = gr.getEd(po);
            for(int j=0;j<agents.container.size();j++)
            {

                if(agents.container.get(j).getSrc()==edge.getSrc())
                {
                    agents.container.get(j).path.add(edge.getDest());
                    agents.container.get(j).target= po.getLocation();
                    save--;
                }
            }
        }
        client.start();
        int moves=0;
        int allocations=0;
        size=info.get("GameServer").getAsJsonObject().get("agents").getAsInt();
        while (client.isRunning().equals("true")) {
            //First we draw some graphics before handling the allocation.

            while (agents.isRun())
            {
                for(int i=0;i<agents.container.size();i++)
                {
                    Agent agent=agents.container.get(i);
                    if(agent.path.isEmpty() || agent.getDest()!=-1)
                        continue;
                    System.out.println("...");
                    int next=agent.path.get(0);
                    agents.container.get(i).path.remove(0);
                    client.chooseNextEdge("{\"agent_id\":"+agent.getId()+", \"next_node_id\": " + next + "}");
                    if(agent.path.isEmpty()) {
                        double dist=agent.getLocation().distance(agent.target);
                        System.out.println(dist);
                        client.move();
                        moves++;
                        while (dist>EPS)
                        {
                            Thread.sleep((long) (100000*dist)); //100000*dist
                            agents.update(client.getAgents());
                            agent=agents.container.get(i);
                            dist=agent.getLocation().distance(agent.target);
                            client.move();
                            moves++;
                        }
                        client.move();
                        agents.container.get(i).isAllocated=false;
//                        if(moves>=9)
//                        {
//                            Thread.sleep(1000);
//                            moves=0;
//                        }
                    }
                    else
                        allocations++;

                    if(moves>=9)
                    {
                        Thread.sleep(1000);
                        moves=0;
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

            }
            long TTL=Long.parseLong(client.timeToEnd());
            if(TTL<500)
                break;
            agents.update(client.getAgents());
            pokemons.update(client.getPokemons());
            size=info.get("GameServer").getAsJsonObject().get("agents").getAsInt();
            for(int i=0;i<pokemons.p.size()&&size>0;i++)
            {
                Pokemon po = pokemons.getMax();
                EdgeData edge = gr.getEd(po);
                if(size==1)
                {

                    Agent agent=agents.container.get(0);
                    if(agent.isAllocated)
                        break;
                    System.out.println(gr.shortestPathDist(agent.getSrc(), edge.getSrc(), agent.getSpeed()));
                    System.out.println(agent.getSrc());
                    System.out.println(edge.getSrc());
                    System.out.println(agent.getSpeed());
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
                for(int j=0;j<agents.container.size();j++)
                {
                    Agent agent=agents.container.get(j);
                    if(agent.isAllocated)
                        continue;
                    if(gr.shortestPathDist(agent.getSrc(), edge.getSrc(), agent.getSpeed())<min)
                    {
                        min=gr.shortestPathDist(agent.getSrc(), edge.getSrc(), agent.getSpeed());
                        curr=agent;
                    }
                    List<NodeData>l=gr.shortestPath(agent.getSrc(), edge.getSrc(), agent.getSpeed());
                    for(NodeData n:l)
                        curr.path.add(n.getKey());
                    curr.path.add(edge.getDest());
                    curr.isAllocated=true;
                    po.allocated=true;
                }
            }



        }

    }
}

