package com.distribute.TeamDistribute;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class Global {
	public static ArrayList<Map<String, String>> neighborTable = new ArrayList<>();
	public static ArrayList<String> filesList = new ArrayList<>();
	public static ArrayList<Map<String, ArrayList<String>>> resultList = new ArrayList<>();	
	public static BlockingQueue<String> messagesQueue =  new LinkedBlockingQueue<String>();;
	public static String bootstrapServerIp;
	public static int bootstrapServerPort;
	public static String nodeIp;
	public static String nodePort;
	public static Timer timer;
	
	public static void clear(){
		neighborTable.clear();
		resultList.clear();
		messagesQueue.clear();
		timer.cancel();
	}
	
	public static void startHeartBeat(){
		timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
                
                for(Map<String,String> neighbour: Global.neighborTable){
    				String neighbourIp = neighbour.get("ip");
    				String neighbourPort = neighbour.get("port");
    				String uri="http://"+neighbourIp+":"+neighbourPort+"/request/heartBeat";
    				try{
    					restTemplate.getForObject(uri, String.class);
    				}
    				catch(Exception e){
    					e.printStackTrace();
    					Global.neighborTable.remove(neighbour);
    				}
    			}
            }
        }, 5 * 1000,10 * 1000);
	}
}
