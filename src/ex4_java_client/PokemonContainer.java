package ex4_java_client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Iterator;

public class PokemonContainer {
     ArrayList<Pokemon>p;
    public PokemonContainer()
    {
        this.p=new ArrayList<Pokemon>();
    }
    public void update(String json)
    {
        ArrayList<Pokemon>temp=new ArrayList<>();
        JsonObject obj;
        try {
            obj= JsonParser.parseString(json).getAsJsonObject();
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        JsonArray agents = (JsonArray) obj.get("Pokemons");
        Iterator<JsonElement> e = agents.iterator();
        while (e.hasNext())
        {
            JsonElement pokemon = e.next();
            double value=pokemon.getAsJsonObject().get("Pokemon").getAsJsonObject().get("value").getAsDouble();
            int type=pokemon.getAsJsonObject().get("Pokemon").getAsJsonObject().get("type").getAsInt();
            String pos=pokemon.getAsJsonObject().get("Pokemon").getAsJsonObject().get("pos").getAsString();
            String[] pos1 = pos.split(",");
            GeoLocation location=new Location(Double.parseDouble(pos1[0]), Double.parseDouble(pos1[1]), Double.parseDouble(pos1[2]));
            Pokemon poke=new Pokemon(value,type,location);
            for(int i=0;i<p.size();i++)
            {
                if(p.get(i).getLocation().distance(poke.getLocation())==0)
                    poke.allocated=p.get(i).allocated;
            }
            temp.add(poke);
        }
        p.removeAll(p);
        for(int i=0;i<temp.size();i++)
            p.add(temp.get(i));
    }
    public Pokemon getMax()
    {
        double max=0;
        int index=0;
        for(int i=0;i<p.size();i++)
        {
            if(!(p.get(i).allocated)&&p.get(i).getValue()>max)
            {
                max=p.get(i).getValue();
                index=i;
            }
        }
        this.p.get(index).allocated=true;
        return this.p.get(index);
    }
    public boolean isEmpty()
    {
        for(int i=0;i<p.size();i++)
        {
            if(p.get(i).allocated==false)
                return false;
        }
        return true;
    }
    public void setAllocFalse()
    {
        for(int i=0;i<p.size();i++)
        {
            p.get(i).allocated=false;
        }
    }
    public int size()
    {
        return p.size();
    }
}
