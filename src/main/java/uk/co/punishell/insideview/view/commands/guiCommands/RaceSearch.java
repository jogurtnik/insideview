package uk.co.punishell.insideview.view.commands.guiCommands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import uk.co.punishell.insideview.view.commands.entityCommands.RaceTypeCommand;

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

    private List<RaceTypeCommand> raceTypes;

    private List<String> selectedRaceTypes;

    private int runnersCountMin = 0;
    private int runnersCountMax = 0;

    private int fiveStarsCountMin = 0;
    private int fiveStarsCountMax = 0;
}
