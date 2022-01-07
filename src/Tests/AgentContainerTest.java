package Tests;

import ex4_java_client.AgentContainer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgentContainerTest {

    @Test
    void update() {
        String json="{\"Agents\":[{\"Agent\":{\"id\":0,\"value\":0.0,\"src\":9,\"dest\":-1,\"speed\":1.0,\"pos\":\"35.19805902663438,32.10525428067227,0.0\"}},{\"Agent\":{\"id\":1,\"value\":0.0,\"src\":12,\"dest\":-1,\"speed\":1.0,\"pos\":\"35.18950462792575,32.10788938151261,0.0\"}},{\"Agent\":{\"id\":2,\"value\":0.0,\"src\":7,\"dest\":-1,\"speed\":1.0,\"pos\":\"35.205764353510894,32.106326494117646,0.0\"}}]}\n";
        AgentContainer agents=new AgentContainer();
        assertEquals(agents.size(),0);
        agents.update(json);
        assertEquals(agents.size(),3);
    }

    @Test
    void isRun() {
        String json="{\"Agents\":[{\"Agent\":{\"id\":0,\"value\":0.0,\"src\":9,\"dest\":-1,\"speed\":1.0,\"pos\":\"35.19805902663438,32.10525428067227,0.0\"}},{\"Agent\":{\"id\":1,\"value\":0.0,\"src\":12,\"dest\":-1,\"speed\":1.0,\"pos\":\"35.18950462792575,32.10788938151261,0.0\"}},{\"Agent\":{\"id\":2,\"value\":0.0,\"src\":7,\"dest\":-1,\"speed\":1.0,\"pos\":\"35.205764353510894,32.106326494117646,0.0\"}}]}\n";
        AgentContainer agents=new AgentContainer();
        assertFalse(agents.isRun());
        agents.update(json);
        assertFalse(agents.isRun());
    }
}