package com.doszke.learnapp.web;

import com.doszke.learnapp.services.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CardController {

    private CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "hello! ";
    }

}
