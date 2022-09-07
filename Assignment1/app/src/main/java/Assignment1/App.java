package Assignment1;
import java.util.*;
public class App {

    public static void main(String[] args) {
        // reload data on app run
        AppLogic.readCurrencyFiles();
        // Starting of the application
        String user = AppLogic.getString(Arrays.asList("1", "2"), "Welcome to Currency Converter! Select displayed options to continue!\nWhat is your user type?\n1: User\n2: Admin");
        AppLogic.selectUserOption(user);

    }


}

//String upArrow = "\u2191";
//String downArrow = "\u2193";