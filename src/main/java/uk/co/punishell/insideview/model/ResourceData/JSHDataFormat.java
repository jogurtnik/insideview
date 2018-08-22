package uk.co.punishell.insideview.model.ResourceData;

import org.springframework.stereotype.Service;

@Service
public class JSHDataFormat extends DataFormat {

    private String[] columsSequence;

    public JSHDataFormat() {
        this.columsSequence = new String[]{"Country", "Meeting", "Distance", "Time", "Horse", "Status", "9am", "10am",
                                            "11am", "Movement 9-11", "Price at 60 min", "Movement 60", "Price at 30 min",
                                            "Movement 30", "Price at 15 min", "Movement 15", "Price at 5 min", "Movement 5",
                                            "Price at 3 min", "Movement 3", "Price at 2 min", "Movement 2", "Price at 1 min",
                                            "Movement 1", "Mean", "1", "Result", "Cherry Pick Rating", "Newspaper Tips",
                                            "Star", "Naps"};
    }

    public String[] getColumsSequence() {
        return columsSequence;
    }

    public void setColumsSequence(String[] columsSequence) {
        this.columsSequence = columsSequence;
    }
}
