package com.techiesbytes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {

    @RequestMapping(value = "/", method = RequestMethod.GET )
    @ResponseBody
    public String HelloWorld() {
        return "Hello World";
    }
}
