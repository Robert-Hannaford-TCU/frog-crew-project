package edu.tcu.cs.frogcrewonline.crewmember;

import edu.tcu.cs.frogcrewonline.crewmember.converter.MemberDtoToMemberConverter;
import edu.tcu.cs.frogcrewonline.crewmember.converter.MemberToMemberDtoConverter;
import edu.tcu.cs.frogcrewonline.crewmember.dto.MemberDto;
import edu.tcu.cs.frogcrewonline.system.Result;
import edu.tcu.cs.frogcrewonline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/crewMember")
public class CrewMemberController {

    private final CrewMemberService crewMemberService;

    private final MemberToMemberDtoConverter memberToMemberDtoConverter;

    public CrewMemberController(CrewMemberService crewMemberService, MemberToMemberDtoConverter memberToMemberDtoConverter) {
        this.crewMemberService = crewMemberService;
        this.memberToMemberDtoConverter = memberToMemberDtoConverter;
    }

    //Test Case 1: add member
    @PostMapping
    public Result addCrewMember(@Valid @RequestBody CrewMember newCrewMember) {
        CrewMember savedMember = this.crewMemberService.save(newCrewMember);
        MemberDto savedMemberDto = this.memberToMemberDtoConverter.convert(savedMember);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedMemberDto);
    }




    //Test Case 3: find member by id
    @GetMapping("/{userId}")
    public Result findCrewMemberById(@PathVariable Integer userId) {
        CrewMember  foundCrewMember = this.crewMemberService.findById(userId);
        return new Result(true, StatusCode.SUCCESS, "Find Success", foundCrewMember);
    }




}
