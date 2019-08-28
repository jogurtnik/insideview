package uk.co.punishell.insideview.view.commands.guiCommands;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class RaceSearch extends Criteria {

    public RaceSearch() {
        this.movementsColorsMap.put("blueMovementMin", 5.01);

        this.movementsColorsMap.put("greenMovementMin",2.5);
        this.movementsColorsMap.put("greenMovementMax", 5.0);

        this.movementsColorsMap.put("yellowMovementMin", 0.01);
        this.movementsColorsMap.put("yellowMovementMax", 2.49);

        this.movementsColorsMap.put("orangeMovementMin", -2.49);
        this.movementsColorsMap.put("orangeMovementMax", -0.01);

        this.movementsColorsMap.put("pinkMovementMax", -2.5);
    }

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate localDateSince = LocalDate.MIN;

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private LocalDate localDateTo = LocalDate.now();

    private String country;
    private String[] countries = {"All", "GB", "IE"};

    private Map<String, Double> movementsColorsMap = new HashMap<>();

    private String[] colors = {"", "blue", "green", "yellow", "orange", "pink"};

    private double trackLengthMin = 0;
    private double trackLengthMax = 0;

    private String[] raceTypes = {"Nov", "Hrd", "Hcap", "Chs", "Listed", "Mdn", "Stks", "Grp1", "Grp2",
                                  "Grp3", "Claim", "PA", "NHF", "INHF", "Sell", "Nursery", "App", "Class"};

    private List<String> selectedRaceTypes = new ArrayList<>();

    private String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private List<String> selectedWeekDays = new ArrayList<>();

    private int runnersCountMin = 0;
    private int runnersCountMax = 0;

    private int fiveStarsCountMin = 0;
    private int fiveStarsCountMax = 0;

    private String mov9to11Color = "";
    private int mov9to11CountPerRace = 0;

    private String lastThreeMovsColor = "";
    private int lastThreeMovsCount = 0;
    private int lastThreeMovsCountPerRace = 0;
}
