package uk.co.punishell.insideview.model.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class RaceCommand {

    private Long id;
    private Date date;
    private String country;
    private String city;
    private String trackLength;
    private String trackType;

    private List<RunnerCommand> runners;
}
