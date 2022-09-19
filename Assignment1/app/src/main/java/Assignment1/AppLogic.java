package Assignment1;
import java.io.*;
import java.sql.SQLOutput;
import java.util.*;
import java.lang.*;
import java.math.BigDecimal;
import java.math.MathContext;

public class AppLogic{
    private static Scanner scan = new Scanner(System.in);
    // A list of all the currencies in the program
    // Each currency is saved as a LinkedHashMap to maintain order of exchange rates
    private static List<LinkedHashMap<String, Double>> currencies = new ArrayList<LinkedHashMap<String, Double>>();
    // A list of the popular currencies. Can be updated by admin
    private static List<String> popCurrencies = new ArrayList<String>(){
        {
            add("USD");
            add("AUD");
            add("SGD");
            add("EUR");
        }
    };

    // Initial choices for admin and users
    public static void selectUserOption(String userType, Scanner scan) {
        if (userType.equals("1")) {
            // run code for user
            String input = UserInterface.getString(Arrays.asList("1", "2", "3"), "User, what would you like to do?\n1: Convert currencies\n2: Display currency table\n3: Summary of 2 currencies", scan);

            switch (input) {
                case "1":
                    convertCurrencies(scan);
                    break;
                case "2":
                    AppLogic.displayCurrencyTable();
                    break;
                case "3":
                    AppLogic.summaryOf2Currencies(scan);
                    break;
            }
        } else if (userType.equals("2")) {
            // run code for admin
            String input = UserInterface.getString(Arrays.asList("1", "2", "3", "4"), "Administrator, what would you like to do?\n1: Convert currencies\n2: Display currency table\n" +
                    "3: Summary of 2 currencies\n4: ", scan);

            switch (input) {
                case "1":
                    convertCurrencies(scan);
                    break;
                case "2":
                    UserInterface.adminDisplayTable(scan, popCurrencies, currencies);
                    break;
                case "3":
                    AppLogic.summaryOf2Currencies(scan);
                    break;
            }
        }
    }

