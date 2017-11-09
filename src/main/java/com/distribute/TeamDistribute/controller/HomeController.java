package com.distribute.TeamDistribute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.distribute.TeamDistribute.Global;


@Controller
public class HomeController {
	@Autowired
	static ResourceLoader resourceLoader;
	
    @Value("${spring.application.name}")
    String appName;
   
 
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        model.addAttribute("nodeIP",Global.nodeIp);
        model.addAttribute("nodePort",Global.nodePort);
        model.addAttribute("files", Global.filesList);
        return "home";
    }
    
}
