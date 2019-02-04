package uk.co.punishell.insideview.model.services.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.database.entities.RaceType;
import uk.co.punishell.insideview.view.commands.entityCommands.RaceTypeCommand;

@Service
public class RaceTypeToRaceTypeCommand implements Converter<RaceType, RaceTypeCommand> {

    @Override
    public RaceTypeCommand convert(RaceType source) {

        if (source == null) {

            return null;
        }

        RaceTypeCommand raceTypeCommand = new RaceTypeCommand();
        raceTypeCommand.setName(source.getName());
        raceTypeCommand.setRaceTypeGroup(source.getRaceTypeGroup());

        return raceTypeCommand;
    }
}
