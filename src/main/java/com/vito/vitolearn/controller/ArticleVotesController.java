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
import com.vito.vitolearn.domain.ArticleVotes;
import com.vito.vitolearn.domain.Community;
import com.vito.vitolearn.domain.User;
import com.vito.vitolearn.service.implementation.ArticleServiceImpl;
import com.vito.vitolearn.service.implementation.ArticleVotesImpl;
import com.vito.vitolearn.service.implementation.UserServiceImplementation;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3002")
@RequestMapping(value = "/articlevote/",consumes = MediaType.APPLICATION_JSON_VALUE,
produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleVotesController {
    @Autowired 
    private ArticleServiceImpl comservice;
    @Autowired
    private UserServiceImplementation userservice;
    @Autowired
    private ArticleVotesImpl avImpl;

     @PostMapping(value="/getbyarticle")
    public List<ArticleVotes> getArticleVotesByArticle(@RequestParam Long articleId){
       // Long communityId = Long.valueOf(request.getParameter("community.communityId"));
        Article cm = new Article();
        cm.setArticleId(articleId);
        return avImpl.getArticleVotesByArticle(cm);
    }
    
    @PostMapping(value="/votearticle",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
    produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> votearticle(HttpServletRequest request){
                System.out.println(request+" the creatoe");

        Long creatorId = Long.valueOf(request.getParameter("creator.userId"));
        Long articleId = Long.valueOf(request.getParameter("article.articleId"));
        Long vote = Long.valueOf(request.getParameter("vote"));
        String dateCreated = request.getParameter("dateCreated");

        Article exist = new Article();
        exist.setArticleId(articleId);
        Article article = comservice.searchArticle(exist);
        if(article==null){
            return new ResponseEntity<>("article not found for the provided communityId", HttpStatus.NOT_FOUND);
        }
        User exist1 = new User();
        exist1.setUserId(creatorId);
        User creator = userservice.searchUser(exist1);
        if (creator == null) {
            return new ResponseEntity<>("User not found for the provided creatorId", HttpStatus.NOT_FOUND);
        }
        List<ArticleVotes> dbvotes =  avImpl.searchVote(article, creator);

        if(dbvotes.size()>0){
            avImpl.removeVote(dbvotes.get(0));
            if(dbvotes.get(0).getVote()==vote){
                return new ResponseEntity<List<ArticleVotes>>( avImpl.getArticleVotesByArticle(article), HttpStatus.OK);
            }
        }
        ArticleVotes av = new ArticleVotes();
        av.setArticle(article);
        av.setCreator(creator);
        av.setDateCreated(dateCreated);
        av.setVote(vote);
        avImpl.addVote(av);
        return new ResponseEntity<List<ArticleVotes>>( avImpl.getArticleVotesByArticle(article), HttpStatus.OK);
    }
}
