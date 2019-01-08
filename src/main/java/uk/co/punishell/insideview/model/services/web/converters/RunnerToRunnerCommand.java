package uk.co.punishell.insideview.model.services.web.converters;

import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.services.web.commands.entityCommands.RunnerCommand;

@Component
public class RunnerToRunnerCommand implements Converter<Runner, RunnerCommand> {

    private HorseToHorseCommand horseToHorseCommand;

    @Autowired
    public RunnerToRunnerCommand(HorseToHorseCommand horseToHorseCommand) {
        super();
        this.horseToHorseCommand = horseToHorseCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RunnerCommand convert(Runner source) {

        if (source == null) {
            return null;
        }

        final RunnerCommand runnerCommand = new RunnerCommand();
        runnerCommand.setId(source.getId());
        runnerCommand.setHorseName(source.getHorse().getName());
        runnerCommand.setStatus(source.isStatus());
        runnerCommand.setPrice9(source.getPrice9());
        runnerCommand.setPrice10(source.getPrice10());
        runnerCommand.setPrice11(source.getPrice11());
        runnerCommand.setMov9to11(source.getMov9to11());
        runnerCommand.setPrice60(source.getPrice60());
        runnerCommand.setMov60(source.getMov60());
        runnerCommand.setPrice30(source.getPrice30());
        runnerCommand.setMov30(source.getMov30());
        runnerCommand.setPrice15(source.getPrice15());
        runnerCommand.setMov15(source.getMov15());
        runnerCommand.setPrice5(source.getPrice5());
        runnerCommand.setMov5(source.getMov5());
        runnerCommand.setPrice3(source.getPrice3());
        runnerCommand.setMov3(source.getMov3());
        runnerCommand.setPrice2(source.getPrice2());
        runnerCommand.setMov2(source.getMov2());
        runnerCommand.setPrice1(source.getPrice1());
        runnerCommand.setMov1(source.getMov1());
        runnerCommand.setMean(source.getMean());
        runnerCommand.setMov3to1(source.getMov3to1());

        if (source.isWinner()) {
            runnerCommand.setResult("W");
        } else if (source.isPlaced()) {
            runnerCommand.setResult("P");
        } else {
            runnerCommand.setResult("");
        }


        runnerCommand.setCpr(source.getCpr());
        runnerCommand.setNptips(source.getNptips());
        runnerCommand.setStars(source.getStars());
        runnerCommand.setNaps(source.getNaps());

        return runnerCommand;
    }
}
