package uk.co.punishell.insideview.model.services.web.converters;

import org.junit.Before;
import org.junit.Test;
import uk.co.punishell.insideview.model.database.entities.RaceType;
import uk.co.punishell.insideview.model.database.entities.RaceTypeGroup;
import uk.co.punishell.insideview.model.services.converters.RaceTypeToRaceTypeCommand;
import uk.co.punishell.insideview.view.commands.entityCommands.RaceTypeCommand;

import static org.junit.Assert.*;

public class RaceTypeToRaceTypeCommandTest {

    public static final String NAME = "type";
    public static final RaceTypeGroup RACE_TYPE_GROUP = RaceTypeGroup.HORSE;
    RaceTypeToRaceTypeCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new RaceTypeToRaceTypeCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new RaceType()));
    }

    @Test
    public void convert() {

        // given
        RaceType raceType = new RaceType();
        raceType.setName(NAME);
        raceType.setRaceTypeGroup(RACE_TYPE_GROUP);

        // when
        RaceTypeCommand raceTypeCommand = converter.convert(raceType);

        // then
        assertEquals(NAME, raceType.getName());
        assertEquals(RACE_TYPE_GROUP, raceType.getRaceTypeGroup());
    }
}