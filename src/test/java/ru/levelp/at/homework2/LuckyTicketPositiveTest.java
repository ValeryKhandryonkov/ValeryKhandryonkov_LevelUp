package ru.levelp.at.homework2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LuckyTicketPositiveTest {

    @Tag("Positive")
    @ParameterizedTest
    @ValueSource(strings = {"000000", "999999", "123141", "009333", "579849", "101200", "333162", "755944"})
    void sumsOfTwoHalvesAreEqual(String input) {
        boolean expected = true;
        boolean actual = LuckyTicket.isLuckyTicket(input);
        assertThat(actual).isEqualTo(expected);
    }

    @Tag("Positive")
    @ParameterizedTest
    @ValueSource(strings = {"111222", "123456", "999000", "665544", "010101", "800002", "789456", "159482"})
    void sumsOfTwoHalvesAreNotEqual(String input) {
        boolean expected = false;
        boolean actual = LuckyTicket.isLuckyTicket(input);
        assertThat(actual).isEqualTo(expected);
    }
}
