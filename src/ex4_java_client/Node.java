package ex4_java_client;


public class Node implements NodeData{
    private int tag;
    private int ID;
    private double weight;
    private GeoLocation location;
    private String Info;
    private int prev;
    public Node(double x, double y, double z, int ID)
    {
        location=new Location(x,y,z);
        this.ID=ID;
        weight=0;
        tag=0;
        Info="ID: "+ID+" The Location of this node is in: "+location.toString()+ "Personal tag: ";
    }
    public Node (NodeData n)
    {
        this.ID=n.getKey();
        this.weight=n.getWeight();
        this.Info=n.getInfo();
        this.location=n.getLocation();
        Info=n.getInfo();
    }

    @Override
    public int getKey() {
        return ID;
    }

    @Override
    public GeoLocation getLocation() {
        return new Location(location.x(),location.y(),location.z());
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.location=new Location(p.x(),p.y(),p.z());
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double w) {
        weight=w;
    }

    @Override
    public String getInfo() {
        return Info;
    }

    @Override
    public void setInfo(String s) {
        Info=s;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
        tag=t;
    }
    public void setPrev(int prev){
        this.prev=prev;

    }
}