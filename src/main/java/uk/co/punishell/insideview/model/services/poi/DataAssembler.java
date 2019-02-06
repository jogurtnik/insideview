package uk.co.punishell.insideview.model.services.poi;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.ResourceData.DataFormat;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.Runner;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class DataAssembler {

    private static final Logger logger = LoggerFactory.getLogger(DataAssembler.class);

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

        log.debug("Inside DataAssembler");

        // Races are stored in LinkedList to maintain the order of iteration compatible with order of adding
        // new elements to the LinkedList
        List<Race> races = new LinkedList();

        // Two-dimensional array to store row index for the start and the end of the race rows
        int[][] raceStartStopIndexes = new int[1000][2];

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

            // if Race is not in the races LinkedList add it to it and set current rowIndex value as a starting point
            // else set current rowIndex value as ending point of the race rows
            if (!this.checkIfContains(races, race)) {

                races.add(race);
                raceStartStopIndexes[races.size()-1][0] = rowIndex;
            } else {

                raceStartStopIndexes[races.size()-1][1] = rowIndex;
            }

            // post-increment value of rowIndex before next iteration
            rowIndex++;
        }

        // Iterate through races LinkedList and set Runners for each race
        for (int raceIndex = 0; raceIndex < races.size(); raceIndex++) {

            // Iterate through rows from starting to ending row index of the race
            for (int positionMarker = raceStartStopIndexes[raceIndex][0];
                 positionMarker <= raceStartStopIndexes[raceIndex][1];
                 positionMarker++) {

                Runner runner = xlsxRunnerAssembler.getRunner(sheet.getRow(positionMarker));

                // check if runner participated in the race
                if (runner.isStatus()) {
                    races.get(raceIndex).getRunners().add(runner);
                }

            }
        }

        return races;
    }

    private boolean checkIfContains(List<Race> racesList, Race race) {

        if (racesList.isEmpty()) {
            return false;
        } else {

            Iterator<Race> itr = racesList.iterator();

            while (itr.hasNext()) {

                Race checkRace = itr.next();

                if (checkRace.getCountry().contains(race.getCountry()) &&
                    checkRace.getCity().contains(race.getCity()) &&
                    checkRace.getTime().equals(race.getTime()) &&
                    checkRace.getTrackLength() == race.getTrackLength()) {

                    return true;
                }
            }

            return false;
        }
    }
}
