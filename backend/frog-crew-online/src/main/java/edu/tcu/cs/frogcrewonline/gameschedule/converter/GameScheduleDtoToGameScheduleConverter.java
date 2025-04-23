package edu.tcu.cs.frogcrewonline.gameschedule.converter;

import edu.tcu.cs.frogcrewonline.gameschedule.GameSchedule;
import edu.tcu.cs.frogcrewonline.gameschedule.dto.GameScheduleDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GameScheduleDtoToGameScheduleConverter implements Converter<GameScheduleDto, GameSchedule> {

    @Override
    public GameSchedule convert(GameScheduleDto source) {
        GameSchedule schedule = new GameSchedule();
        schedule.setSport(source.sport());
        schedule.setSeason(source.season());
        schedule.setPublished(true);
        return schedule;
    }
}
