package uk.co.punishell.insideview.model.services.web.commands.guiCommands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import uk.co.punishell.insideview.model.services.web.commands.entityCommands.RaceTypeCommand;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class RaceSearch extends Criteria {

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateSince = new Date();

    @DateTimeFormat (pattern="yyyy-MM-dd")
    private Date dateTo = new Date();

    private String country;
    private String[] countries = {"GB", "IE"};

    private double trackLengthMin;
    private double trackLengthMax;

    private List<RaceTypeCommand> raceTypes;

    private List<String> selectedRaceTypes;

    private double price9Min;
    private double price9Max;

    private double price10Min;
    private double price10Max;

    private double price11Min;
    private double price11Max;

    private double mov9to11Min;
    private double mov9to11Max;

    private double price60Min;
    private double price60Max;

    private double mov60Min;
    private double mov60Max;

    private double price30Min;
    private double price30Max;

    private double mov30Min;
    private double mov30Max;

    private double price15Min;
    private double price15Max;

    private double mov15Min;
    private double mov15Max;

    private double price5Min;
    private double price5Max;

    private double mov5Min;
    private double mov5Max;

    private double price3Min;
    private double price3Max;

    private double mov3Min;
    private double mov3Max;

    private double price2Min;
    private double price2Max;

    private double mov2Min;
    private double mov2Max;

    private double price1Min;
    private double price1Max;

    private double mov1Min;
    private double mov1Max;

    private double meanMin;
    private double meanMax;

    private double mov3to1Min;
    private double mov3to1Max;

    private boolean winner;
    private boolean placed;

    private int cprMin;
    private int cprMax;

    private int fiveStarsCountMin;
    private int fiveStarsCountMax;

    private int runnersCountMin;
    private int runnersCountMax;

    private int nptipsCountMin;
    private int nptipsCountMax;

    private int nptipsPerRaceMin;
    private int nptipsPerRaceMax;

    private int favouritePlaceMin;
    private int favouritePlaceMax;

    private int runnerStarsMin;
    private int runnerStarsMax;

    private int favouritePlaceAmongStarsMin;
    private int favouritePlaceAmongStarsMax;
}
