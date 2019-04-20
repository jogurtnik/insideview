package uk.co.punishell.insideview.model.services.JSHDataScrapper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.view.commands.entityCommands.JSHRunnerCommand;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JSHRunnerAssembler {

    public JSHRunnerCommand getJshRunner(Element tableRow) {
        JSHRunnerCommand runner = new JSHRunnerCommand();

        runner.setHorseName(this.getJshRunnerHorseName(tableRow));
        runner.setPrice9(this.getJshPriceOrMov(tableRow, 1));
        runner.setMov9to11(this.getJshPriceOrMov(tableRow, 2));
        runner.setPrice60(this.getJshPriceOrMov(tableRow, 3));
        runner.setMov60(this.getJshPriceOrMov(tableRow, 4));
        runner.setPrice30(this.getJshPriceOrMov(tableRow, 5));
        runner.setMov30(this.getJshPriceOrMov(tableRow, 6));
        runner.setPrice15(this.getJshPriceOrMov(tableRow, 7));
        runner.setMov15(this.getJshPriceOrMov(tableRow, 8));
        runner.setPrice5(this.getJshPriceOrMov(tableRow, 9));
        runner.setMov5(this.getJshPriceOrMov(tableRow, 10));
        runner.setPrice3(this.getJshPriceOrMov(tableRow, 11));
        runner.setMov3(this.getJshPriceOrMov(tableRow, 12));
        runner.setPrice2(this.getJshPriceOrMov(tableRow, 13));
        runner.setMov2(this.getJshPriceOrMov(tableRow, 14));
        runner.setPrice1(this.getJshPriceOrMov(tableRow, 15));
        runner.setMov1(this.getJshPriceOrMov(tableRow, 16));
        runner.setMean(this.getJshPriceOrMov(tableRow, 17));
        runner.setMov3to1(this.getJshPriceOrMov(tableRow, 18));
        runner.setResult(this.getJshResult(tableRow));
        runner.setCpr(this.getJshCpr(tableRow));
        runner.setNptips(this.getJshNptips(tableRow));
        runner.setNaps(this.getJshNaps(tableRow));
        runner.setStars(this.getJshStars(tableRow));
        runner.setJockey(this.getJshJockey(tableRow));
        runner.setTrainer(this.getJshTrainer(tableRow));
        runner.setHeadGear(this.getJshHeadGear(tableRow));

        // get movement between 9am and 60min prices in percent
        if ((Double.compare(runner.getPrice9(), 0)) != 0) {
            double mov9to60Double = ((runner.getPrice9() - runner.getPrice60())/runner.getPrice9()) * 100;
            int mov9to60Integer = (int) Math.round(mov9to60Double);
            runner.setMov9to60(mov9to60Integer);
        } else {
            runner.setMov9to60(0);
        }

        return runner;
    }

    private String getJshRunnerHorseName(Element row) {
        String horseName;
        String firstCellText = row.select("td").first().text();
        int horseNameEndIndex = firstCellText.indexOf("Horse ID:");
        horseName = firstCellText.substring(0, horseNameEndIndex);

        return horseName;
    }

    private double getJshPriceOrMov(Element row, int index) {
        Element div = row.select("td").get(index).select("div").first();
        double value;

        if (div != null) {
            value = Double.parseDouble(div.text());
        } else {
            value = Double.parseDouble(row.select("td").get(index).text());
        }

        return value;
    }

    private String getJshResult(Element row) {
        return row.getElementsByClass("result").get(0).text();
    }

    private int getJshCpr(Element row) {

        Element ratingElement = row.getElementsByTag("td").get(20);

        if (ratingElement != null) {
            return Integer.parseInt(ratingElement.text());
        } else {
            return 0;
        }
    }

    private int getJshNptips(Element row) {
        return Integer.parseInt(row.getElementsByTag("td").get(21).text());
    }

    private int getJshNaps(Element row) {
        return Integer.parseInt(row.getElementsByTag("td").get(22).text());
    }

    private int getJshStars(Element row) {
        String starsString;
        String srcPrefix = "/images/";
        String srcSuffix = "t.gif";
        Element starsCell = row.select(".star").first();
        if (starsCell != null) {
            String cellImgSrcValue = starsCell.attr("src");
            starsString = this.extractString(cellImgSrcValue, srcPrefix, srcSuffix);

            return Integer.parseInt(starsString);

        } else {
            return 0;
        }
    }

    private String getJshJockey(Element row) {
        String firstCellText = row.select("td").first().text();
        String jockeyBeginIndexStringMatcher = "Jockey: ";
        String jockeyEndIndexStringMatcher = " Claiming";

        return this.extractString(firstCellText, jockeyBeginIndexStringMatcher, jockeyEndIndexStringMatcher);
    }

    private String getJshTrainer(Element row) {
        String firstCellText = row.select("td").first().text();
        String trainerBeginIndexStringMatcher = "Trainer: ";
        String trainerEndIndexStringMatcher = " Owner";

        return this.extractString(firstCellText, trainerBeginIndexStringMatcher, trainerEndIndexStringMatcher);
    }

    private String getJshHeadGear(Element row) {
        String firstCellText = row.select("td").first().text();
        String headGearBeginIndexStringMatcher = "Wearing: ";
        String headGearEndIndexStringMatcher = " Colours";
        String headGearFull = this.extractString(firstCellText, headGearBeginIndexStringMatcher, headGearEndIndexStringMatcher);
        String[] headGearArray = headGearFull.split(" and ");

        Map<String, String> headGearShortsMap = new HashMap<>();
        headGearShortsMap.put("cheekpieces", "CkPc");
        headGearShortsMap.put("hood", "Hood");
        headGearShortsMap.put("visor", "Vsor");
        headGearShortsMap.put("blinkers", "Blnk");
        headGearShortsMap.put("tongue strap", "TT");
        headGearShortsMap.put("eyeshields", "Eye");

        StringBuilder sb = new StringBuilder();

        if (!headGearFull.equalsIgnoreCase("")) {
            for (String gear : headGearArray) {
                sb.append(headGearShortsMap.get(gear) + " ");
            }
            return sb.toString();
        } else return "";
    }

    // extract string value from between passed matchers,
    // starting at the end of beginMatcher and ending at the start of endMatcher
    private String extractString(String stringData, String beginMatcher, String endMatcher) {
        int beginIndex = stringData.indexOf(beginMatcher) + beginMatcher.length();
        int endIndex = stringData.indexOf(endMatcher);

        if (endIndex < beginIndex) {
            return "";
        } else {
            return stringData.substring(beginIndex, endIndex);
        }
    }

}
