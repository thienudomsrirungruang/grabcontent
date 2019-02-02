package com.thien.grabcontent;

import com.thien.grabcontent.config.CartoonProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({CartoonProperties.class})
public class GrabcontentApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrabcontentApplication.class, args);
	}

}

