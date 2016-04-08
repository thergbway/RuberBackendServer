package com.ruber.controller;

import com.ruber.service.FillDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class HomeController {
    private static AtomicInteger nextId = new AtomicInteger(1);

    @Autowired
    private FillDatabaseService fillDatabaseService;

    @RequestMapping(value = {"/", "/home"}, method = GET)
    public String getHomePage(Model model) {
        model.addAttribute("id", nextId.getAndIncrement());

        return "home";
    }

    @PostConstruct
    public void postConstruct() {
        fillDatabaseService.fill();
    }
}