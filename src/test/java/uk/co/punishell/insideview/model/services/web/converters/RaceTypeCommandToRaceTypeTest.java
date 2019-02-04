package uk.co.punishell.insideview.model.services.web.converters;

import org.junit.Before;
import org.junit.Test;
import uk.co.punishell.insideview.model.database.entities.RaceType;
import uk.co.punishell.insideview.model.database.entities.RaceTypeGroup;
import uk.co.punishell.insideview.model.services.converters.RaceTypeCommandToRaceType;
import uk.co.punishell.insideview.view.web.commands.entityCommands.RaceTypeCommand;

import static org.junit.Assert.*;

public class RaceTypeCommandToRaceTypeTest {

    public static final String NAME = "Mdn";
    public static final RaceTypeGroup RACE_TYPE_GROUP = RaceTypeGroup.HORSE;
    RaceTypeCommandToRaceType converter;


    @Before
    public void setUp() throws Exception {
        converter = new RaceTypeCommandToRaceType();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new RaceTypeCommand()));
    }

    @Test
    public void convert() {

        // given
        RaceTypeCommand raceTypeCommand = new RaceTypeCommand();
        raceTypeCommand.setName(NAME);
        raceTypeCommand.setRaceTypeGroup(RACE_TYPE_GROUP);

        // when
        RaceType raceType = converter.convert(raceTypeCommand);

        // then
        assertEquals(NAME, raceType.getName());
        assertEquals(RACE_TYPE_GROUP, raceType.getRaceTypeGroup());
    }
}