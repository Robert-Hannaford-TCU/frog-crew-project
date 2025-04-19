package edu.tcu.cs.frogcrewonline.crewmember.converter;

import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.crewmember.dto.MemberSimpleDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberToMemberSimpleDtoConverter implements Converter<CrewMember, MemberSimpleDto> {

    @Override
    public MemberSimpleDto convert(CrewMember source) {
        String fullName = source.getFirstName() + " " + source.getLastName();
        return new MemberSimpleDto(
                source.getUserId(),
                fullName,
                source.getEmail(),
                source.getPhoneNumber()
        );
    }
}
