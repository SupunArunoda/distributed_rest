package com.distribute.TeamDistribute.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.distribute.TeamDistribute.Global;

@RestController
public class SearchController {
	
	@Value("${resource.node.ip}")
	String nodeIP;
	
	@Value("${server.port}")
	String nodePort;
	
	@RequestMapping(value = "/selfSearch", method = RequestMethod.POST)
	public String selfSearch(@RequestBody Map<String, String> node) {
		String result = "fail";
		String fileName = node.get("file_name");
		
		if(Global.filesList.contains(fileName)){
			result = "success";
		}
		
		Map<String,String> nodeRequest = new HashMap<>();
		nodeRequest.put("file_name", fileName);
		nodeRequest.put("ip", nodeIP);
        nodeRequest.put("port", nodePort);
       
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
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@RequestBody Map<String, String> node) {
		String result = "fail";
		String fileName = node.get("file_name");
		
		if(Global.filesList.contains(fileName)){
			result = "success";
		}
		
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
	
	@RequestMapping(value = "/searchResult", method = RequestMethod.POST)
	public void searchResult(@RequestBody Map<String, String> node) {
		Global.searchResult.add(node);
	}

}
