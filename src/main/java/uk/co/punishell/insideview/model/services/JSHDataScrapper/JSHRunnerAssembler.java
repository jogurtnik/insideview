package uk.co.punishell.insideview.model.services.JSHDataScrapper;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.view.commands.entityCommands.JSHRunnerCommand;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JSHRunnerAssembler {

    public JSHRunnerCommand getJshRunner(Element tableRow) {

        final Element row = tableRow;

        JSHRunnerCommand runner = new JSHRunnerCommand();

        runner.setHorseName(this.getJshRunnerHorseName(row));
        runner.setPrice9(this.getJshPriceOrMov(row, 1));
        runner.setMov9to11(this.getJshPriceOrMov(row, 2));
        runner.setPrice60(this.getJshPriceOrMov(row, 3));
        runner.setMov60(this.getJshPriceOrMov(row, 4));
        runner.setPrice30(this.getJshPriceOrMov(row, 5));
        runner.setMov30(this.getJshPriceOrMov(row, 6));
        runner.setPrice15(this.getJshPriceOrMov(row, 7));
        runner.setMov15(this.getJshPriceOrMov(row, 8));
        runner.setPrice5(this.getJshPriceOrMov(row, 9));
        runner.setMov5(this.getJshPriceOrMov(row, 10));
        runner.setPrice3(this.getJshPriceOrMov(row, 11));
        runner.setMov3(this.getJshPriceOrMov(row, 12));
        runner.setPrice2(this.getJshPriceOrMov(row, 13));
        runner.setMov2(this.getJshPriceOrMov(row, 14));
        runner.setPrice1(this.getJshPriceOrMov(row, 15));
        runner.setMov1(this.getJshPriceOrMov(row, 16));
        runner.setMean(this.getJshPriceOrMov(row, 17));
        runner.setMov3to1(this.getJshPriceOrMov(row, 18));
        runner.setResult(this.getJshResult(row));
        runner.setCpr(this.getJshCpr(row));
        runner.setNptips(this.getJshNptips(row));
        runner.setNaps(this.getJshNaps(row));
        runner.setStars(this.getJshStars(row));
        runner.setJockey(this.getJshJockey(row));
        runner.setTrainer(this.getJshTrainer(row));
        runner.setHeadGear(this.getJshHeadGear(row));

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
        if (row.select("td").size() == 24) {
        Element div = row.select("td").get(index).select("div").first();


            double value;

            if (div != null) {
                value = Double.parseDouble(div.text());
            } else {
                value = Double.parseDouble(row.select("td").get(index).text());
            }

            return value;
        } else return 0;

    }

    private String getJshResult(Element row) {

        Elements result = row.getElementsByClass("result");

        if (result.size() > 0) {
            return result.get(0).text();
        } else return "";
        /*return row.getElementsByClass("result").get(0).text();*/
    }

    private int getJshCpr(Element row) {

        Elements ratingElement = row.getElementsByTag("td");

        if (ratingElement != null && ratingElement.size() >= 20) {
            return Integer.parseInt(ratingElement.get(20).text());
        } else {
            return 0;
        }
    }

    private int getJshNptips(Element row) {
        Elements tds = row.getElementsByTag("td");
        if (tds.size() >= 21) {
            return Integer.parseInt(tds.get(21).text());
        } else return 0;

    }

    private int getJshNaps(Element row) {
        Elements tds = row.getElementsByTag("td");

        if (tds.size() >= 22) {
            return Integer.parseInt(tds.get(22).text());
        } else return 0;

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
