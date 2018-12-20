package uk.co.punishell.insideview.model.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.ResourceData.FileValidator;
import uk.co.punishell.insideview.model.services.poi.DataAssembler;

import java.io.File;
import java.io.IOException;

@Component
public class DBPopulatingManager {

    private static final Logger logger = LoggerFactory.getLogger(DBPopulatingManager.class);

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

            logger.info("Uploaded file is VALID.");
            dbPopulator.populate(dataAssemlber.getRaces(file));

        } else {
            logger.info("Uploaded file is INVALID.");
            return;
        }
    }


}
