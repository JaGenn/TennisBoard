package org.example.service;

import org.example.dao.MatchDAO;
import org.example.dao.MatchDaoImpl;
import org.example.model.entity.Match;

public class FinishedMatchesService {

    private MatchDAO matchDAO = new MatchDaoImpl();

    public void saveFinishedMatch(Match match) {
        matchDAO.save(match);
        System.out.println("Имя победителя - " + match.getWinner().getName() + " - сервис");
    }
}
