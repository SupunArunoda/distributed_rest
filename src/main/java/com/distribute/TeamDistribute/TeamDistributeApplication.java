package com.distribute.TeamDistribute;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@SpringBootApplication
public class TeamDistributeApplication {
	
	public static void main(String[] args) {
		ArrayList<String> files = new ArrayList<String>();
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream("src/main/resources/fileNames.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String line;
			
			while ((line = br.readLine()) != null) {
		       files.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Random rand = new Random();
		
		int noOfFiles = rand.nextInt((5 - 3) + 1) + 3;
		
		while(Global.filesList.size() < noOfFiles){
			int index = rand.nextInt(20);
			String file = files.get(index);
			if(!Global.filesList.contains(file))
				Global.filesList.add(file);
		}
		SpringApplication.run(TeamDistributeApplication.class, args);
	}
}
