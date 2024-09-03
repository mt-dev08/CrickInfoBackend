package com.cricket.CrickInfoBackend.services;

import com.cricket.CrickInfoBackend.entities.Match;

import java.util.List;

public interface MatchService {

    List<Match> getAllMatches();
    List<Match> getLiveMatches();
    List<List<String>> getPointsTable();
}
