package org.example.service;

import org.example.dao.MatchDAO;
import org.example.dao.MatchDAOImpl;
import org.example.model.entity.Match;

public class FinishedMatchesService {

    private final MatchDAO matchDAO = new MatchDAOImpl();

    public Match saveFinishedMatch(Match match) {
        return matchDAO.save(match);
    }

}
