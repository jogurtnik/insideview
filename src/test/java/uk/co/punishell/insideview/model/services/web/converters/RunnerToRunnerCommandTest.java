package uk.co.punishell.insideview.model.services.web.converters;

import org.junit.Before;
import org.junit.Test;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.services.converters.HorseToHorseCommand;
import uk.co.punishell.insideview.model.services.converters.RunnerToRunnerCommand;
import uk.co.punishell.insideview.view.commands.entityCommands.RunnerCommand;

import static org.junit.Assert.*;

public class RunnerToRunnerCommandTest {

    public static final Long ID_VALUE = 1L;
    public static final boolean STATUS = true;
    public static final double PRICE9 = 1;
    public static final double PRICE10 = 2;
    public static final double PRICE11 = 3;
    public static final double MOV9TO11 = 4;
    public static final double PRICE60 = 5;
    public static final double MOV60 = 6;
    public static final double PRICE30 = 7;
    public static final double MOV30 = 8;
    public static final double PRICE15 = 9;
    public static final double MOV15 = 10;
    public static final double PRICE5 = 11;
    public static final double MOV5 = 12;
    public static final double PRICE3 = 13;
    public static final double MOV3 = 14;
    public static final double PRICE2 = 15;
    public static final double MOV2 = 16;
    public static final double PRICE1 = 17;
    public static final double MOV1 = 18;
    public static final double MEAN = 19;
    public static final double MOV3TO1 = 20;
    public static final double DELTA = 0.001;
    public static final boolean BOOLEAN_IS_WINNER = true;
    public static final boolean BOOLEAN_IS_PLACED = true;
    public static final String RESULT = "W";
    public static final int CPR = 22;
    public static final int NPTIPS = 23;
    public static final int STARS = 5;
    public static final int NAPS = 24;
    private RunnerToRunnerCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new RunnerToRunnerCommand(new HorseToHorseCommand());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Runner()));
    }

    @Test
    public void convert() {

        // given
        Runner runner = new Runner();
        runner.setId(ID_VALUE);
        runner.setStatus(STATUS);
        runner.setPrice9(PRICE9);
        runner.setPrice10(PRICE10);
        runner.setPrice11(PRICE11);
        runner.setMov9to11(MOV9TO11);
        runner.setPrice60(PRICE60);
        runner.setMov60(MOV60);
        runner.setPrice30(PRICE30);
        runner.setMov30(MOV30);
        runner.setPrice15(PRICE15);
        runner.setMov15(MOV15);
        runner.setPrice5(PRICE5);
        runner.setMov5(MOV5);
        runner.setPrice3(PRICE3);
        runner.setMov3(MOV3);
        runner.setPrice2(PRICE2);
        runner.setMov2(MOV2);
        runner.setPrice1(PRICE1);
        runner.setMov1(MOV1);
        runner.setMean(MEAN);
        runner.setMov3to1(MOV3TO1);
        runner.setWinner(BOOLEAN_IS_WINNER);
        runner.setPlaced(BOOLEAN_IS_PLACED);
        runner.setCpr(CPR);
        runner.setNptips(NPTIPS);
        runner.setStars(STARS);
        runner.setNaps(NAPS);

        // when
        RunnerCommand runnerCommand = converter.convert(runner);

        // then
        assertEquals(ID_VALUE, runnerCommand.getId());
        assertEquals(STATUS, runnerCommand.isStatus());
        assertEquals(PRICE9, runnerCommand.getPrice9(), DELTA);
        assertEquals(PRICE10, runnerCommand.getPrice10(), DELTA);
        assertEquals(PRICE11, runnerCommand.getPrice11(), DELTA);
        assertEquals(MOV9TO11, runnerCommand.getMov9to11(), DELTA);
        assertEquals(PRICE60, runnerCommand.getPrice60(), DELTA);
        assertEquals(MOV60, runnerCommand.getMov60(), DELTA);
        assertEquals(PRICE30, runnerCommand.getPrice30(), DELTA);
        assertEquals(MOV30, runnerCommand.getMov30(), DELTA);
        assertEquals(PRICE15, runnerCommand.getPrice15(), DELTA);
        assertEquals(MOV15, runnerCommand.getMov15(), DELTA);
        assertEquals(PRICE5, runnerCommand.getPrice5(), DELTA);
        assertEquals(MOV5, runnerCommand.getMov5(), DELTA);
        assertEquals(PRICE3, runnerCommand.getPrice3(), DELTA);
        assertEquals(MOV3, runnerCommand.getMov3(), DELTA);
        assertEquals(PRICE2, runnerCommand.getPrice2(), DELTA);
        assertEquals(MOV2, runnerCommand.getMov2(), DELTA);
        assertEquals(PRICE1, runnerCommand.getPrice1(), DELTA);
        assertEquals(MOV1, runnerCommand.getMov1(), DELTA);
        assertEquals(MEAN, runnerCommand.getMean(), DELTA);
        assertEquals(MOV3TO1, runnerCommand.getMov3to1(), DELTA);
        assertEquals(RESULT, runnerCommand.getResult());
        assertEquals(CPR, runnerCommand.getCpr());
        assertEquals(NPTIPS, runnerCommand.getNptips());
        assertEquals(STARS, runnerCommand.getStars().length);
        assertEquals(NAPS, runnerCommand.getNaps());
    }
}