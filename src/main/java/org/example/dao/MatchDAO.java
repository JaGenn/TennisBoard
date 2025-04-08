package org.example.dao;

import org.example.model.entity.Match;

import java.util.List;

public interface MatchDAO {

    void save(Match match);

    List<Match> getMatches(int page, int size);

    Long getTotalMatches();

    List<Match> getMatches(int page, int size, String playerName);

    Long getTotalMatches(String playerName);
}
