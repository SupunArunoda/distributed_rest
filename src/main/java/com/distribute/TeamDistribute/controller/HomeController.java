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
    String host;
    
    @Value("${server.port}")
    String port;
 
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("host", host);
        model.addAttribute("port", port);
        return "home";
    }
    
}
