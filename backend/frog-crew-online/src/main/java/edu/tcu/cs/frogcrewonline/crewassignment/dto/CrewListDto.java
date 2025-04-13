package edu.tcu.cs.frogcrewonline.crewassignment.dto;

import java.util.List;

public record CrewListDto(
        Integer gameId,
        String gameStart,
        String gameDate,
        String venue,
        String opponent,
        List<CrewAssignmentDto> crewedMembers
) {
}