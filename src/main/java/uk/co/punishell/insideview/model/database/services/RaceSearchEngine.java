package uk.co.punishell.insideview.model.database.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.database.repositories.RaceRepository;
import uk.co.punishell.insideview.model.database.specifications.RaceSpecification;
import uk.co.punishell.insideview.model.services.web.commands.guiCommands.RaceSearch;
import uk.co.punishell.insideview.model.services.web.commands.guiCommands.RaceSearchResult;
import uk.co.punishell.insideview.model.services.web.converters.RaceToRaceCommand;

@Slf4j
@Component
public class RaceSearchEngine implements SearchEngine<RaceSearch, RaceSearchResult> {

    private RaceRepository raceRepository;
    private RaceToRaceCommand raceToRaceCommand;
    private RaceSpecification raceSpecification;

    @Autowired
    public RaceSearchEngine(RaceRepository raceRepository, RaceToRaceCommand raceToRaceCommand) {

        this.raceRepository = raceRepository;
        this.raceToRaceCommand = raceToRaceCommand;
    }

    @Override
    public RaceSearchResult search(RaceSearch raceSearch) {

        this.raceSpecification = new RaceSpecification(raceSearch);

        return null;
    }
}
