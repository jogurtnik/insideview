package uk.co.punishell.insideview.model.services.web.converters;

import org.junit.Before;
import org.junit.Test;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.services.web.commands.entityCommands.RaceCommand;

import java.time.LocalTime;
import java.util.Date;

import static org.junit.Assert.*;

public class RaceToRaceCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final Date DATE = new Date();
    public static final LocalTime TIME = LocalTime.now();
    public static final String COUNTRY = "HappyLittleCountry";
    public static final String CITY = "HappyLittleCity";
    public static final double TRACK_LENGTH = 2.25;
    private RaceToRaceCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new RaceToRaceCommand(new RunnerToRunnerCommand(new HorseToHorseCommand()),
                                            new RaceTypeToRaceTypeCommand());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Race()));
    }

    @Test
    public void convert() {

        // given
        Race race = new Race();
        race.setId(ID_VALUE);
        race.setDate(DATE);
        race.setTime(TIME);
        race.setCountry(COUNTRY);
        race.setCity(CITY);
        race.setTrackLength(TRACK_LENGTH);
        race.getRunners().add(new Runner());
        race.getRunners().add(new Runner());

        // when
        RaceCommand raceCommand = converter.convert(race);

        // then
        assertEquals(ID_VALUE, raceCommand.getId());
        assertEquals(DATE, raceCommand.getDate());
        assertEquals(TIME, raceCommand.getTime());
        assertEquals(COUNTRY, raceCommand.getCountry());
        assertEquals(CITY, raceCommand.getCity());
        assertEquals(TRACK_LENGTH, raceCommand.getTrackLength(), 0.001);
        assertEquals(2, raceCommand.getRunners().size());
    }
}