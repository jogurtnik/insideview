package uk.co.punishell.insideview.model.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.commands.HorseCommand;
import uk.co.punishell.insideview.model.database.entities.Horse;

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
