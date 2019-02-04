package uk.co.punishell.insideview.model.services.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.database.entities.Horse;
import uk.co.punishell.insideview.view.web.commands.entityCommands.HorseCommand;

@Component
public class HorseToHorseCommand implements Converter<Horse, HorseCommand> {

    @Synchronized
    @Nullable
    @Override
    public HorseCommand convert(Horse source) {

        if (source == null) {
            return null;
        }

        final HorseCommand horseCommand = new HorseCommand();
        horseCommand.setId(source.getId());
        horseCommand.setName(source.getName());

        return horseCommand;
    }
}
