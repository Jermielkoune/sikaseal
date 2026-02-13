package com.sikaseal.sika_engine.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestApplication {
    @GetMapping("/")
    public String sayHello() {
        return "Hello World";
    }

    @GetMapping("/cloudflow")
    public String cloudflow() {
        return "Ceci est une base pour le projet cloudflow, il sera amené à évoluer !";
    }

}
