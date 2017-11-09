package com.distribute.TeamDistribute.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
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
	
	@Value("${server.port}")
	String nodePort;
	
	@Autowired
	SearchService searchService;
	
	@RequestMapping(value = "/selfSearch", method = RequestMethod.POST)
	public ArrayList<String> selfSearch(@RequestBody Map<String, String> node) {
		Global.resultList.clear();
		node.put("ip", nodeIP);
		node.put("port", nodePort);
		node.put("hops", 0+"");
		String uniqueID = UUID.randomUUID().toString();
		Global.messagesQueue.add(uniqueID);
		node.put("id", uniqueID);
		
		ArrayList<String> result = searchService.search(node);

		return result;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public void search(@RequestBody Map<String, String> node) {
		
		System.out.println("Searching....");
		String query = node.get("file_name");
		String ip = node.get("ip");
		String port = node.get("port");
		String messageId = node.get("id");
		//System.out.println("port: "+port+" ip: "+ip+" nodeip: "+nodeIP+" nodePort: "+nodePort);
		
	/*	if(ip.equals(nodeIP) && port.equals(nodePort)){
			try {
				System.out.println(Global.messagesQueue.take());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(messageId);
			System.out.println("success");
			return;
		}*/
		
		if(Global.messagesQueue.contains(messageId)){
			System.out.println("success");
			return;
		}
		
		Global.messagesQueue.add(messageId);
		
		ArrayList<String> result = searchService.search(node);
		if(result.size() > 0){
			Map<String, ArrayList<String>> searchResult = new HashMap<String, ArrayList<String>>();
			ArrayList<String> ipPort = new ArrayList<>();
			ipPort.add(nodeIP);
			ipPort.add(nodePort);
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
					restTemplate.postForLocation(uri, entity);
				}
			});
            requestThread.start();
		}
		return;

	}
	
	@RequestMapping(value = "/searchResult", method = RequestMethod.POST)
	public void searchResult(@RequestBody Map<String, ArrayList<String>> node) {
		Global.resultList.add(node);
		return;
	}

}
