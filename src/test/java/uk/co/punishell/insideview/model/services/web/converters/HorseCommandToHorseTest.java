package uk.co.punishell.insideview.model.services.web.converters;

import org.junit.Before;
import org.junit.Test;
import uk.co.punishell.insideview.model.database.entities.Horse;
import uk.co.punishell.insideview.model.services.converters.HorseCommandToHorse;
import uk.co.punishell.insideview.view.commands.entityCommands.HorseCommand;

import static org.junit.Assert.*;

public class HorseCommandToHorseTest {

    public static final Long ID_VALUE = 1L;
    public static final String HORSE_NAME = "HappyLittleHorse";
    HorseCommandToHorse converter;

    @Before
    public void setUp() throws Exception {
        converter = new HorseCommandToHorse();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new HorseCommand()));
    }

    @Test
    public void convert() {

        // given
        HorseCommand horseCommand = new HorseCommand();
        horseCommand.setId(ID_VALUE);
        horseCommand.setName(HORSE_NAME);

        // when
        Horse horse = converter.convert(horseCommand);

        // then

        assertEquals(ID_VALUE, horse.getId());
        assertEquals(HORSE_NAME, horse.getName());

    }
}