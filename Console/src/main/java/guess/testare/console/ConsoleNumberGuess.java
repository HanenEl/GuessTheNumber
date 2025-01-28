package guess.testare.console;

import guess.testare.Game;
import guess.testare.MessageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleNumberGuess implements ApplicationListener<ContextRefreshedEvent> {

    // == constants ==
    private static final Logger log = LoggerFactory.getLogger(ConsoleNumberGuess.class);

    // == fields ==
    private final Game game;

    private final MessageGenerator messageGenerator;

    // == constructor ==


    public ConsoleNumberGuess(Game game, MessageGenerator messageGenerator) {
        this.game = game;
        this.messageGenerator = messageGenerator;
    }

    // == events ==
    @EventListener
        public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Container ready for use");
        boolean state = true;
        Scanner scanner = new Scanner(System.in);
        game.rest();
        while (state){
            System.out.println(messageGenerator.getMainMessage());
            System.out.println(messageGenerator.getResultMessage());

            int guess = scanner.nextInt();
            scanner.nextLine();
            game.setGuess(guess);
            game.check();

            if (game.isGameWon() || game.isGameLost()){
                System.out.println(messageGenerator.getResultMessage());
                System.out.println("Do you want to play again? y/n?");

                String playAgain = scanner.nextLine().trim();
                if (!playAgain.equalsIgnoreCase("y")){
                    state = false;
                    System.out.println("Exiting game...");
                    System.exit(0);
                }else{
                    game.rest();
                }
            }
        }
    }
}
