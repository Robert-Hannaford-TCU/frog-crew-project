package edu.tcu.cs.frogcrewonline.crewassignment.dto;

public record CrewAssignmentDto(
        Integer crewedUserId,
        Integer userId,
        Integer gameId,
        String position,
        String fullName,
        String reportTime,
        String reportLocation
) {
}

