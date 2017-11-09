package com.distribute.TeamDistribute;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.apache.catalina.core.ApplicationContext;
import org.assertj.core.util.CheckReturnValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@SpringBootApplication
public class TeamDistributeApplication {
	
	public static void main(String[] args) {
		Global.nodeIp = args[0];
		Global.nodePort = args[1];
	
		
		String myfiles[]= {"Adventures of Tintin","Jack and Jill","Glee","The Vampire Diarie","King Arthur","Windows XP","Harry Potter","Kung Fu Panda","Lady Gaga","Twilight","Windows 8","Mission Impossible","Turn Up The Music","Super Mario",
				"American Pickers","Microsoft Office 2010","Happy Feet","Modern Family","American Idol","Hacking for Dummies"};
		ArrayList<String> files = new ArrayList<String>();
		Collections.addAll(files, myfiles);
		
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
