package uk.co.punishell.insideview.model.ResourceData;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/*
 * Class responsible for checking if given file matches DB structure and is valid to populate it with expected data
 */
@Service
public class ExcelFileValidator implements FileValidator{

    private static final Logger logger = LoggerFactory.getLogger(ExcelFileValidator.class);

    private Workbook workbook;
    private DataFormat dataFormat;

    @Autowired
    public ExcelFileValidator(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    @Override
    public boolean isValidFile(File file) {

        logger.info("File upload validation...");

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

        // Get first
        Row headerRow = sheet.getRow(sheet.getFirstRowNum());

        // Iterate through cells in first row
        int cellIteration = 0;
        for (String header : dataFormat.getDefaultDataColumnsHeaders()) {

            // 26th cell in the header row is numerical so it has to be obtained with different method.
            String fileHeader;
            if (cellIteration != 25 ) {
                fileHeader = headerRow.getCell(cellIteration).getStringCellValue();
            } else {
                fileHeader = String.valueOf(headerRow.getCell(cellIteration).getNumericCellValue());
            }

            // For the sake of 26th cell in the header row comparison is made with contains()
            // since getNumericalCellValue() returns variable of type double passing it into String.valueOf()
            // returns 321.0 as String value
            if (!fileHeader.contains(header)) {
                return false;
            } else {
                result = true;
            }

            cellIteration++;
        }
        return result;
    }
}
