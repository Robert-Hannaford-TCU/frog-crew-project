package edu.tcu.cs.frogcrewonline.crewmember;

import edu.tcu.cs.frogcrewonline.crewassignment.CrewAssignmentRespository;
import edu.tcu.cs.frogcrewonline.crewmember.utils.IdWorker;
import edu.tcu.cs.frogcrewonline.system.exception.ConflictException;
import edu.tcu.cs.frogcrewonline.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CrewMemberService {
    private final CrewMemberRepository crewMemberRepository;
    private final CrewAssignmentRespository crewAssignmentRespository;


    public CrewMemberService(CrewMemberRepository crewMemberRepository, CrewAssignmentRespository crewAssignmentRespository) {
        this.crewMemberRepository = crewMemberRepository;
        this.crewAssignmentRespository = crewAssignmentRespository;
    }

    public CrewMember save(CrewMember newCrewMember) {
        return this.crewMemberRepository.save(newCrewMember);
    }

    // Use Case 3
    public CrewMember findById(Integer crewMemberId){
        return this.crewMemberRepository.findById(crewMemberId)
                .orElseThrow(() -> new ObjectNotFoundException("user", crewMemberId));
    }

    // Use Case 15 - delete a crew member
    public void deleteById(Integer userId) {
        if (!crewMemberRepository.existsById(userId)) {
            throw new ObjectNotFoundException("Crew Member", userId);
        }
        if (crewAssignmentRespository.existsById(userId)) {
            throw new ConflictException("Can't delete a crew member that has already been assigned to a game. Try again after game is complete.");
        }

        crewMemberRepository.deleteById(userId);
    }

    // Use Case 16
    public List<CrewMember> findAll() {
        return this.crewMemberRepository.findAll(); // might have to filter all active users. Clarify
    }


}
