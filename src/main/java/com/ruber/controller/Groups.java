package com.ruber.controller;

import com.ruber.model.Currency;
import com.ruber.model.Group;
import com.ruber.model.Market;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
@RequestMapping("/groups")
public class Groups {
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Group[] getGroups(@RequestParam(value = "count", required = false) Long count) {
        if (count == null)
            count = 3L;

        Random r = new Random();

        Group[] rst = new Group[count.intValue()];
        for (int i = 0; i < rst.length; i++) {
            Market market;
            if (r.nextBoolean()) {
                market = new Market(true,
                    r.nextInt(1000000),
                    r.nextInt(1000000),
                    new Currency(r.nextInt(2), "curr" + r.nextInt(2)));
            } else {
                market = new Market(false, null, null, null);
            }

            rst[i] = new Group(
                r.nextInt(50000) + 1,
                r.nextInt(50000) + 1,
                "group" + r.nextInt(100),
                market
            );
        }

        return rst;
    }
}
