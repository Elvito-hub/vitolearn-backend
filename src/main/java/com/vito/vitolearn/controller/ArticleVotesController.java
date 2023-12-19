package com.vito.vitolearn.controller;

import java.util.List;
import java.util.stream.Collectors;

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
    
    @PostMapping(value="/votearticle")
    public ResponseEntity<?> votearticle(@RequestParam("userId") Long creatorId,
    @RequestParam("articleId") Long articleId,
    @RequestParam("vote") Long vote,
    @RequestParam("dateCreated") String dateCreated,
    @RequestParam("comment") String comment

    ){
               // System.out.println(request+" the creatoe");

        // Long creatorId = Long.valueOf(request.getParameter("creator.userId"));
        // Long articleId = Long.valueOf(request.getParameter("article.articleId"));
        // Long vote = Long.valueOf(request.getParameter("vote"));
        // String dateCreated = request.getParameter("dateCreated");

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
        List<ArticleVotes> filteredVotes = dbvotes.stream()
        .filter(vote1 -> vote1.getVote() != null && (vote1.getVote() == 1 || vote1.getVote() == 2))
        .collect(Collectors.toList());

        System.out.println(vote.equals(Long.valueOf(3)));
        System.out.println(dbvotes.size()>0 &&!dbvotes.get(0).getVote().equals(Long.valueOf(3)));

        if(filteredVotes.size()>0 && !vote.equals(Long.valueOf(3)) && !filteredVotes.get(0).getVote().equals(Long.valueOf(3)) ){
            avImpl.removeVote(filteredVotes.get(0));
            if(filteredVotes.get(0).getVote()==vote){
                return new ResponseEntity<List<ArticleVotes>>( avImpl.getArticleVotesByArticle(article), HttpStatus.OK);
            }
        }
        ArticleVotes av = new ArticleVotes();
        av.setArticle(article);
        av.setCreator(creator);
        av.setDateCreated(dateCreated);
        av.setVote(vote);
        av.setComment(comment);
        avImpl.addVote(av);
        return new ResponseEntity<List<ArticleVotes>>( avImpl.getArticleVotesByArticle(article), HttpStatus.OK);
    }
}
