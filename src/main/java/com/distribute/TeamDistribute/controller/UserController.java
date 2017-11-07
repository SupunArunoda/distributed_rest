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

import com.distribute.TeamDistribute.service.RegisterService;


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
	

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public void registerUser(@RequestBody Map<String, String> message) {

		registerService.registerNode(nodeIp, nodePort, message.get("user"));
	}
	
//	public void registerNode(String ip,int port,String username) {
//		
//		try{
//			DatagramSocket receiveSock = new DatagramSocket(port);
//            String init_request = "REG " + ip + " " + port + " " + username;
//            int length = init_request.length() + 5;
//            init_request = String.format("%04d", length) + " " + init_request;
//            DatagramPacket regrequest = new DatagramPacket(init_request.getBytes(), init_request.getBytes().length,
//            		InetAddress.getByName(bootstrapServerUrl), bootstrapServerPort);
//            receiveSock.send(regrequest);
//            receiveSock.close();
//
//        }
//        catch (SocketException e1) {
//            e1.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//	}
}
