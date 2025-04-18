package edu.tcu.cs.frogcrewonline.crewassignment;

import edu.tcu.cs.frogcrewonline.crewassignment.dto.CrewListDto;
import edu.tcu.cs.frogcrewonline.system.Result;
import edu.tcu.cs.frogcrewonline.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crewList")
public class CrewAssignmentController {

    private final CrewAssignmentService crewAssignmentService;

    public CrewAssignmentController(CrewAssignmentService crewAssignmentService) {
        this.crewAssignmentService = crewAssignmentService;
    }

    // Use Case 6

    @GetMapping("/{gameId}")
    public Result getCrewListForGame(@PathVariable Integer gameId) {
        CrewListDto crewList = crewAssignmentService.getCrewListForGame(gameId);

//        if (crewList.crewedMembers().isEmpty()) {
//            return new Result(true, StatusCode.NO_CONTENT, "No crew assigned to this game.");
//        }

        return new Result(true, StatusCode.SUCCESS, "Find Success", crewList);
    }
}
