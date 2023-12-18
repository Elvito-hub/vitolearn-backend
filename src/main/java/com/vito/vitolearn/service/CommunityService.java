package com.vito.vitolearn.service;

import java.util.List;

import com.vito.vitolearn.domain.Community;

public interface CommunityService {
    Community registerCommunity(Community cm);
    Community updateCommunity(Community cm);
    Community searchCommunity(Community cm);
    List<Community> communities();
    List<Community> approvedCommunities();
}
