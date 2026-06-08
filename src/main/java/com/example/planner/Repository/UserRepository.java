package com.example.planner.Repository;

import com.example.planner.Model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kogi <astronaut.kogi@gmail.com>
 */
public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
