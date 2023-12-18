package com.vito.vitolearn.service;

import java.util.List;

import com.vito.vitolearn.domain.User;

public interface UserService {
    User registerUser(User user);
    User updateUser(User user);
    User searchUser(User user);
    List<User> findByEmail(String email);
    List<User> users();
}
