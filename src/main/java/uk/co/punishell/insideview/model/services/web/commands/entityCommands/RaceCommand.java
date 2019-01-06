package uk.co.punishell.insideview.model.services.web.commands.entityCommands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class RaceCommand {

    private Long id = 1L;
    private Date date;
    private LocalTime time;
    private String country;
    private String city;
    private double trackLength;
    private List<RaceTypeCommand> raceTypes = new LinkedList<>();

    private List<RunnerCommand> runners = new LinkedList<>();
}
