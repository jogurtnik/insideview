package uk.co.punishell.insideview.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.ResourceData.DataFormat;
import uk.co.punishell.insideview.model.ResourceData.FileValidator;

import java.io.File;

@Component
public class PopulatingManager {

    File file;
    FileValidator validator;
    DataFormat dataFormat;

    @Autowired
    public PopulatingManager(FileValidator validator, DataFormat dataFormat) {

        this.validator = validator;
        this.dataFormat = dataFormat;
    }

    public String passFile(File file) {

        this.file = file;
        String result = "";
        if (validator.isValidFile(file, dataFormat)) {
            populateDB();
            result = "Populating database was successfully completed.";
        } else {
            result = "File was evaluated as invalid for populating database. Formatting of the file is incorrect. Unable to populate the database";
        }

        return result;
    }

    private void populateDB() {

        // TODO population logic
    }

}
