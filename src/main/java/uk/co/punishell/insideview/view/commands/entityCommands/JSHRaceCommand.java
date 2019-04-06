package uk.co.punishell.insideview.view.commands.entityCommands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class JSHRaceCommand {

    private String generalInfo;
    private List<JSHRunnerCommand> runners = new ArrayList<>();
}
