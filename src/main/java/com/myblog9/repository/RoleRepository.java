package com.myblog9.repository;

import com.myblog9.entity.Role;
//import jdk.nashorn.internal.ir.Optimistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Optional<Role> findByName(String name);
}
