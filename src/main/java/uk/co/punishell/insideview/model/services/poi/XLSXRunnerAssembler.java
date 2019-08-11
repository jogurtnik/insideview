package uk.co.punishell.insideview.model.services.poi;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.ResourceData.DataFormat;
import uk.co.punishell.insideview.model.database.entities.Horse;
import uk.co.punishell.insideview.model.database.entities.Runner;


@Service
public class XLSXRunnerAssembler {

    XLSXRunnerDataReader xlsxRunnerDataReader;
    XLSXHorseAssembler xlsxHorseAssembler;
    DataFormat dataFormat;

    @Autowired
    public XLSXRunnerAssembler(XLSXRunnerDataReader xlsxRunnerDataReader, XLSXHorseAssembler xlsxHorseAssembler, DataFormat dataFormat) {

        this.xlsxRunnerDataReader = xlsxRunnerDataReader;
        this.xlsxHorseAssembler = xlsxHorseAssembler;
        this.dataFormat = dataFormat;
    }

    public Runner getRunner(Row row) {

        Horse horse = xlsxHorseAssembler.getHorse(row);

        boolean status = xlsxRunnerDataReader.getStatus(row);

        double price9 = xlsxRunnerDataReader.getPrice9(row);
        double price10 = xlsxRunnerDataReader.getPrice10(row);
        double price11 = xlsxRunnerDataReader.getPrice11(row);
        double mov9to11 = xlsxRunnerDataReader.getMov9to11(row);
        double price60 = xlsxRunnerDataReader.getPrice60(row);
        double mov60 = xlsxRunnerDataReader.getMov60(row);
        double price30 = xlsxRunnerDataReader.getPrice30(row);
        double mov30 = xlsxRunnerDataReader.getMov30(row);
        double price15 = xlsxRunnerDataReader.getPrice15(row);
        double mov15 = xlsxRunnerDataReader.getMov15(row);
        double price5 = xlsxRunnerDataReader.getPrice5(row);
        double mov5 = xlsxRunnerDataReader.getMov5(row);
        double price3 = xlsxRunnerDataReader.getPrice3(row);
        double mov3 = xlsxRunnerDataReader.getMov3(row);
        double price2 = xlsxRunnerDataReader.getPrice2(row);
        double mov2 = xlsxRunnerDataReader.getPrice2(row);
        double price1 = xlsxRunnerDataReader.getPrice1(row);
        double mov1 = xlsxRunnerDataReader.getMov1(row);
        double mean = xlsxRunnerDataReader.getMean(row);
        double mov3to1 = xlsxRunnerDataReader.getMov3to1(row);

        boolean winner = xlsxRunnerDataReader.isWinner(row);
        boolean placed = xlsxRunnerDataReader.isPlaced(row);

        int cpr = xlsxRunnerDataReader.getCpr(row);
        int nptips = xlsxRunnerDataReader.getNptips(row);
        int stars = xlsxRunnerDataReader.getStars(row);
        int naps = xlsxRunnerDataReader.getNaps(row);

        return new Runner(horse, status, price9, price10, price11, mov9to11, price60, mov60, price30, mov30,
                price15, mov15, price5, mov5, price3, mov3, price2, mov2, price1, mov1, mean, mov3to1,
                winner, placed, cpr, nptips, stars, naps);
    }
}
