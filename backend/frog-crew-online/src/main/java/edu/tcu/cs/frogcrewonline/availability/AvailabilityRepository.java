package edu.tcu.cs.frogcrewonline.availability;

import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
    //check game and crew member
    boolean existsByCrewMemberAndGame(CrewMember crewMember, Game game);
}
