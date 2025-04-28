package edu.tcu.cs.frogcrewonline.game;


import edu.tcu.cs.frogcrewonline.crewmember.utils.IdWorker;
import edu.tcu.cs.frogcrewonline.game.converter.GameDtoToGameConverter;
import edu.tcu.cs.frogcrewonline.game.dto.GameDto;
import edu.tcu.cs.frogcrewonline.gameschedule.GameSchedule;
import edu.tcu.cs.frogcrewonline.gameschedule.GameScheduleRepository;
import edu.tcu.cs.frogcrewonline.system.exception.ObjectNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GameService {

    @Autowired
    private GameScheduleRepository gameScheduleRepository;

    @Autowired
    private GameDtoToGameConverter gameDtoToGameConverter;

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAll(){
        return gameRepository.findAll();
    }

//    public List<Game> findFinalizedAndPublishedGames() {
//        return gameRepository.findByFinalizedTrueAndPublishedTrue();
//    }

    public Game findById(Integer gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new ObjectNotFoundException("game", gameId));
    }

 // Use Case 20: add Game to Game Schedule
    public Game addGameToSchedule(Integer scheduleId, GameDto gameDto) {
        GameSchedule schedule = gameScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ObjectNotFoundException("GameSchedule", scheduleId));

        Game game = gameDtoToGameConverter.convert(gameDto);
        game.setScheduleId(schedule.getId());  // associate it

        return gameRepository.save(game);
    }
}
