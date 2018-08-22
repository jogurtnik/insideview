package uk.co.punishell.insideview.model.ResourceData;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import java.io.*;

/*
 * Class responsible for checking if given file matches DB structure and is able to populate it without with expected data
 */
@Service
public class ExcelFileValidator implements FileValidator{

    private Workbook workbook;

    @Override
    public boolean isValidFile(File file, DataFormat dataFormat) {
        boolean result = false;
        try {
            this.workbook = WorkbookFactory.create(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        // TODO logic of validation
        return result;
    }
}
