package com.terminus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan({ "com.terminus" })
@EnableWebMvc
public class AssignmentService extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AssignmentService.class).properties("spring.config.name: AssignmentService");
	}

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "AssignmentService");
		SpringApplication.run(AssignmentService.class, args);
	}
}
