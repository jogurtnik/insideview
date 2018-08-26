package uk.co.punishell.insideview.model.ResourceData;

public interface DataFormat {

    void setCustomDataColumsHeaders(String[] dataFormat);

    String [] getCustomDataColumsHeaders();

    String[] getDefaultDataColumnsHeaders();

    int getDefaultSheetNumber();
}
