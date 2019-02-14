package uk.co.punishell.insideview.view.commands.entityCommands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class RaceCommand{

    private Long id = 1L;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date = new Date();
    private LocalTime time;
    private String country;
    private String city;
    private double trackLength;
    private String trackLengthString;
    private List<RaceTypeCommand> raceTypes = new LinkedList<>();
    private String raceTypesNames = "";

    private List<RunnerCommand> runners = new LinkedList<>();

}
