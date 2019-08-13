package uk.co.punishell.insideview.view.commands.guiCommands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.co.punishell.insideview.view.commands.entityCommands.JSHRaceCommand;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class TradingAidCommand {

    private List<JSHRaceCommand> races = new ArrayList<>();
    private String errorMessage;
}
