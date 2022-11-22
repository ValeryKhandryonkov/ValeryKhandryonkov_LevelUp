package ru.levelp.at.homework2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class LuckyTicketPositiveTest {

    @Test
    void sumsOfTwoHalvesAreEqual() {
        String input = "123312";
        boolean expected = true;
        boolean actual = LuckyTicket.isLuckyTicket(input);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void sumsOfTwoHalvesAreNotEqual() {
        String input = "111222";
        boolean expected = false;
        boolean actual = LuckyTicket.isLuckyTicket(input);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void numberOfDigitsInTicketNumberIsNotSix() {
        String input = "0";
        boolean expected = false;
        boolean actual = LuckyTicket.isLuckyTicket(input);
        assertThat(actual).isEqualTo(expected);
    }
}
