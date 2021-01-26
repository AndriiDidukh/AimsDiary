package ua.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.domain.DailyTask;
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
    public String getTasks(Model model, final Principal principal){
        model.addAttribute("tasks", taskService.findTasksForCurrentUser(principal));
        model.addAttribute("futureTasks", taskService.getFutureTasksForCurrentUser(principal));
        model.addAttribute("pastTasks", taskService.getPastTasksForCurrentUser(principal));
        return "tasks";
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute Task task, final Principal principal){
        taskService.addTask(task, principal);
        return "redirect:/tasks";
    }

    @PostMapping("/tomorrow/tasks")
    public String addTomorrowTask(@ModelAttribute Task task, final Principal principal){
        taskService.addTomorrowTask(task, principal);
        return "redirect:/#tab3";
    }


    @PostMapping("/delete/task")
    public String deleteTask(@RequestParam int id)
    {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @PostMapping("/delete/tommorow/task")
    public String deleteTomorrowTask(@RequestParam int id)
    {
        taskService.deleteTask(id);
        return "redirect:/#tab3";
    }

    @GetMapping("/edit/task/{id}")
    public String editTask(@PathVariable String id, Model model) {
        model.addAttribute("task", taskService.findTaskById(id));
        return "editTask";
    }
}
