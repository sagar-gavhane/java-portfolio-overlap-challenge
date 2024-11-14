package com.example.geektrust.services;

import com.example.geektrust.entities.MutualFund;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeServiceTest {
    private final ExchangeService exchangeService = ExchangeService.getInstance();

    @Test
    @DisplayName("should be return singleton of ExchangeService")
    public void shouldBeAbleToSingleton() {
        assertSame(exchangeService, ExchangeService.getInstance());
    }

    @Test
    @DisplayName("should be return all mutual funds")
    public void shouldBeReturnAllMutualFunds() {
        Set<MutualFund> mutualFunds = exchangeService.getMutualFunds();

        assertEquals(10, mutualFunds.size());

        for (MutualFund mutualFund : mutualFunds) {
            assertNotNull(mutualFund.getFundName());
            assertTrue(mutualFund.getStocks().size() > 0);
        }
    }
}