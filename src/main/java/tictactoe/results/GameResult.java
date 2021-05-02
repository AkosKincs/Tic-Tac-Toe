package tictactoe.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Class representing the result of a game played by 2 players.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class GameResult {

    @Id
    @GeneratedValue()
    private Long id;

    /**
     * The name of player 1.
     */
    private String player1;

    /**
     * The name of player 2.
     */
    private String player2;

    /**
     * Indicates whether the players have solved the puzzle.
     */
    private boolean solved;

    /**
     * The name of the game winner.
     */
    private String winner;

    /**
     * The duration of the game.
     */
    @Column(nullable = false)
    private Duration duration;

    /**
     * The timestamp when the result was saved.
     */
    private ZonedDateTime date;

    @PrePersist
    protected void onPersist() {
        date = ZonedDateTime.now();
    }

}
