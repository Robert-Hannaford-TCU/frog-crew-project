package edu.tcu.cs.frogcrewonline.crewmember;

import edu.tcu.cs.frogcrewonline.crewmember.converter.MemberDtoToMemberConverter;
import edu.tcu.cs.frogcrewonline.crewmember.converter.MemberToMemberDtoConverter;
import edu.tcu.cs.frogcrewonline.crewmember.converter.MemberToMemberSimpleDtoConverter;
import edu.tcu.cs.frogcrewonline.crewmember.dto.MemberDto;
import edu.tcu.cs.frogcrewonline.crewmember.dto.MemberSimpleDto;
import edu.tcu.cs.frogcrewonline.system.Result;
import edu.tcu.cs.frogcrewonline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/crewMember")
public class CrewMemberController {

    private final CrewMemberService crewMemberService;

    private final MemberToMemberDtoConverter memberToMemberDtoConverter;

    private final MemberToMemberSimpleDtoConverter memberToMemberSimpleDtoConverter;

    public CrewMemberController(CrewMemberService crewMemberService, MemberToMemberDtoConverter memberToMemberDtoConverter, MemberToMemberSimpleDtoConverter memberToMemberSimpleDtoConverter) {
        this.crewMemberService = crewMemberService;
        this.memberToMemberDtoConverter = memberToMemberDtoConverter;
        this.memberToMemberSimpleDtoConverter = memberToMemberSimpleDtoConverter;
    }

    //Use Case 1: add member
    @PostMapping
    public Result addCrewMember(@Valid @RequestBody CrewMember newCrewMember) {
        CrewMember savedMember = this.crewMemberService.save(newCrewMember);
        MemberDto savedMemberDto = this.memberToMemberDtoConverter.convert(savedMember);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedMemberDto);
    }




    //Use Case 3: find member by id
    @GetMapping("/{userId}")
    public Result findCrewMemberById(@PathVariable Integer userId) {
        CrewMember  foundCrewMember = this.crewMemberService.findById(userId);
        MemberDto dto = this.memberToMemberDtoConverter.convert(foundCrewMember);
        return new Result(true, StatusCode.SUCCESS, "Find Success", dto);
    }


    //Use Case 16: Admin views crew members
    @GetMapping
    public Result findAllCrewMembers() {
        List<CrewMember> members = this.crewMemberService.findAll();

        if (members.isEmpty()) {
            return new Result(true, StatusCode.NO_CONTENT, "No Crew Members registered in the system.");
        }

        List<MemberSimpleDto> dtos = members.stream()
                .map(this.memberToMemberSimpleDtoConverter::convert)
                .toList();

        return new Result(true, StatusCode.SUCCESS, "Find Success", dtos);
    }


}
