package ua.com.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.domain.Task;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {

    @Query(
            value = "SELECT t FROM Task t")
    List<Task> findTasksForCurrentUser(@Param("id") final Long id);
}
