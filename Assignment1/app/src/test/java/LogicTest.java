package Assignment1;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.lang.Exception;

class LogicTest{

// Tests the number of hashmaps produced by method against manual calculation of the number of filepaths in currencyFiles.txt
    @Test
    public void currencyListCheck() throws Exception {
        assertNotNull(new AppLogic());
        AppLogic.readCurrencyFiles();
        int counter = 0;
        Scanner currencyFiles = new Scanner(new File("./src/main/java/Assignment1/currencies/currencyFiles.txt"));
        while (currencyFiles.hasNextLine()) {
            currencyFiles.nextLine();
            counter++;
        }
        currencyFiles.close();
        assertEquals(AppLogic.getCurrenciesHashMaps().size(), counter);
    }

    // Tests "adminDisplayTable" method in UserInterface
    @Test
    public void allowsAdminToChangeTable(){
        String input = "y\n" + "SGD\n" + "JPY\n" + "Y\n" + "EUR\n" + "ILS\n" + "n\n";
        Scanner scan = new Scanner(input);

        UserInterface.adminDisplayTable(scan, AppLogic.getPopCurrencies(), AppLogic.getCurrenciesHashMaps());

        ArrayList<String> popCurrencies = new ArrayList<String>();
        popCurrencies.add("USD");
        popCurrencies.add("AUD");
        popCurrencies.add("JPY");
        popCurrencies.add("ILS");

        assertEquals(AppLogic.getPopCurrencies(), popCurrencies);
    }


    // Testing functionality of convertCurrencies method
    @Test
    public void checkConvertCurrencies() {
        String input = "200\n" + "AUD\n" + "SGD\n";
        AppLogic.convertCurrencies(new Scanner(input));
    }

    // Tests "summaryOf2Currencies" method in AppLogic
    @Test
    public void checkSummaryOfCurrencies() {
        String input = "JPY\n" + "EUR\n" + "10/08/22\n" + "12/08/22\n";
        AppLogic.summaryOf2Currencies(new Scanner(input));
    }


    @Test
    public void selectUserOption() {
        AppLogic.selectUserOption("1", new Scanner("2\n")); // only output is to terminal
        AppLogic.selectUserOption("1", new Scanner("3\n" + "AUD\n" + "USD\n" + "03/08/22\n" + "10/08/22\n")); // only output is to terminal
        AppLogic.selectUserOption("1", new Scanner("4\n")); // only output is to terminal
    }

    @Test
    public void testGetUserInput() throws Exception{
        assertNotNull(new UserInterface());
        String input = "5\n" + "2\n"; // incorrect input followed by correct input
        Scanner scan = new Scanner(input);
        assertEquals("2", UserInterface.getString(Arrays.asList("1", "2", "3"), "User, what would you like to do?\n1: Convert currencies" +
                "\n2: Display currency table\n3: Summary of 2 currencies", scan));

    }

    private ArrayList<Double> testNums = new ArrayList<Double>(Arrays.asList(4.35, 3.1415, 1.16457, 42.89, 5.0, 6.73));

    // Each method has been tested to 6 d.p.
    @Test
    void testAverage(){
        assertNotNull(new Operations());
        double num = (double)Math.round(Operations.getAverage(testNums) * 1000000d) /1000000d;
        assertEquals(String.valueOf(num), "10.546012");
    }
    @Test
    void testEvenMedian(){
        double num = (double)Math.round(Operations.getMedian(testNums) * 1000000d) /1000000d;
        assertEquals(String.valueOf(num), "4.675");
    }
    @Test
    void testOddMedian(){
        testNums.add(5.6);
        double num = (double)Math.round(Operations.getMedian(testNums) * 1000000d) /1000000d;
        assertEquals(String.valueOf(num), "5.0");
        testNums.remove(5.6);
    }
    @Test
    void testMax(){
        double num = (double)Math.round(Operations.getMaximum(testNums) * 1000000d) /1000000d;
        assertEquals(String.valueOf(num), "42.89");
    }
    @Test
    void testMin(){
        double num = (double)Math.round(Operations.getMinimum(testNums) * 1000000d) /1000000d;
        assertEquals(String.valueOf(num), "1.16457");
    }

    @Test
    void testStandardDeviation(){
        double num = (double)Math.round(Operations.getSD(testNums) * 1000000d) /1000000d;
        assertEquals(String.valueOf(num), "14.564126");
    }
}



