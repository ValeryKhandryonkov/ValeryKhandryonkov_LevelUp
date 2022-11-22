package ru.levelp.at.homework2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class LuckyTicketNegativeTest {

    @Test
    void ticketNumberContainsNonDigitSymbols() {
        String input = "abcdefg";
        boolean expected = false;
        boolean actual = LuckyTicket.isLuckyTicket(input);
        assertThat(actual).isEqualTo(expected);
    }
}
