package tictactoe.results;

import util.jpa.GenericJpaDao;

import javax.transaction.Transactional;
import java.util.List;

/**
 * DAO class for the {@link GameResult} entity.
 */
public class GameResultDao extends GenericJpaDao<GameResult> {

    /**
     * The constructor of GameResult Data Access Object.
     */
    public GameResultDao() {
        super(GameResult.class);
    }

    /**
     * Returns the list of all results with respect to the number of steps
     * needed for the win and the time spent with playing the game.
     *
     * @return the list of all results with respect to the number of steps
     * needed for the win and the time spent with playing the game
     */
    @Transactional
    public List<GameResult> findAll() {
        return entityManager.createQuery("SELECT r FROM GameResult r order by r.stepsForWin asc, r.duration", GameResult.class)
                .getResultList();
    }

}

