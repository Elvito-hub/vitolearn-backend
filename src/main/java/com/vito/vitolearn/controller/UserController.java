package com.vito.vitolearn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.vito.vitolearn.domain.User;
import com.vito.vitolearn.service.implementation.UserServiceImplementation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "http://localhost:3002")
@RequestMapping(value = "/user/",consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {
    @Autowired 
    private UserServiceImplementation userservice;
    @PostMapping
    public ResponseEntity<List<User>> allstudents(){
        return new ResponseEntity<List<User>>(userservice.users(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        //         System.out.println(user.getUserName()+"encoded password");

        // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // String encodedPassword = passwordEncoder.encode(user.getPassword());
        // user.setPassword(encodedPassword);


        userservice.registerUser(user);
        return new ResponseEntity<>("user registered", HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        List<User> existingUser = userservice.findByEmail(user.getEmail());

        if (existingUser.size() == 0) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(!user.getPassword().equals(existingUser.get(0).getPassword())){
       // if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
        }

        // Here you might generate a JWT token and return it to the user upon successful login
        return new ResponseEntity<>(existingUser.get(0), HttpStatus.OK);
    }

}
