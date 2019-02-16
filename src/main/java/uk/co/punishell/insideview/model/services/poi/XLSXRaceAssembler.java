package uk.co.punishell.insideview.model.services.poi;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.ResourceData.DataFormat;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.RaceType;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class XLSXRaceAssembler {

    XLSXRaceDataReader xlsxRaceDataReader;
    DataFormat dataFormat;

    @Autowired
    public XLSXRaceAssembler(XLSXRaceDataReader xlsxRaceDataReader, DataFormat dataFormat) {

        this.xlsxRaceDataReader = xlsxRaceDataReader;
        this.dataFormat = dataFormat;
    }

    public Race getRace(File file, Row row) {

        Date date = this.getDate(file);
        LocalDate localDate = LocalDate.parse(file.getName().substring(0, 8), DateTimeFormatter.BASIC_ISO_DATE);
        String country = xlsxRaceDataReader.getCountry(row);
        String city = xlsxRaceDataReader.getCity(row);
        double trackLength = xlsxRaceDataReader.getTrackLength(row);
        List<RaceType> raceTypes = xlsxRaceDataReader.getRaceType(row);
        LocalTime time = xlsxRaceDataReader.getTime(row);

        return new Race(localDate, country, city, trackLength, raceTypes, time);
    }

    private Date getDate(File file) {

        // Expected name of the file is YYYMMdd.xlsx what justifies this date format
        // No need for support of other data formats as potential changes relay on an external vendor
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
