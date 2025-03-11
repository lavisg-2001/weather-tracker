package com.weather.tracker.service;

import com.weather.tracker.dao.RegisteredUser;
import com.weather.tracker.exceptions.CustomException;
import com.weather.tracker.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepo repo;

    public RegisteredUser saveUser(RegisteredUser registeredUser) {
        // If registeredUser  is a new user, it should not have an ID set
        if (registeredUser .getId() == 0) {
            return repo.save(registeredUser ); // New user
        } else {
            // Retrieve the existing user from the database
            RegisteredUser  existingUser  = repo.findById(registeredUser .getId())
                    .orElseThrow(() -> new EntityNotFoundException("User  not found"));
            // Update fields
            existingUser .setRole(registeredUser .getRole());
            existingUser .setUsername(registeredUser .getUsername());
            existingUser .setPassword(registeredUser .getPassword());
            return repo.save(existingUser ); // Update existing user
        }
//        try {
//            return repo.save(registeredUser );
//        } catch (ObjectOptimisticLockingFailureException e) {
//            throw new CustomException("The user data has been modified by another transaction. Please try again.");
//        }
    }
}
