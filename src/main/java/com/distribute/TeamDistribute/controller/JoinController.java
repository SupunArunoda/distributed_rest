package com.distribute.TeamDistribute.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.distribute.TeamDistribute.model.Node;
import com.distribute.TeamDistribute.service.JoinService;


@RestController
public class JoinController {
	

@Autowired
private JoinService joinservice;

@Value("${resource.node.ip}")
String nodeIP;

@Value("${server.port}")
int nodePort;	

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public int getJoin(@RequestBody Map<String, String> node) {
		String ip=node.get("ip");
		String port=node.get("port");
		int value=joinservice.joinNode(node);
//		Node node_return=new Node();
//		node.setNodeIP(nodeIP);
//		node.setNodePort(nodePort);
//		node_return.setValue(value);
		return value;
	}
}
