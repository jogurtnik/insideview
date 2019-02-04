package uk.co.punishell.insideview.model.services.web.converters;

import org.junit.Before;
import org.junit.Test;
import uk.co.punishell.insideview.model.database.entities.Horse;
import uk.co.punishell.insideview.model.services.converters.HorseToHorseCommand;
import uk.co.punishell.insideview.view.commands.entityCommands.HorseCommand;

import static org.junit.Assert.*;

public class HorseToHorseCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final String HORSE_NAME = "HappyLittleHorse";
    HorseToHorseCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new HorseToHorseCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Horse()));
    }

    @Test
    public void convert() {

        // given
        Horse horse = new Horse();
        horse.setId(ID_VALUE);
        horse.setName(HORSE_NAME);

        // when
        HorseCommand horseCommand = converter.convert(horse);

        // then
        assertEquals(ID_VALUE, horseCommand.getId());
        assertEquals(HORSE_NAME, horseCommand.getName());
    }
}