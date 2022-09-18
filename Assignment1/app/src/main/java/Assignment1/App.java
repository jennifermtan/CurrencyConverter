package Assignment1;
import java.util.*;
import java.io.*;

public class App {

    public static void main(String[] args) {
        // Reload data on app run
        AppLogic.readCurrencyFiles();
        // Starting of the application
        String user = UserInterface.getString(Arrays.asList("1", "2"), "Welcome to Currency Converter! Select displayed options to continue!" +
                "\nWhat is your user type?\n1: User\n2: Admin", new Scanner(System.in));
        AppLogic.selectUserOption(user, new Scanner(System.in));

        //Make a password for admin
        if (user.equals("2")) {
            System.out.println("Please enter the password for admin access.");
            Scanner scan = new Scanner(System.in);
            String password = scan.nextLine();

            if (password.equals("SOFT2412")) {
                AppLogic.selectUserOption(user, new Scanner(System.in));
            } else {
                System.out.println("Wrong password.");
            }
        } else if (user.equals("1")) {
            AppLogic.selectUserOption(user, new Scanner(System.in));
        }
    }


}
