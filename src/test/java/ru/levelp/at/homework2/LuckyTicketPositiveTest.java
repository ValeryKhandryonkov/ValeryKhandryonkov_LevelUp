package ru.levelp.at.homework2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class LuckyTicketPositiveTest {

    @Test
    void sumsOfHalvesAreEqual() {
        String ticketNumber = "123312";
        boolean expected = true;
        boolean actual = LuckyTicket.isLuckyTicket(ticketNumber);
        assertThat(actual).isEqualTo(expected);
    }
}
