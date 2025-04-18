package edu.tcu.cs.frogcrewonline.game;

import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.crewmember.dto.MemberDto;
import edu.tcu.cs.frogcrewonline.game.converter.GameToGameDtoConverter;
import edu.tcu.cs.frogcrewonline.game.dto.GameDto;
import edu.tcu.cs.frogcrewonline.game.converter.GameToGeneralGameDtoConverter;
import edu.tcu.cs.frogcrewonline.game.dto.GeneralGameDto;
import edu.tcu.cs.frogcrewonline.system.Result;
import edu.tcu.cs.frogcrewonline.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gameSchedule")
public class GameController {

    private final GameService gameService;
    private final GameToGameDtoConverter gameToGameDtoConverter;
    private final GameToGeneralGameDtoConverter gameToGeneralGameDtoConverter;

    public GameController(GameToGameDtoConverter gameToGameDtoConverter,
                          GameToGeneralGameDtoConverter gameToGeneralGameDtoConverter,
                          GameService gameService) {
        this.gameToGameDtoConverter = gameToGameDtoConverter;
        this.gameToGeneralGameDtoConverter = gameToGeneralGameDtoConverter;
        this.gameService = gameService;
    }

   // UC-5: Crew Member Views General Game Schedule
    @GetMapping("/games")
    public Result getGeneralGameSchedule() {
        List<Game> foundGames = gameService.findAll();
        List<GeneralGameDto> gameDtos = foundGames.stream()
                .map(this.gameToGeneralGameDtoConverter::convert)
                .collect(Collectors.toList());

        if (gameDtos.isEmpty()) {
            return new Result(true, StatusCode.NO_CONTENT, "No upcoming games available at this time.", gameDtos);
        }

        return new Result(true, StatusCode.SUCCESS, "Find Success", gameDtos);
    }

    // UC-5: Crew Member finds game by game ID
    @GetMapping("/game/{gameId}")
    public Result getGameById(@PathVariable Integer gameId) {
        Game foundGame =this.gameService.findById(gameId);
        GeneralGameDto dto = gameToGeneralGameDtoConverter.convert(foundGame);
        return new Result(true, StatusCode.SUCCESS, "Find Success", dto);
    }

}
