package handlersTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pokupaka.dao.UsersDao;
import com.pokupaka.dao.impl.UsersDaoImpl;
import com.pokupaka.dao.jooq.tables.records.UserRecord;
import com.pokupaka.exceptions.AndroidServerException;
import com.pokupaka.processor.handlers.impl.RegisterUserHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import util.EmbaddedMysqlDatabase;
import util.EmbeddedMysqlDatabaseBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * By gekoreed on 9/14/15.
 */
public class RegistrationTest {

    UsersDao usersDao = Mockito.mock(UsersDao.class);
    RegisterUserHandler regHandler = new RegisterUserHandler();
    EmbaddedMysqlDatabase dataSource;
    private JdbcTemplate template;

    @Before
    public void initMocks() throws IOException {

    }

    private void setDao() {
        dataSource = EmbeddedMysqlDatabaseBuilder.buildDataSource();
        usersDao = new UsersDaoImpl();
        usersDao.setDataSource(dataSource);

        template = new JdbcTemplate(dataSource);

        regHandler.setUsersDao(usersDao);
    }

    @After
    public void shutDown(){
        if  (dataSource != null)
            dataSource.shutdown();
    }


    @Test(expected = AndroidServerException.class)
    public void testWithoutPassword() throws Exception {
        String jsonWPassword = "{" +
                "  \"cmd\": \"registerUser\"," +
                "\"timestamp\":1442094103000," +
                "\"email\":\"gelkorked@gmail.com\"," +
                "\"name\":\"evgen\"," +
                "\"surname\":\"shevchenko\"" +
                "}";
        regHandler.setUsersDao(usersDao);
        JsonNode node = new ObjectMapper().readTree(jsonWPassword);
        RegisterUserHandler.RegisterResponse handle = regHandler.handle((ObjectNode) node);
    }


    @Test(expected = AndroidServerException.class)
    public void testWithoutEmail() throws Exception {
        String jsonWEmail = "{" +
                "  \"cmd\": \"registerUser\"," +
                "\"timestamp\":1442094103000," +
                "\"passwordHash\":\"oikn\"," +
                "\"name\":\"evgen\"," +
                "\"surname\":\"shevchenko\"" +
                "}";
        JsonNode node = new ObjectMapper().readTree(jsonWEmail);
        regHandler.setUsersDao(usersDao);
        RegisterUserHandler.RegisterResponse handle = regHandler.handle((ObjectNode) node);
    }

    @Test(expected = AndroidServerException.class)
    public void testWithoutBothEmailAndPwd() throws Exception {
        String jsonWEmail = "{" +
                "  \"cmd\": \"registerUser\"," +
                "\"timestamp\":1442094103000," +
                "\"name\":\"evgen\"," +
                "\"surname\":\"shevchenko\"" +
                "}";
        JsonNode node = new ObjectMapper().readTree(jsonWEmail);
        regHandler.setUsersDao(usersDao);
        RegisterUserHandler.RegisterResponse handle = regHandler.handle((ObjectNode) node);
    }

    @Test()
    public void testHandler() throws Exception {

        setDao();

        String json = "{" +
                "  \"cmd\": \"registerUser\"," +
                "\"timestamp\":1442094103000," +
                "\"email\":\"gelkorked@gmail.com\"," +
                "\"passwordHash\":\"dsgsddgas\"," +
                "\"name\":\"evgen\"," +
                "\"surname\":\"shevchenko\"" +
                "}";
        JsonNode node = new ObjectMapper().readTree(json);
        RegisterUserHandler.RegisterResponse handle = regHandler.handle((ObjectNode) node);
        assertEquals(1, handle.serverUserId);

        UserRecord userById = usersDao.getUserById(1);

        assertEquals("evgen", userById.getName());
        assertEquals("shevchenko", userById.getSurname());
        assertEquals("gelkorked@gmail.com", userById.getEmail());


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
