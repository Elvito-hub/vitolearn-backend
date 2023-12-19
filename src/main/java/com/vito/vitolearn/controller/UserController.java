package com.vito.vitolearn.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.vito.vitolearn.domain.User;
import com.vito.vitolearn.service.implementation.UserServiceImplementation;
import com.vito.vitolearn.utils.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "http://localhost:3002", allowCredentials = "true", maxAge = 3600, allowedHeaders = "*")
@RequestMapping(value = "/user/",consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {
    @Autowired 
    private UserServiceImplementation userservice;
    @Autowired
    private HttpServletResponse response;
    @PostMapping
    public ResponseEntity<List<User>> allstudents(){
        return new ResponseEntity<List<User>>(userservice.users(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
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
        String token = JwtUtil.generateToken(existingUser.get(0).getUserName(), existingUser.get(0).getUserId().toString(), existingUser.get(0).getRole());

        System.out.println(token);

        HttpHeaders headers = new HttpHeaders();

        headers.remove(HttpHeaders.CACHE_CONTROL); // Remove Cache-Control header

            // Create a cookie with the token
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge((int) TimeUnit.MILLISECONDS.toSeconds(864_000_000)); // Set the cookie's expiration time in seconds
        cookie.setPath("/"); // Set the cookie's path as needed
        cookie.setHttpOnly(true);
        cookie.setSecure(false);

        response.addCookie(cookie);

        // Here you might generate a JWT token and return it to the user upon successful login
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    @PostMapping("/getuser")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
    
                    // Validate the token using your JwtUtil class or any method you have for token validation
                    boolean isValidToken = JwtUtil.validateToken(token); // Implement this method in JwtUtil
    
                    if (isValidToken) {
                        // Extract userId from the token
                        String userId = (String) JwtUtil.extractClaims(token).get("userId");
    
                        User exist = new User();
                        exist.setUserId(Long.parseLong(userId));

                        
                        // Search for the user by userId
                        User user = userservice.searchUser(exist); // Implement this method in UserService
    
                        if (user != null) {
                            return new ResponseEntity<>(user, HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
                        }
                    } else {
                        return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
                    }
                }
            }
        }
        return new ResponseEntity<>("Token not found", HttpStatus.NOT_FOUND);

    }

      @PostMapping("/loggedin")
        public ResponseEntity<?> isUserLogedIn(HttpServletRequest request) {
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        String token = cookie.getValue();
        
                        // Validate the token using your JwtUtil class or any method you have for token validation
                        boolean isValidToken = JwtUtil.validateToken(token); // Implement this method in JwtUtil
        
                        return new ResponseEntity<>(isValidToken, HttpStatus.OK);
                    }
                }
            }
            return new ResponseEntity<>(false, HttpStatus.OK);
        }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
    Cookie cookie = new Cookie("token", null); // Set a blank value to the cookie
    cookie.setPath("/"); // Set the cookie's path to match the one used during login

    // Set the cookie to expire immediately by setting its maxAge to 0
    cookie.setMaxAge(0);

    response.addCookie(cookie); // Add the modified cookie to the response to delete it

    return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
}



}
