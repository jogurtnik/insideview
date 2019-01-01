package uk.co.punishell.insideview.model.commands;

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
    private String country = "";
    private String city = "";
    private String trackLength = "";
    private String trackType = "";

    private List<RunnerCommand> runners = new LinkedList<>();
}
