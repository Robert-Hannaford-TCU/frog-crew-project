package edu.tcu.cs.frogcrewonline.crewassignment;


import edu.tcu.cs.frogcrewonline.crewassignment.converter.CrewAssignmentDtoToCrewAssignmentConverter;
import edu.tcu.cs.frogcrewonline.crewassignment.converter.CrewAssignmentToCrewAssignmentDtoConverter;
import edu.tcu.cs.frogcrewonline.crewassignment.dto.CrewAssignmentDto;
import edu.tcu.cs.frogcrewonline.crewassignment.dto.CrewListDto;
import edu.tcu.cs.frogcrewonline.game.Game;
import edu.tcu.cs.frogcrewonline.game.GameRepository;
import edu.tcu.cs.frogcrewonline.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CrewAssignmentService {
    private final CrewAssignmentRespository crewAssignmentRespository;
    private final CrewAssignmentToCrewAssignmentDtoConverter crewAssignmentToCrewAssignmentDtoConverter;
    private final CrewAssignmentDtoToCrewAssignmentConverter crewAssignmentDtoToCrewAssignmentConverter;
    private final GameRepository gameRepository;

    public CrewAssignmentService(CrewAssignmentRespository crewAssignmentRespository, CrewAssignmentToCrewAssignmentDtoConverter crewAssignmentToCrewAssignmentDtoConverter, CrewAssignmentDtoToCrewAssignmentConverter crewAssignmentDtoToCrewAssignmentConverter, GameRepository gameRepository) {
        this.crewAssignmentRespository = crewAssignmentRespository;
        this.crewAssignmentToCrewAssignmentDtoConverter = crewAssignmentToCrewAssignmentDtoConverter;
        this.crewAssignmentDtoToCrewAssignmentConverter = crewAssignmentDtoToCrewAssignmentConverter;
        this.gameRepository = gameRepository;
    }

    public CrewListDto getCrewListForGame(Integer gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ObjectNotFoundException("Game", gameId));

        List<CrewAssignment> assignments = crewAssignmentRespository.findByGame_GameId(gameId);

        List<CrewAssignmentDto> crewedMembers = assignments.stream()
                .map(crewAssignmentToCrewAssignmentDtoConverter::convert)
                .toList();

        return new CrewListDto(
                game.getGameId(),
                game.getTime(),
                game.getDate(),
                game.getVenue(),
                game.getOpponent(),
                crewedMembers
        );
    }

    // Use Case 23: Admin schedules crew
    public List<CrewAssignmentDto> saveCrewSchedule(Integer gameId, List<CrewAssignmentDto> assignments) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ObjectNotFoundException("Game", gameId));

        return assignments.stream()
                .map(dto -> {
                    var assignment = crewAssignmentDtoToCrewAssignmentConverter.convert(dto);
                    assignment.setGame(game);
                    return crewAssignmentRespository.save(assignment);
                })
                .map(crewAssignmentToCrewAssignmentDtoConverter::convert)
                .toList();
    }

}
