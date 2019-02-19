package uk.co.punishell.insideview.model.managers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.services.poi.DataAssembler;
import uk.co.punishell.insideview.model.services.util.DBPopulator;

import java.io.File;
import java.io.IOException;
import java.util.Set;

@Slf4j
@Component
public class DBPopulatingManager {

    DBPopulator dbPopulator;
    DataAssembler dataAssemlber;

    @Autowired
    public DBPopulatingManager(DBPopulator dbPopulator, DataAssembler dataAssemlber) {

        this.dbPopulator = dbPopulator;
        this.dataAssemlber = dataAssemlber;
    }

    public void populateDB(File file) {
        long startTime = System.currentTimeMillis();
        try {
            dbPopulator.populate(dataAssemlber.getRaces(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.debug(file.getName() + " saved in " + ((double) elapsedTime)/1000 + " s");
    }

    public void populateDB(Set<File> files) {

        long startTime = System.currentTimeMillis();
        files.stream().forEach(file -> this.populateDB(file));
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        log.debug(files.size() + " files saved in " + ((double) elapsedTime)/1000 + " ms");
    }
}
