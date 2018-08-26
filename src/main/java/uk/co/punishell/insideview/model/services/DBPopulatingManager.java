package uk.co.punishell.insideview.model.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.ResourceData.FileValidator;

import java.io.File;
import java.io.IOException;

@Component
public class DBPopulatingManager {

    private FileValidator validator;

    @Autowired
    public DBPopulatingManager(FileValidator validator) {

        this.validator = validator;
    }

    /*
     * @param File is validated before attempting to populate the DB.
     * @return false is validation failed, true if valid.
     */
    private boolean populateDB(File file) throws IOException, InvalidFormatException {

        boolean result;

        if (validator.isValidFile(file)) {
            result = true;
        } else {
            return false;
        }

        Workbook wb = WorkbookFactory.create(file);


        return result;
    }

}
