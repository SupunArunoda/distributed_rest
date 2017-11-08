package com.distribute.TeamDistribute.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class UnRegisterService {
	
	//First write leave request to neighbour table ip and ports then send UNREG to Bootstrap server
	public void unregisterNode(Map <String,String> neighbour_ip_port,Map <String,String> my_ip_port) {
		
		
	}
}
