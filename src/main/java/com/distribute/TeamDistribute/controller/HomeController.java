package com.distribute.TeamDistribute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.distribute.TeamDistribute.Global;

import net.minidev.json.JSONObject;

@Controller
public class HomeController {
	@Autowired
	static ResourceLoader resourceLoader;
	
    @Value("${spring.application.name}")
    String appName;
    
    @Value("${resource.node.ip}")
    String nodeIP;

    @Value("${server.port}")
    String nodePort;
    
    @Value("${server.port}")
    String port;
 
    @GetMapping("/")
    public String homePage(Model model) {
    	System.out.println(nodeIP+" "+nodePort);
        model.addAttribute("appName", appName);
        model.addAttribute("nodeIP",nodeIP);
        model.addAttribute("nodePort",nodePort);

        model.addAttribute("files", Global.filesList);
      //  System.out.println(Global.filesList.get(0));
        return "home";
    }
    
}
