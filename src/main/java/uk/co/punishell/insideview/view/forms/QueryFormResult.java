package uk.co.punishell.insideview.view.forms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.co.punishell.insideview.view.commands.entityCommands.RaceCommand;

import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class QueryFormResult {

    private List<RaceCommand> races = new LinkedList<>();
}
