package edu.tcu.cs.frogcrewonline.invite;

import edu.tcu.cs.frogcrewonline.crewmember.CrewMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InviteServiceTest {

    @Mock
    CrewMemberRepository crewMemberRepository;

    @Mock
    EmailService emailService;

    @InjectMocks
    InviteService inviteService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSendInvitesToValidEmailsOnlySuccess() {
        // Given
        List<String> emails = List.of("john@example.com", "jane@example.com");

        // john already exists, jane does not
        when(crewMemberRepository.existsByEmail("john@example.com")).thenReturn(true);
        when(crewMemberRepository.existsByEmail("jane@example.com")).thenReturn(false);

        // When
        List<String> result = inviteService.sendInvites(emails);

        // Then
        assertEquals(1, result.size());
        assertEquals("jane@example.com", result.get(0));

        verify(emailService, times(1)).sendEmail(eq("jane@example.com"), anyString(), anyString());
        verify(emailService, never()).sendEmail(eq("john@example.com"), anyString(), anyString());

    }

}