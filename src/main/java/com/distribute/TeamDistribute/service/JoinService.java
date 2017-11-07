package com.distribute.TeamDistribute.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.distribute.TeamDistribute.Global;
import com.distribute.TeamDistribute.model.Node;

@Service
public class JoinService {

public int joinNode(Map<String, String> node) {
	
	if(Global.neighborTable.size()<3){
		Global.neighborTable.add(node);
		return 0;
	}else {
		return 9999;
	}
}
	
}
