package edu.tcu.cs.frogcrewonline.crewassignment;

import edu.tcu.cs.frogcrewonline.crewassignment.dto.CrewAssignmentDto;
import edu.tcu.cs.frogcrewonline.crewassignment.dto.CrewListDto;
import edu.tcu.cs.frogcrewonline.system.Result;
import edu.tcu.cs.frogcrewonline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CrewAssignmentController {

    private final CrewAssignmentService crewAssignmentService;

    public CrewAssignmentController(CrewAssignmentService crewAssignmentService) {
        this.crewAssignmentService = crewAssignmentService;
    }

    // Use Case 6

    @GetMapping("/crewList/{gameId}")
    public Result getCrewListForGame(@PathVariable Integer gameId) {
        CrewListDto crewList = crewAssignmentService.getCrewListForGame(gameId);

//        if (crewList.crewedMembers().isEmpty()) {
//            return new Result(true, StatusCode.NO_CONTENT, "No crew assigned to this game.");
//        }

        return new Result(true, StatusCode.SUCCESS, "Find Success", crewList);
    }

    // Use Case 23: Admin adds new crew Schedule
    @PostMapping("/crewSchedule/{gameId}")
    public Result addCrewSchedule(@PathVariable Integer gameId, @RequestBody @Valid List<CrewAssignmentDto> assignments) {
        List<CrewAssignmentDto> savedAssignments = crewAssignmentService.saveCrewSchedule(gameId, assignments);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedAssignments);
    }
}
