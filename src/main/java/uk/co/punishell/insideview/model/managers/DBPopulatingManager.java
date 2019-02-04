package uk.co.punishell.insideview.model.managers;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.ResourceData.FileValidator;
import uk.co.punishell.insideview.model.services.poi.DataAssembler;
import uk.co.punishell.insideview.model.services.util.DBPopulator;

import java.io.File;
import java.io.IOException;

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

    public void populateDB(File file) throws IOException, InvalidFormatException {

            dbPopulator.populate(dataAssemlber.getRaces(file));
    }


}
