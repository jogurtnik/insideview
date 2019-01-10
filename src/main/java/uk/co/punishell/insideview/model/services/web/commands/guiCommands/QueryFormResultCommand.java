package uk.co.punishell.insideview.model.services.web.commands.guiCommands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.co.punishell.insideview.model.services.web.commands.entityCommands.RaceCommand;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class QueryFormResultCommand {


    private List<RaceCommand> races;
}
