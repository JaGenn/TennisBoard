package org.example.service;

import org.example.model.Score;
import org.example.model.entity.Match;
import org.example.model.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MatchScoreCalculationServiceTest {

    private MatchScoreCalculationService service;
    private Match match;
    private Player player1;
    private Player player2;
    private Score score;

    @BeforeEach
    public void setUp() {
        service = new MatchScoreCalculationService();
        player1 = new Player("Alex");
        player1.setId(1);
        player2 = new Player("Umar");
        player2.setId(2);

        match = new Match(player1, player2);
        score = match.getScore();
    }

    @Nested
    class SimpleScoringBehaviorTests {

        @Test
        @DisplayName("Player 1 earns points: 15 → 30 → 40")
        public void player1WinsPoint() {
            service.calculate(1, match);
            assertEquals(15, score.getPlayer1Points());
            service.calculate(1, match);
            assertEquals(30, score.getPlayer1Points());
            service.calculate(1, match);
            assertEquals(40, score.getPlayer1Points());
        }

        @Test
        @DisplayName("Player wins a game after scoring 4 points")
        public void gameWonWhenPlayerHasEnoughPoints() {
            win1Game(1);

            assertEquals(0, score.getPlayer1Points());
            assertEquals(1, score.getPlayer1Games());
        }

        @Test
        @DisplayName("Player wins a set after winning 6 games")
        public void setWonWhenPlayerWinsEnoughGames() {
            win1Set(1);

            assertEquals(0, score.getPlayer1Games());
            assertEquals(1, score.getPlayer1Sets());
        }

        @Test
        @DisplayName("Match ends when a player wins 2 sets")
        public void matchEndsWhenPlayerWinsTwoSets() {
            win1Set(1);
            win1Set(1);

            assertTrue(service.isGameFinished());
        }

        @Test
        @DisplayName("Winner is set correctly after match ends")
        public void winnerIsSetCorrectlyAfterMatchEnds() {
            win1Set(1);
            win1Set(1);

            assertEquals(1, match.getWinner().getId());
            assertEquals("Alex", match.getWinner().getName());
        }

    }

    @Nested
    class DeuceTests {

        @Test
        @DisplayName("Deuce activates at 40–40")
        public void deuceIsActivatedAt40_40() {
            win3Points(1);
            win3Points(2);
            service.calculate(1, match);

            assertTrue(score.isExtraRoundIsActive());
            assertEquals(1, score.getExtraPoints1());
        }

        @Test
        @DisplayName("Player wins game after 2-point lead in deuce")
        public void playerWinsGameAfterGainingTwoPointLeadInDeuce() {
            win3Points(1);
            win3Points(2);
            service.calculate(1, match);
            service.calculate(1, match);

            assertFalse(score.isExtraRoundIsActive());
            assertEquals(1, score.getPlayer1Games());
        }

        @Test
        @DisplayName("Deuce state resets after game is won")
        public void deuceResetsAfterGameWin() {
            win3Points(1);
            win3Points(2);

            service.calculate(1, match);
            assertTrue(score.isExtraRoundIsActive());

            service.calculate(1, match);
            assertEquals(1, score.getPlayer1Games());
            assertFalse(score.isExtraRoundIsActive());
        }

    }

    @Nested
    class TieBreakTests {

        @Test
        @DisplayName("Tie-break activates at 6–6 games")
        public void tieBreakStartsAt6_6Games() {
            win5Games(1);
            win5Games(2);
            win1Game(1);
            win1Game(2);

            assertTrue(score.isExtraRoundIsActive());
        }

        @Test
        @DisplayName("Tie-break ends when a player leads by 2 points")
        public void tieBreakIsWonWithTwoPointLeadAndSetsUpdate() {
            win5Games(1);
            win5Games(2);
            win1Game(1);
            win1Game(2);

            assertTrue(score.isExtraRoundIsActive());

            win3Points(1);
            win3Points(1);
            win3Points(2);
            win3Points(2);

            service.calculate(1, match);
            assertTrue(score.isExtraRoundIsActive());

            service.calculate(1, match);
            assertFalse(score.isExtraRoundIsActive());
            assertEquals(1, score.getPlayer1Sets());

        }

    }

    @Nested
    class ResetsAndTransitions {

        @Test
        @DisplayName("Points reset after game is won")
        public void pointsResetAfterGameWin() {
            service.calculate(1, match);
            service.calculate(1, match);
            service.calculate(1, match);
            assertEquals(40, score.getPlayer1Points());

            service.calculate(1, match);
            assertEquals(0, score.getPlayer1Points());
            assertEquals(0, score.getPlayer2Points());
            assertEquals(1, score.getPlayer1Games());
        }

        @Test
        @DisplayName("Games reset after set is won")
        public void gamesResetAfterSetWin() {
            win1Game(1);
            win1Game(1);
            win1Game(1);
            win1Game(1);
            win1Game(1);
            assertEquals(5, score.getPlayer1Games());

            win1Game(1);
            assertEquals(0, score.getPlayer1Games());
            assertEquals(0, score.getPlayer2Games());
            assertEquals(1, score.getPlayer1Sets());
        }

        @Test
        @DisplayName("All stats reset after match ends")
        public void allStatsResetAfterMatchEnd() {
            win1Set(1);
            win1Set(2);
            win1Set(1);

            assertTrue(service.isGameFinished());
            assertEquals(0, score.getPlayer1Points());
            assertEquals(0, score.getPlayer1Games());
            assertEquals(0, score.getPlayer2Points());
            assertEquals(0, score.getPlayer2Games());
        }

    }

    private void win1Game(int id) {
        service.calculate(id, match);
        service.calculate(id, match);
        service.calculate(id, match);
        service.calculate(id, match);
    }

    private void win3Points(int id) {
        service.calculate(id, match);
        service.calculate(id, match);
        service.calculate(id, match);
    }

    private void win5Games(int id) {
        win1Game(id);
        win1Game(id);
        win1Game(id);
        win1Game(id);
        win1Game(id);
    }

    private void win1Set(int id) {
        win1Game(id);
        win1Game(id);
        win1Game(id);
        win1Game(id);
        win1Game(id);
        win1Game(id);
    }

}