package ua.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.domain.User;
import ua.com.domain.YearlyTask;
import ua.com.dto.YearlyTaskDto;
import ua.com.repos.UserRepo;
import ua.com.repos.YearlyTaskRepo;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
public class YearlyTaskService {

    @Autowired
    private YearlyTaskRepo yearlyTaskRepo;
    @Autowired
    private UserRepo userRepo;

    public YearlyTaskDto findCurrentYearTasksFotThisUser(final Principal principal) {
        final User user = userRepo.findByUsername(principal.getName());
        List<YearlyTask> yearlyTaskForCurrentUser = yearlyTaskRepo.findYearlyTaskForCurrentUser(user.getId(), String.valueOf(new Date().getYear()));
        final YearlyTask yearlyTask = yearlyTaskForCurrentUser.stream().findFirst().orElse(null);
        YearlyTaskDto yearlyTaskDto = new YearlyTaskDto();
        if (yearlyTask != null) {
            yearlyTaskDto.setId(yearlyTask.getId());
            yearlyTaskDto.setText(yearlyTask.getText());
        }
        return yearlyTaskDto;
    }

    public YearlyTaskDto findNextYearTasksFotThisUser(final Principal principal) {
        final User user = userRepo.findByUsername(principal.getName());
        List<YearlyTask> yearlyTaskForCurrentUser = yearlyTaskRepo.findYearlyTaskForCurrentUser(user.getId(), String.valueOf(new Date().getYear() + 1));
        final YearlyTask yearlyTask = yearlyTaskForCurrentUser.stream().findFirst().orElse(null);
        YearlyTaskDto yearlyTaskDto = new YearlyTaskDto();
        if (yearlyTask != null) {
            yearlyTaskDto.setId(yearlyTask.getId());
            yearlyTaskDto.setText(yearlyTask.getText());
        }
        return yearlyTaskDto;
    }

    public void saveThisYearTasks(YearlyTask yearlyTask, final Principal principal) {
        final User user = userRepo.findByUsername(principal.getName());
        yearlyTask.setYear(String.valueOf(new Date().getYear()));
        yearlyTask.setAuthor(user);
        yearlyTaskRepo.save(yearlyTask);
    }

    public void saveNextYearTasks(YearlyTask yearlyTask, final Principal principal) {
        final User user = userRepo.findByUsername(principal.getName());
        yearlyTask.setYear(String.valueOf(new Date().getYear() + 1));
        yearlyTask.setAuthor(user);
        yearlyTaskRepo.save(yearlyTask);
    }
}
