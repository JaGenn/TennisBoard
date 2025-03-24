package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Matches")
@NoArgsConstructor
@Getter
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "player1", referencedColumnName = "id", nullable = false)
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player2", referencedColumnName = "id", nullable = false)
    private Player player2;

    @ManyToOne
    @JoinColumn(name = "winner", referencedColumnName = "id", nullable = false)
    private Player winner;

    public Match(Player player1, Player player2, Player winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }
}
