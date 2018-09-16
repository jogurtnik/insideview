package uk.co.punishell.insideview.model.services.poi;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.database.entities.Horse;

/*
 * This class obtains properties of the runner from MS Office Excel spreadsheet
 */

@Service
public class XLSXRunnerDataReader {

    public Horse getHorse(Row row) {

        return new Horse(row.getCell(4).getStringCellValue());
    }

    public boolean getStatus(Row row) {

        boolean status = false;

        if (row.getCell(5).getStringCellValue().contains("ACTIVE")) {
            status = true;
        }

        return status;
    }

    public double getPrice9(Row row) {

        return row.getCell(6).getNumericCellValue();
    }

    public double getPrice10(Row row) {

        return row.getCell(7).getNumericCellValue();
    }

    public double getPrice11(Row row) {

        return row.getCell(8).getNumericCellValue();
    }

    public double getMov9to11(Row row) {

        return row.getCell(9).getNumericCellValue();
    }

    public double getPrice60(Row row) {

        return row.getCell(10).getNumericCellValue();
    }

    public double getMov60(Row row) {

        return row.getCell(11).getNumericCellValue();
    }

    public double getPrice30(Row row) {

        return row.getCell(12).getNumericCellValue();
    }

    public double getMov30(Row row) {

        return row.getCell(13).getNumericCellValue();
    }

    public double getPrice15(Row row) {

        return row.getCell(14).getNumericCellValue();
    }

    public double getMov15(Row row) {

        return row.getCell(15).getNumericCellValue();
    }

    public double getPrice5(Row row) {

        return row.getCell(16).getNumericCellValue();
    }

    public double getMov5(Row row) {

        return row.getCell(17).getNumericCellValue();
    }

    public double getPrice3(Row row) {

        return row.getCell(18).getNumericCellValue();
    }

    public double getMov3(Row row) {

        return row.getCell(19).getNumericCellValue();
    }

    public double getPrice2(Row row) {

        return row.getCell(20).getNumericCellValue();
    }

    public double getMov2(Row row) {

        return row.getCell(21).getNumericCellValue();
    }

    public double getPrice1(Row row) {

        return row.getCell(22).getNumericCellValue();
    }

    public double getMov1(Row row) {

        return row.getCell(23).getNumericCellValue();
    }

    public double getMean(Row row) {

        return row.getCell(24).getNumericCellValue();
    }

    public double getMov3to1(Row row) {

        return row.getCell(25).getNumericCellValue();
    }

    public boolean isWinner(Row row) {

        if (row.getCell(26).getStringCellValue().contains("Winner")) {

            return true;
        }

        return false;
    }

    public boolean isPlaced(Row row) {

        if (row.getCell(26) == null) {
            return false;
        }

        return true;
    }

    public int getCpr(Row row) {

        return (int) row.getCell(27).getNumericCellValue();
    }

    public int getNptips(Row row) {

        return (int) row.getCell(28).getNumericCellValue();
    }

    public int getStars(Row row) {

        return (int) row.getCell(29).getNumericCellValue();
    }

    public int getNaps(Row row) {

        return (int) row.getCell(30).getNumericCellValue();
    }
}
