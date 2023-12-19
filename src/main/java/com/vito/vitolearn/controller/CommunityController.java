package com.vito.vitolearn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.vito.vitolearn.domain.Community;
import com.vito.vitolearn.domain.User;
import com.vito.vitolearn.service.StorageService;
import com.vito.vitolearn.service.implementation.CommunityServiceImpl;
import com.vito.vitolearn.service.implementation.UserServiceImplementation;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3002", allowCredentials = "true", maxAge = 3600, allowedHeaders = "*")
@RequestMapping(value = "/community/",consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class CommunityController {

    @Autowired 
    private CommunityServiceImpl comservice;
    @Autowired
    private UserServiceImplementation userservice;
    @Autowired
    private StorageService storageService;
    @PostMapping("/")
    public List<Community> allcommunities(){
        return comservice.communities();
    }

    @PostMapping("/getapproved")
    public List<Community> getApprovedCommunites(HttpServletRequest request){

            Cookie[] cookies = request.getCookies();
            System.out.println(cookies);
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        String token = cookie.getValue();
                        System.out.println(token);
                    }
                }
            }
        return comservice.approvedCommunities();
    }

    @PostMapping(value = "/request", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(MultipartHttpServletRequest request) {
        
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String dateCreated = request.getParameter("dateCreated");
        Boolean isApproved = Boolean.valueOf(request.getParameter("isApproved"));
        Long creatorId = Long.valueOf(request.getParameter("creator.userId"));
        User exist = new User();
        exist.setUserId(creatorId);
        User creator = userservice.searchUser(exist);
        if (creator == null) {
            return new ResponseEntity<>("User not found for the provided creatorId", HttpStatus.NOT_FOUND);
        }
        MultipartFile coverPhoto = request.getFile("coverPhoto"); // Assuming the file parameter is named "coverPhoto"




        Community community = new Community();
        community.setName(name);
        community.setDescription(description);
        community.setDateCreated(dateCreated);
        community.setIsApproved(isApproved);
        community.setCreator(creator);

        if(coverPhoto!=null){

        try {
            String filePath = storageService.saveFile(coverPhoto);
            community.setCoverPhotoPath(filePath);;

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

        comservice.registerCommunity(community);
        return new ResponseEntity<>("community registered", HttpStatus.OK);
    }

    @PutMapping("/approve")
    public ResponseEntity<?> approve(@RequestBody Community user) {
        comservice.updateCommunity(user);
        return new ResponseEntity<>("community updated", HttpStatus.OK);
    }

}
