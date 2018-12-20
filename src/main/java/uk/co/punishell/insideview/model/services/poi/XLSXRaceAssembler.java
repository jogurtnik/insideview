package uk.co.punishell.insideview.model.services.poi;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.ResourceData.DataFormat;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.Runner;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

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
        String country = xlsxRaceDataReader.getCountry(row);
        String city = xlsxRaceDataReader.getCity(row);
        String tackLength = xlsxRaceDataReader.getTrackLength(row);
        String trackType = xlsxRaceDataReader.getTrackType(row);
        LocalTime time = xlsxRaceDataReader.getTime(row);
        Set<Runner> runners = new HashSet<>();

        return new Race(date, country, city, tackLength, trackType, time, runners);
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
