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
	public void registerUser(@RequestBody Map<String, String> message) {

		registerService.registerNode(nodeIp, nodePort, message.get("user"));
	}
	
	@RequestMapping(value = "/nouser", method = RequestMethod.POST)
	public void unregisterUser(@RequestBody Map<String, String> message) {
		for (int i=0;i<Global.neighborTable.size();i++) {
			unregisterService.unregisterNode(Global.neighborTable.get(i),message);
		}

	}
}