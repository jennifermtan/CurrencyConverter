package Assignment1;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.*;
import java.lang.*;


public class App {
    static Scanner scan = new Scanner(System.in);
    // A list of all the currencies in the program
    // Each currency is saves as a LinkedHashMap to maintain order of exchange rates
    private static List<LinkedHashMap<String, Double>> currencies = new ArrayList<>();
    // A list of the popular currencies. Can be updated by admin
    private static List<String> popCurrencies = new ArrayList<>(){
        {
            add("USD");
            add("AUD");
            add("SGD");
            add("EUR");
        }
    };
    public static void main(String[] args) {
        App.readCurrencyFiles();
        // Starting of the application
        System.out.println("Welcome to Currency Converter! Select displayed options to continue!\nWhat is your user type?\n1: User\n2: Admin");
        String user = scan.nextLine();

        selectUserOption(user);

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

    public static void selectUserOption(String userType) {
        if(userType.equals("1")) {
            // run code for user
            System.out.println("User, what would you like to do?\n1: Convert currencies\n2: Display currency table\n3: Summary of 2 currencies");
            String input = scan.nextLine();
            switch (input) {
                case "1":
                    // Convert currencies
                    break;
                case "2":
                    displayCurrencyTable();
                    break;
                case "3":
                    // Summary of two currencies
                    break;
            }
        }
        else if(userType.equals("2")) {
            // run code for admin
            System.out.println("Administrator, what would you like to do?\n1: Convert currencies\n2: Display currency table\n" +
                    "3: Summary of 2 currencies\n4: ");
            String input = scan.nextLine();
            switch (input) {
                case "1":
                    // Convert currencies
                    break;
                case "2":
                    adminDisplayTable();
                    break;
                case "3":
                    // Summary of two currencies
                    break;
            }
        }
    }

    public static void printOptions() {
        ;
    }

    public static void adminDisplayTable(){
        System.out.println("We would like to display the table with the most popular currencies.");
        String answer = "";
        // allow the admin to keep switching out currencies if they want
        do{
            System.out.println("These are the popular currencies we have saved on file: ");
            for (String curr: popCurrencies){
                System.out.println(curr);
            }
            System.out.println("Would you like to modify these? (Y/N)");
            answer = scan.nextLine();
            if (answer.equals("Y")){
                String toRemove = getString(popCurrencies, "Which currency would you like to remove?");
                popCurrencies.remove(toRemove);

                // The only currencies the admin can add are ones that aren't already popular currencies
                // and haven't just been chosen for removal
                ArrayList<String> allCurrencyNames = getAllCurrencies();
                ArrayList<String> possibleCurrencyNames = new ArrayList<String>();

                // copy allCurrencyNames into possibleCurrencyNames
                for (String elem: allCurrencyNames){
                    possibleCurrencyNames.add(elem);
                }

                for (String name: allCurrencyNames){
                    if (name.equals(toRemove)){
                        possibleCurrencyNames.remove(name);
                    }
                    for (String curr: popCurrencies){
                        if (curr.equals(name)){
                            possibleCurrencyNames.remove(name);
                        }
                    }
                }
                String toReplace = getString(possibleCurrencyNames, "Which currency would you like to replace it with?");
                popCurrencies.add(toReplace);
            }
        } while(answer.equals("Y"));

        displayCurrencyTable();
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
        for (LinkedHashMap<String, Double> currency: mostPop){
            System.out.println(String.valueOf(currency.entrySet().toArray()[currency.size() -1]));
        }
        // PROBLEM: CAN'T FIND WHICH CURRENCY BELONGS TO WHICH WITH THIS METHOD


        // TODO print the table

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

    // Method for getting a response from a user which loops when given an incorrect response
    static String getString(List<String> acceptableResponses, String prompt) {
        Scanner s = new Scanner(System.in);

        String response;
        do {
            System.out.println(prompt);
            response = s.nextLine();

            if (!acceptableResponses.contains(response)) {
                System.out.println("We apologise. That response is not computable. Please try again.");
            }
        } while (!acceptableResponses.contains(response));

        return response;
    }


}
