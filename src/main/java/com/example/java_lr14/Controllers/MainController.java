package com.example.java_lr14.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @GetMapping("/")
    public String getHome(@RequestParam(name="text",required = false,defaultValue = "Hello spring")String text,
                          Model model){
        model.addAttribute("text",text);
        return "home";
    }
    @GetMapping("/about")
    public String getAbout(Model model){
        return "about";
    }
    @GetMapping("/contacts")
    public String getContacts(Model model){
        return "contacts";
    }
//    @GetMapping("/blog")
//    public String getBlog(Model model){
//        return "blog";
//    }
}
