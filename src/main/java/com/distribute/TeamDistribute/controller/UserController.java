package com.distribute.TeamDistribute.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "register")
public class UserController {

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String registerUser(@RequestBody Map<String, String> message) {

		return "Supun";
	}
}
