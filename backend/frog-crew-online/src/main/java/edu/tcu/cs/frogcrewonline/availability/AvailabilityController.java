package edu.tcu.cs.frogcrewonline.availability;

import edu.tcu.cs.frogcrewonline.availability.converter.AvailabilityDtoToAvailabilityConverter;
import edu.tcu.cs.frogcrewonline.availability.converter.AvailabilityToAvailabilityDtoConverter;
import edu.tcu.cs.frogcrewonline.availability.dto.AvailabilityDto;
import edu.tcu.cs.frogcrewonline.system.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.tcu.cs.frogcrewonline.system.StatusCode;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;
    private final AvailabilityDtoToAvailabilityConverter availabilityDtoToAvailabilityConverter;
    private final AvailabilityToAvailabilityDtoConverter availabilityToAvailabilityDtoConverter;

    public AvailabilityController(AvailabilityService availabilityService, AvailabilityDtoToAvailabilityConverter availabilityDtoToAvailabilityConverter, AvailabilityToAvailabilityDtoConverter availabilityToAvailabilityDtoConverter) {
        this.availabilityService = availabilityService;
        this.availabilityDtoToAvailabilityConverter = availabilityDtoToAvailabilityConverter;
        this.availabilityToAvailabilityDtoConverter = availabilityToAvailabilityDtoConverter;
    }

    // Use case 7: crew member submits availability
    @PostMapping
    public Result addAvailability(@Valid @RequestBody AvailabilityDto dto){
        Availability incoming = availabilityDtoToAvailabilityConverter.convert(dto);
        Availability saved = availabilityService.create(incoming);
        AvailabilityDto out = availabilityToAvailabilityDtoConverter.convert(saved);

        return new Result(true, StatusCode.SUCCESS, "Add Success", out);
    }

}