    // Repeatable method, always run at beginning of program, which reads into the currency folder
    // and adds those files into the currency list attribute.
    public static void readCurrencyFiles(){
        try {
            Scanner currencyFiles = new Scanner(new File("./src/main/java/Assignment1/currencies/currencyFiles.txt"));
            while(currencyFiles.hasNextLine()) {
                String line = currencyFiles.nextLine();
                currencies.add(new LinkedHashMap<String, Double>());
            }
            currencyFiles.close();

            // Loading data into the hashmaps
            currencyFiles = new Scanner(new File("./src/main/java/Assignment1/currencies/currencyFiles.txt"));
            for (HashMap<String, Double> map : currencies) {
                String line = currencyFiles.nextLine();
                Scanner temp = new Scanner(new File(line));
                while (temp.hasNextLine()) {
                    String values = temp.nextLine();
                    String[] arr = values.split(", ");
                    map.put(arr[0], Double.parseDouble(arr[1]));
                }
                temp.close();
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found exception.");
        }
    }
    public static void convertCurrencies(Scanner scan) {
        System.out.println("Please input the amount.");
        double amount = Double.parseDouble(scan.nextLine());
        String currency1 = UserInterface.getString(getAllCurrencies(), "Please choose the current currency symbol.", scan);
        String currency2 = UserInterface.getString(getAllCurrencies(), "Please choose the currency symbol you want to convert it to.", scan);
        //Find the exchange rate in the hashmap
        double currentRate = 0;
        double convertedRate = 0;
        LinkedHashMap<String, Double> currentCurrency = getCurrency(currency1);
        LinkedHashMap<String, Double> convertedCurrency = getCurrency(currency2);

        for (String key : currentCurrency.keySet()) {
            if (key.equals(currency1)) {
                String currentKey = (String) currentCurrency.keySet().toArray()[currentCurrency.size() - 1]; //Put the keys in an array to get the last element
                currentRate = currentCurrency.get(currentKey);

            }
        }
        for (String key : convertedCurrency.keySet()) {
            if (key.equals(currency2)) {
                String currentKey = (String) convertedCurrency.keySet().toArray()[convertedCurrency.size() - 1];
                convertedRate = convertedCurrency.get(currentKey);
            }
        }
        double rate = calcExchangeRate(convertedRate, currentRate);
        double convertedAmount = amount * rate;
        System.out.printf("The converted currency is %s %g", currency2, convertedAmount); //Format to 6 significant figures
        System.out.println();
    }
    // Returns linked hashmap of a specified currency
    public static LinkedHashMap<String, Double> getCurrency(String symbol) {
        for (LinkedHashMap<String, Double> currency : currencies) {
            for (String key : currency.keySet()) {
                if (key.equals(symbol)) {
                    return currency;
                }
            }
        }
        return null;
    }

    public static void displayCurrencyTable(){
        // Find the most popular currency hashmaps according to the popular currencies set by admin
        List<LinkedHashMap<String, Double>> mostPop = new ArrayList<>();
        for (LinkedHashMap<String, Double> currency: currencies){
            for (String desiredCurrency: popCurrencies) {
                if ((currency.keySet().contains(desiredCurrency))) {
                    mostPop.add(currency);
                }
            }
        }

        // Find the latest exchange rate of those currencies
        HashMap<String, Double> latestRates = new HashMap<>();
        // AND the 2nd last rate
        HashMap<String, Double> prevRates = new HashMap<>();
        for (LinkedHashMap<String, Double> currency: mostPop){
            // get the latest exchange rate
            String lastLine = String.valueOf(currency.entrySet().toArray()[currency.size() -1]);
            Double exRate = Double.parseDouble(lastLine.split("=")[1]);

            String secondLastLine = String.valueOf(currency.entrySet().toArray()[currency.size() -2]);
            Double prevRate = Double.parseDouble(secondLastLine.split("=")[1]);
            String currName = "";
            // get its name
            for (String name: currency.keySet()){
                currName = name;
                break;
            }
            latestRates.put(currName, exRate);
            prevRates.put(currName, prevRate);


        }

        // Print the first line of the table
        String entireLine = "___________________________________________________________________________";
        System.out.println(entireLine);
        System.out.print("| From/to |");
        for (String currName: latestRates.keySet()){
            System.out.print("      " + currName + "      |");
        }
        System.out.println("\n" + entireLine);
        // Print the comparisons
        for (HashMap.Entry<String,Double> currency: latestRates.entrySet()){
            System.out.print("|   " + currency.getKey() + "   |");
            for (HashMap.Entry<String,Double> otherCurrency: latestRates.entrySet()){
                double epsilon = 0.000001d;
                // If we're dealing with the same currency, then we just want to print a dash
                if (Math.abs(currency.getValue() - otherCurrency.getValue()) < epsilon){
                    System.out.print("       -       |");
                    continue;
                }

                // calculate the current exchange rate and the previous exchange rate so you can compare them
                double exRate = calcExchangeRate(currency.getValue(), otherCurrency.getValue());
                double prevExRate = calcExchangeRate(prevRates.get(currency.getKey()), prevRates.get(otherCurrency.getKey()));

                // round to 6s.f
                BigDecimal bd = new BigDecimal(exRate);
                bd = bd.round(new MathContext(6));
                double rounded = bd.doubleValue();
                String ex = Double.toString(rounded);

                // Change the number's length so that table format is preserved
                if (ex.length() > 7){
                    while (ex.length() > 7){
                        ex = ex.substring(0, ex.length() - 1);
                    }
                }

                if (ex.length() < 7){
                    while (ex.length() < 7){
                        ex += "0";
                    }
                }

                if (prevExRate > exRate){
                    System.out.print("  " + ex + " (D)  |");
                }
                if (prevExRate < exRate){
                    System.out.print("  " + ex + " (I)  |");
                }

            }
            System.out.println("\n"+ entireLine);
        }


    }

    // Displays the summary 2 currencies chosen by input
    public static void summaryOf2Currencies(Scanner scan) {
        System.out.println("Please choose 2 currencies so we can compare them against each other.");
        System.out.println("Here is a list of saved currencies.");
        List<String> acceptableCurrencies = new ArrayList<String>();
        List<String> acceptableDates = new ArrayList<String>();
        // Saving the acceptable currencies and dates for the user to input
        for (LinkedHashMap<String, Double> currency : currencies) {
            for (String text : currency.keySet()) {
                if (text.indexOf('/') == -1) {
                    acceptableCurrencies.add(text);
                    System.out.println(text);
                }
                else {
                    if (!acceptableDates.contains(text)) {
                        acceptableDates.add(text);
                    }
                }
            }
        }
        // Asking the user for the currencies (and indexing them) and dates
        String firstCurrency = UserInterface.getString(acceptableCurrencies, "Please input the first currency in the 3-lettered format as shown above.", scan);
        int firstCurrencyIndex = acceptableCurrencies.indexOf(firstCurrency);
        acceptableCurrencies.remove(String.valueOf(firstCurrency));
        String secondCurrency = UserInterface.getString(acceptableCurrencies, "Please input the second currency.", scan);
        acceptableCurrencies.add(firstCurrencyIndex, firstCurrency);
        int secondCurrencyIndex = acceptableCurrencies.indexOf(secondCurrency);
        System.out.println("Here is a list of saved dates for each currency's conversation rates.");
        for (String date : acceptableDates) {
            System.out.println(date);
        }
        String startDate = UserInterface.getString(acceptableDates, "Please input the start date in the format DD/MM/YY as shown above.", scan);
        while (acceptableDates.contains(startDate)) {
            acceptableDates.remove(0);
        }
        String endDate = UserInterface.getString(acceptableDates, "Please input the end date.", scan);
        // Saving the dates and intial rates of the specified currencies
        LinkedHashMap<String, Double> currency;
        ArrayList<String> specifiedDates = new ArrayList<String>();
        ArrayList<Double> firstRates = new ArrayList<Double>();
        ArrayList<Double> secondRates = new ArrayList<Double>();
        boolean selectDates = false;
        for (int i = 0; i < currencies.size(); i++) {
            if ((i == firstCurrencyIndex) || (i == secondCurrencyIndex)) {
                currency = currencies.get(i);
                selectDates = false;
                for (String date : currency.keySet()) {
                    if (date.equals(startDate)) {
                        selectDates = true;
                    }
                    else if (date.equals(endDate)) {
                        selectDates = false;
                        specifiedDates.add(date);
                        if (i == firstCurrencyIndex) {
                            firstRates.add(currency.get(date));
                        }
                        else if (i == secondCurrencyIndex) {
                            secondRates.add(currency.get(date));
                        }
                        break;
                    }
                    if (selectDates) {
                        specifiedDates.add(date);
                        if (i == firstCurrencyIndex) {
                            firstRates.add(currency.get(date));
                        }
                        else if (i == secondCurrencyIndex) {
                            secondRates.add(currency.get(date));
                        }
                    }
                }
            }
        }
        // Displaying the summary for the currencies (conversion to one another)
        ArrayList<Double> firstToSecondRates = new ArrayList<Double>();
        ArrayList<Double> secondToFirstRates = new ArrayList<Double>();
        System.out.println("Here is a summary of the 2 currencies.\n");
        System.out.println(firstCurrency + " to " + secondCurrency + " Conversion Rates\n");
        for (int i = 0; i < firstRates.size(); i++) {
            firstToSecondRates.add(calcExchangeRate(firstRates.get(i), secondRates.get(i)));
            secondToFirstRates.add(calcExchangeRate(secondRates.get(i), firstRates.get(i)));
            System.out.printf(specifiedDates.get(i) + ": %g\n", firstToSecondRates.get(i));
        }
        System.out.printf("\nAverage: %g", Operations.getAverage(firstToSecondRates));
        System.out.printf("\nMedian: %g", Operations.getMedian(firstToSecondRates));
        System.out.printf("\nMaximum: %g", Operations.getMaximum(firstToSecondRates));
        System.out.printf("\nMinimum: %g", Operations.getMinimum(firstToSecondRates));
        System.out.printf("\nStandard Deviation: %g\n\n", Operations.getSD(firstToSecondRates));
        System.out.println(secondCurrency + " to " + firstCurrency + " Conversion Rates\n");
        for (int i = 0; i < secondToFirstRates.size(); i++) {
            System.out.printf(specifiedDates.get(i) + ": %g\n", secondToFirstRates.get(i));
        }
        System.out.printf("\nAverage: %g", Operations.getAverage(secondToFirstRates));
        System.out.printf("\nMedian: %g", Operations.getMedian(secondToFirstRates));
        System.out.printf("\nMaximum: %g", Operations.getMaximum(secondToFirstRates));
        System.out.printf("\nMinimum: %g", Operations.getMinimum(secondToFirstRates));
        System.out.printf("\nStandard Deviation: %g\n\n", Operations.getSD(secondToFirstRates));
    }

    // Returns the Names of all our currencies
    public static ArrayList<String> getAllCurrencies(){
        ArrayList<String> allCurrencyNames = new ArrayList<String>();
        for (LinkedHashMap<String, Double> currency: currencies){
            // Loop once in the currency to retrieve the first key of the currency: its name
            for (String name: currency.keySet()){
                allCurrencyNames.add(name);
                break;
            }

        }
        return allCurrencyNames;
    }

    // Method for getting the exchange rate between 2 currencies
    public static double calcExchangeRate(double toUSDCurrencyA, double toUsdCurrencyB){
        return toUSDCurrencyA / toUsdCurrencyB;
    }

    public static List<LinkedHashMap<String, Double>> getCurrenciesHashMaps(){
        return currencies;
    }

    public static List<String> getPopCurrencies(){ return popCurrencies;}

    public static void addPopCurrency(String currName){popCurrencies.add(currName);}

    public static void removePopCurrency(String currName) {popCurrencies.remove(currName);}

}
