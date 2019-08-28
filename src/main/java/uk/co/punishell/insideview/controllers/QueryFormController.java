package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ComparisonOperator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uk.co.punishell.insideview.model.database.services.RaceSearchEngine;
import uk.co.punishell.insideview.model.database.services.RunnerSearchEngine;
import uk.co.punishell.insideview.model.exceptions.BindingFormException;
import uk.co.punishell.insideview.view.commands.entityCommands.RaceCommand;
import uk.co.punishell.insideview.view.commands.entityCommands.RunnerCommand;
import uk.co.punishell.insideview.view.commands.guiCommands.RaceSearch;
import uk.co.punishell.insideview.view.commands.guiCommands.RaceSearchResult;
import uk.co.punishell.insideview.view.commands.guiCommands.RunnerSeachResult;
import uk.co.punishell.insideview.view.commands.guiCommands.RunnerSearch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Controller
@SessionAttributes({"raceSearch", "runnerSearch"})
public class QueryFormController {

    private RaceSearchEngine raceSearchEngine;
    private RunnerSearchEngine runnerSearchEngine;

    @Autowired
    public QueryFormController(RaceSearchEngine raceSearchEngine, RunnerSearchEngine runnerSearchEngine) {
        this.raceSearchEngine = raceSearchEngine;
        this.runnerSearchEngine = runnerSearchEngine;
    }

    @GetMapping({"query", "query.html"})
    public String getQueryPage() {

        return "query";
    }

    @PostMapping("performRaceQuery")
    public String performRaceQuery(@ModelAttribute("raceSearch") @Valid RaceSearch raceSearch,
                               BindingResult bindingResult,
                               HttpSession session) throws BindingFormException {

        if (bindingResult.hasErrors()) {
            throw new BindingFormException("There has been unidentified issue while processing the query form.");
        }

        RaceSearchResult raceSearchResult = raceSearchEngine.search(raceSearch);

        session.setAttribute("raceSearchResult", raceSearchResult);

        return "redirect:/query";
    }

    @PostMapping("performRunnerQuery")
    public String performRunnerQuery(@ModelAttribute("runnerSearch") @Valid RunnerSearch runnerSearch,
                                     BindingResult bindingResult,
                                     HttpSession session) throws BindingFormException {
        if (bindingResult.hasErrors()) {
            throw new BindingFormException("There has been unidentified issue while processing the query form.");
        }

        RunnerSeachResult runnerSeachResult = runnerSearchEngine.search(runnerSearch);

        session.setAttribute("runnerSearchResult", runnerSeachResult);

        return "redirect:/query";
    }

    @RequestMapping({"clearRaceAndRunnerForms", "/clearRaceAndRunnerForms"})
    public String clearRaceAndRunnerForms(HttpSession session) {

        session.setAttribute("raceSearch", new RaceSearch());
        session.setAttribute("runnerSearch", new RunnerSearch());

        return "redirect:/query";
    }

    @RequestMapping({"clearRaceForm", "/clearRaceForm"})
    public String clearRaceForm(@ModelAttribute("raceSearch") RaceSearch raceSearch,
                                Model model) {
        model.addAttribute("raceSearch", new RaceSearch());
        return "redirect:/query";
    }

    @RequestMapping({"clearRaceResults", "/clearRaceResults"})
    public String clearRaceResults(HttpSession session) {
        session.setAttribute("raceSearchResult", null);
        return "redirect:/query";
    }

    @RequestMapping({"clearRunnerForm", "/clearRunnerForm"})
    public String clearRunnerForm(@ModelAttribute("runnerSearch") RunnerSearch runnerSearch,
                                  Model model) {
        model.addAttribute("runnerSearch", new RunnerSearch());
        return "redirect:/query";
    }

    @RequestMapping({"clearRunnerResults", "/clearRunnerResults"})
    public String clearRunnerResults(@ModelAttribute("runnerSearchResult") RunnerSeachResult runnerSeachResult,
                                     HttpSession session) {
        session.setAttribute("runnerSearchResult", null);
        return "redirect:/query";
    }

    @ModelAttribute("raceSearch")
    public RaceSearch getRaceSearchFormData() {

        return new RaceSearch();
    }

    @ModelAttribute("runnerSearch")
    public RunnerSearch gerRunnerSearchFormData() {
        return new RunnerSearch();
    }

    @RequestMapping("exportRaceResultsToExcel")
    public void exportRaceResultsToExcel(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        RaceSearchResult result = (RaceSearchResult) session.getAttribute("raceSearchResult");

        log.debug("Requested exporting data to excel.");

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
        header.createCell(24).setCellValue("FP");

        int rowIndex = 1;
        for (RaceCommand race : result.getRaces()) {

            HSSFRow row = sheet.createRow(rowIndex);
            HSSFCell cell = row.createCell(0);
            String raceInfo;
            StringBuilder sb = new StringBuilder();
            sb.append(race.getTime().toString() + " ");
            sb.append(race.getCity() + " ");
            sb.append(race.getCountry() + ", ");
            sb.append(race.getTrackLengthString() + " ");
            sb.append(race.getRaceTypesNames());
            sb.append("WIN market, BACK prices");
            raceInfo = sb.toString();
            cell.setCellValue(raceInfo);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 24));

            rowIndex++;

            int runnerFavPos = 1;
            for (RunnerCommand runner : race.getRunners()) {
                row = sheet.createRow(rowIndex);
                row.createCell(0).setCellValue(runner.getHorse().getName());
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
                row.createCell(23).setCellValue(runner.getStars().length);
                row.createCell(24).setCellValue(runnerFavPos++);

                rowIndex++;
            }
        }

        for (int i = 0; i < 24; i++) {
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
                new CellRangeAddress(1, lastRowNum, 19, 19)

        };

        conditionalFormattingLayer.addConditionalFormatting(resultCellRange, winnerRule);
        conditionalFormattingLayer.addConditionalFormatting(resultCellRange, placedRule);

        // Writing the file into Http response
        workbook.write(outExcel);

        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date today = new Date();
        response.setHeader("Content-disposition", "attachment; filename=" + "race-query-result-" + dateFormat.format(today) + ".xls");

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

    @RequestMapping("exportRunnerResultsToExcel")
    public void exportRunnerResultsToExcel(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {

        RunnerSeachResult result = (RunnerSeachResult) session.getAttribute("runnerSearchResult");

        log.debug("Requested exporting data to excel.");

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
        header.createCell(24).setCellValue("FP");

        int rowIndex = 1;
        HSSFRow row;
        for (RunnerCommand runner : result.getRunners()) {
            row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(runner.getHorse().getName());
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
            row.createCell(23).setCellValue(runner.getStars().length);
            row.createCell(24).setCellValue(runner.getFavPos());

            rowIndex++;
        }

        for (int i = 0; i < 24; i++) {
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
                new CellRangeAddress(1, lastRowNum, 19, 19)

        };

        conditionalFormattingLayer.addConditionalFormatting(resultCellRange, winnerRule);
        conditionalFormattingLayer.addConditionalFormatting(resultCellRange, placedRule);

        // Writing the file into Http response
        workbook.write(outExcel);

        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date today = new Date();
        response.setHeader("Content-disposition", "attachment; filename=" + "runner-query-result-" + dateFormat.format(today) + ".xls");

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
}
