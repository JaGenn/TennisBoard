package org.example.model;

import lombok.Getter;

@Getter
public class FinishedMatchViewDto {
    private String player1;
    private String player2;
    private String winner;
    private int player1Sets;
    private int player2Sets;

    public FinishedMatchViewDto(String player1, String player2, String winner, int player1Sets, int player2Sets) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        this.player1Sets = player1Sets;
        this.player2Sets = player2Sets;
    }
}
