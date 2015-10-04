package util;

import com.selfach.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * By gekoreed on 10/4/15.
 */
@Import(Config.class)
@Configuration
@Profile("test")
public class FakeConfig {

    @Bean
    EmbaddedMysqlDatabase getDriverManagerDataSource() {
        return EmbeddedMysqlDatabaseBuilder.buildDataSource();
    }

}
