package com.distribute.TeamDistribute.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.distribute.TeamDistribute.model.Node;

@Service
public class JoinService {
public static ArrayList<Map<String, String>> neighborTable=new ArrayList<>();

public int joinNode(Map<String, String> node) {
	
	if(neighborTable.size()<3){
		neighborTable.add(node);
		return 0;
	}else {
		return 9999;
	}
}
	
}
