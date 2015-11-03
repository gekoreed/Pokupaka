package cucumber.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.selfach.dao.UsersDao;
import com.selfach.dao.jooq.tables.records.UserRecord;
import com.selfach.processor.handlers.impl.HandshakeHandler;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.entyties.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import util.EmbeddedMysqlDatabase;
import util.FakeConfig;

import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * By gekoreed on 10/4/15.
 */
@ContextConfiguration(classes = FakeConfig.class)
public class HandShakeSteps {

    @Autowired
    UsersDao usersDao;

    @Autowired
    HandshakeHandler handler;

    @Autowired
    EmbeddedMysqlDatabase database;

    HandshakeHandler.HandShakeResponse responce;

    @Given("^Дано парочку таких персон$")
    public void Дано_парочку_таких_персон(List<Person> persons) throws Throwable {
        persons.stream().map(person -> {
            UserRecord user = new UserRecord();
            user.setEmail(person.email);
            user.setName(person.name);
            user.setSurname(person.surname);
            return user;
        }).forEach(usersDao::save);
    }

    @When("^клиент запросил проверку связи$")
    public void клиент_запросил_проверку_связи() throws Throwable {
        responce = handler.handle((ObjectNode) new ObjectMapper().readTree("{\"cmd\":\"handshake\"}"));
    }

    @Then("^ответить что на сервере есть (\\d+) клиента$")
    public void ответить_что_на_сервере_есть_клиента(int count) throws Throwable {
        assertEquals(count, responce.usersCount);
    }

    @Before
    public void clean(){
        JdbcTemplate template = new JdbcTemplate(database);
        template.execute("DELETE FROM User");
    }
}
