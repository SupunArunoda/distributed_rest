package com.distribute.TeamDistribute.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.distribute.TeamDistribute.service.LeaveService;

@RestController
public class LeaveController {
	
	@Autowired
	private LeaveService leaveService;
	
	@Value("${resource.node.ip}")
	String nodeIP;
	
	@Value("${server.port}")
	int nodePort;	
	
	@RequestMapping(value = "/leave", method = RequestMethod.POST)
	public int getLeave(@RequestBody Map<String, String> node) {
		int value = leaveService.leaveNode(node);
		return value;
	}
	
}
