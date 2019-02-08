package uk.co.punishell.insideview.model.managers;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.services.poi.DataAssembler;
import uk.co.punishell.insideview.model.services.util.DBPopulator;

import java.io.File;
import java.io.IOException;
import java.util.Set;

@Slf4j
@Component
public class DBPopulatingManager implements Runnable {

    DBPopulator dbPopulator;
    DataAssembler dataAssemlber;
    private File file;

    @Autowired
    public DBPopulatingManager(DBPopulator dbPopulator, DataAssembler dataAssemlber) {

        this.dbPopulator = dbPopulator;
        this.dataAssemlber = dataAssemlber;
    }

    public void populateDB(File file) {

        this.file = file;
        this.run();
    }

    public void populateDB(Set<File> files) {
        files.stream().forEach(file -> this.populateDB(file));
    }


    @Override
    public void run() {
        try {
            dbPopulator.populate(dataAssemlber.getRaces(file));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}
