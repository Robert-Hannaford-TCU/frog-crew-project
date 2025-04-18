package edu.tcu.cs.frogcrewonline.crewassignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.frogcrewonline.crewassignment.converter.CrewAssignmentToCrewAssignmentDtoConverter;
import edu.tcu.cs.frogcrewonline.crewassignment.dto.CrewAssignmentDto;
import edu.tcu.cs.frogcrewonline.crewassignment.dto.CrewListDto;
import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.game.Game;
import edu.tcu.cs.frogcrewonline.system.StatusCode;
import edu.tcu.cs.frogcrewonline.system.exception.ObjectNotFoundException;
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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

    @SpringBootTest
    @AutoConfigureMockMvc(addFilters = false)
    class CrewAssignmentControllerTest {

        @Autowired
        MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        @MockitoBean
        CrewAssignmentService crewAssignmentService;

        CrewAssignmentDto dto;
        CrewListDto fullList;
        CrewListDto emptyList;

        @BeforeEach
        void setUp() {

            // Crew Members
            CrewMember cm = new CrewMember();
            cm.setUserId(2);
            cm.setFirstName("Jane");
            cm.setLastName("Smith");

            CrewMember cm2 = new CrewMember();
            cm2.setUserId(3);
            cm2.setFirstName("John");
            cm2.setLastName("Doe");

            // Game
            Game game = new Game();
            game.setGameId(1);
            game.setDate("2025-09-07");
            game.setTime("12:00");
            game.setVenue("Amon G. Carter Stadium");
            game.setOpponent("Texas Longhorns");

            // Assign Crew Members to game
            CrewAssignment ca = new CrewAssignment();
            ca.setId(1);
            ca.setCrewMember(cm);
            ca.setGame(game);
            ca.setPosition("CAMERA");
            ca.setReportTime("11:45");
            ca.setReportLocation("Truck Bay");

            CrewAssignment ca2 = new CrewAssignment();
            ca2.setId(2);
            ca2.setCrewMember(cm2);
            ca2.setGame(game);
            ca2.setPosition("DIRECTOR");
            ca2.setReportTime("11:30");
            ca2.setReportLocation("Control Room");

            // Convert both entities to dtos

            CrewAssignmentToCrewAssignmentDtoConverter converter =
                    new CrewAssignmentToCrewAssignmentDtoConverter();

            CrewAssignmentDto dto1 = converter.convert(ca);
            CrewAssignmentDto dto2 = converter.convert(ca2);

            fullList = new CrewListDto(
                    1,
                    "12:00",
                    "2025-09-07",
                    "Amon G. Carter Stadium",
                    "Texas Longhorns",
                    List.of(dto1, dto2)     // with crew
            );

            emptyList = new CrewListDto(
                    1,
                    "12:00",
                    "2025-09-07",
                    "Amon G. Carter Stadium",
                    "Texas Longhorns",
                    List.of()         // no crew
            );
        }

        @AfterEach
        void tearDown() {
        }

        // Find Success
        @Test
        void testGetCrewListSuccess() throws Exception {
            //Given
            given(this.crewAssignmentService.getCrewListForGame(1)).willReturn(fullList);

            //When and then
            this.mockMvc.perform(get("/crewList/1").accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.flag").value(true))
                    .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                    .andExpect(jsonPath("$.message").value("Find Success"))
                    .andExpect(jsonPath("$.data.gameId").value(1))
                    .andExpect(jsonPath("$.data.opponent").value("Texas Longhorns"))
                    .andExpect(jsonPath("$.data.crewedMembers[0].userId").value(2))
                    .andExpect(jsonPath("$.data.crewedMembers[0].position").value("CAMERA"))
                    .andExpect(jsonPath("$.data.crewedMembers[1].userId").value(3))
                    .andExpect(jsonPath("$.data.crewedMembers[0].fullName").value("Jane Smith"))
                    .andExpect(jsonPath("$.data.crewedMembers[1].fullName").value("John Doe"))
                    .andExpect(jsonPath("$.data.crewedMembers[1].position").value("DIRECTOR"));;
        }

        // Game exists but no crew assigned ----- not sure if needed... Will ask
//        @Test
//        void testGetCrewListNoAssignments() throws Exception {
//            // Given
//            given(this.crewAssignmentService.getCrewListForGame(1)).willReturn(emptyList);
//
//            // When and then
//            this.mockMvc.perform(get("/crewList/1").accept(MediaType.APPLICATION_JSON))
//                    .andExpect(jsonPath("$.flag").value(true))
//                    .andExpect(jsonPath("$.code").value(StatusCode.NO_CONTENT))
//                    .andExpect(jsonPath("$.message").value("No crew assigned to this game."))
//                    .andExpect(jsonPath("$.data").doesNotExist());
//        }

        // Game not found
        @Test
        void testGetCrewListGameNotFound() throws Exception {
            //Given
            given(this.crewAssignmentService.getCrewListForGame(5)).willThrow(new ObjectNotFoundException("Game", 5));

            //When and then
            this.mockMvc.perform(get("/crewList/5").accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.flag").value(false))
                    .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                    .andExpect(jsonPath("$.message").value("Could not find Game with id 5"))
                    .andExpect(jsonPath("$.data").isEmpty());
        }
    }

