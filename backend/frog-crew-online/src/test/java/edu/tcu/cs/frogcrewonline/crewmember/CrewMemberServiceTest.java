package edu.tcu.cs.frogcrewonline.crewmember;

import edu.tcu.cs.frogcrewonline.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CrewMemberServiceTest {

    @Mock
    CrewMemberRepository crewMemberRepository;

    @InjectMocks
    CrewMemberService crewMemberService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSaveSuccess() {
        // Given
        CrewMember newMember = new CrewMember();
        newMember.setUserId(1);
        newMember.setFirstName("John");
        newMember.setLastName("Doe");
        newMember.setEmail("john.doe@example.com");
        newMember.setPhoneNumber("1234567890");
        newMember.setPassword("password123");
        newMember.setRole("USER");
        newMember.setQualifiedPosition(Arrays.asList("CAMERAS", "PRODUCER"));

        given(this.crewMemberRepository.save(newMember)).willReturn(newMember);

        // When
        CrewMember savedMember = this.crewMemberService.save(newMember);

        // Then
        assertThat(savedMember.getUserId()).isEqualTo(1);
        assertThat(savedMember.getFirstName()).isEqualTo("John");
        assertThat(savedMember.getLastName()).isEqualTo("Doe");
        assertThat(savedMember.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(savedMember.getPhoneNumber()).isEqualTo("1234567890");
        assertThat(savedMember.getPassword()).isEqualTo("password123");
        assertThat(savedMember.getRole()).isEqualTo("USER");
        assertThat(savedMember.getQualifiedPosition()).containsExactly("CAMERAS", "PRODUCER");

        verify(this.crewMemberRepository, times(1)).save(newMember);
    }

    @Test
    void testFindCrewMemberByIdSuccess() {
        // Given. Arrange inputs and targets. Define behavior of Mock object
        /*
        "userId": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "1234567890",
    "role": "ADMIN",
    "positions": [
      "DIRECTOR",
      "PRODUCER"
    ]
         */
        CrewMember cm = new CrewMember();
        cm.setUserId(1);
        cm.setFirstName("John");
        cm.setLastName("Doe");
        cm.setEmail("john.doe@example.com");
        cm.setPhoneNumber("1234567890");
        cm.setRole("ADMIN");
        List<String> positions = Arrays.asList("DIRECTOR", "PRODUCER");
        cm.setQualifiedPosition(positions); // should be director and producer...

        given(crewMemberRepository.findById(1)).willReturn(Optional.of(cm)); // Defines the behavior of mock object

        // When. Act on the target behavior. When steps should cover the method to be tested
        CrewMember returnedCrewMember = this.crewMemberService.findById(1);

        // Then. Assert expected outcomes.
        assertThat(returnedCrewMember.getUserId()).isEqualTo(cm.getUserId());
        assertThat(returnedCrewMember.getFirstName()).isEqualTo(cm.getFirstName());
        assertThat(returnedCrewMember.getLastName()).isEqualTo(cm.getLastName());
        assertThat(returnedCrewMember.getEmail()).isEqualTo(cm.getEmail());
        assertThat(returnedCrewMember.getPhoneNumber()).isEqualTo(cm.getPhoneNumber());
        assertThat(returnedCrewMember.getRole()).isEqualTo(cm.getRole());
        assertThat(returnedCrewMember.getQualifiedPosition()).isEqualTo(cm.getQualifiedPosition());
        verify(this.crewMemberRepository, times(1)).findById(1);
    }

    @Test
    void testFindCrewMemberByIdNotFound() {
        // Given
        given(this.crewMemberRepository.findById(Mockito.any(Integer.class))).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            CrewMember returnedUser = this.crewMemberService.findById(1);
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find user with id 1");
        verify(this.crewMemberRepository, times(1)).findById(Mockito.any(Integer.class));

    }

}