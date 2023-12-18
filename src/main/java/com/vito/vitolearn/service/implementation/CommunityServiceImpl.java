package com.vito.vitolearn.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vito.vitolearn.domain.Community;
import com.vito.vitolearn.repository.CommunityRepository;
import com.vito.vitolearn.service.CommunityService;

@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityRepository repo;

    @Override
    public Community registerCommunity(Community cm){
        return repo.save(cm);
    }
    @Override
    public Community updateCommunity(Community cm){
        Community exist = repo.findById(cm.getCommunityId()).orElse(null);
        exist.setIsApproved(true);
        return repo.save(exist);
    }
    @Override
    public Community searchCommunity(Community cm){
        Optional<Community> optionalComm =  repo.findById(cm.getCommunityId());
        return optionalComm.orElse(null);
    }
    @Override
    public List<Community> communities() {
        return repo.findAll();
    }
        @Override
    public List<Community> approvedCommunities() {
        return repo.findallApprovedCommunites();
    }
}
