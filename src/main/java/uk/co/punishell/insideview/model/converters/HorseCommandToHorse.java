package uk.co.punishell.insideview.model.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.model.commands.HorseCommand;
import uk.co.punishell.insideview.model.database.entities.Horse;

import java.util.HashSet;

@Component
public class HorseCommandToHorse implements Converter<HorseCommand, Horse> {

    @Synchronized
    @Nullable
    @Override
    public Horse convert(HorseCommand source) {

        if (source == null) {
            return null;
        }

        final Horse horse = new Horse();
        horse.setName(source.getName());
        horse.setRunners(new HashSet<>());

        return horse;
    }
}
