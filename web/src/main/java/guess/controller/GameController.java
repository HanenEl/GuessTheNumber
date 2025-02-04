package guess.controller;

import guess.services.GameService;
import guess.util.AttributeNames;
import guess.util.GameMappings;
import guess.util.viewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class GameController {

    // == fields ==
    private final GameService gameService;

    // == constructor ==
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // == request methods ==

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("playUrl",GameMappings.PLAY);
        return "Home";
    }

    @GetMapping("game-over")
    public String gameOver(Model model){
        model.addAttribute("restartUrl","/" + GameMappings.RESTART);
        model.addAttribute("homeUrl",GameMappings.HOME);
        model.addAttribute(AttributeNames.RESULT_MESSAGE,gameService.getResultMessage());
        return "game-over";
    }

    @GetMapping(GameMappings.PLAY)
    public String play(Model model){
        viewNames view = new viewNames();
        model.addAttribute(AttributeNames.MAIN_MESSAGE,gameService.getMainMessage());
        model.addAttribute(AttributeNames.RESULT_MESSAGE,gameService.getResultMessage());
        model.addAttribute("playUrl",GameMappings.PLAY);
        log.info("model={}, gameover={}",model,gameService.isGameOver());
        if (gameService.isGameOver()){
            return "redirect:/game-over";
        }
        return view.PLAY;
    }

    @PostMapping(GameMappings.PLAY)
    public String processMessage(@RequestParam int guess){
        log.info("guess={}",guess);
        gameService.checkGuess(guess);
        viewNames view = new viewNames();
        return GameMappings.REDIRECT;
    }

    @GetMapping(GameMappings.RESTART)
    public String restart(){
        gameService.reset();
        log.info("Game restarted. isGameOver = {}", gameService.isGameOver());
        return GameMappings.REDIRECT;
    }
}
