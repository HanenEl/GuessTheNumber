package guess.testare;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Getter
@Component
public class GameImpl implements Game{

    // == fields ==
    @Getter(AccessLevel.NONE)
    private final NumberGenerator numberGenerator;

    private final int guessCount;
    private int number;
    private int smallest;
    private int biggest;
    private int remainingGuesses;
    private boolean validNumberRange = true;

    @Setter
    private int guess;

    // == constructor ==

    @Autowired
    public GameImpl(NumberGenerator numberGenerator,@GuessCounter int guessCount) {
        this.numberGenerator = numberGenerator;
        this.guessCount = guessCount;
    }

    @Override
    public int getRemaining() {
        return getRemainingGuesses();
    }

    // == init ==
    @PostConstruct
    @Override
    public void rest() {
        smallest = numberGenerator.getMinNumber();
        guess = 0;
        remainingGuesses = guessCount;
        this.biggest = numberGenerator.getMaxNumber();
        number = numberGenerator.next();
    }
    @PreDestroy
    public void preDestroy(){
        log.info("In Game preDestroy()");
    }

    // == public methods ==
    @Override
    public void check() {
        checkValidRange();

        if (validNumberRange){
            if (guess > number){
                biggest=guess - 1;
            }
            else if (guess < number){
                smallest = guess + 1;
            }
        }
        remainingGuesses--;
    }
    @Override
    public boolean isGameWon() {
        return guess == number;
    }

    @Override
    public boolean isGameLost() {
        return !isGameWon() && remainingGuesses <= 0;
    }

    //== private methods ==
    private void checkValidRange(){
        validNumberRange = (guess >= smallest) && (guess <= biggest);
    }
}
