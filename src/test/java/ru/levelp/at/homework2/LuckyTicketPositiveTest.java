package ru.levelp.at.homework2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LuckyTicketPositiveTest {

    @ParameterizedTest
    @ValueSource(strings = {"000000", "999999", "123141", "009333", "579849"})
    void sumsOfTwoHalvesAreEqual(String input) {
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
