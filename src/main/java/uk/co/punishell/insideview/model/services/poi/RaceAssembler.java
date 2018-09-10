package uk.co.punishell.insideview.model.services.poi;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.ResourceData.DataFormat;
import uk.co.punishell.insideview.model.database.entities.Race;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

@Service
public class RaceAssembler {

    RaceDataReader raceDataReader;
    DataFormat dataFormat;

    @Autowired
    public RaceAssembler(RaceDataReader raceDataReader, DataFormat dataFormat) {

        this.raceDataReader = raceDataReader;
        this.dataFormat = dataFormat;
    }

    public Race getRace(File file, Row row) {

        Date date = this.getDate(file);
        String country = raceDataReader.getCountry(row);
        String city = raceDataReader.getCity(row);
        String tackLength = raceDataReader.getTrackLength(row);
        String trackType = raceDataReader.getTrackType(row);
        LocalTime time = raceDataReader.getTime(row);

        return new Race(date, country, city, tackLength, trackType, time);
    }

    private Date getDate(File file) {

        // Expected name of the file is YYYMMdd.xlsx what justifies this date format
        // No need for support of other data formats as potential changes relay on a external vendor
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = null;
        try {
            // get first 8 characters of the file name and parse as a date
            date = dateFormat.parse(file.getName().substring(0, 8));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

}
