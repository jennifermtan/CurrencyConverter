package Assignment1;
import java.util.*;

public class Operations{
    //Returns the average rate from a history list of a specific currency
    public static double getAverage(ArrayList<Double> ls) {
        double total = 0;

        for (double value : ls) {
            total += value;
        }
        return total / ls.size();
    }

    public static double getMedian(ArrayList<Double> ls) {
        Collections.sort(ls);

        if (ls.size() % 2 == 1) {
            int index = (int) Math.ceil(ls.size() / 2);
            return ls.get(index);
        } else {
            int index1 = (int) (ls.size() / 2) - 1;
            int index2 = index1 + 1;

            return (ls.get(index1) + ls.get(index2)) / 2;
        }
    }

    public static double getMaximum(ArrayList<Double> ls) {
        double largest = ls.get(0);

        for (Double l : ls) {
            if (l > largest) {
                largest = l;
            }
        }
        return largest;
    }

    public static double getMinimum(ArrayList<Double> ls) {
        double smallest = ls.get(0);

        for (Double l : ls) {
            if (l < smallest) {
                smallest = l;
            }
        }
        return smallest;
    }

    public static double getSD(ArrayList<Double> ls) {
        double total = 0;
        double standardDeviation = 0;

        for (double value : ls) {
            total += value;
        }

        double mean = getAverage(ls);

        for (double value : ls) {
            standardDeviation += Math.pow(value - mean, 2);
        }

        return Math.sqrt(standardDeviation / ls.size());
    }
}