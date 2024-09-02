package util;

import MVP_src.AllModules;
import MVP_src.StudentInfo;

import javax.swing.*;
import java.sql.SQLOutput;

/**
 *
 */
public class Verifier {
    private static final int BIT_MASK_ISDIGIT = 0x01;
    private static int[] lookup = new int[128];

    static {
        for (int i = '0'; i <= '9'; i++) {
            lookup[i] |= BIT_MASK_ISDIGIT;
        }
    }

    /**
     * Checks if the input string is a valid integer.
     *
     * @param input the string to check
     * @return true if the input string is a valid integer, false otherwise
     */
    public static boolean isInt(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        // Check if the first character is a sign or a digit
        char firstChar = input.charAt(0);
        if (firstChar != '+' && firstChar != '-' && !isDigit(firstChar)) {
            return false;
        }

        // Check the remaining characters for digits
        for (int i = 1; i < input.length(); i++) {
            if (!isDigit(input.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the given character is a digit.
     *
     * @param c the character to check
     * @return true if the character is a digit, false otherwise
     */
    private static boolean isDigit(char c) {
        return (lookup[c] & BIT_MASK_ISDIGIT) != 0;
    }

    /**
     * Checks if the student ID exists
     *
     * @param input
     * @param info
     * @return
     */
    public static boolean StudentIDExists(String input, StudentInfo info)
    {
        if(isInt(input)){
            for (var i : info.entrySet()) {
                if (input.equals(i.getKey().toString())){
                    return true;
                }
            }
            System.out.println("Student ID doesn't exit");
        }
        System.out.println(input + " is not an int value");
        return false;
    }


    /**
     * Checks if the input string is a valid module name.
     *
     * @param input the string to check
     * @param allModules a list of all valid module names
     * @return true if the input string is a valid module name, false otherwise
     */
    public static boolean ModuleNameExists(String input, AllModules allModules) {
        if (input == null) {
            return false;
        }

        for (int i = 0; i < allModules.size(); i++) {
            if (input.equals(allModules.get(i))) {
                return true;
            }
        }

        return false;
    }
}