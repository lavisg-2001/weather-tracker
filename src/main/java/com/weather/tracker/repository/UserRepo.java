package com.weather.tracker.repository;

import com.weather.tracker.dao.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<RegisteredUser, Integer> {
    RegisteredUser findByUsername(String username);
}
