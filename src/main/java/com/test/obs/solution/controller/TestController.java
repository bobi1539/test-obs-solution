package com.test.obs.solution.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public Map<String, String> hello() {
        Map<String, String> map = new HashMap<>();
        map.put("hello", "hello");
        return map;
    }
}
