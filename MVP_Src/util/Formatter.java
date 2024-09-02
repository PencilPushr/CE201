package util;

import java.text.DecimalFormat;

public class Formatter {

    public static String clampTrailingDecimals(double input, int n) {
        DecimalFormat formatter = new DecimalFormat();
        formatter.setMaximumFractionDigits(n);
        return formatter.format(input);
    }

}
