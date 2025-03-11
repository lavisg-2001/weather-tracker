package com.weather.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackerApplication.class, args);

		//    new SpringApplicationBuilder(MyApplication.class)
		//        .web(WebApplicationType.NONE) -> disable embedded server
		//        .run(args);
	}

}
