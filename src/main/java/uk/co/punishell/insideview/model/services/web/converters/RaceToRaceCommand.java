package uk.co.punishell.insideview.model.services.web.converters;

import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.RaceType;
import uk.co.punishell.insideview.model.services.web.commands.entityCommands.RaceCommand;

@Component
public class RaceToRaceCommand implements Converter<Race, RaceCommand> {

    RunnerToRunnerCommand runnerToRunnerCommand;
    RaceTypeToRaceTypeCommand raceTypeToRaceTypeCommand;

    @Autowired
    public RaceToRaceCommand(RunnerToRunnerCommand runnerToRunnerCommand,
                             RaceTypeToRaceTypeCommand raceTypeToRaceTypeCommand) {
        this.runnerToRunnerCommand = runnerToRunnerCommand;
        this.raceTypeToRaceTypeCommand = raceTypeToRaceTypeCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RaceCommand convert(Race source) {

        if (source == null) {
            return null;
        }

        final RaceCommand raceCommand = new RaceCommand();
        source
                .getRunners()
                .iterator()
                .forEachRemaining(runner -> raceCommand.getRunners().add(runnerToRunnerCommand.convert(runner)));

        raceCommand.setId(source.getId());
        raceCommand.setDate(source.getDate());
        raceCommand.setTime(source.getTime());
        raceCommand.setCountry(source.getCountry());
        raceCommand.setCity(source.getCity());
        raceCommand.setTrackLength(source.getTrackLength());

        source
                .getRaceTypes()
                .iterator()
                .forEachRemaining(
                        (RaceType raceType) -> raceCommand.getRaceTypes()
                                                          .add(raceTypeToRaceTypeCommand.convert(raceType)));

        return raceCommand;
    }
}
