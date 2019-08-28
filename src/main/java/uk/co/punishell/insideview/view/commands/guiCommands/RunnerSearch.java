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
public class RunnerSearch extends Criteria {

    public RunnerSearch() {
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

    private String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private List<String> selectedWeekDays = new ArrayList<>();

    Map<String, Double> movementsColorsMap = new HashMap<>();

    private String[] colors = {"", "blue", "green", "yellow", "orange", "pink"};

    private double price9Min = 0;
    private double price9Max = 0;

    private double price10Min = 0;
    private double price10Max = 0;

    private double price11Min = 0;
    private double price11Max = 0;

    private double mov9to11Min = 0;
    private double mov9to11Max = 0;

    private String mov9to11Color = "";
    private int mov9to11CountPerRace = 0;
    private int mov9to11FavPos = 0;

    private double price60Min = 0;
    private double price60Max = 0;

    private double mov60Min = 0;
    private double mov60Max = 0;

    private double price30Min = 0;
    private double price30Max = 0;

    private double mov30Min = 0;
    private double mov30Max = 0;

    private double price15Min = 0;
    private double price15Max = 0;

    private double mov15Min = 0;
    private double mov15Max = 0;

    private double price5Min = 0;
    private double price5Max = 0;

    private double mov5Min = 0;
    private double mov5Max = 0;

    private double price3Min = 0;
    private double price3Max = 0;

    private double mov3Min = 0;
    private double mov3Max = 0;

    private double price2Min = 0;
    private double price2Max = 0;

    private double mov2Min = 0;
    private double mov2Max = 0;

    private double price1Min = 0;
    private double price1Max = 0;

    private double mov1Min = 0;
    private double mov1Max = 0;

    private double meanMin = 0;
    private double meanMax = 0;

    private double mov3to1Min = 0;
    private double mov3to1Max = 0;

    private String allDayMovsColor = "";
    private String lastThreeMovsColor = "";
    private int allDayMovsCount = 0;
    private int allDayMovsCountPerRace = 0;
    private int lastThreeMovsCount = 0;
    private int lastThreeMovsCountPerRace = 0;

    private int favouritePlaceAmongColors = 0;

    private String result = "all";

    private int cprMin = 0;
    private int cprMax = 0;

    private int fiveStarsCountMin = 0;
    private int fiveStarsCountMax = 0;

    private int runnersCountMin = 0;
    private int runnersCountMax = 0;

    private int nptipsCountMin = 0;
    private int nptipsCountMax = 0;

    private int nptipsPerRaceMin = 0;
    private int nptipsPerRaceMax = 0;

    private int favouritePlaceMin = 0;
    private int favouritePlaceMax = 0;

    private boolean lastRunner = false;

    private int runnerStarsMin = 0;
    private int runnerStarsMax = 0;

    private int runnerStarsPerRaceMin = 0;
    private int runnerStarsPerRaceMax = 0;

    private int favouritePlaceAmongStarsMin = 0;
    private int favouritePlaceAmongStarsMax = 0;
}
