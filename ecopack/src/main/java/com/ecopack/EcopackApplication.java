package com.ecopack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ComponentScan(basePackages="com")
@SpringBootApplication
@ImportResource("classpath:app-config.xml")
public class EcopackApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EcopackApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(EcopackApplication.class, args);
	}
}
