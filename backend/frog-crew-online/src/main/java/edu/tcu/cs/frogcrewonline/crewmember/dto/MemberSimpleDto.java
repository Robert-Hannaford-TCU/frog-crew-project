package edu.tcu.cs.frogcrewonline.crewmember.dto;

public record MemberSimpleDto(
        Integer userId,
        String fullName,
        String email,
        String phoneNumber
) {}