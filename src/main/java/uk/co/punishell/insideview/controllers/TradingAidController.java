package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ComparisonOperator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.co.punishell.insideview.model.exceptions.VendorsConnectionException;
import uk.co.punishell.insideview.model.services.JSHDataScrapper.JSHRaceAssembler;
import uk.co.punishell.insideview.model.services.JSHDataScrapper.JSHTradingAidAssembler;
import uk.co.punishell.insideview.model.services.JSHDataScrapper.WebpageScrapper;
import uk.co.punishell.insideview.view.commands.entityCommands.JSHRaceCommand;
import uk.co.punishell.insideview.view.commands.entityCommands.JSHRunnerCommand;
import uk.co.punishell.insideview.view.commands.guiCommands.TradingAidCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class TradingAidController {

    @Autowired
    private WebpageScrapper scrapper;

    @Autowired
    private JSHRaceAssembler jshRaceAssembler;

    @Autowired
    private JSHTradingAidAssembler jshTradingAidAssembler;

    private final String JSHLoginUrl = "https://juststarthere.co.uk/cgi-bin/just.pl";
    private final String JSHDataTargetUrl = "https://juststarthere.co.uk/allwinback.html";

    @GetMapping("tradingAid")
    public String getTradingAid(Model model) throws VendorsConnectionException {

        TradingAidCommand jshData = getJshData();

        if (jshData == null) {
            throw new VendorsConnectionException("The data is currently unavailable.");
        }

        model.addAttribute("jshData", jshData);

        return "tradingAid";
    }

    // TODO add custom exception
    @RequestMapping("exportToExcel")
    public void exportFundamentalsToExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {

        log.debug("Requested exporting data to excel.");

        TradingAidCommand jshData = getJshData();

        // create a new file
        FileOutputStream outExcel = new FileOutputStream("workbook");
        // create a new workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        // create a new sheet
        HSSFSheet sheet = workbook.createSheet();

        sheet.setDefaultColumnWidth(7);

        // create header row
        HSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Horse");
        header.createCell(1).setCellValue("9am");
        header.createCell(2).setCellValue("MovAM");
        header.createCell(3).setCellValue("60min");
        header.createCell(4).setCellValue("Mov60");
        header.createCell(5).setCellValue("30min");
        header.createCell(6).setCellValue("Mov30");
        header.createCell(7).setCellValue("15min");
        header.createCell(8).setCellValue("Mov15");
        header.createCell(9).setCellValue("5min");
        header.createCell(10).setCellValue("Mov5");
        header.createCell(11).setCellValue("3min");
        header.createCell(12).setCellValue("Mov3");
        header.createCell(13).setCellValue("2min");
        header.createCell(14).setCellValue("Mov2");
        header.createCell(15).setCellValue("1min");
        header.createCell(16).setCellValue("Mov1");
        header.createCell(17).setCellValue("Mean");
        header.createCell(18).setCellValue("321");
        header.createCell(19).setCellValue("Result");
        header.createCell(20).setCellValue("CPR");
        header.createCell(21).setCellValue("NPTips");
        header.createCell(22).setCellValue("Naps");
        header.createCell(23).setCellValue("Stars");
        header.createCell(24).setCellValue("Jockey");
        header.createCell(25).setCellValue("Wins");
        header.createCell(26).setCellValue("R");
        header.createCell(27).setCellValue("Rs");
        header.createCell(28).setCellValue("Mov9-60");
        header.createCell(29).setCellValue("FP");
        header.createCell(30).setCellValue("HG");
        header.createCell(31).setCellValue("Trainer");
        header.createCell(32).setCellValue("Wins");
        header.createCell(33).setCellValue("R");
        header.createCell(34).setCellValue("Rs");

        int rowIndex = 1;
        for (JSHRaceCommand race : jshData.getRaces()) {

            HSSFRow row = sheet.createRow(rowIndex);
            HSSFCell cell = row.createCell(0);
            cell.setCellValue(race.getGeneralInfo());
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 34));

            rowIndex++;

            int runnerFavPos = 1;
            for (JSHRunnerCommand runner : race.getRunners()) {
                row = sheet.createRow(rowIndex);
                row.createCell(0).setCellValue(runner.getHorseName());
                row.createCell(1).setCellValue(runner.getPrice9());
                row.createCell(2).setCellValue(runner.getMov9to11());
                row.createCell(3).setCellValue(runner.getPrice60());
                row.createCell(4).setCellValue(runner.getMov60());
                row.createCell(5).setCellValue(runner.getPrice30());
                row.createCell(6).setCellValue(runner.getMov30());
                row.createCell(7).setCellValue(runner.getPrice15());
                row.createCell(8).setCellValue(runner.getMov15());
                row.createCell(9).setCellValue(runner.getPrice5());
                row.createCell(10).setCellValue(runner.getMov5());
                row.createCell(11).setCellValue(runner.getPrice3());
                row.createCell(12).setCellValue(runner.getMov3());
                row.createCell(13).setCellValue(runner.getPrice2());
                row.createCell(14).setCellValue(runner.getMov2());
                row.createCell(15).setCellValue(runner.getPrice1());
                row.createCell(16).setCellValue(runner.getMov1());
                row.createCell(17).setCellValue(runner.getMean());
                row.createCell(18).setCellValue(runner.getMov3to1());
                row.createCell(19).setCellValue(runner.getResult());
                row.createCell(20).setCellValue(runner.getCpr());
                row.createCell(21).setCellValue(runner.getNptips());
                row.createCell(22).setCellValue(runner.getNaps());
                row.createCell(23).setCellValue(runner.getStars());
                row.createCell(24).setCellValue(runner.getJockey());
                row.createCell(25).setCellValue(runner.getJockeyWins());
                row.createCell(26).setCellValue(runner.getJockeyRideNo());
                row.createCell(27).setCellValue(runner.getJockeyRides());
                row.createCell(28).setCellValue(runner.getMov9to60());
                row.createCell(29).setCellValue(runnerFavPos++);
                row.createCell(30).setCellValue(runner.getHeadGear());
                row.createCell(31).setCellValue(runner.getTrainer());
                row.createCell(32).setCellValue(runner.getTrainerWins());
                row.createCell(33).setCellValue(runner.getTrainerRunnerNo());
                row.createCell(34).setCellValue(runner.getTrainerRunners());

                rowIndex++;
            }

        }

        for (int i = 0; i < 34; i++) {
            sheet.autoSizeColumn(i);
        }

        // Movement formatting
        HSSFSheetConditionalFormatting conditionalFormattingLayer = sheet.getSheetConditionalFormatting();
        HSSFConditionalFormattingRule blueRule = conditionalFormattingLayer.createConditionalFormattingRule(ComparisonOperator.GT, "5");
        HSSFConditionalFormattingRule greenRule = conditionalFormattingLayer.createConditionalFormattingRule(ComparisonOperator.BETWEEN, "2.5", "5");
        HSSFConditionalFormattingRule yellowRule = conditionalFormattingLayer.createConditionalFormattingRule(ComparisonOperator.BETWEEN, "0.01", "2.5");
        HSSFConditionalFormattingRule orangeRule = conditionalFormattingLayer.createConditionalFormattingRule(ComparisonOperator.BETWEEN, "-2.5", "-0.01");
        HSSFConditionalFormattingRule pinkRule = conditionalFormattingLayer.createConditionalFormattingRule(ComparisonOperator.LT, "-2.5");

        HSSFPatternFormatting blueFormatting = blueRule.createPatternFormatting();
        blueFormatting.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());

        HSSFPatternFormatting greenFormatting = greenRule.createPatternFormatting();
        greenFormatting.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());

        HSSFPatternFormatting yellowFormatting = yellowRule.createPatternFormatting();
        yellowFormatting.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());

        HSSFPatternFormatting orangeFormatting = orangeRule.createPatternFormatting();
        orangeFormatting.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.ORANGE.getIndex());

        HSSFPatternFormatting pinkFormatting = pinkRule.createPatternFormatting();
        pinkFormatting.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.PINK.getIndex());

        HSSFPalette palette = workbook.getCustomPalette();
        palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.BLUE.getIndex(), (byte) 153, (byte) 204, (byte) 255);
        palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.GREEN.getIndex(), (byte) 204, (byte) 255, (byte) 204);
        palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.YELLOW.getIndex(), (byte) 255, (byte) 255, (byte) 153);
        palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.ORANGE.getIndex(), (byte) 255, (byte) 204, (byte) 153);
        palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.PINK.getIndex(), (byte) 255, (byte) 153, (byte) 204);

        int lastRowNum = sheet.getLastRowNum();

        CellRangeAddress[] basicMovementCellRangeAddresses = {

                // 9-11 Movement
                new CellRangeAddress(1, lastRowNum, 2, 2),
                // 60 min Movement
                new CellRangeAddress(1, lastRowNum, 4, 4),
                // 30 min Movement
                new CellRangeAddress(1, lastRowNum, 6, 6),
                // 15 min Movement
                new CellRangeAddress(1, lastRowNum, 8, 8),
                // 5 min Movement
                new CellRangeAddress(1, lastRowNum, 10, 10),
                // 3 min Movement
                new CellRangeAddress(1, lastRowNum, 12, 12),
                // 2 min Movement
                new CellRangeAddress(1, lastRowNum, 14, 14),
                // 1 min Movement
                new CellRangeAddress(1, lastRowNum, 16, 16),
                // Mean
                new CellRangeAddress(1, lastRowNum, 17, 17),
                // 3-1 Movement
                new CellRangeAddress(1, lastRowNum, 18, 18),
        };

        conditionalFormattingLayer.addConditionalFormatting(basicMovementCellRangeAddresses, blueRule);
        conditionalFormattingLayer.addConditionalFormatting(basicMovementCellRangeAddresses, greenRule);
        conditionalFormattingLayer.addConditionalFormatting(basicMovementCellRangeAddresses, yellowRule);
        conditionalFormattingLayer.addConditionalFormatting(basicMovementCellRangeAddresses, orangeRule);
        conditionalFormattingLayer.addConditionalFormatting(basicMovementCellRangeAddresses, pinkRule);


        // Movement 9 to 60 formatting
        HSSFConditionalFormattingRule mov9to60LessThanZeroRule = conditionalFormattingLayer.createConditionalFormattingRule(ComparisonOperator.LT, "0");
        HSSFFontFormatting mov9to60LessThanZeroFormatting = mov9to60LessThanZeroRule.createFontFormatting();
        mov9to60LessThanZeroFormatting.setFontColorIndex(HSSFColor.HSSFColorPredefined.RED.getIndex());
        CellRangeAddress[] mov9to60CellRange = {new CellRangeAddress(1, lastRowNum, 28, 28)};
        conditionalFormattingLayer.addConditionalFormatting(mov9to60CellRange, mov9to60LessThanZeroRule);

        // CPR Formatting
        HSSFConditionalFormattingRule cprRule = conditionalFormattingLayer.createConditionalFormattingRule(ComparisonOperator.LT, "0");
        HSSFFontFormatting cprFormatting = cprRule.createFontFormatting();
        cprFormatting.setFontColorIndex(HSSFColor.HSSFColorPredefined.RED.getIndex());
        CellRangeAddress[] cprCellRange = {new CellRangeAddress(1, lastRowNum, 20, 20)};
        conditionalFormattingLayer.addConditionalFormatting(cprCellRange, cprRule);

        // Result formatting
        HSSFConditionalFormattingRule winnerRule = conditionalFormattingLayer.createConditionalFormattingRule("EXACT($T1, \"Won\")");
        HSSFConditionalFormattingRule placedRule = conditionalFormattingLayer.createConditionalFormattingRule("EXACT($T1, \"Placed\")");

        HSSFPatternFormatting winnerFormatting = winnerRule.createPatternFormatting();
        winnerFormatting.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN.getIndex());

        HSSFPatternFormatting placedFormatting = placedRule.createPatternFormatting();
        placedFormatting.setFillBackgroundColor(IndexedColors.LEMON_CHIFFON.getIndex());

        palette.setColorAtIndex(IndexedColors.BRIGHT_GREEN.getIndex(), (byte) 62, (byte) 213, (byte) 120);
        palette.setColorAtIndex(IndexedColors.LEMON_CHIFFON.getIndex(), (byte) 242, (byte) 218, (byte) 193);

        CellRangeAddress[] resultCellRange = {
                // Horse name
                new CellRangeAddress(1, lastRowNum, 0, 0),
                // Result
                new CellRangeAddress(1, lastRowNum, 19, 19),
                // Jockey
                new CellRangeAddress(1, lastRowNum, 24, 24),
                // Trainer
                new CellRangeAddress(1, lastRowNum, 31, 31)

        };

        conditionalFormattingLayer.addConditionalFormatting(resultCellRange, winnerRule);
        conditionalFormattingLayer.addConditionalFormatting(resultCellRange, placedRule);

        // Writing the file into Http response
        workbook.write(outExcel);

        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date today = new Date();
        response.setHeader("Content-disposition", "attachment; filename=" + "trading-aid-" + dateFormat.format(today) + ".xls");

        FileInputStream inputStream = new FileInputStream(new File("workbook"));

        OutputStream outputStream = response.getOutputStream();

        byte[] buffer = new byte[1024];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {  // write bytes read from the input stream into the output stream
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.flush();

        log.debug("Data export to excel successful.");

    }

    private TradingAidCommand getJshData() {

        // define login form input elements
        Map<String, String> data = new HashMap<>();
        data.put("op", "login");
        data.put("usr_login", "gavinb");
        data.put("usr_password", "passw0rd");

        Document doc;
        doc = scrapper.loginAndGetWebpage(JSHLoginUrl, data, JSHDataTargetUrl);


        Elements raceInfo = doc.getElementsByClass("race_infoback");
        Elements raceBody = doc.getElementsByClass("racebody");

        TradingAidCommand tradingAidCommand = new TradingAidCommand();

        if (raceInfo != null &&
                raceInfo.size() > 0 &&
                raceInfo.size() == raceBody.size()) {

            List<JSHRaceCommand> races = tradingAidCommand.getRaces();

            for (int i = 0; i < raceInfo.size(); i++) {

                Element raceInfoElement = raceInfo.get(i);
                Element raceBodyElement = raceBody.get(i);

                JSHRaceCommand race = jshRaceAssembler.getJshRace(raceInfoElement, raceBodyElement);

                races.add(race);

            }

            races = jshTradingAidAssembler.assembleRaceData(races);

            tradingAidCommand.setRaces(races);

        } else {
            if (raceInfo == null || raceInfo.size() != raceBody.size()) {
                tradingAidCommand.setErrorMessage("Error reading data from JSH.");
            } else if (raceInfo.size() == 0) {
                tradingAidCommand.setErrorMessage("No race data available at the moment. Try again later.");
            }
        }

        return tradingAidCommand;
    }
}
