package ro.esolutions.crowdmockserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.*;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableScheduling
public class CrowdMockServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrowdMockServerApplication.class, args);
	}

    @Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
			}
		};
	}
}
