package ex4_java_client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represent an agent container.
 */
public class AgentContainer {
     ArrayList<Agent> container;

    public AgentContainer() {
        container = new ArrayList<Agent>();
    }
    public int size()
    {
        return container.size();
    }

    /**
     *
     * @param json
     * sets the container with the server agent data.
     */
    public void update(String json) {
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
        JsonArray agents = (JsonArray) obj.get("Agents");
        Iterator<JsonElement> e = agents.iterator();
        while (e.hasNext()) {
            JsonElement agent = e.next();
            int id = agent.getAsJsonObject().get("Agent").getAsJsonObject().get("id").getAsInt();
            double value = agent.getAsJsonObject().get("Agent").getAsJsonObject().get("value").getAsDouble();
            int src = agent.getAsJsonObject().get("Agent").getAsJsonObject().get("src").getAsInt();
            int dest = agent.getAsJsonObject().get("Agent").getAsJsonObject().get("dest").getAsInt();
            double speed = agent.getAsJsonObject().get("Agent").getAsJsonObject().get("speed").getAsDouble();
            String pos = agent.getAsJsonObject().get("Agent").getAsJsonObject().get("pos").getAsString();
            String[] pos1 = pos.split(",");
            GeoLocation location=new Location(Double.parseDouble(pos1[0]), Double.parseDouble(pos1[1]), Double.parseDouble(pos1[2]));
            Agent a=new Agent(id,value,src,dest,speed,location);
            boolean added=true;
            for(int i=0;i< container.size();i++)
            {
                if(container.get(i).getId()==a.getId()) {
                    a.path=container.get(i).path;
                    a.isAllocated=container.get(i).isAllocated;
                    a.target=container.get(i).target;
                    container.remove(i);
                    container.add(i, a);
                    added=false;
                }
            }
            if(added)
            {
                container.add(a);
            }
        }
    }

    /**
     *
     * @return true if there there is an agent that not finished his travel, else false.
     */
    public boolean isRun()
    {
        if(container.size()>1)
        {
            for(int i=0;i<container.size();i++)
            {
                if(container.get(i).path.isEmpty())// || container.get(i).getDest()==-1)
                    return false;
            }
        }
        for(int i=0;i<container.size();i++)
        {
            if(!container.get(i).path.isEmpty() || container.get(i).getDest()!=-1)
                return true;
        }
        return false;
    }
}
