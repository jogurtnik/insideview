package uk.co.punishell.insideview.view.commands.guiCommands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
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

    private double trackLengthMin = 0;
    private double trackLengthMax = 0;

    private String[] raceTypes = {"Nov", "Hrd", "Hcap", "Chs", "Listed", "Mdn", "Stks", "Grp1", "Grp2",
                                  "Grp3", "Claim", "PA", "NHF", "INHF", "Sell", "Nursery", "App", "Class"};

    private List<String> selectedRaceTypes = new ArrayList<>();

    private int runnersCountMin = 0;
    private int runnersCountMax = 0;

    private int fiveStarsCountMin = 0;
    private int fiveStarsCountMax = 0;
}
