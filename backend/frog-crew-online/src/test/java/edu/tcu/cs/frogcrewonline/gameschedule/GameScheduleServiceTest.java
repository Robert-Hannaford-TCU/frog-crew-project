package edu.tcu.cs.frogcrewonline.gameschedule;

import edu.tcu.cs.frogcrewonline.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameScheduleServiceTest {

    @Mock
    GameScheduleRepository gameScheduleRepository;

    @InjectMocks
    GameScheduleService gameScheduleService;

    GameSchedule schedule;

    @BeforeEach
    void setUp() {
        this.schedule = new GameSchedule();
        this.schedule.setId(1);
        this.schedule.setSport("Basketball");
        this.schedule.setSeason("Spring 2025");
        this.schedule.setPublished(false);
    }

    @AfterEach
    void tearDown() {
    }

    // Use Case 18 Tests:
    @Test
    void testSaveSuccess() {
        // Given
        given(this.gameScheduleRepository.save(schedule)).willReturn(schedule);

        // When
        GameSchedule saved = this.gameScheduleService.save(schedule);

        // Then
        assertThat(saved.getId()).isEqualTo(1);
        assertThat(saved.getSport()).isEqualTo("Basketball");
        assertThat(saved.getSeason()).isEqualTo("Spring 2025");
        assertThat(saved.isPublished()).isFalse();

        verify(this.gameScheduleRepository, times(1)).save(schedule);
    }

}
