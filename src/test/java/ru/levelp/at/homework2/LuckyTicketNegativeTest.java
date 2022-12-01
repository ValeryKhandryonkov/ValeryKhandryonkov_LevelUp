package ru.levelp.at.homework2;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class LuckyTicketNegativeTest {

    static Stream<String> nonDigitSymbolsDataProvider() {
        return Stream.of(null, "", " ", "  22  ", " 55555", "Number", "-11222", "+99555",
            "!@#$%^", "33333!", "100.0d", "555.66", "7777,2", "1.5e-10", "666-66", "0xFF");
    }

    @Tag("Negative")
    @ParameterizedTest
    @MethodSource("nonDigitSymbolsDataProvider")
    void ticketNumberContainsNonDigitSymbols(String input) {
        if (input == null) {
            String expectedMessage = "Ticket number is null!";
            assertThatThrownBy(() -> LuckyTicket.isLuckyTicket(null))
                .isInstanceOf(IllegalArgumentException.class).hasMessage(expectedMessage);
        } else {
            String expectedMessage = "Ticket number should be 6 digits long and contain only digits from 0 to 9!";
            assertThatThrownBy(() -> LuckyTicket.isLuckyTicket(input)).isInstanceOf(IllegalArgumentException.class)
                                                                      .hasMessage(expectedMessage);
        }
    }

    @Tag("Negative")
    @ParameterizedTest
    @ValueSource(strings = {"1", "22", "333", "4444", "55555", "7777777", "88888888", "999999999", "0000000010",
        "00000000011", "000000000012"})
    void numberOfDigitsInTicketNumberIsNotSix(String input) {
        String expectedMessage = "Ticket number should be 6 digits long and contain only digits from 0 to 9!";
        assertThatThrownBy(() -> LuckyTicket.isLuckyTicket(input)).isInstanceOf(IllegalArgumentException.class)
                                                                  .hasMessage(expectedMessage);
    }
}
