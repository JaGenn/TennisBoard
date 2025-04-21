package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.NotFoundException;
import org.example.model.Score;
import org.example.model.dto.FinishedMatchViewDto;
import org.example.model.entity.Match;
import org.example.service.FinishedMatchesService;
import org.example.service.MatchScoreCalculationService;
import org.example.service.OngoingMatchesService;
import org.example.util.MappingUtil;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();
    private final MatchScoreCalculationService matchScoreCalculationService = new MatchScoreCalculationService();
    private final FinishedMatchesService finishedMatchesService = new FinishedMatchesService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = MappingUtil.convertToUUID(req.getParameter("uuid"));
        Match match = ongoingMatchesService.getCurrentMatch(uuid);
        if (match == null) {
            throw new NotFoundException("UUID of match doesn't present in request!");
        }

        req.setAttribute("matchId", uuid);
        req.setAttribute("player1", match.getPlayer1());
        req.setAttribute("player2", match.getPlayer2());
        req.setAttribute("score", match.getScore());
        req.getServletContext().getRequestDispatcher("/view/ongoing_match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UUID uuid = MappingUtil.convertToUUID(req.getParameter("uuid"));
        int id = MappingUtil.convertIdToInt(req.getParameter("winnerId"));

        Match match = ongoingMatchesService.getCurrentMatch(uuid);
        if (match == null) {
            throw new NotFoundException("UUID of match doesn't present in request!");
        }

        matchScoreCalculationService.calculate(id, match);

        if (!matchScoreCalculationService.isGameFinished()) {
            resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
        } else {
            ongoingMatchesService.deleteMatch(uuid);
            Match finishedMatch = finishedMatchesService.saveFinishedMatch(match);

            FinishedMatchViewDto matchResultsView = new FinishedMatchViewDto(
                    finishedMatch.getPlayer1().getName(),
                    finishedMatch.getPlayer2().getName(),
                    finishedMatch.getWinner().getName(),
                    finishedMatch.getScore().getPlayer1Sets(),
                    finishedMatch.getScore().getPlayer2Sets()
            );

            req.setAttribute("result", matchResultsView);
            req.getServletContext().getRequestDispatcher("/view/finished_match.jsp").forward(req, resp);
        }
    }
}
