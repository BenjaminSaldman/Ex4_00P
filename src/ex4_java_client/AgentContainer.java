package ex4_java_client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Iterator;

public class AgentContainer {
     ArrayList<Agent> container;

    public AgentContainer() {
        container = new ArrayList<Agent>();
    }

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
    public boolean isRun()
    {
        for(int i=0;i<container.size();i++)
        {
            if(!container.get(i).path.isEmpty())
                return true;
        }
        return false;
    }
}
