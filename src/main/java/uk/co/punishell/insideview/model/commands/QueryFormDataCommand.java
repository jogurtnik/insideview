package uk.co.punishell.insideview.model.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class QueryFormDataCommand {

    /*private Date dateSince;
    private Date dateTo;*/

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
