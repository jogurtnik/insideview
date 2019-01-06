package uk.co.punishell.insideview.model.services.poi;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.database.entities.RaceType;
import uk.co.punishell.insideview.model.database.entities.RaceTypeGroup;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

/*
 * This class obtains properties of the race from MS Office Excel spreadsheet
 */

@Slf4j
@Service
public class XLSXRaceDataReader {

    public String getCountry(Row row) {

        // Hardcoded position of the cell as first column in the resource sheet.
        // Lack of better idea at this point.
        return row.getCell(0).getStringCellValue();
    }

    public String getCity(Row row) {

        return row.getCell(1).getStringCellValue();
    }

    public double getTrackLength(Row row) {
        return extractTrackLength(row.getCell(2).getStringCellValue());
    }

    public List<RaceType> getRaceType(Row row) {

        String[] raceTypesStrings = extractTrackTypes(row.getCell(2).getStringCellValue());

        List<RaceType> raceTypes = new LinkedList<>();

        for (String raceTypeString : raceTypesStrings) {

            raceTypes.add(new RaceType(raceTypeString));

            // TODO set race type group
            if (raceTypeString.equalsIgnoreCase("nov")) {
                ((LinkedList<RaceType>) raceTypes).getLast().setRaceTypeGroup(RaceTypeGroup.HORSE);
            } else if (raceTypeString.equalsIgnoreCase("mdn")) {
                ((LinkedList<RaceType>) raceTypes).getLast().setRaceTypeGroup(RaceTypeGroup.HORSE);
            }
        }


        return raceTypes;
    }

    public LocalTime getTime(Row row) {

        return LocalTime.parse(row.getCell(3).getStringCellValue());
    }

    private double extractTrackLength(String distance) {

        double trackLength;

        StringBuilder sb = new StringBuilder();
        char[] c = distance.toCharArray();

        for (char x : c) {
            if (x != ' ') {
                sb.append(x);
            } else {

                if (sb.toString().toCharArray().length == 2) {

                    if (sb.toString().toCharArray()[1] == 'm') {

                        distance = sb.toString();
                        sb = new StringBuilder();
                        sb.append(distance.toCharArray()[0]);
                        distance = sb.toString();

                        trackLength = Double.parseDouble(distance);

                        log.debug("(M) Track length: " + trackLength);

                        return trackLength;

                    } else if (sb.toString().toCharArray()[1] == 'f') {

                        distance = sb.toString();
                        sb = new StringBuilder();
                        sb.append(distance.toCharArray()[0]);
                        distance = sb.toString();

                        double one = 1.0;
                        double eight = 8.0;
                        trackLength = (one / eight) * Double.parseDouble(distance);

                        log.debug("(F) Track length: " + trackLength);

                        return trackLength;

                    } else {

                        return 0;
                    }

                } else if (sb.toString().toCharArray().length == 4) {

                    distance = sb.toString();
                    sb = new StringBuilder();
                    sb.append(distance.toCharArray()[0]);

                    double miles = Double.parseDouble(sb.toString());

                    sb.deleteCharAt(0);
                    sb.append(distance.toCharArray()[2]);

                    double one = 1.0;
                    double eight = 8.0;
                    double furlongs = (one / eight) * Double.parseDouble(sb.toString());

                    trackLength = miles + furlongs;

                    log.debug("(4) Track length: " + trackLength);

                    return  trackLength;

                } else return 0;
            }
        }

        return 0;
    }

    private String[] extractTrackTypes(String distance) {

        StringBuilder sb = new StringBuilder();

        String[] trackTypesTemp = distance.split(" ");

        String[] trackTypes = new String[trackTypesTemp.length - 1];

        for (int i = 0; i < trackTypesTemp.length; i++) {

            // skip first since it contains info about race track length
            if (i != 0) {

                trackTypes[i - 1] = trackTypesTemp[i];
            }

        }

        return trackTypes;
    }
}
