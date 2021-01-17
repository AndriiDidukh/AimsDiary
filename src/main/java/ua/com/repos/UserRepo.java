package ua.com.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);


    @Query(
            value = "SELECT * FROM User u WHERE u.email =:email",
            nativeQuery = true)
    User findUserByEmail(@Param("email") final String email);



}
