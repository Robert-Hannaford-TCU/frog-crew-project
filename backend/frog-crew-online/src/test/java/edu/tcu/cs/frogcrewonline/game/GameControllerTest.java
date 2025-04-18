package edu.tcu.cs.frogcrewonline.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.frogcrewonline.system.StatusCode;
import edu.tcu.cs.frogcrewonline.system.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private GameService gameService;

    List<Game> games;

    @BeforeEach
    void setUp() {
        this.games = new ArrayList<>();

        Game g1 = new Game();
        g1.setGameId(1);
        g1.setScheduleId(1);
        g1.setDate("2024-09-07");
        g1.setVenue("Carter");
        g1.setOpponent("LSU");
        g1.setFinalized(true);
        g1.setPublished(true);
        this.games.add(g1);

        Game g2 = new Game();
        g2.setGameId(2);
        g2.setScheduleId(1);
        g2.setDate("2024-09-07");
        g2.setVenue("G stadium");
        g2.setOpponent("TX State");
        g2.setFinalized(true);
        g2.setPublished(true);
        this.games.add(g2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetGeneralGameScheduleSuccess() throws Exception {
        // Given
        given(this.gameService.findAll()).willReturn(this.games);

        // When and Then
        this.mockMvc.perform(get("/gameSchedule/games").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.games.size())))

                // First Game
                .andExpect(jsonPath("$.data[0].gameId").value(1))
                .andExpect(jsonPath("$.data[0].scheduleId").value(1))
                .andExpect(jsonPath("$.data[0].gameDate").value("2024-09-07"))
                .andExpect(jsonPath("$.data[0].venue").value("Carter"))
                .andExpect(jsonPath("$.data[0].opponent").value("LSU"))
                .andExpect(jsonPath("$.data[0].isFinalized").value(true))

                // Second Game
                .andExpect(jsonPath("$.data[1].gameId").value(2))
                .andExpect(jsonPath("$.data[1].scheduleId").value(1))
                .andExpect(jsonPath("$.data[1].gameDate").value("2024-09-07"))
                .andExpect(jsonPath("$.data[1].venue").value("G stadium"))
                .andExpect(jsonPath("$.data[1].opponent").value("TX State"))
                .andExpect(jsonPath("$.data[1].isFinalized").value(true));
    }

    @Test
    void testGetGameByIdSuccess() throws Exception {
        // Given
        given(gameService.findById(1)).willReturn(this.games.get(0));

        // when and then
        mockMvc.perform(get("/gameSchedule/game/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find Success"))
                .andExpect(jsonPath("$.data.gameId").value(1))
                .andExpect(jsonPath("$.data.scheduleId").value(1))
                .andExpect(jsonPath("$.data.gameDate").value("2024-09-07"))
                .andExpect(jsonPath("$.data.venue").value("Carter"))
                .andExpect(jsonPath("$.data.opponent").value("LSU"))
                .andExpect(jsonPath("$.data.isFinalized").value(true));
    }

    @Test
    void testGetGameByIdNotFound() throws Exception {
        // Given
        given(gameService.findById(42))
                .willThrow(new ObjectNotFoundException("game", 42));

        // When and then
        mockMvc.perform(get("/gameSchedule/game/42")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message")
                        .value("Could not find game with id 42"))
                .andExpect(jsonPath("$.data").isEmpty());
    }



}


