package com.krasnovozBek.krasnovozBek.controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3000")
public class TestController {


    @GetMapping("/test")
    public String test() {
        return "hi";
    }
}