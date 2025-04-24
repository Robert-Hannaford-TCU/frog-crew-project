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

    //test with dummy data
    @PostConstruct
    public void init() {
        if (gameRepository.count() == 0) {
            Game g1 = new Game();
            g1.setScheduleId(1);
            g1.setDate("2024-09-07");
            g1.setTime("13:00:00");
            g1.setVenue("Carter");
            g1.setOpponent("LSU");
            g1.setFinalized(true);
            g1.setPublished(true);
            g1.setSportType("Football");
            g1.setCrewPositions("Camera,Audio");

            Game g2 = new Game();
            g2.setScheduleId(1);
            g2.setDate("2024-09-14");
            g2.setTime("14:00:00");
            g2.setVenue("G stadium");
            g2.setOpponent("TX State");
            g2.setFinalized(true);
            g2.setPublished(true);
            g2.setSportType("Football");
            g2.setCrewPositions("Director,Replay");

            gameRepository.saveAll(List.of(g1, g2));

        }
    }

}
