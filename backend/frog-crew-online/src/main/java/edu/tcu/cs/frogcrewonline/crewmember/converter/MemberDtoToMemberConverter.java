package edu.tcu.cs.frogcrewonline.crewmember.converter;

import edu.tcu.cs.frogcrewonline.crewmember.CrewMember;
import edu.tcu.cs.frogcrewonline.crewmember.dto.MemberDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MemberDtoToMemberConverter implements Converter<MemberDto, CrewMember> {

    @Override
    public CrewMember convert(MemberDto source) {
        CrewMember member = new CrewMember();
        member.setFirstName(source.firstName());
        member.setLastName(source.lastName());
        member.setEmail(source.email());
        member.setPhoneNumber(source.phoneNumber());
        member.setRole(source.role());
        member.setQualifiedPosition(source.qualifiedPosition());
        return member;
    }
}
