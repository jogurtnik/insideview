package uk.co.punishell.insideview.model.database.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.repositories.RaceRepository;
import uk.co.punishell.insideview.model.database.specifications.RaceSpecification;
import uk.co.punishell.insideview.model.services.web.commands.guiCommands.RaceSearch;
import uk.co.punishell.insideview.model.services.web.commands.guiCommands.RaceSearchResult;
import uk.co.punishell.insideview.model.services.web.converters.RaceToRaceCommand;

import java.util.List;

@Slf4j
@Component
public class RaceSearchEngine implements SearchEngine<RaceSearch, RaceSearchResult> {

    private RaceRepository raceRepository;
    private RaceToRaceCommand raceToRaceCommand;
    private RaceSpecification raceSpecification;

    @Autowired
    public RaceSearchEngine(RaceRepository raceRepository,
                            RaceToRaceCommand raceToRaceCommand) {

        this.raceRepository = raceRepository;
        this.raceToRaceCommand = raceToRaceCommand;
    }

    @Override
    public RaceSearchResult search(RaceSearch raceSearch) {

        this.raceSpecification = new RaceSpecification(raceSearch);

        List<Race> races = raceRepository.findAll(raceSpecification);

        RaceSearchResult result = new RaceSearchResult();
        races
                .iterator()
                .forEachRemaining(race -> result.getRaces().add(raceToRaceCommand.convert(race)));

        result.setMessage("Query has " + result.getRaces().size() + " results.");
        log.debug("Query results: " + result.getRaces().size());

        return result;
    }
}
