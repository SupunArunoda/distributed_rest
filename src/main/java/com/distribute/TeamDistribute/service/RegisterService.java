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
	

	public Map<String, String> registerNode(String ip,String port,Map<String, String> message) {
		Map<String, String> result = new HashMap<>();
		DatagramSocket receiveSock = null;
		try{
			receiveSock = new DatagramSocket(Integer.parseInt(Global.nodePort)+2);
			receiveSock.setSoTimeout(10000);
			byte[] buffer = new byte[65536];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
            String init_request = "REG " + ip + " " + port + " " + message.get("user");
            int length = init_request.length() + 5;
            init_request = String.format("%04d", length) + " " + init_request;
            DatagramPacket regrequest = new DatagramPacket(init_request.getBytes(), init_request.getBytes().length,
            		InetAddress.getByName(Global.bootstrapServerIp), Global.bootstrapServerPort);
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
            	if(noOfNodes == 0){
            		Global.startHeartBeat();
            		result.put("success", "true");
            		result.put("result", "Node Successfully registered");
            	}
            	else if(noOfNodes == 9999) {
            		result.put("success", "false");
            		result.put("result", "There is some error in the command");
            	}
            	else if(noOfNodes == 9998) {
            		result.put("success", "false");
            		result.put("result", "Already registered you, unregister first");
            	}
            	else if(noOfNodes == 9997) {
            		result.put("success", "false");
            		result.put("result", "Registered to another user, try a different IP and port");
            	}
            	else if(noOfNodes == 9996) {
            		result.put("success", "false");
            		result.put("result", "Can’t register, BS full");
            	}
            	else if(noOfNodes==1) {
            		String neighbourIp = values[3];
                    String neighbourPort = values[4];
                    String uri="http://"+neighbourIp+":"+neighbourPort+"/join";
                    RestTemplate restTemplate = new RestTemplate();
                    Map<String,String> node=new HashMap<>();
                    node.put("ip", Global.nodeIp);
                    node.put("port", Global.nodePort);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

                    HttpEntity<Map> entity = new HttpEntity<Map>(node,headers);
                    int answer = 1;
                    
                    try{
                    	answer = restTemplate.postForObject(uri, entity, Integer.class);
                    }
                    catch(Exception e){
                    	e.printStackTrace();
                    }
                    
                    node.put("ip", neighbourIp);
                    node.put("port", neighbourPort);
                    if(answer==0) {
                    	Global.neighborTable.add(node);
                    }
                    System.out.println("Neighbour value "+node.get("ip")+" "+node.get("port"));
                    result.put("success", "true");
                    result.put("result", "Node Successfully registered");
                    Global.startHeartBeat();
            	}
            	else {
            		String neighbourIp = values[3];
                    String neighbourPort = values[4];
                    String uri="http://"+neighbourIp+":"+neighbourPort+"/join";
                    RestTemplate restTemplate = new RestTemplate();
                    Map<String,String> node=new HashMap<>();
                    node.put("ip", Global.nodeIp);
                    node.put("port", Global.nodePort);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

                    HttpEntity<Map> entity = new HttpEntity<Map>(node,headers);
                    int answer = 1;
                    try{
                    	answer = restTemplate.postForObject(uri, entity, Integer.class);
                    }
                    catch(Exception e){
                    	e.printStackTrace();
                    }

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
            
                    try{
                    	answer = restTemplate.postForObject(uri, entity, Integer.class);
                    }
                    catch(Exception e){
                    	answer = 1;
                    	e.printStackTrace();
                    }

                    if(answer==0) {
                    	Map<String, String> neighbour2 = new HashMap<String,String>();
                    	neighbour2.put("ip", neighbourIp);
                    	neighbour2.put("port", neighbourPort);
                    	Global.neighborTable.add(neighbour2);
                    }
                    System.out.println("My value "+node.get("ip")+" "+node.get("port"));
                    result.put("success", "true");
                    result.put("result", "Node Successfully registered");;
                    Global.startHeartBeat();
            	}
            	
            }
            

        }
        catch (SocketException e) {
            e.printStackTrace();
            receiveSock.close();
            result.put("success", "false");
            result.put("result", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            receiveSock.close();
            result.put("success", "false");
            result.put("result", e.toString());
        }
		return result;
	}
}
