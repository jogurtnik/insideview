package uk.co.punishell.insideview.model.services.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.ResourceData.DataFormat;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.services.DBPopulator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Consumer;

@Service
public class DataAssembler {


    XLSXRaceAssembler xlsxRaceAssembler;
    XLSXRunnerAssembler xlsxRunnerAssembler;
    DataFormat dataFormat;



    @Autowired
    public DataAssembler(XLSXRaceAssembler xlsxRaceAssembler, XLSXRunnerAssembler xlsxRunnerAssembler, DataFormat dataFormat) {

        this.xlsxRunnerAssembler = xlsxRunnerAssembler;
        this.xlsxRaceAssembler = xlsxRaceAssembler;
        this.dataFormat = dataFormat;
    }

    public List<Race> getRaces(File file) throws IOException, InvalidFormatException {

        // Races are stored in LinkedList to maintain the order of iteration compatible with order of adding
        // new elements to the LinkedList
        List<Race> races = new LinkedList<>();

        // Two-dimensional array to store row index for the start and the end of the race rows
        int[][] raceStartStopIndexes = new int[50][2];

        // iteration will start from second row
        int rowIndex = 1;

        Sheet sheet = new WorkbookFactory().create(file).getSheetAt(dataFormat.getDefaultSheetNumber());

        Iterator<Row> iterator = sheet.rowIterator();

        // skip first row
        iterator.next();

        // Iterate through the races LinkedList
        // If race object doesn't exist add it to the list and record its starting row index
        // else update ending row index
        while (iterator.hasNext()) {

            Row row = iterator.next();
            Race race = xlsxRaceAssembler.getRace(file, row);

            if (!races.contains(race)) {

                races.add(race);
                raceStartStopIndexes[races.size()-1][0] = rowIndex;

            } else {

                raceStartStopIndexes[races.size()-1][1] = rowIndex;
            }

            rowIndex++;
        }

        // Iterate through races LinkedList and set Runners for each race
        for (int raceIndex = 0; raceIndex <= races.size(); raceIndex++) {

            // Iterate through rows from starting to ending row index of the race
            for (int positionMarker = raceStartStopIndexes[raceIndex][0];
                 positionMarker <= raceStartStopIndexes[raceIndex][1];
                 positionMarker++) {

                 races.get(raceIndex)
                         .getRunners()
                         .add(xlsxRunnerAssembler.getRunner(sheet.getRow(positionMarker)));
            }

        }

        return races;
    }

}
