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
            System.out.println(edge.getSrc()+" "+ edge.getDest());
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

                if(agents.container.get(j).getSrc()==edge.getSrc())
                {
                    agents.container.get(j).path.add(edge.getDest());
                    //po.allocated=true;

                }
            }
        }
        client.start();
        int counter = 0;
        int allocations=1;
        System.out.println(client.getAgents());
        System.out.println(client.getPokemons());
        Thread.sleep(1000);
        long prev=0;//Long.parseLong(client.timeToEnd());
        int moves=0;
        System.out.println(gr.getGraph().getNode(9).getLocation().toString());
        System.out.println(gr.getGraph().getNode(8).getLocation().toString());
        while (client.isRunning().equals("true")) {
            //First we draw some graphics before handling the allocation.
            long start=System.currentTimeMillis();
            while (agents.isRun())
            {
                for(int i=0;i<agents.container.size();i++)
                {
                    Agent agent=agents.container.get(i);
                    if(agent.path.isEmpty() || agent.getDest()!=-1)
                        continue;
                    int next=agent.path.get(0);
                    agent.path.remove(0);
                    client.chooseNextEdge("{\"agent_id\":"+agent.getId()+", \"next_node_id\": " + next + "}");
                }
//                long end=System.currentTimeMillis();
//                if(end-start<=1000 && moves>=9)
//                {
//                    Thread.sleep(1000);
//                    moves=0;
//                }
                client.move();
                client.move();
                client.move();
                client.move();
                client.move();
                client.move();
                client.move();
                client.move();
                client.move();
                client.move();
                System.out.println(client.getAgents());
                moves++;
            }

            agents.update(client.getAgents());
            pokemons.update(client.getPokemons());

//            long current=Long.parseLong(client.timeToEnd());
//            if(allocations==0)
//                continue;
//            if(current-prev>=1000 && counter>=9) {
//                System.out.println("sleep");
//                Thread.sleep(1000);
//                counter=0;
//            }

        }

    }
}

