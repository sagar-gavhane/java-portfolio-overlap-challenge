package com.example.geektrust.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvestorTest {
    @Test
    void givenName_whenCreatingInvestor_thenCorrect() {
        Investor investor = new Investor("John Doe");
        assertEquals("John Doe", investor.getName());
    }

    @Test
    void givenPortfolio_whenCreatingInvestor_thenCorrect() {
        Investor investor = new Investor("John Doe");
        assertEquals(0, investor.getPortfolio().getMutualFunds().size());
    }
}