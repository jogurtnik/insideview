package uk.co.punishell.insideview.model.services.poi;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

@Service
public class XLSXHorseDataReader {

    public String getHorseName(Row row) {

        return row.getCell(4).getStringCellValue();
    }
}
