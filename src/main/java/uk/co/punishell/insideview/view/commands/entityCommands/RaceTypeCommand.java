package uk.co.punishell.insideview.view.commands.entityCommands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.co.punishell.insideview.model.database.entities.RaceTypeGroup;

@Setter
@Getter
@NoArgsConstructor
public class RaceTypeCommand {

    private String name;

    private RaceTypeGroup raceTypeGroup;
}
