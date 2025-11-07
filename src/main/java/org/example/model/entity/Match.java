package org.example.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Score;


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

    @Setter
    @ManyToOne
    @JoinColumn(name = "winner", referencedColumnName = "id", nullable = false)
    private Player winner;

    @Transient
    private Score score;

    public Match(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.score = new Score();
    }
}
