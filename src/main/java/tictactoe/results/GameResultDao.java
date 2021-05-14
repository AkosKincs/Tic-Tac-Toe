package tictactoe.results;

import util.jpa.GenericJpaDao;

import javax.transaction.Transactional;
import java.util.List;


public class GameResultDao extends GenericJpaDao<GameResult> {

    public GameResultDao() {
        super(GameResult.class);
    }

    /**
     * Returns the list of all results with respect to the number of steps
     * and the time spent for playing the game.
     *
     * @return the list of all results with respect to the number of steps
     * and the time spent for playing the game
     */
    @Transactional
    public List<GameResult> findAll() {
        return entityManager.createQuery("SELECT r FROM GameResult r order by r.stepsForWin asc, r.duration", GameResult.class)
                .getResultList();
    }

}

