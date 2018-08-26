package uk.co.punishell.insideview.model.ResourceData;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

/*
 * Class responsible for checking if given file matches DB structure and is able to populate it without with expected data
 */
@Service
public class ExcelFileValidator implements FileValidator{

    private Workbook workbook;
    private DataFormat dataFormat;

    @Autowired
    public ExcelFileValidator(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    @Override
    public boolean isValidFile(File file) {

        boolean result = false;
        try {
            this.workbook = WorkbookFactory.create(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            return false;
        }

        Sheet sheet = workbook.getSheetAt(dataFormat.getDefaultSheetNumber());

        // Get first row as it contains colums headers
        Row headerRow = sheet.getRow(0);

        for (String header : dataFormat.getDefaultDataColumnsHeaders()) {

            int cellIteration = 0;
            if (!headerRow.getCell(cellIteration).getStringCellValue().contains(header)) {
                return false;
            } else {
                result = true;
            }
        }
        return result;
    }
}
