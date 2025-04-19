package edu.tcu.cs.frogcrewonline.availability.converter;

import edu.tcu.cs.frogcrewonline.availability.Availability;
import edu.tcu.cs.frogcrewonline.availability.dto.AvailabilityDto;
import edu.tcu.cs.frogcrewonline.crewmember.CrewMemberRepository;
import edu.tcu.cs.frogcrewonline.game.GameRepository;
import edu.tcu.cs.frogcrewonline.system.exception.ObjectNotFoundException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityDtoToAvailabilityConverter implements Converter<AvailabilityDto, Availability> {
    private final CrewMemberRepository memberRepo;
    private final GameRepository gameRepo;

    public AvailabilityDtoToAvailabilityConverter(
            CrewMemberRepository memberRepo,
            GameRepository       gameRepo
    ) {
        this.memberRepo = memberRepo;
        this.gameRepo   = gameRepo;
    }

    @Override
    public Availability convert(AvailabilityDto source) {

        var member = memberRepo.findById(source.userId())
                .orElseThrow(() -> new ObjectNotFoundException("CrewMember", source.userId()));
        var game = gameRepo.findById(source.gameId())
                .orElseThrow(() -> new ObjectNotFoundException("Game", source.gameId()));

        Availability a = new Availability();
        a.setCrewMember(member);
        a.setGame(game);
        a.setAvailable(source.available());
        a.setComment(source.comment());
        return a;
    }
}
