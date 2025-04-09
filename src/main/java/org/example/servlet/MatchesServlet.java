package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.MatchDAO;
import org.example.dao.MatchDaoImpl;
import org.example.model.entity.Match;
import org.example.util.Validator;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {

    private final MatchDAO matchDAO = new MatchDaoImpl();
    private final int SIZE_OF_PAGES = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Match> matches;
        long totalMatchesCount;

        int page = Validator.parsePageNumber(req.getParameter("page"));
        String playerName = Validator.validateName(req.getParameter("filter_by_player_name"));

        if (playerName == null) {
            matches = matchDAO.getMatches(page, SIZE_OF_PAGES);
            totalMatchesCount = matchDAO.getTotalMatches();
        } else {
            matches = matchDAO.getMatches(page, SIZE_OF_PAGES, playerName);
            totalMatchesCount = matchDAO.getTotalMatches(playerName);
        }

        int totalPages = (int) Math.ceil((double) totalMatchesCount / SIZE_OF_PAGES);

        req.setAttribute("matches", matches);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("filterPlayerName", playerName);
        req.getRequestDispatcher("view/matches.jsp").forward(req, resp);
    }

}
