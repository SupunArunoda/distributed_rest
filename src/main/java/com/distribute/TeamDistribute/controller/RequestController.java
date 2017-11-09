package com.distribute.TeamDistribute.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.distribute.TeamDistribute.Global;

@RestController
@RequestMapping(value = "request")
public class RequestController {
	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public ArrayList<Map<String, ArrayList<String>>> getResult() {
		return Global.resultList;
	}
	
	@RequestMapping(value = "/neighbours", method = RequestMethod.GET)
	public ArrayList<Map<String, String>> getNeighbours() {
		return Global.neighborTable;
	}
	
	@RequestMapping(value = "/heartBeat", method = RequestMethod.GET)
	public String checkAvailability() {
		return "success";
	}
}

