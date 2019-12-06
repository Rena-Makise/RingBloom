package ringbloom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={MultipartAutoConfiguration.class})
@EnableAutoConfiguration
@ComponentScan(basePackages="ringbloom")
public class RingBloomApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RingBloomApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(RingBloomApplication.class, args);
	}

}