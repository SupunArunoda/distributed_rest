package com.distribute.TeamDistribute.model;

import org.springframework.beans.factory.annotation.Value;




public class Node {
	@Value("${resource.node.ip}")
	String nodeIP;

	@Value("${resource.server.port}")
	int nodePort;
	
	int value;
	
	public Node() {
		
	}
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getNodeIP() {
		return nodeIP;
	}

	public void setNodeIP(String nodeIP) {
		this.nodeIP = nodeIP;
	}

	public int getNodePort() {
		return nodePort;
	}

	public void setNodePort(int nodePort) {
		this.nodePort = nodePort;
	}
	
	
}
