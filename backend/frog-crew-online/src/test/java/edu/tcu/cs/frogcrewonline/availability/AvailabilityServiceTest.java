package edu.tcu.cs.frogcrewonline.availability;

import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.crewmember.CrewMemberRepository;
import edu.tcu.cs.frogcrewonline.game.Game;
import edu.tcu.cs.frogcrewonline.game.GameRepository;
import edu.tcu.cs.frogcrewonline.system.exception.ConflictException;
import edu.tcu.cs.frogcrewonline.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvailabilityServiceTest {

    @Mock
    AvailabilityRepository availabilityRepository;

    @Mock
    CrewMemberRepository crewMemberRepository;

    @Mock
    GameRepository gameRepository;

    @InjectMocks
    AvailabilityService availabilityService;

    CrewMember crew;
    Game game;
    Availability availability;

    @BeforeEach
    void setUp() {
        crew = new CrewMember();
        crew.setUserId(1);

        game = new Game();
        game.setGameId(2);

        availability = new Availability();
        availability.setCrewMember(crew);
        availability.setGame(game);
        availability.setAvailable(true);
        availability.setComment("Available and ready!");
    }

    @Test
    void testAddSuccess() {
        // Given
        given(crewMemberRepository.existsById(1)).willReturn(true);
        given(gameRepository.existsById(2)).willReturn(true);
        given(availabilityRepository.existsByCrewMemberAndGame(crew, game)).willReturn(false);
        given(availabilityRepository.save(availability)).willReturn(availability);

        // When
        Availability saved = availabilityService.create(availability);

        // Then
        assertThat(saved.getCrewMember()).isEqualTo(crew);
        assertThat(saved.getGame()).isEqualTo(game);
        assertThat(saved.getAvailable()).isTrue();
        assertThat(saved.getComment()).isEqualTo("Available and ready!");

        verify(availabilityRepository, times(1)).save(availability);
    }

    @Test
    void testAddFailsWhenCrewMemberNotFound() {
        // Given
        given(crewMemberRepository.existsById(1)).willReturn(false);

        // When
        Throwable thrown = catchThrowable(() -> availabilityService.create(availability));

        // Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find user with id 1");

        verify(availabilityRepository, times(0)).save(availability);
    }

    @Test
    void testAddFailsWhenGameNotFound() {
        // Given
        given(crewMemberRepository.existsById(1)).willReturn(true);
        given(gameRepository.existsById(2)).willReturn(false);

        // When
        Throwable thrown = catchThrowable(() -> availabilityService.create(availability));

        // Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find game with id 2");

        verify(availabilityRepository, times(0)).save(availability);
    }

    @Test
    void testAddFailsWhenAvailabilityAlreadyExists() {
        // Given
        given(crewMemberRepository.existsById(1)).willReturn(true);
        given(gameRepository.existsById(2)).willReturn(true);
        given(availabilityRepository.existsByCrewMemberAndGame(crew, game)).willReturn(true);

        // When
        Throwable thrown = catchThrowable(() -> availabilityService.create(availability));

        // Then
        assertThat(thrown)
                .isInstanceOf(ConflictException.class)
                .hasMessage("Availability already exists for user 1 and game 2");

        verify(availabilityRepository, times(0)).save(availability);
    }
}
