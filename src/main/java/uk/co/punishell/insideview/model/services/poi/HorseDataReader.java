package uk.co.punishell.insideview.model.services.poi;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.database.entities.Horse;

@Service
public class HorseDataReader {

    public String getHorseName(Row row) {

        return row.getCell(4).getStringCellValue();
    }
}
