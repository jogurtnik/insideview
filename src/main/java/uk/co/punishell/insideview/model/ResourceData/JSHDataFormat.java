package uk.co.punishell.insideview.model.ResourceData;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


@Service
@Primary
public class JSHDataFormat implements DataFormat {

    private static final String[] XLSX_DATA_COLUMNS_HEADERS = {"County", "Meeting", "Distance", "Time", "Horse", "Status",
                                                              "9am", "10am", "11am", "Movement 9-11", "Price at 60 min",
                                                              "Movement 60", "Price at 30 min", "Movement 30", "Price at 15 min",
                                                              "Movement 15", "Price at 5 min", "Movement 5", "Price at 3 min",
                                                              "Movement 3", "Price at 2 min", "Movement 2", "Price at 1 min",
                                                              "Movement 1", "Mean", "321", "Result", "Cherry Pick Rating",
                                                              "Newspapaer Tips", "Star", "Naps"};

    private String[] customDataColumnsHeaders;

    private static final int XLSX_SHEET_NUMBER = 0;

    @Override
    public void setCustomDataColumnsHeaders(String[] customDataColumnsHeaders) {
        this.customDataColumnsHeaders = customDataColumnsHeaders;
    }

    @Override
    public String[] getCustomDataColumnsHeaders() {
        return customDataColumnsHeaders;
    }

    @Override
    public String[] getDefaultDataColumnsHeaders() {
        return XLSX_DATA_COLUMNS_HEADERS;
    }

    public int getDefaultSheetNumber() {
        return XLSX_SHEET_NUMBER;
    }
}
