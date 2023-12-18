package com.vito.vitolearn.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vito.vitolearn.domain.User;
import com.vito.vitolearn.repository.UserRepository;
import com.vito.vitolearn.service.UserService;

@Service
public class UserServiceImplementation implements UserService{
    @Autowired private UserRepository repo;
    @Override
    public User registerUser(User user) {
       String username = generateUniqueUsername(user.getFirstName(), user.getLastName());
       user.setUserName(username);
        return repo.save(user);
    }

    private String generateUniqueUsername(String firstName, String lastName) {
        String baseUsername = firstName.toLowerCase() + lastName.toLowerCase();
        String username = baseUsername;

        int suffix = 1;
        while (usernameExists(username)) {
            username = baseUsername + suffix;
            suffix++;
        }
        return username;
    }

    private boolean usernameExists(String username) {
        Optional<User> existingUser = repo.findByUsername(username);
        return existingUser.isPresent();
    }
    @Override
    public User updateUser(User student) {
        return repo.save(student);
    }

    @Override
    public User searchUser(User student) {
        Optional<User> optionalUser = repo.findById(student.getUserId());
        return optionalUser.orElse(null); 
    }

    @Override
    public List<User> findByEmail(String email) {
        List<User> users = repo.findByEmail(email);
        return users; 
    }

    @Override
    public List<User> users() {
        return repo.findAll();
    }
}
