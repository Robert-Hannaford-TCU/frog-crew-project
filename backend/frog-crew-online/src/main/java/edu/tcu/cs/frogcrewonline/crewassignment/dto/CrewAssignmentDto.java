package edu.tcu.cs.frogcrewonline.crewassignment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrewAssignmentDto(
        Integer crewedUserId,
        @NotNull(message = "UserId is required")
        Integer userId,
        @NotNull(message = "GameId is required")
        Integer gameId,
        @NotBlank(message = "Position is required")
        String position,
        String fullName,
        @NotBlank(message = "Report time is required")
        String reportTime,
        @NotBlank(message = "Report location is required")
        String reportLocation
) {
}

