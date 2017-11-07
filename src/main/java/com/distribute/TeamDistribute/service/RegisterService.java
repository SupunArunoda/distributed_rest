package com.distribute.TeamDistribute.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
	
	@Value("${resource.bootstrap.url}")
	String bootstrapServerUrl;
	
	@Value("${resource.bootstrap.port}")
    int bootstrapServerPort;

	public void registerNode(String ip,int port,String username) {
		
		try{
			DatagramSocket receiveSock = new DatagramSocket(port);
            String init_request = "REG " + ip + " " + port + " " + username;
            int length = init_request.length() + 5;
            init_request = String.format("%04d", length) + " " + init_request;
            DatagramPacket regrequest = new DatagramPacket(init_request.getBytes(), init_request.getBytes().length,
            		InetAddress.getByName(bootstrapServerUrl), bootstrapServerPort);
            receiveSock.send(regrequest);
            receiveSock.close();

        }
        catch (SocketException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
