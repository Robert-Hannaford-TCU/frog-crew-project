package edu.tcu.cs.frogcrewonline.crewmember;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.frogcrewonline.system.StatusCode;
import static org.hamcrest.Matchers.contains;
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

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CrewMemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CrewMemberService crewMemberService;

    List<CrewMember> members;

    @BeforeEach
    void setUp() {
        this.members = new ArrayList<>();

        CrewMember m1 = new CrewMember();
        m1.setUserId(1);
        m1.setFirstName("John");
        m1.setLastName("Doe");
        m1.setEmail("john.doe@example.com");
        m1.setPhoneNumber("1234567890");
        m1.setPassword("123456");
        m1.setRole("ADMIN");
        m1.setQualifiedPosition(Arrays.asList("DIRECTOR", "PRODUCER"));
        this.members.add(m1);

        CrewMember m2 = new CrewMember();
        m2.setUserId(2);
        m2.setFirstName("Mike");
        m2.setLastName("Jords");
        m2.setEmail("Jords@gmail.com");
        m2.setPhoneNumber("1231234567");
        m2.setPassword("PAsWOrD");
        m2.setRole("USER");
        m2.setQualifiedPosition(Arrays.asList("TALENT", "PRODUCER", "UTILITY"));
        this.members.add(m2);

        CrewMember m3 = new CrewMember();
        m3.setUserId(3);
        m3.setFirstName("Rob");
        m3.setLastName("Maddison");
        m3.setEmail("RM@example.com");
        m3.setPhoneNumber("9999999999");
        m3.setPassword("yeeess");
        m3.setRole("USER");
        m3.setQualifiedPosition(Arrays.asList("DIRECTOR"));
        this.members.add(m3);

        CrewMember m4 = new CrewMember();
        m4.setUserId(4);
        m4.setFirstName("Sam");
        m4.setLastName("Henderson");
        m4.setEmail("s.h@example.com");
        m4.setPhoneNumber("5555555555");
        m4.setPassword("000033334444");
        m4.setRole("USER");
        m4.setQualifiedPosition(Arrays.asList("CAMERAS", "PRODUCER"));
        this.members.add(m4);

    }

    @AfterEach
    void tearDown() {
    }


    // Test Case 1
    @Test
    void testAddUserSuccess() throws Exception {
        CrewMember member = new CrewMember();
        member.setUserId(5);
        member.setFirstName("Mitchel");
        member.setLastName("Heath");
        member.setEmail("m.h@example.com");
        member.setPhoneNumber("1110002222");
        member.setPassword("password123");
        member.setRole("USER");
        member.setQualifiedPosition(Arrays.asList("CAMERAS", "PRODUCER"));

        String json = this.objectMapper.writeValueAsString(member);


        // Given. Arrange inputs and targets. Define the behavior of Mock object CrewMemberService.
        given(this.crewMemberService.save(Mockito.any(CrewMember.class))).willReturn(member);

        // When and then
        this.mockMvc.perform(post("/crewMember").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.userId").isNotEmpty())
                .andExpect(jsonPath("$.data.firstName").value("Mitchel"))
                .andExpect(jsonPath("$.data.lastName").value("Heath"))
                .andExpect(jsonPath("$.data.email").value("m.h@example.com"))
                .andExpect(jsonPath("$.data.phoneNumber").value("1110002222"))
                .andExpect(jsonPath("$.data.role").value("USER"))
                .andExpect(jsonPath("$.data.qualifiedPosition", contains("CAMERAS", "PRODUCER")));
    }

    @Test
    void testAddMemberMissingRequiredFields() throws Exception {
        // Given: an empty CrewMember (missing all required fields)
        CrewMember invalidMember = new CrewMember();

        String json = objectMapper.writeValueAsString(invalidMember);

        // When & Then: perform the request and expect 400 with validation errors
        mockMvc.perform(post("/crewMember")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details."))
                .andExpect(jsonPath("$.data.firstName").value("First Name is required."))
                .andExpect(jsonPath("$.data.lastName").value("Last Name is required."))
                .andExpect(jsonPath("$.data.email").value("Email is required."))
                .andExpect(jsonPath("$.data.phoneNumber").value("Phone Number is required."))
                .andExpect(jsonPath("$.data.role").value("Role is required."))
                .andExpect(jsonPath("$.data.qualifiedPosition").value("Qualified Position is required."));
    }




    // Test Case 3
    @Test
    void testFindCrewMemberByIdSuccess() throws Exception {
        // Given
        given(this.crewMemberService.findById(2)).willReturn(this.members.get(1));

        // When and then
        this.mockMvc.perform(get("/crewMember/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find Success"))
                .andExpect(jsonPath("$.data.userId").value(2))
                .andExpect(jsonPath("$.data.firstName").value("Mike"))
                .andExpect(jsonPath("$.data.lastName").value("Jords"))
                .andExpect(jsonPath("$.data.email").value("Jords@gmail.com"))
                .andExpect(jsonPath("$.data.phoneNumber").value("1231234567"))
                .andExpect(jsonPath("$.data.role").value("USER"))
                .andExpect(jsonPath("$.data.qualifiedPosition[0]").value("TALENT"))
                .andExpect(jsonPath("$.data.qualifiedPosition[1]").value("PRODUCER"))
                .andExpect(jsonPath("$.data.qualifiedPosition[2]").value("UTILITY"));
    }

    @Test
    void testFindCrewMemberByIdNotFound() throws Exception {
        // Given
        given(this.crewMemberService.findById(2)).willThrow(new ObjectNotFoundException("user", 2));

        // When and then
        this.mockMvc.perform(get("/crewMember/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find user with id 2"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

}