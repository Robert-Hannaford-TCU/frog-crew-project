package edu.tcu.cs.frogcrewonline.system;

import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.crewmember.CrewMemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final CrewMemberRepository crewMemberRepository;

    public DBDataInitializer(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        CrewMember m1 = new CrewMember();
       // m1.setUserId(1);
        m1.setFirstName("John");
        m1.setLastName("Doe");
        m1.setEmail("john.doe@example.com");
        m1.setPhoneNumber("1234567890");
        m1.setPassword("123456");
        m1.setRole("ADMIN");
        m1.setQualifiedPosition(Arrays.asList("DIRECTOR", "PRODUCER"));

        CrewMember m2 = new CrewMember();
       // m2.setUserId(2);
        m2.setFirstName("Mike");
        m2.setLastName("Jords");
        m2.setEmail("Jords@gmail.com");
        m2.setPhoneNumber("1231234567");
        m2.setPassword("PAsWOrD");
        m2.setRole("USER");
        m2.setQualifiedPosition(Arrays.asList("TALENT", "PRODUCER", "UTILITY"));

        CrewMember m3 = new CrewMember();
       // m3.setUserId(3);
        m3.setFirstName("Rob");
        m3.setLastName("Maddison");
        m3.setEmail("RM@example.com");
        m3.setPhoneNumber("9999999999");
        m3.setPassword("yeeess");
        m3.setRole("USER");
        m3.setQualifiedPosition(Arrays.asList("DIRECTOR"));

        CrewMember m4 = new CrewMember();
      //  m4.setUserId(4);
        m4.setFirstName("Sam");
        m4.setLastName("Henderson");
        m4.setEmail("s.h@example.com");
        m4.setPhoneNumber("5555555555");
        m4.setPassword("000033334444");
        m4.setRole("USER");
        m4.setQualifiedPosition(Arrays.asList("CAMERAS", "PRODUCER"));

        crewMemberRepository.save(m1);
        crewMemberRepository.save(m2);
        crewMemberRepository.save(m3);
        crewMemberRepository.save(m4);
    }
}
