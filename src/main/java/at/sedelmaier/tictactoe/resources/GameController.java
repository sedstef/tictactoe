package at.sedelmaier.tictactoe.resources;

import at.sedelmaier.tictactoe.service.GameService;
import at.sedelmaier.tictactoe.model.Board;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping
    public Board getBoard() {
        return service.getBoard();
    }

}
