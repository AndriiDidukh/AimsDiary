package ua.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.domain.YearlyTask;
import ua.com.service.YearlyTaskService;

import java.security.Principal;

@Controller
public class YearlyTaskController {

    @Autowired
    private YearlyTaskService yearlyTaskService;

    @GetMapping("/yearly/tasks")
    public String getYearlyTasks(Model model, final Principal principal)
    {
        model.addAttribute("thisYearTasks", yearlyTaskService.findCurrentYearTasksFotThisUser(principal));
        model.addAttribute("nextYearTasks", yearlyTaskService.findNextYearTasksFotThisUser(principal));
        return "yearlyTasks";
    }

    @PostMapping("/save/this/year/tasks")
    public String saveThisYearTasks(@ModelAttribute YearlyTask yearlyTask, final Principal principal) {
        yearlyTaskService.saveThisYearTasks(yearlyTask, principal);
        return "redirect:/yearly/tasks";
    }

    @PostMapping("/save/next/year/tasks")
    public String saveNextYearTasks(@ModelAttribute YearlyTask yearlyTask, final Principal principal) {
        yearlyTaskService.saveNextYearTasks(yearlyTask, principal);
        return "redirect:/yearly/tasks";
    }
}
