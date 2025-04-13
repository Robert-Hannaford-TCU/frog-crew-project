package edu.tcu.cs.frogcrewonline.crewassignment.converter;

import edu.tcu.cs.frogcrewonline.crewassignment.CrewAssignment;
import edu.tcu.cs.frogcrewonline.crewassignment.dto.CrewAssignmentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CrewAssignmentToCrewAssignmentDtoConverter implements Converter<CrewAssignment, CrewAssignmentDto> {

    @Override
    public CrewAssignmentDto convert(CrewAssignment source) {
        return new CrewAssignmentDto(
                source.getId(), // crewedUserId
                source.getCrewMember().getUserId(),
                source.getGame().getGameId(),
                source.getPosition(),
                source.getCrewMember().getFirstName() + " " + source.getCrewMember().getLastName(),
                source.getReportTime(),
                source.getReportLocation()
        );
    }
}
