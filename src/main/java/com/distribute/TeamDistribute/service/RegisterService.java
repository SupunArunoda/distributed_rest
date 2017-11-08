package com.distribute.TeamDistribute.service;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.distribute.TeamDistribute.Global;
import com.distribute.TeamDistribute.model.Node;

@Service
public class RegisterService {
	
	@Value("${resource.bootstrap.url}")
	String bootstrapServerUrl;
	
	@Value("${resource.bootstrap.port}")
    int bootstrapServerPort;
	
	@Value("${resource.node.ip}")
	String nodeIP;

	@Value("${server.port}")
	String nodePort;
	

	public void registerNode(String ip,int port,String username) {
		
		try{
			DatagramSocket receiveSock = new DatagramSocket(Integer.parseInt(nodePort));
			byte[] buffer = new byte[65536];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
            String init_request = "REG " + ip + " " + port + " " + username;
            int length = init_request.length() + 5;
            init_request = String.format("%04d", length) + " " + init_request;
            DatagramPacket regrequest = new DatagramPacket(init_request.getBytes(), init_request.getBytes().length,
            		InetAddress.getByName(bootstrapServerUrl), bootstrapServerPort);
            receiveSock.send(regrequest);
            receiveSock.receive(incoming);
            receiveSock.close();
            byte[] data = incoming.getData();
            String s = new String(data, 0, incoming.getLength());
            System.out.println(s);
            String[] values = s.split(" ");
            String command = values[1];
            if(command.equals("REGOK")){
            	int noOfNodes = Integer.parseInt(values[2]);
            	if(noOfNodes == 0) {
            		
            	}
            	else if(noOfNodes==1) {
            		String neighbourIp = values[3];
                    String neighbourPort = values[4];
                    String uri="http://"+neighbourIp+":"+neighbourPort+"/join";
                    RestTemplate restTemplate = new RestTemplate();
                    Map<String,String> node=new HashMap<>();
                    node.put("ip", nodeIP);
                    node.put("port", nodePort);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

                    HttpEntity<Map> entity = new HttpEntity<Map>(node,headers);
                    int answer = restTemplate.postForObject(uri, entity, Integer.class);
                    
                    node.put("ip", neighbourIp);
                    node.put("port", neighbourPort);
                    if(answer==0) {
                    	Global.neighborTable.add(node);
                    }
                    System.out.println("Neighbour value "+node.get("ip")+" "+node.get("port"));
            	}
            	else {
            		String neighbourIp = values[3];
                    String neighbourPort = values[4];
                    String uri="http://"+neighbourIp+":"+neighbourPort+"/join";
                    RestTemplate restTemplate = new RestTemplate();
                    Map<String,String> node=new HashMap<>();
                    node.put("ip", nodeIP);
                    node.put("port", nodePort);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

                    HttpEntity<Map> entity = new HttpEntity<Map>(node,headers);
                    int answer = restTemplate.postForObject(uri, entity, Integer.class);

                    Map<String, String> neighbour = new HashMap<String,String>();
                    
                    if(answer==0) {
                    	neighbour.put("ip", neighbourIp);
                    	neighbour.put("port", neighbourPort);
                    	Global.neighborTable.add(neighbour);
                    }
                    System.out.println("My value "+node.get("ip")+" "+node.get("port"));
                    
                    neighbourIp = values[5];
                    neighbourPort = values[6];
                    uri="http://"+neighbourIp+":"+neighbourPort+"/join";
            
                    answer = restTemplate.postForObject(uri, entity, Integer.class);

                    if(answer==0) {
                    	neighbour.put("ip", neighbourIp);
                    	neighbour.put("port", neighbourPort);
                    	Global.neighborTable.add(neighbour);
                    }
                    System.out.println("My value "+node.get("ip")+" "+node.get("port"));
                    
            	}
            	
            }
            

        }
        catch (SocketException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
