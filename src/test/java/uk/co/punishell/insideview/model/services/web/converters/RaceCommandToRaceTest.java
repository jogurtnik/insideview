package uk.co.punishell.insideview.model.services.web.converters;

import org.junit.Before;
import org.junit.Test;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.services.converters.RaceCommandToRace;
import uk.co.punishell.insideview.model.services.converters.RaceTypeCommandToRaceType;
import uk.co.punishell.insideview.view.commands.entityCommands.RaceCommand;

import java.time.LocalTime;
import java.util.Date;

import static org.junit.Assert.*;

public class RaceCommandToRaceTest {

    public static final Long ID_VALUE = 1L;
    public static final Date DATE = new Date();
    public static final LocalTime TIME = LocalTime.now();
    public static final String COUNTRY = "HappyLittleCountry";
    public static final String CITY = "HappyLittleCity";
    public static final double TRACK_LENGTH = 2.25;
    RaceCommandToRace converter;


    @Before
    public void setUp() {
        converter = new RaceCommandToRace(new RaceTypeCommandToRaceType());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyOject() {
        assertNotNull(converter.convert(new RaceCommand()));
    }

    @Test
    public void convert() {

        // given
        RaceCommand raceCommand = new RaceCommand();
        raceCommand.setId(ID_VALUE);
        raceCommand.setDate(DATE);
        raceCommand.setTime(TIME);
        raceCommand.setCountry(COUNTRY);
        raceCommand.setCity(CITY);
        raceCommand.setTrackLength(TRACK_LENGTH);

        // when
        Race race = converter.convert(raceCommand);

        // then
        assertEquals(ID_VALUE, race.getId());
        assertEquals(DATE, race.getDate());
        assertEquals(TIME, race.getTime());
        assertEquals(COUNTRY, race.getCountry());
        assertEquals(CITY, race.getCity());
        assertEquals(TRACK_LENGTH, race.getTrackLength(), 0.001);
        assertNotNull(race.getRunners());
        assertNotNull(race.getRaceTypes());
    }
}