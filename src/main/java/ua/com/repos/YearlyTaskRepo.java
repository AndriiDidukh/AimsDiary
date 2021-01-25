package ua.com.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.domain.Note;
import ua.com.domain.YearlyTask;

import java.util.Date;
import java.util.List;

@Repository
public interface YearlyTaskRepo extends JpaRepository<YearlyTask, Integer> {


    @Query(
            value = "SELECT y FROM YearlyTask y WHERE y.author.id=:id AND y.year=:year")
    List<YearlyTask> findYearlyTaskForCurrentUser(@Param("id") final int id, @Param("year") final String year);

}
