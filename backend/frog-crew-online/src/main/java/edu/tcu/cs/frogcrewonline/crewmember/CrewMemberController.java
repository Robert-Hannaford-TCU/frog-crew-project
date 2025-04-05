package edu.tcu.cs.frogcrewonline.crewmember;

import edu.tcu.cs.frogcrewonline.system.Result;
import edu.tcu.cs.frogcrewonline.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/crewMember")
public class CrewMemberController {

    private final CrewMemberService crewMemberService;


    public CrewMemberController(CrewMemberService crewMemberService) {
        this.crewMemberService = crewMemberService;
    }

    //Test Case 1: add member




    //Test Case 3: find member by id
    @GetMapping("/{userId}")
    public Result findCrewMemberById(@PathVariable Integer userId) {
        CrewMember  foundCrewMember = this.crewMemberService.findById(userId);
        return new Result(true, StatusCode.SUCCESS, "Find Success", foundCrewMember);
    }




}
