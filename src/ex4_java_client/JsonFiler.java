package ex4_java_client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class JsonFiler {
    public static JsonObject WriteToJson(DirectedWeightedGraph graph)
    {
        JsonObject  output=new JsonObject();
        JsonArray nodes=new JsonArray();
        JsonArray edges=new JsonArray();
        Iterator<NodeData>e= graph.nodeIter();
        while(e.hasNext())
        {
            JsonObject o=new JsonObject();
            NodeData n=e.next();
            o.addProperty("pos",n.getLocation().toString());
            o.addProperty("id",n.getKey());
            nodes.add(o);
        }
        for(int i=0;i< graph.nodeSize();i++) {
            Iterator<EdgeData> e1 = graph.edgeIter(i);
            while (e1.hasNext()) {
                JsonObject o = new JsonObject();
                EdgeData edge = e1.next();
                o.addProperty("src", edge.getSrc());
                o.addProperty("w", edge.getWeight());
                o.addProperty("dest", edge.getDest());
                edges.add(o);
            }
        }
        output.add("Edges",edges);
        output.add("Nodes",nodes);
        return output;



    }
    public static DirectedWeightedGraph ReadFromJson(String filename) throws IOException{
        DirectedWeightedGraph g = new DirectedGraph();
        JsonParser jsonParser = new JsonParser();
        try (FileReader reader = new FileReader(filename)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JsonObject jsonObject = (JsonObject) obj;
            JsonArray edges = (JsonArray) jsonObject.get("Edges");
            JsonArray nodes = (JsonArray) jsonObject.get("Nodes");
            Iterator<JsonElement> e = nodes.iterator();
            while (e.hasNext()) {
                JsonElement node = e.next();
                String pos = node.getAsJsonObject().get("pos").getAsString();
                int id = node.getAsJsonObject().get("id").getAsInt();
                String[] pos1 = pos.split(",");
                NodeData nodeData = new Node(Double.parseDouble(pos1[0]), Double.parseDouble(pos1[1]), Double.parseDouble(pos1[2]), id);
                g.addNode(nodeData);
            }
            e = edges.iterator();
            while (e.hasNext()) {
                JsonElement edge = e.next();
                int src = edge.getAsJsonObject().get("src").getAsInt();
                double w = edge.getAsJsonObject().get("w").getAsDouble();
                int dst = edge.getAsJsonObject().get("dest").getAsInt();
                g.connect(src, dst, w);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return g;
    }


}
