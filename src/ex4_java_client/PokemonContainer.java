package ex4_java_client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Iterator;

public class PokemonContainer {
    private ArrayList<Pokemon>p;
    public PokemonContainer()
    {
        this.p=new ArrayList<Pokemon>();
    }
    public void update(String json)
    {
        this.p.removeAll(this.p);
        //this.p=new ArrayList<Pokemon>();
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
        JsonArray agents = (JsonArray) obj.get("Pokemons");
        Iterator<JsonElement> e = agents.iterator();
        while (e.hasNext())
        {
            JsonElement pokemon = e.next();
            double value=pokemon.getAsJsonObject().get("value").getAsDouble();
            int type=pokemon.getAsJsonObject().get("type").getAsInt();
            String pos=pokemon.getAsJsonObject().get("pos").getAsString();
            String[] pos1 = pos.split(",");
            GeoLocation location=new Location(Double.parseDouble(pos1[0]), Double.parseDouble(pos1[1]), Double.parseDouble(pos1[2]));
            Pokemon poke=new Pokemon(value,type,location);
            p.add(poke);

        }
    }
}
