package uk.co.punishell.insideview.model.services.web.commands.guiCommands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class RaceSearch {

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateSince = new Date();

    @DateTimeFormat (pattern="yyyy-MM-dd")
    private Date dateTo = new Date();

    private String country;
    private String[] countries = {"UK", "IRL", "FRA", "USA"};

    private String trackLength;
    private String[] trackLengths = {"4F", "5F", "6F", "7F"};

    private String trackType;
    private String[] trackTypes = {"Hcap", "Hcap Hrd", "Nov Stks", "Mdn Stks", "Stks", "Mdn", "Grp 3"};

    private int fiveStarsCountMin;
    private int fiveStarsCountMax;

    private int runnersCountMin;
    private int runnersCountMax;

    private double mov1Min;
    private double mov1Max;

    private double meanMin;
    private double meanMax;

    private double mov3to1Min;
    private double mov3to1Max;

    private int nptipsCountMin;
    private int nptipsCountMax;

    private int nptipsPerRaceMin;
    private int nptipsPerRaceMax;

    private int favouritePlace;

    private int runnerStars;

    private int favouritePlaceAmongStars;
}
