package uk.co.punishell.insideview.view.commands.guiCommands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.co.punishell.insideview.view.commands.entityCommands.RaceCommand;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class QueryFormResultCommand {


    private List<RaceCommand> races;
}