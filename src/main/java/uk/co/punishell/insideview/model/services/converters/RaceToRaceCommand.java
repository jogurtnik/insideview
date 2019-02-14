package uk.co.punishell.insideview.model.services.converters;

import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.RaceType;
import uk.co.punishell.insideview.view.commands.entityCommands.RaceCommand;

@Slf4j
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

        // find a different way, this violates Single Responsibility Principle
        raceCommand.getRunners().iterator().forEachRemaining(runner -> runner.setFavPos(raceCommand.getRunners().indexOf(runner) + 1));

        raceCommand.setId(source.getId());
        raceCommand.setDate(source.getDate());
        raceCommand.setTime(source.getTime());
        raceCommand.setCountry(source.getCountry());
        raceCommand.setCity(source.getCity());

        // Convert track length to common for horse racing format (miles and furlongs)
        raceCommand.setTrackLength(source.getTrackLength());
        int miles = (int) raceCommand.getTrackLength();
        int furlongs = (int) ((raceCommand.getTrackLength() - (double) miles) / 0.125);

        StringBuilder sb = new StringBuilder();
        sb.append(miles).append("m").append(furlongs).append("f");
        raceCommand.setTrackLengthString(sb.toString());

        sb.delete(0, sb.length());


        source
                .getRaceTypes()
                .iterator()
                .forEachRemaining(
                        (RaceType raceType) -> raceCommand.getRaceTypes()
                                                          .add(raceTypeToRaceTypeCommand.convert(raceType)));


        source.getRaceTypes().iterator().forEachRemaining(type -> sb.append(type.getName() + " "));

        raceCommand.setRaceTypesNames(sb.toString());

        return raceCommand;
    }
}
