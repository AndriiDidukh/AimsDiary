package ua.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.Utils.DateUtil;
import ua.com.domain.DailyTask;
import ua.com.domain.Task;
import ua.com.domain.User;
import ua.com.dto.DailyTaskDto;
import ua.com.repos.DailyTaskRepo;
import ua.com.repos.TaskRepo;
import ua.com.repos.UserRepo;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DailyTaskService {

    private static final Integer DAYS_COUNT = 1;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TaskRepo taskRepo;
    @Autowired
    private DailyTaskRepo dailyTaskRepo;

    public DailyTask findDailyTaskById(final String id)
    {
        return dailyTaskRepo.findById(Integer.valueOf(id)).get();
    }

    public List<DailyTaskDto> getDailyTasksForCurrentUser(final Principal principal) {
        final User user = userRepo.findByUsername(principal.getName());
        List<DailyTask> dailyTasksForCurrentUser = dailyTaskRepo.findDailyTasksForCurrentUser(user.getId());
        List<DailyTaskDto> dailyTaskDtos = new ArrayList<>();
        for (DailyTask task:dailyTasksForCurrentUser) {
            DailyTaskDto dailyTaskDto = new DailyTaskDto();
            dailyTaskDto.setId(task.getId());
            dailyTaskDto.setText(task.getText());
            dailyTaskDto.setStartDate(DateUtil.toStringDate(task.getStartDate()));
            dailyTaskDto.setEndDate(DateUtil.toStringDate(task.getEndDate()));
            dailyTaskDtos.add(dailyTaskDto);
        }
        return dailyTaskDtos;
    }

    public void addDailyTask(DailyTask dailyTask, Principal principal) {
        final User user = userRepo.findByUsername(principal.getName());
        final Date startDate = dailyTask.getStartDate();
        final Date endDate = dailyTask.getEndDate();
        dailyTask.setStartDate(startDate);
        dailyTask.setAuthor(user);
        LocalDate date = LocalDate.now();
        while (date.isBefore(DateUtil.convertDateToLocalDate(endDate).plusDays(DAYS_COUNT))) {
            Task task = new Task();
            task.setTaskDate(DateUtil.convertLocalDateToDate(date));
            task.setDone(false);
            task.setAuthor(user);
            task.setText(dailyTask.getText());
            task.setDaily(true);
            taskRepo.save(task);
            date = date.plusDays(DAYS_COUNT);
        }
        dailyTaskRepo.save(dailyTask);
    }

    public void deleteDailyTask(final int id)
    {
        DailyTask dailyTask = dailyTaskRepo.findById(id).get();
        dailyTaskRepo.deleteById(id);
        List<Task> tasksByText = taskRepo.findTasksByText(dailyTask.getText());
        for (Task task: tasksByText) {
            taskRepo.deleteById(task.getId());
        }
    }

    public void editDailyTask(final DailyTask dailyTask)
    {
//        DailyTask oldDailyTask = dailyTaskRepo.findById(dailyTask.getId()).get();
//        dailyTaskRepo.save(dailyTask);
    }

}
