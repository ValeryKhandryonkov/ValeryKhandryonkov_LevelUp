package ru.levelp.at.homework2;

import java.util.ArrayList;
import java.util.List;

public class LuckyTicket {

    /* Calculates sum of 3 digits */
    private static int sumOfHalf(String half) {
        String[] halfNumbers = half.split("");
        int sum = 0;
        for (String ch : halfNumbers) {
            int number = Integer.parseInt(ch);
            sum += number;
        }
        return sum;
    }

    /* Returns True if given number is 6 digits long and sum of digits 1-3 equals sum of digits 4-6 */
    /* Otherwise returns False */
    public static boolean isLuckyTicket(String ticketNumber) {
        System.out.printf("\nChecking ticket with number: '%s' \n", ticketNumber);

        /* Checks if all characters in string are digits */
        for (int i = 0; i < ticketNumber.length(); i++) {
            if (!Character.isDigit(ticketNumber.charAt(i))) {
                System.out.println("Ticket number should contain only digits from 0 to 9!");
                return false;
            }
        }

        if (ticketNumber.length() == 6) {
            String firstHalf = ticketNumber.substring(0, 3);
            String secondHalf = ticketNumber.substring(3);

            int firstHalfSum = sumOfHalf(firstHalf);
            int secondHalfSum = sumOfHalf(secondHalf);

            if (firstHalfSum == secondHalfSum) {
                System.out.printf("Sum of ticket's first half of numbers '%s' = %d \n", firstHalf, firstHalfSum);
                System.out.printf("Sum of ticket's second half of numbers '%s' = %d \n", secondHalf, secondHalfSum);
                System.out.print("The ticket is lucky! Congratulations! \n");
                return true;
            } else {
                System.out.printf("Sum of ticket's first half of numbers '%s' = %d \n", firstHalf, firstHalfSum);
                System.out.printf("Sum of ticket's second half of numbers '%s' = %d \n", secondHalf, secondHalfSum);
                System.out.print("The ticket is not lucky. \n");
                return false;
            }
        } else {
            System.out.printf("The ticket has %d digits. The ticket is not lucky. \n", ticketNumber.length());
            return false;
        }
    }
}
