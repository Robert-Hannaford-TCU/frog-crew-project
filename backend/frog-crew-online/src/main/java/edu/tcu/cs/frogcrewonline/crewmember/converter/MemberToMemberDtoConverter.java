package edu.tcu.cs.frogcrewonline.crewmember.converter;

import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.crewmember.dto.MemberDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberToMemberDtoConverter implements Converter<CrewMember, MemberDto> {

    @Override
    public MemberDto convert(CrewMember source) {
    final MemberDto memberDto = new MemberDto(source.getUserId(),
            source.getFirstName(),
            source.getLastName(),
            source.getEmail(),
            source.getPhoneNumber(),
            source.getRole(),
            source.getQualifiedPosition());
    return memberDto;
    }
}
