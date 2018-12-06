package uk.co.punishell.insideview.model.services.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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

    public Set<Race> getRaces(File file) throws IOException, InvalidFormatException {
        return new HashSet<>();
    }
}
