package ua.com.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.domain.Note;

import java.util.List;

public interface NoteRepo extends JpaRepository<Note, Long> {

    @Query(
            value = "SELECT * FROM Note n WHERE n.author_id =:id",
            nativeQuery = true)
    List<Note> findNotesFroCurrentUser(@Param("id") final Long id);
}
