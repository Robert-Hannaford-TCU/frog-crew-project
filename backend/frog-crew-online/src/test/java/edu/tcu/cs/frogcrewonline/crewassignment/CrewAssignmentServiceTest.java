package edu.tcu.cs.frogcrewonline.crewassignment;

import edu.tcu.cs.frogcrewonline.crewassignment.converter.CrewAssignmentToCrewAssignmentDtoConverter;
import edu.tcu.cs.frogcrewonline.crewassignment.dto.CrewAssignmentDto;
import edu.tcu.cs.frogcrewonline.crewassignment.dto.CrewListDto;
import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.game.Game;
import edu.tcu.cs.frogcrewonline.game.GameRepository;
import edu.tcu.cs.frogcrewonline.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class CrewAssignmentServiceTest {

    private CrewAssignmentRespository crewAssignmentRespository;
    private CrewAssignmentToCrewAssignmentDtoConverter converter;
    private GameRepository gameRepository;
    private CrewAssignmentService service;

    @BeforeEach
    void setUp() {
        this.crewAssignmentRespository = mock(CrewAssignmentRespository.class);
        this.converter = mock(CrewAssignmentToCrewAssignmentDtoConverter.class);
        this.gameRepository = mock(GameRepository.class);

        this.service = new CrewAssignmentService(
                crewAssignmentRespository,
                converter,
                gameRepository
        );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetCrewListSuccess() {
        // Given
        Integer gameId = 1;
        Game game = new Game();
        game.setGameId(gameId);
        game.setDate("2025-09-07");
        game.setTime("12:00");
        game.setVenue("Amon G. Carter Stadium");
        game.setOpponent("Texas Longhorns");

        CrewMember member = new CrewMember();
        member.setUserId(2);
        member.setFirstName("Jane");
        member.setLastName("Smith");

        CrewAssignment assignment = new CrewAssignment();
        assignment.setId(1);
        assignment.setGame(game);
        assignment.setCrewMember(member);
        assignment.setPosition("CAMERA");
        assignment.setReportTime("11:45");
        assignment.setReportLocation("Truck Bay");

        CrewAssignmentDto dto = new CrewAssignmentDto(
                1, 2, gameId, "CAMERA", "Jane Smith", "11:45", "Truck Bay"
        );

        given(gameRepository.findById(gameId)).willReturn(Optional.of(game));
        given(crewAssignmentRespository.findByGame_GameId(gameId)).willReturn(List.of(assignment));
        given(converter.convert(assignment)).willReturn(dto);

        // When
        CrewListDto result = service.getCrewListForGame(gameId);

        // Then
        assertEquals(1, result.crewedMembers().size());
        assertEquals("Texas Longhorns", result.opponent());
        assertEquals("Jane Smith", result.crewedMembers().get(0).fullName());

        verify(converter).convert(assignment); // <-- moved to AFTER the call
    }

    @Test
    void testGetCrewListWithEmptyAssignments() {
        // Given
        Integer gameId = 1;
        Game game = new Game();
        game.setGameId(gameId);
        game.setDate("2025-09-07");
        game.setTime("12:00");
        game.setVenue("Amon G. Carter Stadium");
        game.setOpponent("Texas Longhorns");

        given(gameRepository.findById(gameId)).willReturn(Optional.of(game));
        given(crewAssignmentRespository.findByGame_GameId(gameId)).willReturn(List.of());

        // When
        CrewListDto result = service.getCrewListForGame(gameId);

        // Then
        assertNotNull(result);
        assertEquals(0, result.crewedMembers().size());
    }

    @Test
    void testGetCrewListGameNotFound() {
        // Given
        Integer gameId = 100;
        given(gameRepository.findById(gameId)).willReturn(Optional.empty());

        // When and Then
        assertThrows(ObjectNotFoundException.class, () -> {
            service.getCrewListForGame(gameId);
        });
    }
}
