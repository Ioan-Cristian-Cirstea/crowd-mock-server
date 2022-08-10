package ro.esolutions.crowdmockserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrowdMockServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrowdMockServerApplication.class, args);
	}

}
