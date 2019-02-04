package uk.co.punishell.insideview.view.commands.guiCommands;

import lombok.Getter;
import lombok.Setter;
import uk.co.punishell.insideview.view.commands.entityCommands.RaceCommand;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class RaceSearchResult extends SearchResult<RaceCommand> {

    private List<RaceCommand> races = new ArrayList<>();
    private String message;
}
