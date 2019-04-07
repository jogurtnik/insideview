package uk.co.punishell.insideview.model.services.JSHDataScrapper;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.view.commands.entityCommands.JSHRunnerCommand;


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

        if ((Double.compare(runner.getPrice9(), 0)) != 0) {
            double mov9to60 = (runner.getPrice9() - runner.getPrice60())/runner.getPrice60();
            runner.setMov9to60(mov9to60);
        } else {
            runner.setMov9to60(0.00);
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

        Elements ratingElements = row.getElementsByClass("rating");

        if (!ratingElements.isEmpty()) {
            return Integer.parseInt(ratingElements.get(0).text());
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

    private boolean[] getJshStars(Element row) {
        boolean[] stars = {false, false, false, false, false};
        String starsString;
        String srcPrefix = "./JustStartHere_files/";
        String srcSuffix = "t.gif";
        Element starsCell = row.select(".star").first();
        if (starsCell != null) {
            String cellImgSrcValue = starsCell.attr("src");
            starsString = this.extractString(cellImgSrcValue, srcPrefix, srcSuffix);

            for (int i = 0; i < Integer.parseInt(starsString); i++) {
                stars[i] = true;
            }

            return stars;

        } else {
            return stars;
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

        return this.extractString(firstCellText, headGearBeginIndexStringMatcher, headGearEndIndexStringMatcher);
    }

    // extract string value from between passed matchers,
    // starting at the end of beginMatcher and ending at the start of endMatcher
    private String extractString(String stringData,String beginMatcher, String endMatcher) {
        int beginIndex = stringData.indexOf(beginMatcher) + beginMatcher.length() - 1;
        int endIndex = stringData.indexOf(endMatcher);
        return stringData.substring(beginIndex, endIndex);
    }
}
