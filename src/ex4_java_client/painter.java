package ex4_java_client;

public class painter implements Runnable{
    Client client;
    paint p;
    public painter(Client client)
    {
        this.client=client;
        p=new paint(client);
    }
    @Override
    public void run() {
        while (client.isRunning().equals("true"))
        {
            synchronized (this) {
                p.update();
            }
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
