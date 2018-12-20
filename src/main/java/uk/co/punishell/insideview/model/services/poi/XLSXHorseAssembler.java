package uk.co.punishell.insideview.model.services.poi;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.database.entities.Horse;

@Service
public class XLSXHorseAssembler {

    XLSXHorseDataReader xlsxHorseDataReader;

    @Autowired
    public XLSXHorseAssembler(XLSXHorseDataReader xlsxHorseDataReader) {
        this.xlsxHorseDataReader = xlsxHorseDataReader;
    }

    public Horse getHorse(Row row) {

        return new Horse(xlsxHorseDataReader.getHorseName(row));
    }

}
