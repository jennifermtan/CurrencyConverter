package Assignment1;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.lang.Exception;

class LogicTest{

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    // Redirects stdout stream to a new print stream to help check terminal output
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    // Resets the output stream after each test to prevent involvement with other code
    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

//    @Test
//    public void currencyListCheck() throws Exception{
//        assertNotNull(new AppLogic());
//        AppLogic.readCurrencyFiles();
//        assertEquals(AppLogic.getCurrenciesHashMaps().size(), 6);
//        ArrayList<String> allCurrencies = new ArrayList<String>();
//        allCurrencies.add("AUD");
//        allCurrencies.add("SGD");
//        allCurrencies.add("EUR");
//        allCurrencies.add("USD");
//        allCurrencies.add("JPY");
//        allCurrencies.add("ILS");
//
//        assertEquals(AppLogic.getAllCurrencies(), allCurrencies);
//    }


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

    // Tests "summaryOf2Currencies" method in AppLogic
    @Test
    public void checkSummaryOfCurrencies() {
        String input = "JPY\n" + "EUR\n" + "10/08/22\n" + "12/08/22\n";
        AppLogic.summaryOf2Currencies(new Scanner(input));
    }

    // Tests "addNewCurrency" method in AppLogic
//    @Test
//    public void addNewCurrency() throws Exception {
//        // outputStreamCaptor.toString().trim()
//        AppLogic.addNewCurrency(new Scanner("AUD\n" + "6\n"));
//        assertEquals("Please input name of the currency you want to add.\nError: that currency already exists in the system.\n", outputStreamCaptor.toString());

//        // Deleting the test file created by method
//        File testFile = new File("./src/main/java/Assignment1/currencies/TEST.csv");
//        assertTrue(testFile.delete());
//
//        // "Deleting" the added line from currencyList.txt
//        File inputFile = new File("./src/main/java/Assignment1/currencies/currencyFiles.txt");
//        File tempFile = new File("myTempFile.txt");
//
//        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
//        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
//
//        String lineToRemove = "./src/main/java/Assignment1/currencies/TEST.csv\n";
//        String currentLine;
//
//        while((currentLine = reader.readLine()) != null) {
//            // trim newline when comparing with lineToRemove
//            if(currentLine.equals(lineToRemove)) continue;
//            writer.write(currentLine);
//        }
//        writer.close();
//        reader.close();
//        inputFile.delete();
//        boolean successful = tempFile.renameTo(new File("./src/main/java/Assignment1/currencies/currencyFiles.txt"));
//
//        assertTrue(successful);
//    }

//    @Test
//    public void addExchangeRate() throws Exception{
//        AppLogic.addExchangeRate(new Scanner("test\n" + "6\n"));
//        assertEquals("Please input currency name.\nCurrency does not exist.", outputStreamCaptor.toString());

//        // "Deleting" the added line from currencyList.txt
//        File inputFile = new File("./src/main/java/Assignment1/currencies/AUD.csv");
//        File tempFile = new File("myTempFile.txt");
//
//        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
//        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
//
//        String lineToRemove = "testDate, 1.12345\n";
//        String currentLine;
//
//        while((currentLine = reader.readLine()) != null) {
//            // trim newline when comparing with lineToRemove
//            if(currentLine.equals(lineToRemove)) continue;
//            writer.write(currentLine);
//        }
//        writer.close();
//        reader.close();
//        inputFile.delete();
//        boolean successful = tempFile.renameTo(new File("./src/main/java/Assignment1/currencies/AUD.csv"));
//        assertTrue(successful);
//    }

    @Test
    public void selectUserOption() {
        AppLogic.selectUserOption("1", new Scanner("2\n" + "4\n")); // only output is to terminal
    }


    @Test
    public void testInitialChoices(){
        // USER tests
        // choose to convert currencies as a normal user
        AppLogic.selectUserOption("1", new Scanner("1\n" + "100\n" + "AUD\n" + "USD\n" + "4\n")); // what should I check here?

        // choose to display the currency table as a normal user
        AppLogic.selectUserOption("1", new Scanner("2\n" + "4\n")); // only output is to terminal

        // choose to display a summary of two currencies as a normal user
        AppLogic.selectUserOption("1", new Scanner("3\n" + "USD\n" + "AUD\n" + "06/08/22\n" + "12/08/22\n" + "4\n")); // what should I check here?

        // ADMIN tests
        // choose to convert currencies as an admin
        AppLogic.selectUserOption("2", new Scanner("1\n" + "100\n" + "AUD\n" + "USD\n" + "6\n")); // what should I check here?

        // choose to display the currency table as a normal user
        AppLogic.selectUserOption("2", new Scanner("2\n" + "n\n")); // admin pop currencies manipulation is already tested so only output is to terminal

        // choose to display a summary of two currencies as a normal user
        AppLogic.selectUserOption("2", new Scanner("3\n" + "JPY\n" + "SGD\n" + "08/08/22\n" + "10/08/22\n" + "6\n")); // what should I check here?

    }
}
