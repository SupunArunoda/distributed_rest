package com.distribute.TeamDistribute;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Global {
	public static ArrayList<Map<String, String>> neighborTable = new ArrayList<>();
	public static ArrayList<String> filesList = new ArrayList<>();
	public static ArrayList<Map<String, ArrayList<String>>> resultList = new ArrayList<>();	
	public static BlockingQueue<String> messagesQueue =  new LinkedBlockingQueue<String>();;
	public static String bootstrapServerIp;
	public static int bootstrapServerPort;
	public static String nodeIp;
	public static String nodePort;
	
	public static void clear(){
		neighborTable.clear();
		resultList.clear();
		messagesQueue.clear();
	}
}
