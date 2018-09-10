package uk.co.punishell.insideview.model.services;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.services.poi.RaceAssembler;

import java.io.File;

@Service
public class DBPopulatorXLSX implements DBPopulator {


    RaceAssembler raceAssembler;

    @Autowired
    public DBPopulatorXLSX(RaceAssembler raceAssembler) {

        this.raceAssembler = raceAssembler;
    }

    @Override
    public void populate(File file) {


    }



}
