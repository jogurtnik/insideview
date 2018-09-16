package uk.co.punishell.insideview.model.services.poi;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

/*
 * This class obtains properties of the race from MS Office Excel spreadsheet
 */

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

    public String getTrackLength(Row row) {
        return extractTrackLength(row.getCell(2).getStringCellValue());
    }

    public String getTrackType(Row row) {

        return extractTrackType(row.getCell(2).getStringCellValue());
    }

    public LocalTime getTime(Row row) {

        return LocalTime.parse(row.getCell(3).getStringCellValue());
    }

    private String extractTrackLength(String distance) {

        StringBuilder sb = new StringBuilder();
        char[] c = distance.toCharArray();

        for (char x : c) {
            if (x != ' ') {
                sb.append(x);
            } else {
                return sb.toString();
            }
        }

        return null;
    }

    private String extractTrackType(String distance) {

        StringBuilder sb = new StringBuilder();
        char[] c = distance.toCharArray();

        for (int i = 0; i < c.length; i++) {

            if (c[i] == ' ') {

                for (int j = i + 1; j < c.length; j++) {

                    sb.append(c[j]);
                }

                return sb.toString();
            }
        }

        return null;
    }
}
