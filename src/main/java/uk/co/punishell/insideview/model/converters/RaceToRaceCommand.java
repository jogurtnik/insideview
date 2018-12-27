package uk.co.punishell.insideview.model.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.commands.RaceCommand;
import uk.co.punishell.insideview.model.database.entities.Race;

@Component
public class RaceToRaceCommand implements Converter<Race, RaceCommand> {

    @Synchronized
    @Nullable
    @Override
    public RaceCommand convert(Race source) {

        if (source == null) {
            return null;
        }

        final RaceCommand raceCommand = new RaceCommand();
        raceCommand.setId(source.getId());
        raceCommand.setDate(source.getDate());
        raceCommand.setCountry(source.getCountry());
        raceCommand.setCity(source.getCity());
        raceCommand.setTrackLength(source.getTrackLength());
        raceCommand.setTrackType(source.getTrackType());
        raceCommand.setTime(source.getTime());

        return raceCommand;
    }
}
