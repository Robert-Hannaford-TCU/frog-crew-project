package edu.tcu.cs.frogcrewonline.crewmember;

import edu.tcu.cs.frogcrewonline.crewmember.utils.IdWorker;
import edu.tcu.cs.frogcrewonline.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CrewMemberService {
    private CrewMemberRepository crewMemberRepository;


    public CrewMemberService(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }

    public CrewMember save(CrewMember newCrewMember) {
        return this.crewMemberRepository.save(newCrewMember);
    }

    // Test Case 3
    public CrewMember findById(Integer crewMemberId){
        return this.crewMemberRepository.findById(crewMemberId)
                .orElseThrow(() -> new ObjectNotFoundException("user", crewMemberId));
    }

}
