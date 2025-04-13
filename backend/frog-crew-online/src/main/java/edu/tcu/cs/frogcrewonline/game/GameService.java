package edu.tcu.cs.frogcrewonline.game;


import edu.tcu.cs.frogcrewonline.crewmember.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAll(){
        return gameRepository.findAll();
    }

}
