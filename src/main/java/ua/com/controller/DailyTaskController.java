package ua.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.domain.DailyTask;
import ua.com.service.DailyTaskService;

import java.security.Principal;

@Controller
public class DailyTaskController {

    @Autowired
    private DailyTaskService dailyTaskService;

    @GetMapping("/daily/tasks")
    public String getDailyTasks(Model model, Principal principal) {
        model.addAttribute("dailyTasks", dailyTaskService.getDailyTasksForCurrentUser(principal));
        return "dailyTasks";
    }

    @PostMapping("/daily/tasks")
    public String addDailyTask(@ModelAttribute DailyTask dailyTask, Principal principal) {
        dailyTaskService.addDailyTask(dailyTask, principal);
        return "redirect:/daily/tasks";
    }

    @PostMapping("/delete/daily/task")
    public String deleteDailyTask(@RequestParam int id) {
        dailyTaskService.deleteDailyTask(id);
        return "redirect:/daily/tasks";
    }

    @GetMapping("/edit/daily/task/{id}")
    public String editDailyTask(@PathVariable String id, Model model) {
        model.addAttribute("dailyTask", dailyTaskService.findDailyTaskById(id));
        return "editDailyTask";
    }

    @PostMapping("/daily/tasks/edit")
    public String editDailyTaskButton(@ModelAttribute DailyTask dailyTask)   {
        dailyTaskService.editDailyTask(dailyTask);
        return "dailyTasks";
    }
}
