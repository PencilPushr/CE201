package util;
import java.util.Arrays;

public class Statistics {
    public static double mean(double[] data) {
        return Arrays.stream(data).sum() / data.length;
    }

    public static double variance(double[] data){
        double mean = mean(data);
        return Arrays.stream(data)
                .map(x -> Math.pow(x - mean, 2))
                .sum() / (data.length - 1);
    }

    public static double standardDeviation(double[] data) {
        return Math.sqrt(variance(data));
    }

    //probability density
    public static double pdf(double x) {
        return Math.exp(-x*x / 2) / Math.sqrt(2 * Math.PI);
    }

    //pdf with mean (mu) and stddev (sigma)
    public static double pdf(double x, double mu, double sigma) {
        return pdf((x - mu) / sigma) / sigma;
    }
}
