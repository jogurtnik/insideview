package uk.co.punishell.insideview.model.database.services;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.database.repositories.RunnerRepository;
import uk.co.punishell.insideview.model.database.specifications.RunnerSpecification;
import uk.co.punishell.insideview.model.services.converters.RunnerToRunnerCommand;
import uk.co.punishell.insideview.view.commands.guiCommands.RunnerSeachResult;
import uk.co.punishell.insideview.view.commands.guiCommands.RunnerSearch;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RunnerSearchEngine implements SearchEngine<RunnerSearch, RunnerSeachResult> {

    private RunnerRepository runnerRepository;
    private RunnerToRunnerCommand runnerToRunnerCommand;
    private RunnerSpecification runnerSpecification;

    @Autowired
    public RunnerSearchEngine(RunnerRepository runnerRepository, RunnerToRunnerCommand runnerToRunnerCommand) {
        this.runnerRepository = runnerRepository;
        this.runnerToRunnerCommand = runnerToRunnerCommand;
    }

    @Override
    public RunnerSeachResult search(RunnerSearch criteria) {

        this.runnerSpecification = new RunnerSpecification(criteria);

        List<Runner> runners = runnerRepository.findAll(runnerSpecification);

        RunnerSeachResult result = new RunnerSeachResult();

        /*runners
                .iterator()
                .forEachRemaining(runner -> result.getRunners().add(runnerToRunnerCommand.convert(runner)));*/

        result.setRunners(runners
                .stream()
                .map((@NotNull var runner) -> runnerToRunnerCommand.convert(runner))
                .collect(Collectors.toList()));

        result.setMessage("Runner Query has " + result.getRunners().size() + " results.");
        log.debug("Runner Query results: " + result.getRunners().size());

        return result;
    }
}
