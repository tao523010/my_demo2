package com;

import com.config.dynamicds.EnableDynamicDS;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableDynamicDS
@Configuration
@ComponentScan(basePackages = {"com.module.**"})
public class DemoConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(DemoConfiguration.class, args);
    }

}
