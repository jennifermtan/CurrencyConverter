package Assignment1;

import java.sql.SQLOutput;
import java.util.*;
import java.lang.*;
import java.io.*;

public class UserInterface{

    // Method for getting a response from a user which loops when given an incorrect response
    public static String getString(List<String> acceptableResponses, String prompt, Scanner scan) {

        String response;
        do {
            System.out.println(prompt);
            response = scan.nextLine();

            if (!acceptableResponses.contains(response)) {
                System.out.println("We apologise. That response is not computable. Please try again.");
            }

        } while (!acceptableResponses.contains(response));

        return response;
    }

    public static void adminDisplayTable(Scanner scan, List<String> popCurrencies, List<LinkedHashMap<String, Double>> currencies){
        System.out.println("We would like to display the table with the most popular currencies.");
        String answer = "";
        // allow the admin to keep switching out currencies if they want
        do{
            System.out.println("These are the popular currencies we have saved on file: ");
            for (String curr: popCurrencies){
                System.out.println(curr);
            }

            answer = getString(Arrays.asList("Y", "y", "N", "n"), "Would you like to modify these popular currencies? (Y/N)", scan);

            if (answer.equals("Y") || answer.equals("y")){
                String toRemove = getString(popCurrencies, "Which currency would you like to remove?", scan);
                popCurrencies.remove(toRemove);

                // The only currencies the admin can add are ones that aren't already popular currencies
                // and haven't just been chosen for removal. That's what possibleCurrencyNames records.
                ArrayList<String> allCurrencyNames = AppLogic.getAllCurrencies();
                ArrayList<String> possibleCurrencyNames = new ArrayList<String>();

                // copy allCurrencyNames into possibleCurrencyNames
                for (String elem: allCurrencyNames){
                    possibleCurrencyNames.add(elem);
                }
                // adjust possible currency names accordingly
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
                String toReplace = UserInterface.getString(possibleCurrencyNames, "Which currency would you like to replace it with?", scan);
                popCurrencies.add(toReplace);
            }
        } while(answer.equals("Y") || answer.equals("y"));

        AppLogic.displayCurrencyTable();
    }

}