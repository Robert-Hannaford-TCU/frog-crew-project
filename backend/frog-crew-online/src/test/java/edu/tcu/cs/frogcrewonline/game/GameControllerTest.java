package edu.tcu.cs.frogcrewonline.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.frogcrewonline.game.converter.GameDtoToGameConverter;
import edu.tcu.cs.frogcrewonline.game.dto.GameDto;
import edu.tcu.cs.frogcrewonline.system.StatusCode;
import edu.tcu.cs.frogcrewonline.system.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
    private GameDtoToGameConverter gameToGameDtoConverter;

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
    
    
    //Use Case 20 test:
    @Test
    void testAddGameToScheduleSuccess() throws Exception {
        Game game = new Game();
        game.setGameId(3);
        game.setScheduleId(1);
        game.setSportType("Football");
        game.setDate("2025-09-10");
        game.setTime("13:00:00");
        game.setVenue("Amon G. Carter Stadium");
        game.setOpponent("Texas Longhorns");
        game.setCrewPositions("Camera,Audio");
        game.setFinalized(true);
        game.setPublished(true);

        String json = objectMapper.writeValueAsString(game);

        // Given
        given(this.gameService.addGameToSchedule(Mockito.eq(1), Mockito.any(GameDto.class)))
                .willReturn(game);

        // When and then
        this.mockMvc.perform(post("/gameSchedule/1/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.gameId").value(3))
                .andExpect(jsonPath("$.data.sportType").value("Football"))
                .andExpect(jsonPath("$.data.venue").value("Amon G. Carter Stadium"))
                .andExpect(jsonPath("$.data.opponent").value("Texas Longhorns"));
    }


    // invalid id provided
    @Test
    void testAddGameToScheduleWithInvalidScheduleId() throws Exception {
        // Given
        Game game = new Game();
        game.setSportType("Football");
        game.setDate("2025-09-10");
        game.setTime("13:00:00");
        game.setVenue("Amon G. Carter Stadium");
        game.setOpponent("Texas Longhorns");
        game.setCrewPositions("Camera,Audio");
        game.setFinalized(true);
        game.setPublished(true);

        String json = objectMapper.writeValueAsString(game);

        given(this.gameService.addGameToSchedule(Mockito.eq(99), Mockito.any()))
                .willThrow(new ObjectNotFoundException("GameSchedule", 99));

        this.mockMvc.perform(post("/gameSchedule/99/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Could not find GameSchedule with id 99"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAddGameToScheduleAllFieldsMissing() throws Exception {
        // Given nothing

        // When and then
        this.mockMvc.perform(post("/gameSchedule/1/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details."))
                .andExpect(jsonPath("$.data.sportType").value("Sport type is required."))
                .andExpect(jsonPath("$.data.date").value("Date is required."))
                .andExpect(jsonPath("$.data.time").value("Time is required."))
                .andExpect(jsonPath("$.data.venue").value("Venue is required."))
                .andExpect(jsonPath("$.data.crewPositions").value("Crew positions are required."));
    }



}


