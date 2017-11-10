package com.distribute.TeamDistribute.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.distribute.TeamDistribute.Global;
import com.distribute.TeamDistribute.service.SearchService;

@RestController
public class SearchController {
	
	@Value("${resource.node.ip}")
	String nodeIP;
	
	@Autowired
	SearchService searchService;
	
	@RequestMapping(value = "/selfSearch", method = RequestMethod.POST)
	public ArrayList<String> selfSearch(@RequestBody Map<String, String> node) {
		Global.startTime = new Date();
		Global.resultList.clear();
		node.put("ip", Global.nodeIp);
		node.put("port", Global.nodePort);
		node.put("hops", 0+"");
		String uniqueID = UUID.randomUUID().toString();
		Global.messagesQueue.add(uniqueID);
		node.put("id", uniqueID);
		
		ArrayList<String> result = searchService.search(node);
		if(result.size()>0){
			float diffSec = (new Date().getTime() - Global.startTime.getTime());	
			Map<String, ArrayList<String>> searchResult = new HashMap<String, ArrayList<String>>();
			ArrayList<String> ipPort = new ArrayList<>();
			ipPort.add(Global.nodeIp);
			ipPort.add(Global.nodePort);
			ipPort.add(node.get("hops"));
			ipPort.add(String.valueOf(diffSec));
			searchResult.put("ipPort", ipPort);
			searchResult.put("files", result);
			Global.resultList.add(searchResult);
			result.add(String.valueOf(diffSec));
		}

		return result;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public void search(@RequestBody Map<String, String> node) {
		
		System.out.println("Searching....");
		String query = node.get("file_name");
		String ip = node.get("ip");
		String port = node.get("port");
		String messageId = node.get("id");
		
		if(Global.messagesQueue.contains(messageId)){
			System.out.println("success");
			return;
		}
		
		Global.messagesQueue.add(messageId);
		
		ArrayList<String> result = searchService.search(node);
		if(result.size() > 0){
			Map<String, ArrayList<String>> searchResult = new HashMap<String, ArrayList<String>>();
			ArrayList<String> ipPort = new ArrayList<>();
			ipPort.add(Global.nodeIp);
			ipPort.add(Global.nodePort);
			ipPort.add(node.get("hops"));
			searchResult.put("ipPort", ipPort);
			searchResult.put("files", result);
			
			RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			String uri="http://"+ip+":"+port+"/searchResult";
            HttpEntity<Map> entity = new HttpEntity<Map>(searchResult,headers);
            
            Thread requestThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try{
						restTemplate.postForLocation(uri, entity);
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
			});
            requestThread.start();
		}
		return;

	}
	
	@RequestMapping(value = "/searchResult", method = RequestMethod.POST)
	public void searchResult(@RequestBody Map<String, ArrayList<String>> node) {
		float diffSec = (new Date().getTime() - Global.startTime.getTime());
		node.get("ipPort").add(String.valueOf(diffSec));
		Global.resultList.add(node);
		return;
	}

}
