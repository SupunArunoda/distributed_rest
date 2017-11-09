package com.distribute.TeamDistribute.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.distribute.TeamDistribute.model.Node;
import com.distribute.TeamDistribute.service.JoinService;


@RestController
public class JoinController {
	

	@Autowired
	private JoinService joinservice;	

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public int getJoin(@RequestBody Map<String, String> node) {
		int value=joinservice.joinNode(node);
		return value;
	}
}
