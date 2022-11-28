package ru.levelp.at.homework2;

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

        if (ticketNumber == null) {
            throw new IllegalArgumentException("Ticket number is null!");
        }

        /* Checks if all characters in string are digits */
        if (!ticketNumber.matches("^\\d{6}$")) {
            throw new IllegalArgumentException("Ticket number should be 6 digits long"
                + " and contain only digits from 0 to 9!");
        }

        String firstHalf = ticketNumber.substring(0, 3);
        String secondHalf = ticketNumber.substring(3);

        int firstHalfSum = sumOfHalf(firstHalf);
        int secondHalfSum = sumOfHalf(secondHalf);

        return firstHalfSum == secondHalfSum;
    }
}
