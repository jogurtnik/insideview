package uk.co.punishell.insideview.view.commands.guiCommands;

import lombok.Getter;
import lombok.Setter;
import uk.co.punishell.insideview.view.commands.entityCommands.RunnerCommand;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class RunnerSeachResult extends SearchResult<RunnerCommand> {

    List<RunnerCommand> runners = new ArrayList<>();
    private String message;
}
