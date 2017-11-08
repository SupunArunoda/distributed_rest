package com.distribute.TeamDistribute.service;

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
		String ip = node.get("ip");
		String port = node.get("port");
		
		ArrayList<String> result = searchMyFiles(query);
		
		Map<String,String> nodeRequest = new HashMap<>();
		nodeRequest.put("file_name", query);
		nodeRequest.put("ip", ip);
        nodeRequest.put("port", port);
       
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		
		for(Map<String,String> neighbour: Global.neighborTable){
			String neighbourIp = neighbour.get("ip");
			String neighbourPort = neighbour.get("port");
			String uri="http://"+neighbourIp+":"+neighbourPort+"/search";
            HttpEntity<Map> entity = new HttpEntity<Map>(node,headers);
            restTemplate.postForObject(uri, entity, String.class);
		}
		
		return result;
	}
	
	public ArrayList<String> searchMyFiles(String query){
		ArrayList<String> result = new ArrayList<>();
		String values[]=query.split(" ");
		List<String> queryWords = new ArrayList<>();
		Collections.addAll(queryWords, values);

		ArrayList<String> myFiles = Global.filesList;
		
		for(String file: myFiles) {
			List<String> subStrings = new ArrayList<>();
			Collections.addAll(subStrings, file.split(" "));
			
			boolean check=subStrings.retainAll(queryWords);
			if(!check || (subStrings.size()==queryWords.size())){
				result.add(file);
			}
		}
			
		return result;
	}
}
