package edu.tcu.cs.frogcrewonline.game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {


    @Mock
    GameRepository gameRepository;

    @InjectMocks
    GameService gameService;

    List<Game> games;

    @BeforeEach
    void setUp() {
        this.games = new ArrayList<>();

        Game g1 = new Game();
        g1.setGameId(1);
        g1.setDate("2024-09-07");
        g1.setTime("13:00:00");
        g1.setVenue("Carter");
        g1.setOpponent("LSU");
        g1.setFinalized(false);

        this.games.add(g1);

        Game g2 = new Game();
        g2.setGameId(2);
        g2.setDate("2024-09-07");
        g2.setTime("14:00:00");
        g2.setVenue("G stadium");
        g2.setOpponent("TX State");
        g2.setFinalized(false);

        this.games.add(g2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAllGamesSuccess() {
        // Given. Arrange inputs and targets. Define the behavior of Mock object userRepository.
        given(this.gameRepository.findAll()).willReturn(this.games);

        // When. Act on the target behavior. Act steps should cover the method to be tested.
        List<Game> actualUsers = this.gameService.findAll();

        // Then. Assert expected outcomes.
        assertThat(actualUsers.size()).isEqualTo(this.games.size());

        // Verify gameRepository.findAll() is called exactly once.
        verify(this.gameRepository, times(1)).findAll();
    }

}