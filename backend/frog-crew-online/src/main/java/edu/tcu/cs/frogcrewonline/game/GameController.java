package edu.tcu.cs.frogcrewonline.game;

import edu.tcu.cs.frogcrewonline.game.converter.GameToGameDtoConverter;
import edu.tcu.cs.frogcrewonline.game.dto.GameDto;
import edu.tcu.cs.frogcrewonline.system.Result;
import edu.tcu.cs.frogcrewonline.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gameSchedule")
public class GameController {

    private GameService gameService;

    private final GameToGameDtoConverter gameToGameDtoConverter;

    public GameController(GameToGameDtoConverter gameToGameDtoConverter, GameService gameService) {
        this.gameToGameDtoConverter = gameToGameDtoConverter;
        this.gameService = gameService;
    }

   // UC-5: Crew Member Views General Game Schedule
    @GetMapping("/games")
    public Result getGeneralGameSchedule() {
        List<Game> foundGames = gameService.findAll();
        List<GameDto> gameDtos = foundGames.stream()
                .map(this.gameToGameDtoConverter::convert)
                .collect(Collectors.toList());

        if (gameDtos.isEmpty()) {
            return new Result(true, StatusCode.NO_CONTENT, "No upcoming games available at this time.", gameDtos);
        }

        return new Result(true, StatusCode.SUCCESS, "Find Success", gameDtos);
    }

}
