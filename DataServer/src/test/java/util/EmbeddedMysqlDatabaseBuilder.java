package util;

import com.mysql.management.MysqldResource;
import com.mysql.management.MysqldResourceI;
import org.apache.log4j.Logger;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.support.nativejdbc.WebLogicNativeJdbcExtractor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.System.*;

/**
 * By gekoreed on 9/14/15.
 */
public class EmbeddedMysqlDatabaseBuilder {
    private static final Logger LOG = Logger.getLogger(EmbeddedMysqlDatabaseBuilder.class);

    private final String baseDatabaseDir = getProperty("java.io.tmpdir");
    private String databaseName = "selfach";
    private final int port = new Random().nextInt(10000) + 3306;
    private final String username = "root";
    private final String password = "";
    private boolean foreignKeyCheck;

    private final ResourceLoader resourceLoader;
    private final ResourceDatabasePopulator databasePopulator;

    public EmbeddedMysqlDatabaseBuilder() {
        resourceLoader = new DefaultResourceLoader();
        databasePopulator = new ResourceDatabasePopulator();
        foreignKeyCheck = true;
    }

    public static EmbeddedMysqlDatabase buildDataSource() {
        EmbeddedMysqlDatabaseBuilder builder = new EmbeddedMysqlDatabaseBuilder();
        builder.addSqlScript("file:sql/create.sql");
        return builder.build();
    }

    private EmbeddedMysqlDatabase createDatabase(MysqldResource mysqldResource) {
        EmbeddedMysqlDatabase database = new EmbeddedMysqlDatabase(mysqldResource);
        database.setDriverClassName("com.mysql.jdbc.Driver");
        database.setUsername(username);
        database.setPassword(password);
        String url = "jdbc:mysql://localhost:" + port + "/" + databaseName + "?createDatabaseIfNotExist=true";

        if (!foreignKeyCheck) {
            url += "&sessionVariables=FOREIGN_KEY_CHECKS=0";
        }
        database.setUrl(url);
        return database;
    }

    private MysqldResource createMysqldResource() {

        Map<String, String> databaseOptions = new HashMap<>();
        databaseOptions.put(MysqldResourceI.PORT, Integer.toString(port));

        MysqldResource mysqldResource = new MysqldResource(new File(baseDatabaseDir, databaseName));
        mysqldResource.start("embedded-mysqld-thread-" + currentTimeMillis(), databaseOptions);

        return mysqldResource;
    }

    private void populateScripts(EmbeddedMysqlDatabase database) {
        try {
            DatabasePopulatorUtils.execute(databasePopulator, database);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            database.shutdown();
        }
    }

    public EmbeddedMysqlDatabaseBuilder addSqlScript(String script) {
        databasePopulator.addScript(resourceLoader.getResource(script));
        return this;
    }

    public EmbeddedMysqlDatabaseBuilder setForeignKeyCheck(boolean foreignKeyCheck) {
        this.foreignKeyCheck = foreignKeyCheck;
        return this;
    }

    public final void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public EmbeddedMysqlDatabase build() {
        MysqldResource mysqldResource = createMysqldResource();
        EmbeddedMysqlDatabase database = createDatabase(mysqldResource);
        populateScripts(database);
        return database;
    }
}