package ua.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticController {

    @GetMapping("/statistic")
    public String getStatistic(Model model){
        return "statistic";
    }
}
