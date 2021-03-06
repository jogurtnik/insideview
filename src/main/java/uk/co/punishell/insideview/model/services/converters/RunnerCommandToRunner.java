package uk.co.punishell.insideview.model.services.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.view.commands.entityCommands.RunnerCommand;

@Component
public class RunnerCommandToRunner implements Converter<RunnerCommand, Runner> {

    private HorseCommandToHorse horseCommandToHorse;

    public RunnerCommandToRunner(HorseCommandToHorse horseCommandToHorse) {
        this.horseCommandToHorse = horseCommandToHorse;
    }

    @Synchronized
    @Nullable
    @Override
    public Runner convert(RunnerCommand source) {

        if(source == null) {
            return null;
        }

        final Runner runner = new Runner();
        runner.setId(source.getId());
        runner.setHorse(horseCommandToHorse.convert(source.getHorse()));
        runner.setStatus(source.isStatus());
        runner.setPrice9(source.getPrice9());
        runner.setPrice10(source.getPrice10());
        runner.setPrice11(source.getPrice11());
        runner.setMov9to11(source.getMov9to11());
        runner.setPrice60(source.getPrice60());
        runner.setMov60(source.getMov60());
        runner.setPrice30(source.getPrice30());
        runner.setMov30(source.getMov30());
        runner.setPrice15(source.getPrice15());
        runner.setMov15(source.getMov15());
        runner.setPrice5(source.getPrice5());
        runner.setMov5(source.getMov5());
        runner.setPrice3(source.getPrice3());
        runner.setMov3(source.getMov3());
        runner.setPrice2(source.getPrice2());
        runner.setMov2(source.getMov2());
        runner.setPrice1(source.getPrice1());
        runner.setMov1(source.getMov1());
        runner.setMean(source.getMean());
        runner.setMov3to1(source.getMov3to1());

        if (source.getResult() != null) {

            if (source.getResult().equalsIgnoreCase("Won")) {

                runner.setWinner(true);
                runner.setPlaced(true);

            } else if (source.getResult().equalsIgnoreCase("Placed")) {

                runner.setWinner(false);
                runner.setPlaced(true);

            } else {

                runner.setWinner(false);
                runner.setPlaced(false);

            }
        }

        runner.setCpr(source.getCpr());
        runner.setNptips(source.getNptips());
        runner.setStars(source.getStars().length);
        runner.setNaps(source.getNaps());

        return runner;
    }
}
