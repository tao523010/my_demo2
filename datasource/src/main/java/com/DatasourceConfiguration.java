package com;

import com.config.dynamicds.EnableDynamicDS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"com.config.**"})
@Configuration
public class DatasourceConfiguration {

//    public static void main(String[] args) {
//        SpringApplication.run(DatasourceConfiguration.class, args);
//    }

}
