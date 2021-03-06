package ua.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.Utils.DateUtil;
import ua.com.domain.Task;
import ua.com.domain.User;
import ua.com.dto.FutureTaskDto;
import ua.com.dto.PastTaskDto;
import ua.com.repos.TaskRepo;
import ua.com.repos.UserRepo;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TaskRepo taskRepo;

    public Task findTaskById(final String id) {
        return taskRepo.findById(Integer.valueOf(id)).get();
    }

    public List<Task> findTasksForCurrentUser(final Principal principal) {
        final User user = userRepo.findByUsername(principal.getName());
        return taskRepo.findTasksForCurrentUser(user.getId());
    }

    public List<Task> findTodayTasksForCurrentUser(final Principal principal) {
        final User user = userRepo.findByUsername(principal.getName());
        Date startDate = createDayDate(new Date());
        Date endDate = createDayDate(DateUtil.findTomorrowDate());
        return taskRepo.findTasksForCurrentUserWithDate(user.getId(), startDate, endDate);
    }

    public List<Task> findYesterdayTasksForCurrentUser(final Principal principal) {
        final User user = userRepo.findByUsername(principal.getName());
        Date startDate = createDayDate(DateUtil.findYesterdayDate());
        Date endDate = createDayDate(new Date());
        return taskRepo.findTasksForCurrentUserWithDate(user.getId(), startDate, endDate);
    }

    public List<Task> findTomorrowTasksForCurrentUser(final Principal principal) {
        final User user = userRepo.findByUsername(principal.getName());
        Date startDate = createDayDate(DateUtil.findTomorrowDate());
        Date endDate = createDayDate(DateUtil.findAfterTomorrowDate());
        return taskRepo.findTomorrowTasksForCurrentUserWithDate(user.getId(), startDate, endDate);
    }

    public void addTask(Task task, final Principal principal) {
        final User user = userRepo.findByUsername(principal.getName());
        task.setDone(false);
        task.setAuthor(user);
        task.setDaily(false);
        taskRepo.save(task);
    }

    public void addTomorrowTask(Task task, final Principal principal) {
        final User user = userRepo.findByUsername(principal.getName());
        task.setTaskDate(createDayDate(DateUtil.findTomorrowDate()));
        task.setDone(false);
        task.setAuthor(user);
        task.setDaily(false);
        taskRepo.save(task);
    }

    public void completeTask(final int id) {
        Task task = taskRepo.findById(id).get();
        if (task != null) {
            task.setDone(true);
            taskRepo.save(task);
        }
    }

    public List<FutureTaskDto> getFutureTasksForCurrentUser(final Principal principal) {
        final User user = userRepo.findByUsername(principal.getName());

        List<Task> futureTasksForCurrentUser = taskRepo.findFutureTasksForCurrentUser(user.getId(), createDayDate(new Date()));
        return populateFutureTasksToDto(futureTasksForCurrentUser);
    }

    public List<PastTaskDto> getPastTasksForCurrentUser(final Principal principal) {
        final User user = userRepo.findByUsername(principal.getName());
        List<Task> pastTasksForCurrentUser = taskRepo.findPastTasksForCurrentUser(user.getId(), createDayDate(new Date()));
        return populatePastTasksToDto(pastTasksForCurrentUser);
    }

    public void deleteTask(final int id) {
        taskRepo.deleteById(id);
    }

    private List<FutureTaskDto> populateFutureTasksToDto(final List<Task> tasks) {
        List<FutureTaskDto> taskDtos = new ArrayList<>();
        for (Task task : tasks) {
            FutureTaskDto taskDto = new FutureTaskDto();
            taskDto.setId(task.getId());
            taskDto.setDate(DateUtil.toStringDate(task.getTaskDate()));
            taskDto.setText(task.getText());
            taskDtos.add(taskDto);
        }
        return taskDtos;
    }

    private List<PastTaskDto> populatePastTasksToDto(final List<Task> tasks) {
        List<PastTaskDto> taskDtos = new ArrayList<>();
        for (Task task : tasks) {
            PastTaskDto taskDto = new PastTaskDto();
            taskDto.setId(task.getId());
            taskDto.setDate(DateUtil.toStringDate(task.getTaskDate()));
            taskDto.setText(task.getText());
            taskDto.setDone(task.isDone());
            taskDtos.add(taskDto);
        }
        return taskDtos;
    }

    private Date createDayDate(final Date date) {
        SimpleDateFormat format1 = new SimpleDateFormat(DATE_FORMAT);
        String date1 = format1.format(date);
        Date inActiveDate = null;
        try {
            inActiveDate = format1.parse(date1);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return inActiveDate;
    }

}
