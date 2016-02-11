package com.ruber.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class HomeController {
    private static AtomicInteger nextId = new AtomicInteger(1);
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping({"/", "/home"})
    public String getHomePage(Model model) {
        logger.error("new call");
        model.addAttribute("id", nextId.getAndIncrement());

        return "home";
    }
}