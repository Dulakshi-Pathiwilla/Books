package com.example.LibraryManagement.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.LibraryManagement.Entity.User;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
