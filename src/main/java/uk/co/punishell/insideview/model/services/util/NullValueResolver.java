package uk.co.punishell.insideview.model.services.util;

import org.springframework.stereotype.Service;

@Service
public class NullValueResolver {

    private double defaultDoubleValue;
    private String defaultStringValue;

    public NullValueResolver() {
        this.defaultDoubleValue = 0.00;
        this.defaultStringValue = "";
    }

    public double getDefaultValueIfNull(double value) {

        if (((Double) value).isNaN()) {
            return defaultDoubleValue;
        } else {
            return value;
        }
    }

    public String getDefaultValueIfNull(String value) {

        if (value.isEmpty()) {
            return defaultStringValue;
        } else {
            return value;
        }
    }
}
