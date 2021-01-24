package ua.com.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.domain.DailyTask;

import java.util.List;

public interface DailyTaskRepo extends JpaRepository<DailyTask, Integer> {

    @Query(value = "SELECT t FROM DailyTask t WHERE t.author.id=:id order by t.startDate")
    List<DailyTask> findDailyTasksForCurrentUser(@Param("id") final int id);
}
