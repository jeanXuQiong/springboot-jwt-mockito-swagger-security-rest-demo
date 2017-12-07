package com.example.demo;

import com.example.demo.dao.UserDao;
import com.example.demo.dao.UserDaoImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ServletComponentScan(value = {"com.example.demo.filter"})
@ComponentScan
public class DemoApplication {

	@Bean
	public UserDao userDao() {
		return new UserDaoImpl();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
