package edu.tcu.cs.frogcrewonline.availability.converter;

import edu.tcu.cs.frogcrewonline.availability.Availability;
import edu.tcu.cs.frogcrewonline.availability.dto.AvailabilityDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityToAvailabilityDtoConverter implements Converter<Availability, AvailabilityDto> {

    @Override
    public AvailabilityDto convert(Availability source) {
       final AvailabilityDto aDto = new AvailabilityDto(
        source.getCrewMember().getUserId(),
        source.getGame().getGameId(),
        source.getAvailable(),
        source.getComment()
        );
        return aDto;
    }
}
