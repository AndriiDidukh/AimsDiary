package ua.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.repos.UserRepo;
import ua.com.service.TaskService;

import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TaskService taskService;


    @GetMapping("/")
    public String greeting(Model model, final Principal principal) {
        model.addAttribute("user", userRepo.findByUsername(principal.getName()));
        model.addAttribute("todayTasks", taskService.findTodayTaskForCurrentUser(principal));
        model.addAttribute("yesterdayTasks", taskService.findYesterdayTasksForCurrentUser(principal));
        return "index";
    }

    @PostMapping("/done")
    public String doneTask(@RequestParam int id){
        taskService.completeTask(id);
        return "redirect:/";
    }
}
