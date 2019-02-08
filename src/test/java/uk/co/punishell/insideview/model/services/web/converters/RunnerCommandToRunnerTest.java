package uk.co.punishell.insideview.model.services.web.converters;

import org.junit.Before;
import org.junit.Test;
import uk.co.punishell.insideview.model.database.entities.Runner;
import uk.co.punishell.insideview.model.services.converters.HorseCommandToHorse;
import uk.co.punishell.insideview.model.services.converters.RunnerCommandToRunner;
import uk.co.punishell.insideview.view.commands.entityCommands.RunnerCommand;

import static org.junit.Assert.*;

public class RunnerCommandToRunnerTest {

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
    public static final boolean BOOLEAN_IS_WINNER = false;
    public static final boolean BOOLEAN_IS_PLACED = true;
    public static final String RESULT = "P";
    public static final int CPR = 22;
    public static final int NPTIPS = 23;
    public static final int[] STARS = new int[5];
    public static final int NAPS = 24;
    private RunnerCommandToRunner converter;

    @Before
    public void setUp() throws Exception {
        converter = new RunnerCommandToRunner(new HorseCommandToHorse());
    }

    @Test
    public void setNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void setEmptyObject() {
        assertNotNull(converter.convert(new RunnerCommand()));
    }

    @Test
    public void convert() {

        // given
        RunnerCommand runnerCommand = new RunnerCommand();
        runnerCommand.setId(ID_VALUE);
        runnerCommand.setStatus(STATUS);
        runnerCommand.setPrice9(PRICE9);
        runnerCommand.setPrice10(PRICE10);
        runnerCommand.setPrice11(PRICE11);
        runnerCommand.setMov9to11(MOV9TO11);
        runnerCommand.setPrice60(PRICE60);
        runnerCommand.setMov60(MOV60);
        runnerCommand.setPrice30(PRICE30);
        runnerCommand.setMov30(MOV30);
        runnerCommand.setPrice15(PRICE15);
        runnerCommand.setMov15(MOV15);
        runnerCommand.setPrice5(PRICE5);
        runnerCommand.setMov5(MOV5);
        runnerCommand.setPrice3(PRICE3);
        runnerCommand.setMov3(MOV3);
        runnerCommand.setPrice2(PRICE2);
        runnerCommand.setMov2(MOV2);
        runnerCommand.setPrice1(PRICE1);
        runnerCommand.setMov1(MOV1);
        runnerCommand.setMean(MEAN);
        runnerCommand.setMov3to1(MOV3TO1);
        runnerCommand.setResult(RESULT);
        runnerCommand.setCpr(CPR);
        runnerCommand.setNptips(NPTIPS);
        runnerCommand.setStars(STARS);
        runnerCommand.setNaps(NAPS);

        // when
        Runner runner = converter.convert(runnerCommand);

        // then
        assertEquals(ID_VALUE, runner.getId());
        assertEquals(STATUS, runner.isStatus());
        assertEquals(PRICE9, runner.getPrice9(), DELTA);
        assertEquals(PRICE10, runner.getPrice10(), DELTA);
        assertEquals(PRICE11, runner.getPrice11(), DELTA);
        assertEquals(MOV9TO11, runner.getMov9to11(), DELTA);
        assertEquals(PRICE60, runner.getPrice60(), DELTA);
        assertEquals(MOV60, runner.getMov60(), DELTA);
        assertEquals(PRICE30, runner.getPrice30(), DELTA);
        assertEquals(MOV30, runner.getMov30(), DELTA);
        assertEquals(PRICE15, runner.getPrice15(), DELTA);
        assertEquals(MOV15, runner.getMov15(), DELTA);
        assertEquals(PRICE5, runner.getPrice5(), DELTA);
        assertEquals(MOV5, runner.getMov5(), DELTA);
        assertEquals(PRICE3, runner.getPrice3(), DELTA);
        assertEquals(MOV3, runner.getMov3(), DELTA);
        assertEquals(PRICE2, runner.getPrice2(), DELTA);
        assertEquals(MOV2, runner.getMov2(), DELTA);
        assertEquals(PRICE1, runner.getPrice1(), DELTA);
        assertEquals(MOV1, runner.getMov1(), DELTA);
        assertEquals(MEAN, runner.getMean(), DELTA);
        assertEquals(MOV3TO1, runner.getMov3to1(), DELTA);
        assertEquals(BOOLEAN_IS_WINNER, runner.isWinner());
        assertEquals(BOOLEAN_IS_PLACED, runner.isPlaced());
        assertEquals(CPR, runner.getCpr());
        assertEquals(NPTIPS, runner.getNptips());
        assertEquals(STARS.length, runner.getStars());
        assertEquals(NAPS, runner.getNaps());
    }
}