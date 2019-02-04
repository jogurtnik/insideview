package uk.co.punishell.insideview.model.services.converters;

import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.view.commands.entityCommands.RaceCommand;
import uk.co.punishell.insideview.view.commands.entityCommands.RaceTypeCommand;

@Component
public class RaceCommandToRace implements Converter<RaceCommand, Race> {

    RaceTypeCommandToRaceType raceTypeCommandToRaceType;

    @Autowired
    public RaceCommandToRace(RaceTypeCommandToRaceType raceTypeCommandToRaceType) {
        this.raceTypeCommandToRaceType = raceTypeCommandToRaceType;
    }

    @Synchronized
    @Nullable
    @Override
    public Race convert(RaceCommand source) {

        if (source == null) {
            return null;
        }

        final Race race = new Race();
        race.setId(source.getId());
        race.setDate(source.getDate());
        race.setTime(source.getTime());
        race.setCountry(source.getCountry());
        race.setCity(source.getCity());
        race.setTrackLength(source.getTrackLength());

        source
                .getRaceTypes()
                .iterator()
                .forEachRemaining(
                        (RaceTypeCommand raceTypeCommand) -> race.getRaceTypes()
                                                                 .add(raceTypeCommandToRaceType.convert(raceTypeCommand)));

        return race;
    }
}