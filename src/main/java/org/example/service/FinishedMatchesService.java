package org.example.service;

import lombok.Getter;
import org.example.dao.MatchDAO;
import org.example.dao.MatchDaoImpl;
import org.example.model.FinishedMatchViewDto;
import org.example.model.entity.Match;
import org.example.model.entity.Player;

public class FinishedMatchesService {

    private final MatchDAO matchDAO = new MatchDaoImpl();
    private static FinishedMatchViewDto resultDto;

    public void saveFinishedMatch(Match match) {
        matchDAO.save(match);
    }

    public static void createResultDto(int id, Player player1, Player player2, int player1Sets, int player2Sets) {
        if (player1.getId() == id) {
            resultDto = new FinishedMatchViewDto(player1.getName(), player2.getName(), player1.getName(),
                    player1Sets, player2Sets);
        } else {
            resultDto = new FinishedMatchViewDto(player1.getName(), player2.getName(), player2.getName(),
                    player1Sets, player2Sets);
        }
    }

    public FinishedMatchViewDto getResultDto() {
        return resultDto;
    }
}
