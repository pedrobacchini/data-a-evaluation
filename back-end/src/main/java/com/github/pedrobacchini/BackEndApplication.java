package com.github.pedrobacchini;

import com.github.pedrobacchini.config.BackEndProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableConfigurationProperties(BackEndProperty.class)
public class BackEndApplication {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(BackEndApplication.class, args);
    }

    public static <T> T getBean(Class<T> type) {
        assert applicationContext != null;
        return applicationContext.getBean(type);
    }
}
