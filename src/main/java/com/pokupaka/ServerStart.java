package com.pokupaka;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * By gekoreed on 9/12/15.
 */
@Component("main")
public class ServerStart {
    public static void main(String[] args) {
        PropertyConfigurator.configureAndWatch("conf/log4j.properties");
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
