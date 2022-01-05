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
        }
        pokemons.setAllocFalse();
        agents.update(client.getAgents());
        for(int i=0;i<p.size();i++)
        {
            Pokemon po = pokemons.getMax();
            EdgeData edge = gr.getEd(po);
            for(int j=0;j<agents.container.size();j++)
            {

                if(agents.container.get(i).getSrc()==edge.getSrc())
                {
                    agents.container.get(i).path.add(edge.getDest());
                    po.allocated=true;

                }
            }
        }
        client.start();
        int counter = 0;
        int allocations=0;
        double min=Double.MAX_VALUE;
        Agent curr=null;
        while (client.isRunning().equals("true")) {
            //First we draw some graphics before handling the allocation.
            long prev=Long.parseLong(client.timeToEnd());
            agents.update(client.getAgents());
            pokemons.update(client.getPokemons());
            for(int i=0;i<agents.container.size();i++)
            {
                Agent agent=agents.container.get(i);
                if(agent.getDest()!=-1 || agent.path.isEmpty())
                    continue;
                int next=agent.path.get(0);
                agent.path.remove(0);
                client.chooseNextEdge("{\"agent_id\":"+agent.getId()+", \"next_node_id\": " + next + "}");
                allocations++;
            }
//
//            //Allocations of agents:
//            while(!pokemons.isEmpty())
//            {
//                Pokemon pokemon = pokemons.getMax();
//                EdgeData edge=gr.getEd(pokemon);
//                for(int i=0;i<agents.container.size();i++)
//                {
//                    Agent agent=agents.container.get(i);
//                    if(gr.shortestPathDist(agent.getLast(), edge.getSrc(), agent.getSpeed())<min)
//                    {
//                        min=gr.shortestPathDist(agent.getLast(), edge.getSrc(), agent.getSpeed());
//                        curr=agent;
//                    }
//                }
//                if(curr!=null)
//                {
//                    List<NodeData>l=gr.shortestPath(curr.getLast(), edge.getSrc(),curr.getSpeed());
//                    for(int j=0;j<l.size();j++)
//                        curr.path.add(l.get(j).getKey());
//                    curr.path.add(edge.getDest());
//                }
//            }
            System.out.println(client.getAgents());
            long current=Long.parseLong(client.timeToEnd());
            if(allocations==0)
                continue;
            if(current-prev>=1000 && counter>=9) {
                Thread.sleep(1000);
                counter=0;
            }
            client.move();
            counter++;
            allocations=0;

        }

    }
}

