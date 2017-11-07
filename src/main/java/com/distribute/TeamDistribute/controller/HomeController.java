package com.distribute.TeamDistribute.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Value("${spring.application.name}")
    String appName;
    
    @Value("${resource.node.ip}")
    String nodeIP;

    @Value("${server.port}")
    String nodePort;
 
    @GetMapping("/")
    public String homePage(Model model) {
    	System.out.println(nodeIP+" "+nodePort);
        model.addAttribute("appName", appName);
        model.addAttribute("nodeIP",nodeIP);
        model.addAttribute("nodePort",nodePort);
        return "home";
    }
    
}
