package edu.tcu.cs.frogcrewonline.gameschedule;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GameScheduleService {

    private final GameScheduleRepository gameScheduleRepository;

    public GameScheduleService(GameScheduleRepository gameScheduleRepository) {
        this.gameScheduleRepository = gameScheduleRepository;
    }

    public GameSchedule save(GameSchedule newGameSchedule) {
        return this.gameScheduleRepository.save(newGameSchedule);
    }
}
