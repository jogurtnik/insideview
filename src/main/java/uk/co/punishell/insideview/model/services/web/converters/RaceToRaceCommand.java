package uk.co.punishell.insideview.model.services.web.converters;

import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.services.web.commands.entityCommands.RaceCommand;
import uk.co.punishell.insideview.model.services.web.commands.entityCommands.RunnerCommand;

import java.util.LinkedList;
import java.util.List;

@Component
public class RaceToRaceCommand implements Converter<Race, RaceCommand> {

    RunnerToRunnerCommand runnerToRunnerCommand;

    @Autowired
    public RaceToRaceCommand(RunnerToRunnerCommand runnerToRunnerCommand) {
        this.runnerToRunnerCommand = runnerToRunnerCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RaceCommand convert(Race source) {

        if (source == null) {
            return null;
        }

        final RaceCommand raceCommand = new RaceCommand();

        List<RunnerCommand> runners = new LinkedList<>();
        source
                .getRunners()
                .iterator()
                .forEachRemaining(runner -> runners.add(runnerToRunnerCommand.convert(runner)));
        raceCommand.setRunners(runners);

        raceCommand.setId(source.getId());
        raceCommand.setDate(source.getDate());
        raceCommand.setTime(source.getTime());
        raceCommand.setCountry(source.getCountry());
        raceCommand.setCity(source.getCity());
        raceCommand.setTrackLength(source.getTrackLength());
        raceCommand.setTrackType(source.getTrackType());

        return raceCommand;
    }
}
