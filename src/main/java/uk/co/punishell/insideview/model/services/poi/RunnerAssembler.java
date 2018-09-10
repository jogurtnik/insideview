package uk.co.punishell.insideview.model.services.poi;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.ResourceData.DataFormat;
import uk.co.punishell.insideview.model.database.entities.Horse;
import uk.co.punishell.insideview.model.database.entities.Runner;


@Service
public class RunnerAssembler {

    RunnerDataReader runnerDataReader;
    HorseAssembler horseAssembler;
    DataFormat dataFormat;

    @Autowired
    public RunnerAssembler(RunnerDataReader runnerDataReader, DataFormat dataFormat) {

        this.runnerDataReader = runnerDataReader;
        this.dataFormat = dataFormat;
    }

    public Runner getRunner(Row row) {

        Horse horse = horseAssembler.getHorse(row);

        boolean status = runnerDataReader.getStatus(row);

        double price9 = runnerDataReader.getPrice9(row);
        double price10 = runnerDataReader.getPrice10(row);
        double price11 = runnerDataReader.getPrice11(row);
        double mov9to11 = runnerDataReader.getMov9to11(row);
        double price60 = runnerDataReader.getPrice60(row);
        double mov60 = runnerDataReader.getMov60(row);
        double price30 = runnerDataReader.getPrice30(row);
        double mov30 = runnerDataReader.getMov30(row);
        double price15 = runnerDataReader.getPrice15(row);
        double mov15 = runnerDataReader.getMov15(row);
        double price5 = runnerDataReader.getPrice5(row);
        double mov5 = runnerDataReader.getMov5(row);
        double price3 = runnerDataReader.getPrice3(row);
        double mov3 = runnerDataReader.getMov3(row);
        double price2 = runnerDataReader.getPrice2(row);
        double mov2 = runnerDataReader.getPrice2(row);
        double price1 = runnerDataReader.getPrice1(row);
        double mov1 = runnerDataReader.getMov1(row);
        double mean = runnerDataReader.getMean(row);
        double mov3to1 = runnerDataReader.getMov3to1(row);

        boolean winner = runnerDataReader.isWinner(row);
        boolean placed = runnerDataReader.isPlaced(row);

        int cpr = runnerDataReader.getCpr(row);
        int nptips = runnerDataReader.getNptips(row);
        int stars = runnerDataReader.getStars(row);
        int naps = runnerDataReader.getNaps(row);

        Runner runner = new Runner(horse, status, price9, price10, price11, mov9to11, price60, mov60, price30, mov30,
                                   price15, mov15, price5, mov5, price3, mov3, price2, mov2, price1, mov1, mean, mov3to1,
                                   winner, placed, cpr, nptips, stars, naps);

        return runner;
    }
}
