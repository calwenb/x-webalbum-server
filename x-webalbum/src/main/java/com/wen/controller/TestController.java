package com.wen.controller;

import com.wen.annotation.PassToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @PassToken
    @GetMapping("/t1")
    public String t1(@RequestParam("a") String a) {
        return "t1: " + a;
    }

}
