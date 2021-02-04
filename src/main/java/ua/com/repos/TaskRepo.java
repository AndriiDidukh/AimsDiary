package ua.com.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.domain.Task;

import java.util.Date;
import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Integer> {

    @Query(value = "SELECT t FROM Task t WHERE t.author.id=:id AND t.taskDate >=:date1 AND t.taskDate <:date2 ORDER BY t.taskDate")
    List<Task> findTasksForCurrentUserWithDate(@Param("id") final int id, @Param("date1") final Date startDate, @Param("date2") final Date endDate);

    @Query(value = "SELECT t FROM Task t WHERE t.author.id=:id AND t.taskDate >=:date1 AND t.taskDate <:date2 AND t.text NOT IN (SELECT dt.text FROM DailyTask dt) ORDER BY t.taskDate")
    List<Task> findTomorrowTasksForCurrentUserWithDate(@Param("id") final int id, @Param("date1") final Date startDate, @Param("date2") final Date endDate);

    @Query(value = "SELECT t FROM Task t WHERE t.author.id=:id ORDER BY t.taskDate")
    List<Task> findTasksForCurrentUser(@Param("id") final int id);

    @Query(value = "SELECT t FROM Task t WHERE t.author.id=:id AND t.taskDate >=:date AND t.text NOT IN (SELECT dt.text FROM DailyTask dt) ORDER BY t.taskDate")
    List<Task> findFutureTasksForCurrentUser(@Param("id") final int id, @Param("date") final Date date);

    @Query(value = "SELECT t FROM Task t WHERE t.author.id=:id AND t.taskDate <:date ORDER BY t.taskDate DESC")
    List<Task> findPastTasksForCurrentUser(@Param("id") final int id, @Param("date") final Date date);

    @Query(value = "SELECT t FROM Task t WHERE t.text=:text")
    List<Task> findTasksByText(@Param("text") final String name);

}
