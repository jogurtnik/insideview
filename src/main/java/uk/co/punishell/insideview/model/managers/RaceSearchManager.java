package uk.co.punishell.insideview.model.managers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.database.repositories.RaceRepository;
import uk.co.punishell.insideview.model.services.web.commands.entityCommands.RaceTypeCommand;
import uk.co.punishell.insideview.model.services.web.commands.guiCommands.RaceSearch;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class RaceSearchManager {

    private RaceRepository raceRepository;

    @Autowired
    public RaceSearchManager(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    public RaceSearch prepare(RaceSearch raceSearch) {



        return raceSearch;
    }

    private List<RaceTypeCommand> getRaceTypesList() {

        List<RaceTypeCommand> raceTypes = new LinkedList<>();

        // TODO

        return raceTypes;
    }
}
