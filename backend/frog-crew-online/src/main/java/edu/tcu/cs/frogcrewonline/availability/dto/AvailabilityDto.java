package edu.tcu.cs.frogcrewonline.availability.dto;

import jakarta.validation.constraints.NotNull;

public record AvailabilityDto(
        @NotNull(message = "userId is required.")
        Integer userId,
        @NotNull(message = "gameId is required.")
        Integer gameId,
        @NotNull(message = "Available is required.")
        Boolean available,
        String comment
) {

}
