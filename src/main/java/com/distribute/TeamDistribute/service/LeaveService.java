package com.distribute.TeamDistribute.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import com.distribute.TeamDistribute.Global;

@Service
public class LeaveService {

	public int leaveNode(Map<String, String> node) {
		// TODO Auto-generated method stub
		
		if(Global.neighborTable.contains(node)) {
			Global.neighborTable.remove(node);
			System.out.println("Remove node - "+node);
			return 0;
		}else {
			return 9999;
		}
	}
	
}

