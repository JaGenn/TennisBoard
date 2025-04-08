package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FinishedMatchViewDto {
    private String player1;
    private String player2;
    private String winner;
    private int player1Sets;
    private int player2Sets;
}
