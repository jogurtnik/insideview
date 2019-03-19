package uk.co.punishell.insideview.view.commands.guiCommands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class DatabaseStatusCheck {

    private String summaryMessage;

    private List<LocalDate> missingDates = new ArrayList<>();
}
