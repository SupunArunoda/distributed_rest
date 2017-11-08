package com.distribute.TeamDistribute.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.distribute.TeamDistribute.Global;

@RestController
public class RequestController {
	@RequestMapping(value = "/result", method = RequestMethod.POST)
	public ArrayList<Map<String, ArrayList<String>>> getResult() {
		return Global.resultList;
	}
}
