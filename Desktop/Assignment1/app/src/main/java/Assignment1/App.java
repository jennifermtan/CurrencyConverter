/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Assignment1;
import java.util.*;
import java.lang.*;


public class App {

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to Currency Converter! Select displayed options to continue!\nWhat is your user type?\n1: User\n2: Admin");

            Scanner scan = new Scanner(System.in);
            String user = scan.nextLine();

            if (user.equals("1")) {
                System.out.println("Select displayed options to continue!\nWhat is your user type?\n1: Convert currencies\n2: Display currency table\n3: Summary of 2 currencies");
            }
            System.out.println("What would you like to do?");
            String input = scan.nextLine();

            if(input.equals("1")) {

            }
            else if(input.equals("2")) {
                
            }
            else if(input.equals("3")) {

            }

            // File f = new File("data.txt");
            // try {
            //     scan = new Scanner(f);
            //     scan.write("test");
            //     scan.close();
    
            // } catch (FileNotFoundException e) {
            //     e.printStackTrace();
            //     return;
            // }
        }
    }

    public double getAverage(ArrayList<Double> ls) {
        double total = 0;

        for (double value : ls) {
            total += value;
        }
        return total / ls.size();
    }

    public double getMedian(ArrayList<Double> ls) {
        Collections.sort(ls);

        if (ls.size() % 2 == 1) {
            int index = (int)Math.ceil(ls.size() / 2);
            return ls.get(index);
        } else {
            int index1 = (int)Math.ceil(ls.size() / 2);
            int index2 = index1 + 1;

            return (ls.get(index1) + ls.get(index2)) / 2;
        }
    }

    public double getMaximum(ArrayList<Double> ls) {
        double largest = ls.get(0);

        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i) > largest) {
                largest = ls.get(i);
            }
        }
        return largest;
    }

    public double getMinimum(ArrayList<Double> ls) {
        double smallest = ls.get(0);

        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i) < smallest) {
                smallest = ls.get(i);
            }
        }
        return smallest;
    }

    public double getSD(ArrayList<Double> ls) {
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
