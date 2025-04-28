package edu.tcu.cs.frogcrewonline.system;

import edu.tcu.cs.frogcrewonline.availability.Availability;
import edu.tcu.cs.frogcrewonline.availability.AvailabilityRepository;
import edu.tcu.cs.frogcrewonline.crewassignment.CrewAssignment;
import edu.tcu.cs.frogcrewonline.crewassignment.CrewAssignmentRespository;
import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.crewmember.CrewMemberRepository;
import edu.tcu.cs.frogcrewonline.game.Game;
import edu.tcu.cs.frogcrewonline.game.GameRepository;
import edu.tcu.cs.frogcrewonline.gameschedule.GameSchedule;
import edu.tcu.cs.frogcrewonline.gameschedule.GameScheduleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final CrewMemberRepository crewMemberRepository;
    private final GameRepository gameRepository;
    private final CrewAssignmentRespository crewAssignmentRespository;
    private final AvailabilityRepository availabilityRepository;
    private final GameScheduleRepository gameScheduleRepository;
    private final PasswordEncoder passwordEncoder;


    public DBDataInitializer(CrewMemberRepository crewMemberRepository, GameRepository gameRepository, CrewAssignmentRespository crewAssignmentRespository, AvailabilityRepository availabilityRepository, GameScheduleRepository gameScheduleRepository, PasswordEncoder passwordEncoder) {
        this.crewMemberRepository = crewMemberRepository;
        this.gameRepository = gameRepository;
        this.crewAssignmentRespository = crewAssignmentRespository;
        this.availabilityRepository = availabilityRepository;
        this.gameScheduleRepository = gameScheduleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // Crew Members
        CrewMember m1 = new CrewMember();
       // m1.setUserId(1);
        m1.setFirstName("John");
        m1.setLastName("Doe");
        m1.setEmail("john.doe@example.com");
        m1.setPhoneNumber("1234567890");
        m1.setPassword(passwordEncoder.encode("123456"));
        m1.setRole("USER");
        m1.setQualifiedPosition(Arrays.asList("DIRECTOR", "PRODUCER"));

        CrewMember m2 = new CrewMember();
       // m2.setUserId(2);
        m2.setFirstName("Mike");
        m2.setLastName("Jords");
        m2.setEmail("Jords@gmail.com");
        m2.setPhoneNumber("1231234567");
        m2.setPassword(passwordEncoder.encode("password"));
        m2.setRole("USER");
        m2.setQualifiedPosition(Arrays.asList("TALENT", "PRODUCER", "UTILITY"));

        CrewMember m3 = new CrewMember();
       // m3.setUserId(3);
        m3.setFirstName("Rob");
        m3.setLastName("Maddison");
        m3.setEmail("RM@example.com");
        m3.setPhoneNumber("9999999999");
        m3.setPassword(passwordEncoder.encode("yeeess"));
        m3.setRole("USER");
        m3.setQualifiedPosition(Arrays.asList("DIRECTOR"));

        CrewMember m4 = new CrewMember();
      //  m4.setUserId(4);
        m4.setFirstName("Sam");
        m4.setLastName("Henderson");
        m4.setEmail("s.h@example.com");
        m4.setPhoneNumber("5555555555");
        m4.setPassword(passwordEncoder.encode("000033334444"));
        m4.setRole("USER");
        m4.setQualifiedPosition(Arrays.asList("CAMERAS", "PRODUCER"));

        CrewMember m5 = new CrewMember();
        m5.setFirstName("Admin");
        m5.setLastName("Admin");
        m5.setEmail("Admin@admin.com");
        m5.setPhoneNumber("0000000000");
        m5.setPassword(passwordEncoder.encode("ADMIN"));
        m5.setRole("ADMIN");
        m5.setQualifiedPosition(Arrays.asList("CAMERAS", "PRODUCER"));

        crewMemberRepository.save(m1);
        crewMemberRepository.save(m2);
        crewMemberRepository.save(m3);
        crewMemberRepository.save(m4);
        crewMemberRepository.save(m5);


        // Games
        Game g1 = new Game();
        g1.setScheduleId(1);
        g1.setDate("2024-09-07");
        g1.setTime("13:00:00");
        g1.setVenue("Carter");
        g1.setOpponent("LSU");
        g1.setFinalized(true);
        g1.setPublished(true);
        g1.setSportType("Football");
        g1.setCrewPositions("Camera, Audio, Director");

        Game g2 = new Game();
        g2.setScheduleId(1);
        g2.setDate("2024-09-14");
        g2.setTime("14:00:00");
        g2.setVenue("G stadium");
        g2.setOpponent("TX State");
        g2.setFinalized(true);
        g2.setPublished(true);
        g2.setSportType("Football");
        g2.setCrewPositions("Director, Replay, Camera, Audio");

        Game g3 = new Game();
        g3.setScheduleId(1);
        g3.setDate("2024-09-21");
        g3.setTime("10:30:00");
        g3.setVenue("TCU Tennis Center");
        g3.setOpponent("Baylor");
        g3.setFinalized(true);
        g3.setPublished(true);
        g3.setSportType("Tennis");
        g3.setCrewPositions("Camera, Talent");

        gameRepository.save(g1);
        gameRepository.save(g2);
        gameRepository.save(g3);

        // Game Schedules
        GameSchedule gs1 = new GameSchedule();
       // gs1.setId(1);
        gs1.setSport("Football");
        gs1.setSeason("2024-2025");

        GameSchedule gs2 = new GameSchedule();
       // gs2.setId(2);
        gs2.setSport("Tennis");
        gs2.setSeason("2024-2025");

        gameScheduleRepository.save(gs1);
        gameScheduleRepository.save(gs2);

        // Set Game Schedules

       // Crew Assignment
        CrewAssignment ca1 = new CrewAssignment();
       // ca1.setId(1);
        ca1.setCrewMember(m2);
        ca1.setGame(g1);
        ca1.setPosition("Director");
        ca1.setReportTime("11:30");
        ca1.setReportLocation("Control Room");

        CrewAssignment ca2 = new CrewAssignment();
        //ca2.setId(2);
        ca2.setCrewMember(m1);
        ca2.setGame(g1);
        ca2.setPosition("Audio");
        ca2.setReportTime("13:30");
        ca2.setReportLocation("Control Room");

        crewAssignmentRespository.save(ca1);
        crewAssignmentRespository.save(ca2);
//
        // Availability
        Availability availability = new Availability();
        availability.setCrewMember(m3);
        availability.setGame(g3);
        availability.setAvailable(true);
        availability.setComment("Ready to work");

        Availability availability2 = new Availability();
        availability2.setCrewMember(m4);
        availability2.setGame(g3);
        availability2.setAvailable(true);
        availability2.setComment("Hoping to work!!!");

        availabilityRepository.save(availability);
        availabilityRepository.save(availability2);

    }
}
