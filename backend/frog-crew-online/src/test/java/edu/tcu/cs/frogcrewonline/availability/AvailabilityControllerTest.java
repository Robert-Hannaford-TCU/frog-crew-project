package edu.tcu.cs.frogcrewonline.availability;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.frogcrewonline.availability.converter.AvailabilityDtoToAvailabilityConverter;
import edu.tcu.cs.frogcrewonline.availability.converter.AvailabilityToAvailabilityDtoConverter;
import edu.tcu.cs.frogcrewonline.availability.dto.AvailabilityDto;
import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.game.Game;
import edu.tcu.cs.frogcrewonline.system.StatusCode;
import edu.tcu.cs.frogcrewonline.system.exception.ConflictException;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AvailabilityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    AvailabilityService availabilityService;

    @MockitoBean
    AvailabilityDtoToAvailabilityConverter dtoToEntity;

    @MockitoBean
    AvailabilityToAvailabilityDtoConverter entityToDto;

    private AvailabilityDto dto;
    private Availability availability;

    @BeforeEach
    void setUp() {

        //Test data
        dto = new AvailabilityDto(1, 2, true, "Ready to work");
        CrewMember crew = new CrewMember();
        crew.setUserId(1);

        Game game = new Game();
        game.setGameId(2);

        availability = new Availability();
        availability.setCrewMember(crew);
        availability.setGame(game);
        availability.setAvailable(true);
        availability.setComment("Ready to work");

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAddAvailabilitySuccess() throws Exception {
        // Given
        given(this.dtoToEntity.convert(dto)).willReturn(availability);
        given(this.availabilityService.create(Mockito.any(Availability.class))).willReturn(availability);
        given(this.entityToDto.convert(availability)).willReturn(dto);

        // When and then
        this.mockMvc.perform(post("/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.gameId").value(2))
                .andExpect(jsonPath("$.data.available").value(true))
                .andExpect(jsonPath("$.data.comment").value("Ready to work"));

    }

    @Test
    void testAddAvailabilityConflict() throws Exception {
        // Given
        given(this.dtoToEntity.convert(dto)).willReturn(availability);
        given(this.availabilityService.create(Mockito.any(Availability.class)))
                .willThrow(new ConflictException("Availability already exists"));

        // When and then
        this.mockMvc.perform(post("/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.CONFLICT))
                .andExpect(jsonPath("$.message").value("Availability already exists"));
    }

    @Test
    void testAddAvailabilityNotFound() throws Exception {
        // Given
        given(this.dtoToEntity.convert(dto))
                .willThrow(new ObjectNotFoundException("CrewMember", 1));

        // When and then
        this.mockMvc.perform(post("/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find CrewMember with id 1"));
    }

    @Test
    void AddAvailabilityBadRequest() throws Exception {
        // Given: a DTO missing required field (userId)
        AvailabilityDto badDto = new AvailabilityDto(null, 2, true, "Missing userId");

        // When and then
        this.mockMvc.perform(post("/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.INVALID_ARGUMENT))
                .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details."))
                .andExpect(jsonPath("$.data.userId").value("userId is required."));
    }


}