package uk.co.punishell.insideview.model.services.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.database.entities.RaceType;
import uk.co.punishell.insideview.model.services.web.commands.entityCommands.RaceTypeCommand;

@Service
public class RaceTypeCommandToRaceType implements Converter<RaceTypeCommand, RaceType> {

    @Override
    public RaceType convert(RaceTypeCommand source) {

        if (source == null) {
            return null;
        }

        RaceType raceType = new RaceType();
        raceType.setName(source.getName());
        raceType.setRaceTypeGroup(source.getRaceTypeGroup());

        return raceType;
    }
}
