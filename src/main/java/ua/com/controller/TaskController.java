package ua.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.domain.Task;
import ua.com.service.TaskService;
import ua.com.service.UserService;

import java.security.Principal;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;

    @GetMapping("/tasks")
    public String main(Model model, Principal principal){
       // model.addAttribute("tasks", taskService.findTasksForCurrentUser(principal));
        return "tasks";
    }

    @PostMapping("/tasks")
    public String add(@ModelAttribute Task task, Principal principal){
        taskService.addTask(task, principal);
        return "redirect:/tasks";
    }
}
