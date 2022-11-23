package ru.levelp.at.homework2;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class LuckyTicketNegativeTest {

    static Stream<String> nonDigitSymbolsDataProvider() {
        return Stream.of(null, "", " ", "  22  ", " 55555", "Number", "-11222", "+99555",
            "!@#$%^", "33333!", "100.0d", "555.66", "7777,2", "1.5e-10", "666-66", "0xFF");
    }

    @ParameterizedTest
    @MethodSource("nonDigitSymbolsDataProvider")
    void ticketNumberContainsNonDigitSymbols(String input) {
        boolean expected = false;
        boolean actual = LuckyTicket.isLuckyTicket(input);
        assertThat(actual).isEqualTo(expected);
    }
}
