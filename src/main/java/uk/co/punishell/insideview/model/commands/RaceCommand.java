package uk.co.punishell.insideview.model.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

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
    private LocalTime time;
}
