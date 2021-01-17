package ua.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.domain.Task;
import ua.com.domain.User;
import ua.com.repos.TaskRepo;
import ua.com.repos.UserRepo;

import java.security.Principal;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TaskRepo taskRepo;

    public List<Task> findTasksForCurrentUser(Principal principal)
    {
        final User user = userRepo.findByUsername(principal.getName());
        return taskRepo.findTasksForCurrentUser(user.getId());
    }

    public void addTask(Task task, Principal principal)
    {
        taskRepo.save(task);
    }
}
