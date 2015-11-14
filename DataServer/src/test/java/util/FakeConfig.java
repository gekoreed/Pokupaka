package util;

import com.selfach.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * By gekoreed on 10/4/15.
 */
@Import(Config.class)
@Configuration
public class FakeConfig {

    @Bean
    EmbeddedMysqlDatabase getDriverManagerDataSource() {
        return EmbeddedMysqlDatabaseBuilder.buildDataSource();
    }

}
