package edu.tcu.cs.frogcrewonline.crewmember.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record MemberDto (Integer userId,

        @NotEmpty(message = "First Name is required.")
        String firstName,

        @NotEmpty(message = "Last Name is required.")
        String lastName,

        @NotEmpty(message = "Email is required.")
        String email,

        @NotEmpty(message = "Phone Number is required.")
        //@Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits.")
        String phoneNumber,

        @NotEmpty(message = "Role is required.")
        String role,

        @NotEmpty(message = "Qualified Position is required.")
        List<String> qualifiedPosition){
}
