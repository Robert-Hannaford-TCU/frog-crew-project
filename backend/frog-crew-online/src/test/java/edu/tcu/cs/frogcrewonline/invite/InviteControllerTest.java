package edu.tcu.cs.frogcrewonline.invite;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tcu.cs.frogcrewonline.system.StatusCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class InviteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InviteService inviteService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSendInviteSucess() throws Exception {
        EmailDto emailDto = new EmailDto();
        emailDto.setEmails(List.of("john@example.com", "jane@example.com"));
        given(inviteService.sendInvites(emailDto.getEmails())).willReturn(emailDto.getEmails());

        // When + Then
        mockMvc.perform(post("/invite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emailDto)))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Invite Success"))
                .andExpect(jsonPath("$.data[0]").value("john@example.com"))
                .andExpect(jsonPath("$.data[1]").value("jane@example.com"));
    }
}