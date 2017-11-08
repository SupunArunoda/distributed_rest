package com.distribute.TeamDistribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.distribute.TeamDistribute.controller.HomeController;

@SpringBootApplication
public class TeamDistributeApplication {
	
	ResourceLoader resourceLoader;
	public static void main(String[] args) {
		SpringApplication.run(TeamDistributeApplication.class, args);
	
	}
	
	
}
