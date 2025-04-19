package edu.tcu.cs.frogcrewonline.availability;

import edu.tcu.cs.frogcrewonline.crewmember.CrewMemberRepository;
import edu.tcu.cs.frogcrewonline.game.GameRepository;
import edu.tcu.cs.frogcrewonline.system.exception.ConflictException;
import edu.tcu.cs.frogcrewonline.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AvailabilityService {
    private final AvailabilityRepository availabilityRepository;
    private final CrewMemberRepository crewMemberRepository;
    private final GameRepository gameRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository, CrewMemberRepository crewMemberRepository, GameRepository gameRepository) {
        this.availabilityRepository = availabilityRepository;
        this.crewMemberRepository = crewMemberRepository;
        this.gameRepository = gameRepository;
    }

    // Case 7
    public Availability create(Availability availability) {
        // Check for CrewMember and Game objects
        if (availability.getCrewMember() == null || availability.getGame() == null) {
            throw new IllegalArgumentException("Crew member and game must be provided.");
        }

        Integer userId = availability.getCrewMember().getUserId();
        Integer gameId = availability.getGame().getGameId();

        // Check userId and gameId
        if (userId == null || gameId == null) {
            throw new IllegalArgumentException("User ID and Game ID must not be null.");
        }

        // Check CrewMember exists (404 scenario)
        boolean userExists = crewMemberRepository.existsById(userId);
        if (!userExists) {
            throw new ObjectNotFoundException("user", userId);
        }

        // Check Game exists (404 scenario)
        boolean gameExists = gameRepository.existsById(gameId);
        if (!gameExists) {
            throw new ObjectNotFoundException("game", gameId);
        }

        // Check for existing availability (409 scenario)
        boolean availabilityExists = availabilityRepository.existsByCrewMemberAndGame(
                availability.getCrewMember(), availability.getGame());

        if (availabilityExists) {
            throw new ConflictException("Availability already exists for user "
                    + userId + " and game " + gameId);
        }

        // Save the availability if all checks pass (200 scenario)
        return availabilityRepository.save(availability);
    }
}

