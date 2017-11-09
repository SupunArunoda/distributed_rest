package com.distribute.TeamDistribute.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.distribute.TeamDistribute.Global;

@Service
public class SearchService {
	
	public ArrayList<String> search(Map<String, String> node){
		String query = node.get("file_name");
		int numHops = Integer.parseInt(node.get("hops"))+1;
		node.put("hops", numHops+"");
		
		ArrayList<String> result = searchMyFiles(query);
       
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		
        Thread requestThread = new Thread(new Runnable() {			
			@Override
			public void run() {
				for(Map<String,String> neighbour: Global.neighborTable){
					String neighbourIp = neighbour.get("ip");
					String neighbourPort = neighbour.get("port");
					String uri="http://"+neighbourIp+":"+neighbourPort+"/search";
		            HttpEntity<Map> entity = new HttpEntity<Map>(node,headers);
		            //restTemplate.postForObject(uri, entity, String.class);
		            restTemplate.postForLocation(uri, entity);
				}
			}
		});
        requestThread.start();
	
		return result;
	}
	
	public ArrayList<String> searchMyFiles(String query){
		ArrayList<String> result = new ArrayList<>();
		String values[]=query.toLowerCase().split(" ");
		List<String> queryWords = new ArrayList<>();
		Collections.addAll(queryWords, values);

		ArrayList<String> myFiles = Global.filesList;
		
		for(String file: myFiles) {
			List<String> subStrings = new ArrayList<>();
			Collections.addAll(subStrings, file.toLowerCase().split(" "));
			boolean check=subStrings.retainAll(queryWords);
			if(!check || (subStrings.size() > 0)){
				result.add(file);
			}
		}
			
		return result;
	}
}
