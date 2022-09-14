package Assignment1;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.lang.Exception;

class LogicTest{

    @Test
    public void currencyListCheck() throws Exception{
        assertNotNull(new AppLogic());
        AppLogic.readCurrencyFiles();
        assertEquals(AppLogic.getCurrencies().size(), 6);
        ArrayList<String> allCurrencies = new ArrayList<String>();
        allCurrencies.add("AUD");
        allCurrencies.add("SGD");
        allCurrencies.add("EUR");
        allCurrencies.add("USD");
        allCurrencies.add("JPY");
        allCurrencies.add("ILS");

        assertEquals(AppLogic.getAllCurrencies(), allCurrencies);
    }

    // Tests "adminDisplayTable" method in UserInterface
    @Test
    public void allowsAdminToChangeTable(){
        String input = "y\n" + "SGD\n" + "JPY\n" + "Y\n" + "EUR\n" + "ILS\n" + "n\n";
        Scanner scan = new Scanner(input);

        UserInterface.adminDisplayTable(scan, AppLogic.popCurrencies, AppLogic.currencies);

        ArrayList<String> popCurrencies = new ArrayList<String>();
        popCurrencies.add("USD");
        popCurrencies.add("AUD");
        popCurrencies.add("JPY");
        popCurrencies.add("ILS");

        assertEquals(AppLogic.popCurrencies, popCurrencies);
    }

    @Test
    public void testInitialChoices(){
        // USER tests
        // choose to convert currencies as a normal user
        AppLogic.selectUserOption("1", new Scanner("1")); // what should I check here?

        // choose to display the currency table as a normal user
        AppLogic.selectUserOption("1", new Scanner("2")); // only output is to terminal

        // choose to display a summary of two currencies as a normal user
        AppLogic.selectUserOption("1", new Scanner("3")); // what should I check here?

        // ADMIN tests
        // choose to convert currencies as an admin
        AppLogic.selectUserOption("2", new Scanner("1")); // what should I check here?

        // choose to display the currency table as a normal user
        AppLogic.selectUserOption("2", new Scanner("2\n" + "n\n")); // admin pop currencies manipulation is already tested so only output is to terminal

        // choose to display a summary of two currencies as a normal user
        AppLogic.selectUserOption("2", new Scanner("3")); // what should I check here?


    }
}