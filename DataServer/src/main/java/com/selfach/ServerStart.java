package com.selfach;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * By gekoreed on 9/12/15.
 */
@Component("main")
public class ServerStart {
    public static void main(String[] args) {
        PropertyConfigurator.configureAndWatch("conf/log4j.properties");

        File f = new File("pictures/c");
        if (!f.exists()) {
            f.mkdirs();
            new File("last").mkdir();
        }

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        new Thread(() -> {
            try {
                context.getBean(Config.HttpAppServerBean.class).start();
            } catch (Exception e) {
                System.exit(1);
            }
        }, "App server thread").start();
    }
}
