package handlersTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.UsersDao;
import com.selfach.processor.handlers.impl.HandshakeHandler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import util.FakeConfig;

import java.io.IOException;

/**
 * By gekoreed on 9/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={FakeConfig.class})
@ActiveProfiles("test")
public class HandShakeTest {

    @Autowired
    UsersDao usersDao;

    private String json = "{\"cmd\":\"handshake\"}";

    @Test
    public void testHandler() throws IOException {
        JsonNode node = new ObjectMapper().readTree(json);
        HandshakeHandler handshakeHandler = new HandshakeHandler();
        handshakeHandler.setUsersDao(usersDao);
        HandshakeHandler.HandShakeResponse handle = handshakeHandler.handle((ObjectNode) node);

        Assert.assertEquals(1, handle.usersCount);
    }

}
