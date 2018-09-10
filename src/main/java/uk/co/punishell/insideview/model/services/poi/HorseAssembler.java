package uk.co.punishell.insideview.model.services.poi;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.database.entities.Horse;

@Service
public class HorseAssembler {

    HorseDataReader horseDataReader;

    public Horse getHorse(Row row) {

        return new Horse(horseDataReader.getHorseName(row));
    }
}
