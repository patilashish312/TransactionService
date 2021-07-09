package app.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"app.model","app.service","app.controller"})
public class SpringBootStart {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootStart.class, args);
	}

}
