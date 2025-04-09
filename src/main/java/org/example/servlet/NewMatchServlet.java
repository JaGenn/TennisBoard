package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.PlayerDaoImpl;
import org.example.dao.PlyerDAO;
import org.example.model.dto.PlayerNameHasErrorDto;
import org.example.model.entity.Match;
import org.example.model.entity.Player;
import org.example.service.OngoingMatchesService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private PlyerDAO playerDAO = new PlayerDaoImpl();
    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();
    private PlayerNameHasErrorDto errorDto;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/view/new_match_form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String player1name = req.getParameter("player1");
        String player2name = req.getParameter("player2");

        boolean namesNotValid = validatePlayerNames(player1name, player2name);

        if (namesNotValid) {
            req.setAttribute("errors", errorDto);
            req.getServletContext().getRequestDispatcher("/view/new_match_form.jsp").forward(req, resp);
        } else {

            Player pl1 = playerDAO.findByName(player1name).orElseGet(() -> playerDAO.save(new Player(player1name)));
            Player pl2 = playerDAO.findByName(player2name).orElseGet(() -> playerDAO.save(new Player(player2name)));

            UUID uuid = UUID.randomUUID();
            Match match = new Match(pl1, pl2);

            ongoingMatchesService.createNewMatch(uuid, match);

            resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
        }
    }

    // Почему то после деплоя приложения на сервер, валидация перестала работать корректно, исключения при
    // ошибках не выбрасывались, поэтому пришлось делать все вот так.
    private boolean validatePlayerNames(String firstPName, String secondPName) {

        boolean hasErrors = false;
        errorDto = new PlayerNameHasErrorDto();

        if (!firstPName.chars().allMatch(Character::isLetter)) {
            errorDto.setPlayerOneNameNotValid(true);
            errorDto.setPlayerOneError("Player 1 name should contain only letters!");
            hasErrors = true;
        }

        if (!secondPName.chars().allMatch(Character::isLetter)) {
            errorDto.setPlayerTwoNameNotValid(true);
            errorDto.setPlayerTwoError("Player 2 name should contain only letters!");
            hasErrors = true;
        }

        if (firstPName == null || firstPName.isBlank()) {
            errorDto.setPlayerOneNameNotValid(true);
            errorDto.setPlayerOneError("Missing parameter - player 1 name");
            hasErrors = true;
        }

        if (secondPName == null || secondPName.isBlank()) {
            errorDto.setPlayerTwoNameNotValid(true);
            errorDto.setPlayerTwoError("Missing parameter - player 2 name");
            hasErrors = true;
        }

        if (firstPName.equals(secondPName)) {
            errorDto.setPlayerNamesAreSame(true);
            errorDto.setCommonError("Player names can't be same");
            hasErrors = true;
        }

        return hasErrors;
    }

}