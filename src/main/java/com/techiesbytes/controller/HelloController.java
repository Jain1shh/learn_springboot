package com.techiesbytes.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {

    @Value("${My.Name}")
    private String Name;

    @RequestMapping(value = "/", method = RequestMethod.GET )
    @ResponseBody
    public String HelloWorld() {
        return "Hello "+Name+"!";
    }
}
