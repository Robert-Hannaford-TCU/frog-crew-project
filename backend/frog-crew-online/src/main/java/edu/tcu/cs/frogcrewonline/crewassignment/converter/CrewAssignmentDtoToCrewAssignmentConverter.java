package edu.tcu.cs.frogcrewonline.crewassignment.converter;

import edu.tcu.cs.frogcrewonline.crewassignment.CrewAssignment;
import edu.tcu.cs.frogcrewonline.crewassignment.dto.CrewAssignmentDto;
import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.crewmember.CrewMemberRepository;
import edu.tcu.cs.frogcrewonline.game.Game;
import edu.tcu.cs.frogcrewonline.game.GameRepository;
import edu.tcu.cs.frogcrewonline.system.exception.ObjectNotFoundException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CrewAssignmentDtoToCrewAssignmentConverter implements Converter<CrewAssignmentDto, CrewAssignment> {

    private final CrewMemberRepository crewMemberRepository;
    private final GameRepository gameRepository;

    public CrewAssignmentDtoToCrewAssignmentConverter(CrewMemberRepository crewMemberRepository,
                                                      GameRepository gameRepository) {
        this.crewMemberRepository = crewMemberRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public CrewAssignment convert(CrewAssignmentDto source) {
        CrewAssignment assignment = new CrewAssignment();

        CrewMember crewMember = crewMemberRepository.findById(source.userId())
                .orElseThrow(() -> new ObjectNotFoundException("CrewMember", source.userId()));

        Game game = gameRepository.findById(source.gameId())
                .orElseThrow(() -> new ObjectNotFoundException("Game", source.gameId()));

        assignment.setId(source.crewedUserId()); // Only useful for updates
        assignment.setCrewMember(crewMember);
        assignment.setGame(game);
        assignment.setPosition(source.position());
        assignment.setReportTime(source.reportTime());
        assignment.setReportLocation(source.reportLocation());

        return assignment;
    }
}