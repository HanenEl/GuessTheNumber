package guess.services;

import guess.testare.Game;
import guess.testare.MessageGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class GameServiceImpl implements GameService{

    // == fields ==

    private final Game game;
    private final MessageGenerator message;

    // == constructor injection ==

    public GameServiceImpl(Game game , MessageGenerator message) {
        this.game = game;
        this.message = message;
    }

    // == test ==
    @PostConstruct
    public void init(){
        log.info("MainMessage: {}", getMainMessage());
        log.info("MainMessage: {}", game.getNumber());
    }

    // == public methods ==
    @Override
    public boolean isGameOver() {
        if (game.isGameWon() && !game.isGameLost()){

            return true;

        }
        return false;
    }

    @Override
    public String getMainMessage() {
        return message.getMainMessage();
    }

    @Override
    public String getResultMessage() {
        return message.getResultMessage();
    }

    @Override
    public void checkGuess(int guess) {
        game.setGuess(guess);
        game.check();
    }

    @Override
    public void reset() {
        game.rest();
    }
}
