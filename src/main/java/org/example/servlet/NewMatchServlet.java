package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.PlayerDaoImpl;
import org.example.dao.PlyerDAO;
import org.example.model.entity.Match;
import org.example.model.entity.Player;
import org.example.exception.InvalidParameterException;
import org.example.service.OngoingMatchesService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private PlyerDAO playerDAO = new PlayerDaoImpl();
    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/view/new_match_form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String player1name = req.getParameter("player1");
        String player2name = req.getParameter("player2");

        validatePlayerNames(player1name, player2name);

        Player pl1 = playerDAO.findByName(player1name).orElseGet(() -> playerDAO.save(new Player(player1name)));
        Player pl2 = playerDAO.findByName(player2name).orElseGet(() -> playerDAO.save(new Player(player2name)));

        UUID uuid = UUID.randomUUID();
        Match match = new Match(pl1, pl2);

        ongoingMatchesService.createNewMatch(uuid, match);

        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
    }

    private void validatePlayerNames(String firstPName, String secondPName) {

        if (firstPName == null || firstPName.isBlank()) {
            throw new InvalidParameterException("Missing parameter - player 1 name");
        }

        if (secondPName == null || secondPName.isBlank()) {
            throw new InvalidParameterException("Missing parameter - player 2 name");
        }

        if (!firstPName.chars().allMatch(Character::isLetter)) {
            throw new InvalidParameterException("Player 1 name should contain only letters!");
        }

        if (!secondPName.chars().allMatch(Character::isLetter)) {
            throw new InvalidParameterException("Player 2 name should contain only letters!");
        }

        if (firstPName.equals(secondPName)) {
            throw new InvalidParameterException("Player names can't be same");
        }
    }
}

