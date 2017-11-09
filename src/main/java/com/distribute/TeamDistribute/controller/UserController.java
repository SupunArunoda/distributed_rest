package com.distribute.TeamDistribute.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.distribute.TeamDistribute.Global;
import com.distribute.TeamDistribute.service.RegisterService;
import com.distribute.TeamDistribute.service.UnRegisterService;


@RestController
@RequestMapping(value = "register")
public class UserController {
	
	@Value("${resource.node.ip}")
	String nodeIp;
	
	@Value("${server.port}")
    int nodePort;
	
	@Value("${resource.bootstrap.url}")
	String bootstrapServerUrl;
	
	@Value("${resource.bootstrap.port}")
    int bootstrapServerPort;
	
	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private UnRegisterService unregisterService;
	

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public Map<String, String> registerUser(@RequestBody Map<String, String> message) {
		Global.bootstrapServerIp = message.get("ip");
		Global.bootstrapServerPort = Integer.parseInt(message.get("port"));
		return registerService.registerNode(nodeIp, nodePort, message);
	}
	
	@RequestMapping(value = "/nouser", method = RequestMethod.POST)
	public Map<String, String> unregisterUser(@RequestBody Map<String, String> message) {
		return unregisterService.unregisterNode(message);
	}
}