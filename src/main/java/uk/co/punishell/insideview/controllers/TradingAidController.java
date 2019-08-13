package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.co.punishell.insideview.model.exceptions.VendorsException;
import uk.co.punishell.insideview.model.services.JSHDataScrapper.JSHRaceAssembler;
import uk.co.punishell.insideview.model.services.JSHDataScrapper.JSHTradingAidAssembler;
import uk.co.punishell.insideview.model.services.JSHDataScrapper.WebpageScrapper;
import uk.co.punishell.insideview.view.commands.entityCommands.JSHRaceCommand;
import uk.co.punishell.insideview.view.commands.guiCommands.TradingAidCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    public String getTradingAid(Model model) throws IOException {

        // define login form input elements
        Map<String, String> data = new HashMap<>();
        data.put("op", "login");
        data.put("usr_login", "gavinb");
        data.put("usr_password", "passw0rd");

        Document doc;
        try {
            doc = scrapper.loginAndGetWebpage(JSHLoginUrl, data, JSHDataTargetUrl);
        } catch (VendorsException e) {
            throw new VendorsException("Unfortunately the application wasn't able to retrieve data from the vendors.");
        }


        Elements raceInfo = doc.getElementsByClass("race_infoback");
        Elements raceBody = doc.getElementsByClass("racebody");

        log.debug("Loaded Races: " + raceInfo.size());

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

        model.addAttribute("jshData", tradingAidCommand);

        return "tradingAid";
    }

    // TODO add custom exception
    @RequestMapping("exportToExcel")
    public void exportFundamentalsToExcel(@ModelAttribute("jshData") TradingAidCommand jshData,
                                            HttpServletRequest request, HttpServletResponse response) throws IOException {

        // create a new file
        FileOutputStream outExcel = new FileOutputStream("workbook.xls");
        // create a new workbook
        Workbook workbook = new HSSFWorkbook();
        // create a new sheet
        Sheet sheet = workbook.createSheet();

        sheet.setDefaultColumnWidth(7);
        /*sheet.setColumnWidth(0, 20);
        sheet.setColumnWidth(14, 20);
        sheet.setColumnWidth(21, 20);*/

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setBold(true);
        style.setFont(font);

        // TODO set the cells conditional color formatting

        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Horse");
        header.createCell(1).setCellValue("9am");
        header.createCell(2).setCellValue("MovAM");
        header.createCell(3).setCellValue("60min");
        header.createCell(4).setCellValue("Mov60");
        header.createCell(5).setCellValue("1min");
        header.createCell(6).setCellValue("Mov1");
        header.createCell(7).setCellValue("Mean");
        header.createCell(8).setCellValue("321");
        header.createCell(9).setCellValue("Result");
        header.createCell(10).setCellValue("CPR");
        header.createCell(11).setCellValue("NPTips");
        header.createCell(12).setCellValue("Naps");
        header.createCell(13).setCellValue("Stars");
        header.createCell(14).setCellValue("Jockey");
        header.createCell(15).setCellValue("Wins");
        header.createCell(16).setCellValue("R");
        header.createCell(17).setCellValue("Rs");
        header.createCell(18).setCellValue("Mov9-60");
        header.createCell(19).setCellValue("FP");
        header.createCell(20).setCellValue("HG");
        header.createCell(21).setCellValue("Trainer");
        header.createCell(22).setCellValue("Wins");
        header.createCell(23).setCellValue("R");
        header.createCell(24).setCellValue("Rs");

        // TODO Write the data

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(14);
        sheet.autoSizeColumn(21);

        header.setRowStyle(style);

        workbook.write(outExcel);

        response.setContentType("application/octet-stream");

        FileInputStream inputStream = new FileInputStream(new File("workbook.xls"));

        OutputStream outputStream = response.getOutputStream();

        byte[] buffer = new byte[1024];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {  // write bytes read from the input stream into the output stream
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.flush();

    }
}
