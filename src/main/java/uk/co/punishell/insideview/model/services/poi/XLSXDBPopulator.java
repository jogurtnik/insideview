package uk.co.punishell.insideview.model.services.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.ResourceData.DataFormat;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.services.DBPopulator;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Service
public class XLSXDBPopulator implements DBPopulator {


    XLSXRaceAssembler xlsxRaceAssembler;
    XLSXRunnerAssembler xlsxRunnerAssembler;
    DataFormat dataFormat;


    @Autowired
    public XLSXDBPopulator(XLSXRaceAssembler xlsxRaceAssembler, XLSXRunnerAssembler xlsxRunnerAssembler, DataFormat dataFormat) {

        this.xlsxRunnerAssembler = xlsxRunnerAssembler;
        this.xlsxRaceAssembler = xlsxRaceAssembler;
        this.dataFormat = dataFormat;
    }

    @Override
    public void populate(File file) throws IOException, InvalidFormatException {

        Set<Race> races = new HashSet<>();
        final Race[] newRace = {new Race()};

        // Initialize objects for row interation
        final Integer[] firstRowExcluder = {new Integer(0)};
        String city = "city";
        String trackLength = "trackLength";
        String trackType = "trackType";
        LocalTime time = LocalTime.parse("00:00");

        WorkbookFactory.create(file)
                .getSheetAt(dataFormat.getDefaultSheetNumber())
                .rowIterator().forEachRemaining(new Consumer<Row>() {
            @Override
            public void accept(Row cells) {

                // Skip first row
                if (firstRowExcluder[0].intValue() != 0) {

                    firstRowExcluder[0] = new Integer(1);
                } else {

                    Race race = xlsxRaceAssembler.getRace(file, cells);

                    Runner runner = xlsxRunnerAssembler.getRunner(cells);

                    if (race.getCity() != city && race.getTrackLength() != trackLength && race.getTrackType() != trackType && race.getTime() != time) {

                        newRace[0] = race;
                        runner.setRace(race);

                        race.getRunners().add(runner);
                        races.add(race);

                    } else {

                        runner.setRace(newRace[0]);

                        newRace[0].getRunners().add(runner);
                    }
                }
            }
        });


    }



}
