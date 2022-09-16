package Assignment1;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.lang.Exception;

class UserTest{

    @Test
    public void testGetUserInput() throws Exception{
        assertNotNull(new UserInterface());
       String input = "5\n" + "2\n"; // incorrect input followed by correct input
       Scanner scan = new Scanner(input);
       assertEquals("2", UserInterface.getString(Arrays.asList("1", "2", "3"), "User, what would you like to do?\n1: Convert currencies" +
               "\n2: Display currency table\n3: Summary of 2 currencies", scan));

    }

}