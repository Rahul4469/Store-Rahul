package com.IdeaProjects.store.controllers;

import com.IdeaProjects.store.entities.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class MessageController {

    @RequestMapping("/1")
    public Message sayHello() {
        return new Message("Hello World");

    }
}
