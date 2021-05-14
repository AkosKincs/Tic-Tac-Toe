package tictactoe.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.ZonedDateTime;


/**
 * Class for representing the result of a game
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class GameResult {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * The name of the player who played the game.
     */

    @Column(nullable = false)
    private String winnerName;

    /**
     * The name of the opponent.
     */

    @Column(nullable = false)
    private String loserName;


    /**
     * The number of steps made by the winner player.
     */

    @Column(nullable = false)
    private Integer stepsForWin;


    /**
     * The duration of the game.
     */

    @Column(nullable = false)
    private Duration duration;

    /**
     * The timestamp when the result was saved.
     */

    @Column(nullable = false)
    private ZonedDateTime created;

    @PrePersist
    protected void onPersist() {
        created = ZonedDateTime.now();
    }

}

