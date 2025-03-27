package org.example.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Score {

    private int player1Sets;
    private int player2Sets;
    private int player1Games;
    private int player2Games;
    private int player1Points;
    private int player2Points;

    public Score() {
        this.player1Games = 0;
        this.player2Games = 0;
        this.player1Sets = 0;
        this.player2Sets = 0;
        this.player1Points = 0;
        this.player2Points = 0;
    }
}
