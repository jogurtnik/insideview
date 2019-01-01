package uk.co.punishell.insideview.model.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.commands.RaceCommand;
import uk.co.punishell.insideview.model.database.entities.Race;

import java.util.HashSet;

@Component
public class RaceCommandToRace implements Converter<RaceCommand, Race> {

    @Synchronized
    @Nullable
    @Override
    public Race convert(RaceCommand source) {

        if (source == null) {
            return null;
        }

        final Race race = new Race();
        race.setDate(source.getDate());
        race.setTime(source.getTime());
        race.setCountry(source.getCountry());
        race.setCity(source.getCity());
        race.setTrackLength(source.getTrackLength());
        race.setTrackType(source.getTrackType());
        race.setRunners(new HashSet<>());

        return race;
    }
}