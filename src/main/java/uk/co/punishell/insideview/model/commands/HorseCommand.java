package uk.co.punishell.insideview.model.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class HorseCommand {

    private Long id;
    private String name;

    private List<RunnerCommand> runners;
}
