package edu.tcu.cs.frogcrewonline.crewassignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.frogcrewonline.crewassignment.dto.CrewAssignmentDto;
import edu.tcu.cs.frogcrewonline.crewassignment.dto.CrewListDto;
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
            this.dto = new CrewAssignmentDto(
                    1,
                    2,
                    1,
                    "CAMERA",
                    "Jane Smith",
                    "11:45",
                    "Truck Bay"
            );

            this.fullList = new CrewListDto(
                    1,
                    "12:00",
                    "2025-09-07",
                    "Amon G. Carter Stadium",
                    "Texas Longhorns",
                    List.of(dto)
            );

            this.emptyList = new CrewListDto(
                    1,
                    "12:00",
                    "2025-09-07",
                    "Amon G. Carter Stadium",
                    "Texas Longhorns",
                    List.of()
            );
        }

        @AfterEach
        void tearDown() {
        }

        // Find Success
        @Test
        void testGetCrewListSuccess() throws Exception {
            given(this.crewAssignmentService.getCrewListForGame(1)).willReturn(fullList);

            this.mockMvc.perform(get("/crewList/1").accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.flag").value(true))
                    .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                    .andExpect(jsonPath("$.message").value("Find Success"))
                    .andExpect(jsonPath("$.data.gameId").value(1))
                    .andExpect(jsonPath("$.data.opponent").value("Texas Longhorns"))
                    .andExpect(jsonPath("$.data.crewedMembers[0].userId").value(2))
                    .andExpect(jsonPath("$.data.crewedMembers[0].position").value("CAMERA"))
                    .andExpect(jsonPath("$.data.crewedMembers[0].fullName").value("Jane Smith"));
        }

        // Game exists but no crew assigned
        @Test
        void testGetCrewListNoAssignments() throws Exception {
            given(this.crewAssignmentService.getCrewListForGame(1)).willReturn(emptyList);

            this.mockMvc.perform(get("/crewList/1").accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.flag").value(true))
                    .andExpect(jsonPath("$.code").value(StatusCode.NO_CONTENT))
                    .andExpect(jsonPath("$.message").value("No crew assigned to this game."))
                    .andExpect(jsonPath("$.data").doesNotExist());
        }

        // Game not found
        @Test
        void testGetCrewListGameNotFound() throws Exception {
            given(this.crewAssignmentService.getCrewListForGame(5)).willThrow(new ObjectNotFoundException("Game", 5));

            this.mockMvc.perform(get("/crewList/5").accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.flag").value(false))
                    .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                    .andExpect(jsonPath("$.message").value("Could not find Game with id 5"))
                    .andExpect(jsonPath("$.data").isEmpty());
        }
    }

