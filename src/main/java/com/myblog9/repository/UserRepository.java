package com.myblog9.repository;

import com.myblog9.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // additional queries if needed

    public Optional<User> findByEmail(String email);

    public Optional<User> findByUsername(String username);
    public Optional<User> findByUsernameOrEmail(String username, String email);
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
}
