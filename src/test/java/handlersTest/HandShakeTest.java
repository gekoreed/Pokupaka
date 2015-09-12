package handlersTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pokupaka.dao.UsersDao;
import com.pokupaka.dao.jooq.tables.records.UserRecord;
import com.pokupaka.processor.handlers.impl.HandshakeHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * By gekoreed on 9/12/15.
 */

public class HandShakeTest {
    UsersDao usersDao = Mockito.mock(UsersDao.class);
    private String json = "{\"cmd\":\"handshake\"}";

    @Before
    public void initMocks(){
        Mockito.when(usersDao.getAllUsers()).thenReturn(getUsers());
    }

    @Test
    public void testHandler() throws IOException {
        JsonNode node = new ObjectMapper().readTree(json);
        HandshakeHandler handshakeHandler = new HandshakeHandler();
        handshakeHandler.setUsersDao(usersDao);
        HandshakeHandler.HandShakeResponse handle = handshakeHandler.handle((ObjectNode) node);

        Assert.assertEquals(3, handle.usersCount);
    }

    public List<UserRecord> getUsers() {
        return Arrays.asList("evgen", "Oleg", "Dima").stream()
                .map(name -> {
                    UserRecord rec = new UserRecord() ;
                    rec.setName(name);
                    return rec;
                })
                .collect(Collectors.toList());

    }
}
