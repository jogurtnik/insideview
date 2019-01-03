package uk.co.punishell.insideview.model.services.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.services.RaceService;

import java.util.List;

@Slf4j
@Service
public class DBPopulatorImpl implements DBPopulator {

    private RaceService raceService;

    @Autowired
    public DBPopulatorImpl(RaceService raceService) {
        this.raceService = raceService;
    }

    @Override
    public void populate(List<Race> races) {

        log.info("Populating database...");
        raceService.saveAll(races);
    }
}
