package com.ftn.SlozeniOblikVezbiProjekat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.ftn.SlozeniOblikVezbiProjekat.SlozeniOblikVezbiProjekatApplication;

@SpringBootApplication
public class SlozeniOblikVezbiProjekatApplication extends SpringBootServletInitializer {
	  
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SlozeniOblikVezbiProjekatApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SlozeniOblikVezbiProjekatApplication.class, args);
	}
}
