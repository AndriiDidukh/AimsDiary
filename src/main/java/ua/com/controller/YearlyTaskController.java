package ua.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class YearlyTaskController {

    @GetMapping("/yearly/tasks")
    public String getYearlyTasks(Model model)
    {
        return "yearlyTasks";
    }

}
