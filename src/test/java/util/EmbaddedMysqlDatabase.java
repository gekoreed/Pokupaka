package util;

import com.mysql.management.MysqldResource;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.IOException;

public class EmbaddedMysqlDatabase extends DriverManagerDataSource {
    private final Logger logger = Logger.getLogger(EmbaddedMysqlDatabase.class);
    private final MysqldResource mysqldResource;

    public EmbaddedMysqlDatabase(MysqldResource mysqldResource) {
        this.mysqldResource = mysqldResource;
    }

    public void shutdown() {
        if (mysqldResource != null) {
            mysqldResource.shutdown();
            if (!mysqldResource.isRunning()) {
                try {
                    FileUtils.deleteDirectory(mysqldResource.getBaseDir());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}