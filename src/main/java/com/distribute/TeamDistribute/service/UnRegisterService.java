package com.distribute.TeamDistribute.service;

import java.util.Map;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.distribute.TeamDistribute.Global;
import com.distribute.TeamDistribute.service.RegisterService;
import com.distribute.TeamDistribute.service.UnRegisterService;

import sun.rmi.server.InactiveGroupException;


@Service
public class UnRegisterService {
	
	//First write leave request to neighbour table ip and ports then send UNREG to Bootstrap server
	public Map<String, String> unregisterNode(Map <String,String> message) {
		Map<String, String> result = new HashMap<>();
		int size = Global.neighborTable.size();
		for (int i = 0; i <size; i++) {
			
			Map <String,String> neighbor = Global.neighborTable.get(i);
			String neighborIp = neighbor.get("ip");
			String neighborPort = neighbor.get("port");
			String uri="http://"+neighborIp+":"+neighborPort+"/leave";
			Map<String,String> node = new HashMap<>();
            node.put("ip", Global.nodeIp);
            node.put("port", Global.nodePort);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			HttpEntity<Map> entity = new HttpEntity<Map>(node,headers);
			int answer = restTemplate.postForObject(uri, entity, Integer.class);
		}
		
		DatagramSocket receiveSock = null;
		try {
			receiveSock = new DatagramSocket(Integer.parseInt(Global.nodePort)+2);
			receiveSock.setSoTimeout(10000);
			byte[] buffer = new byte[65536];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
            String unreg_request = "UNREG " + Global.nodeIp + " " + Global.nodePort + " " + message.get("username");
            
            int length = unreg_request.length() + 5;
            unreg_request = String.format("%04d", length) + " " + unreg_request;
            DatagramPacket regrequest = new DatagramPacket(unreg_request.getBytes(), unreg_request.getBytes().length,
            		InetAddress.getByName(Global.bootstrapServerIp), Global.bootstrapServerPort);
            receiveSock.send(regrequest);
            receiveSock.receive(incoming);
            byte[] data = incoming.getData();
            receiveSock.close();
            String s = new String(data, 0, incoming.getLength());
            String[] values = s.split(" ");
            int value = Integer.parseInt(values[2]);
            if(value == 0){
        		result.put("success", "true");
        		result.put("result", "Node Successfully unregistered");
        	}
            else{
            	result.put("success", "false");
        		result.put("result", "Error while unregistering. IP and port may not be in the registry or command is incorrect");
            }
            Global.clear();
            System.out.println(s);
		} catch (SocketException e) {
			receiveSock.close();
            e.printStackTrace();
            result.put("success", "false");
            result.put("result", e.toString());
        } catch (IOException e) {
        	receiveSock.close();
            e.printStackTrace();           
            result.put("success", "false");
            result.put("result", e.toString());
        }
		
		return result;
	}
}
