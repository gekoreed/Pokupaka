package handlersTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.UsersDao;
import com.selfach.dao.jooq.tables.records.UserRecord;
import com.selfach.exceptions.AndroidServerException;
import com.selfach.processor.handlers.impl.RegisterUserHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import util.FakeConfig;

import static org.junit.Assert.assertEquals;

/**
 * By gekoreed on 9/14/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes={FakeConfig.class})
@ActiveProfiles("test")
public class RegistrationTest {

    @Autowired
    UsersDao usersDao;

    @Autowired
    RegisterUserHandler regHandler;


    public void testWithoutPassword() throws Exception {
        String jsonWPassword = "{" +
                "  \"cmd\": \"registerUser\"," +
                "\"timestamp\":1442094103000," +
                "\"email\":\"gelkorked@gmail.com\"," +
                "\"name\":\"evgen\"," +
                "\"surname\":\"shevchenko\"" +
                "}";
        JsonNode node = new ObjectMapper().readTree(jsonWPassword);
        regHandler.handle((ObjectNode) node);
    }

    public void testWithoutEmail() throws Exception {
        String jsonWEmail = "{" +
                "  \"cmd\": \"registerUser\"," +
                "\"timestamp\":1442094103000," +
                "\"passwordHash\":\"oikn\"," +
                "\"name\":\"evgen\"," +
                "\"surname\":\"shevchenko\"" +
                "}";
        JsonNode node = new ObjectMapper().readTree(jsonWEmail);
        regHandler.handle((ObjectNode) node);
    }

    public void testWithoutBothEmailAndPwd() throws Exception {
        String jsonWEmail = "{" +
                "  \"cmd\": \"registerUser\"," +
                "\"timestamp\":1442094103000," +
                "\"name\":\"evgen\"," +
                "\"surname\":\"shevchenko\"" +
                "}";
        JsonNode node = new ObjectMapper().readTree(jsonWEmail);
        regHandler.handle((ObjectNode) node);
    }

    @Test()
    public void testHandler() throws Exception {

        String json = "{" +
                "  \"cmd\": \"registerUser\"," +
                "\"timestamp\":1442094103000," +
                "\"email\":\"gelkorked@gmail.com\"," +
                "\"passwordHash\":\"dsgsddgas\"," +
                "\"name\":\"Evgen\"," +
                "\"surname\":\"Shevchenko\"" +
                "}";
        JsonNode node = new ObjectMapper().readTree(json);
        RegisterUserHandler.RegisterResponse handle = regHandler.handle((ObjectNode) node);
        assertEquals(2, handle.serverUserId);

        UserRecord userById = usersDao.getUserById(handle.serverUserId);

        assertEquals("none", userById.getName());
        assertEquals("none", userById.getSurname());

    }
}
