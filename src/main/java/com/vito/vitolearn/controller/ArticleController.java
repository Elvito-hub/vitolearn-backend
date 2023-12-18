package com.vito.vitolearn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.vito.vitolearn.domain.Article;
import com.vito.vitolearn.domain.Community;
import com.vito.vitolearn.domain.User;
import com.vito.vitolearn.service.StorageService;
import com.vito.vitolearn.service.implementation.ArticleServiceImpl;
import com.vito.vitolearn.service.implementation.CommunityServiceImpl;
import com.vito.vitolearn.service.implementation.UserServiceImplementation;

@RestController
@CrossOrigin(origins = "http://localhost:3002")
@RequestMapping(value = "/article/",consumes = MediaType.APPLICATION_JSON_VALUE,
produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleController {
    @Autowired 
    private ArticleServiceImpl comservice;
    @Autowired
    private UserServiceImplementation userservice;
    @Autowired 
    private CommunityServiceImpl comservice1;
    @Autowired
    private StorageService storageService;

    @PostMapping("/")
    public List<Article> allstudents(){
        return comservice.articles();
    }

    @PostMapping("/getArticleById")
    public Article getById(@RequestParam Long articleId){

        Article art = new Article();
        art.setArticleId(articleId);
        return comservice.searchArticle(art);
    }

    @PostMapping(value="/getbycommunity")
    public List<Article> getArticlesByCommunity(@RequestParam Long communityId){
       // Long communityId = Long.valueOf(request.getParameter("community.communityId"));
        Community cm = new Community();
        cm.setCommunityId(communityId);
        System.out.println(communityId+"okokokoko");
        return comservice.getArticlesByCommunity(cm);
    }

    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
    produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(MultipartHttpServletRequest request) {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String dateCreated = request.getParameter("dateCreated");
        Long communityId = Long.valueOf(request.getParameter("community.communityId"));
        Long creatorId = Long.valueOf(request.getParameter("creator.userId"));
        User exist = new User();
        exist.setUserId(creatorId);
        User creator = userservice.searchUser(exist);
        if (creator == null) {
            return new ResponseEntity<>("User not found for the provided creatorId", HttpStatus.NOT_FOUND);
        }
        Community existCommunity = new Community();
        existCommunity.setCommunityId(communityId);
        Community community = comservice1.searchCommunity(existCommunity);

        if (community == null) {
            return new ResponseEntity<>("Community not found for the provided communityId", HttpStatus.NOT_FOUND);
        }
        MultipartFile thumbnail = request.getFile("thumbnail");

        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setDateCreated(dateCreated);
        article.setCreator(creator);
        article.setCommunity(community);
        if (thumbnail != null && !thumbnail.isEmpty()) {
            try {
                String filePath = storageService.saveFile(thumbnail);
                article.setThumbnail(filePath);
            } catch (Exception e) {
                // Handle the exception
                return new ResponseEntity<>("Failed to save the thumbnail", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        comservice.createArticle(article);
        return new ResponseEntity<>("article registered", HttpStatus.OK);
    }

    
}
