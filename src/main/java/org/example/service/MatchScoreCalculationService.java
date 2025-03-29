package org.example.service;

import lombok.Getter;
import org.example.model.FinishedMatchViewDto;
import org.example.model.Score;
import org.example.model.entity.Match;
import org.example.model.entity.Player;

public class MatchScoreCalculationService {

    private static final short COUNT_OF_SETS_TO_WIN = 2;
    private FinishedMatchViewDto resultDto;

    private Match match;
    private Player player1;
    private Player player2;
    private Score score;
    private int player1Sets = 0;
    private int player2Sets = 0;
    private int player1Games = 0;
    private int player2Games = 0;
    private int player1Points = 0;
    private int player2Points = 0;
    private int p1extra = 0;
    private int p2extra = 0;
    @Getter
    private boolean gameFinished;
    private boolean deyusIsActive = false;
    private boolean tieBreakIsActive = false;

    public void initMatch(Match match) {
        this.match = match;
        this.player1 = match.getPlayer1();
        this.player2 = match.getPlayer2();
        this.score = match.getScore();
        this.gameFinished = false;
    }

    public void calculate(int id) {
        // deyus - это ситуация когда счет по очкам 40-40 и для гейма надо быть на 2 очка впереди
        if (deyusIsActive) {
            deyus(id);
        } else if (tieBreakIsActive) {
            tieBreak(id);
        } else {
            addPoints(id);
        }
    }

    // Начисление очков
    private void addPoints(int id) {
        if (player1.getId() == id) {
            player1Points += 1;
        } else {
            player2Points += 1;
        }

        if (player1Points >= 4 || player2Points >= 4) {
            int pointsDiff = Math.abs(player1Points - player2Points);
            if (pointsDiff >= 2) {
                makeBothPointsZero();
                addGames(id);
            } else {
                deyusIsActive = true;
                score.setExtraRoundIsActive(true);
                deyus(id);
            }
        }
        increasePoints(player1Points, player2Points);
    }

    // Начисление геймов
    private void addGames(int id) {
        if (player1.getId() == id) {
            player1Games += 1;
        } else {
            player2Games += 1;
        }

        if (player1Games >= 6 || player2Games >= 6) {
            int gamesDiff = Math.abs(player1Games - player2Games);
            if (gamesDiff >= 2) {
                makeBothPointsZero();
                makeBothGamesZero();
                addSets(id);
            } else if (player1Games == 6 && player2Games == 6) {
                tieBreakIsActive = true;
                score.setExtraRoundIsActive(true);
            }
        }

        score.setPlayer1Games(player1Games);
        score.setPlayer2Games(player2Games);
    }

    // Начисление сетов
    private void addSets(int id) {
        if (player1.getId() == id) {
            player1Sets += 1;
        } else {
            player2Sets += 1;
        }

        if (player1Sets == COUNT_OF_SETS_TO_WIN) {
            FinishedMatchesService.createResultDto(id, player1, player2, player1Sets, player2Sets);
            makeBothSetsZero();
            finish(player1);
        }
        if (player2Sets == COUNT_OF_SETS_TO_WIN) {
            FinishedMatchesService.createResultDto(id, player1, player2, player1Sets, player2Sets);
            makeBothSetsZero();
            finish(player2);
        }

        score.setPlayer1Sets(player1Sets);
        score.setPlayer2Sets(player2Sets);
    }

    private void deyus(int id) {
        if (player1.getId() == id) {
            p1extra += 1;
        } else {
            p2extra += 1;
        }
        int diff = Math.abs(p1extra - p2extra);
        if (diff >= 2) {
            makeBothPointsZero();
            offDeyus();
            addGames(id);
        }

        score.setExtraPoints1(p1extra);
        score.setExtraPoints2(p2extra);
    }

    private void tieBreak(int id) {
        if (player1.getId() == id) {
            p1extra += 1;
        } else {
            p2extra += 1;
        }
        if (p1extra >= 7 || p2extra >= 7) {
            int diff = Math.abs(p1extra - p2extra);
            if (diff >= 2) {
                makeBothPointsZero();
                makeBothGamesZero();
                offTieBreak();
                addSets(id);
            }
        }
        score.setExtraPoints1(p1extra);
        score.setExtraPoints2(p2extra);
    }

    private void offDeyus() {
        deyusIsActive = false;
        p1extra = 0;
        p2extra = 0;
        score.setExtraRoundIsActive(false);
        score.setExtraPoints1(p1extra);
        score.setExtraPoints2(p2extra);
    }

    private void offTieBreak() {
        tieBreakIsActive = false;
        p1extra = 0;
        p2extra = 0;
        score.setExtraRoundIsActive(false);
        score.setExtraPoints1(p1extra);
        score.setExtraPoints2(p2extra);
    }

    private void makeBothPointsZero() {
        player1Points = 0;
        player2Points = 0;
        score.setPlayer1Points(player1Points);
        score.setPlayer2Points(player2Points);
    }

    private void makeBothGamesZero() {
        player1Games = 0;
        player2Games = 0;
        score.setPlayer1Games(player1Games);
        score.setPlayer2Games(player2Games);
    }

    private void makeBothSetsZero() {
        player1Sets = 0;
        player2Sets = 0;
        score.setPlayer1Sets(player1Sets);
        score.setPlayer2Sets(player2Sets);
    }

    private void increasePoints(int player1Points, int player2Points) {

        switch (player1Points) {
            case 1:
                score.setPlayer1Points(15);
                break;
            case 2:
                score.setPlayer1Points(30);
                break;
            case 3:
                score.setPlayer1Points(40);
                break;
        }

        switch (player2Points) {
            case 1:
                score.setPlayer2Points(15);
                break;
            case 2:
                score.setPlayer2Points(30);
                break;
            case 3:
                score.setPlayer2Points(40);
                break;
        }
    }

    private void finish(Player winner) {
        match.setWinner(winner);
        gameFinished = true;
    }



}
