package uk.co.punishell.insideview.model.ResourceData;

public interface DataFormat {

    void setCustomDataColumnsHeaders(String[] dataFormat);

    String [] getCustomDataColumnsHeaders();

    String[] getDefaultDataColumnsHeaders();

    int getDefaultSheetNumber();
}
