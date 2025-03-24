package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.PlayerDaoImpl;
import org.example.dao.PlyerDAO;
import org.example.entity.Player;
import org.example.exception.InvalidParameterException;

import java.io.IOException;
import java.util.List;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private PlyerDAO playerDAO = new PlayerDaoImpl();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String player1name = req.getParameter("player1");
        String player2name = req.getParameter("player2");

        validatePlayerNames(player1name, player2name);

        if (playerDAO.findByName(player1name).isEmpty()) {
            playerDAO.save(new Player(player1name));
        }

        if (playerDAO.findByName(player2name).isEmpty()) {
            playerDAO.save(new Player(player2name));
        }

        //потом createNewMatch(pl1, pl2);
    }

    private void validatePlayerNames(String firstPName, String secondPName) {

        if (firstPName == null || firstPName.isBlank()) {
            throw new InvalidParameterException("Missing parameter - player1 name");
        }

        if (secondPName == null || secondPName.isBlank()) {
            throw new InvalidParameterException("Missing parameter - player2 name");
        }

        if (firstPName.equals(secondPName)) {
            throw new InvalidParameterException("Player names can't be same");
        }
    }
}

