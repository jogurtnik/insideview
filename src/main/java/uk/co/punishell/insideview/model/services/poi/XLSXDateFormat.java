package uk.co.punishell.insideview.model.services.poi;

import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Service
public class XLSXDateFormat {

    private DateFormat dateFormat;

    public XLSXDateFormat() {
        this.dateFormat = new SimpleDateFormat("yyyyMMdd");
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }
}
