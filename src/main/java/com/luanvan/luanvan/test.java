package com.luanvan.luanvan;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
    @GetMapping("/hi")
    public String getdemo(){
        return  "hi";
    }
}
