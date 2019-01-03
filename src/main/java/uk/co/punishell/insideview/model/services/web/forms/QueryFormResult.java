package uk.co.punishell.insideview.model.services.web.forms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.co.punishell.insideview.model.services.web.commands.entityCommands.RaceCommand;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class QueryFormResult {

    private List<RaceCommand> races;
}
