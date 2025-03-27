package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.InvalidParameterException;
import org.example.model.entity.Match;
import org.example.service.OngoingMatchesService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuidParam = req.getParameter("uuid");
        UUID uuid = convertToUUID(uuidParam);
        Match match = ongoingMatchesService.getCurrentMatch(uuid);
        req.setAttribute("matchId", uuid);
        req.setAttribute("player1", match.getPlayer1());
        req.setAttribute("player2", match.getPlayer2());
        req.setAttribute("score", match.getScore());
        req.getServletContext().getRequestDispatcher("/view/ongoing_match.jsp").forward(req, resp);
    }

    private UUID convertToUUID(String uuid) {

        if (uuid == null || uuid.isBlank()) {
            throw new InvalidParameterException("Missing parameter - uuid");
        }

        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new InvalidParameterException("Invalid UUID format");
        }
    }
}
