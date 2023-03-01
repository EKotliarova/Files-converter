package org.converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FilesConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilesConverterApplication.class, args);
    }
}