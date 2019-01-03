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
    private FileValidator validator;

    @Autowired
    public DBPopulatingManager(DBPopulator dbPopulator, DataAssembler dataAssemlber, FileValidator validator) {

        this.dbPopulator = dbPopulator;
        this.dataAssemlber = dataAssemlber;
        this.validator = validator;
    }

    /*
     * @param File is validated before attempting to populate the DB.
     * @return false if validation has failed, true if it passed.
     */
    public void populateDB(File file) throws IOException, InvalidFormatException {

        if (validator.isValidFile(file)) {

            log.info("Uploaded file is VALID.");
            dbPopulator.populate(dataAssemlber.getRaces(file));

        } else {
            log.info("Uploaded file is INVALID.");
            return;
        }
    }


}
