package edu.tcu.cs.frogcrewonline.gameschedule;

import edu.tcu.cs.frogcrewonline.gameschedule.converter.GameScheduleDtoToGameScheduleConverter;
import edu.tcu.cs.frogcrewonline.gameschedule.dto.GameScheduleDto;
import edu.tcu.cs.frogcrewonline.gameschedule.dto.GameScheduleResponseDto;
import edu.tcu.cs.frogcrewonline.system.Result;
import edu.tcu.cs.frogcrewonline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gameSchedule")
public class GameScheduleController {

    private final GameScheduleService gameScheduleService;
    private final GameScheduleDtoToGameScheduleConverter gameScheduleDtoToGameScheduleConverter;

    public GameScheduleController(GameScheduleService gameScheduleService, GameScheduleDtoToGameScheduleConverter gameScheduleDtoToGameScheduleConverter) {
        this.gameScheduleService = gameScheduleService;
        this.gameScheduleDtoToGameScheduleConverter = gameScheduleDtoToGameScheduleConverter;
    }

    // Use Case 18: Admin Creates Game Schedule
    @PostMapping
    public Result addGameSchedule(@Valid @RequestBody GameScheduleDto dto) {
        GameSchedule newSchedule = this.gameScheduleDtoToGameScheduleConverter.convert(dto);
        GameSchedule saved = this.gameScheduleService.save(newSchedule);

        GameScheduleResponseDto responseDto = new GameScheduleResponseDto(
                saved.getId(),
                saved.getSport(),
                saved.getSeason()
        );

        return new Result(true, StatusCode.SUCCESS, "Add Success", responseDto);
    }
}