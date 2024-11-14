package com.example.geektrust.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MutualFundTest {
    @Test
    @DisplayName("should be able to set fund name")
    public void shouldBeAbleToSetFundName() {
        MutualFund mutualFund = new MutualFund("ICICI_PRU_NIFTY_NEXT_50_INDEX", new HashSet<>());
        assertEquals("ICICI_PRU_NIFTY_NEXT_50_INDEX", mutualFund.getFundName());
    }

    @Test
    @DisplayName("should be able to set stocks")
    public void shouldBeAbleToSetStocks() {
        Set<String> stocks = new HashSet<>();

        stocks.add("DABUR INDIA LIMITED");
        stocks.add("BAJAJ HOLDINGS & INVESTMENT LIMITED");
        stocks.add("ADANI ENTERPRISES LIMITED");
        stocks.add("ACC LIMITED");
        stocks.add("NMDC LIMITED");
        stocks.add("GAIL (INDIA) LIMITED");

        MutualFund mutualFund = new MutualFund("ICICI_PRU_NIFTY_NEXT_50_INDEX", new HashSet<>(stocks));
        assertEquals(6, mutualFund.getStocks().size());
        assertTrue(mutualFund.getStocks().containsAll(stocks));
    }

    @Test
    @DisplayName("should be able to add stock")
    public void shouldBeAbleToAddStock() {
        MutualFund mutualFund = new MutualFund("ICICI_PRU_NIFTY_NEXT_50_INDEX", new HashSet<>());
        mutualFund.addStock("DABUR INDIA LIMITED");
        assertEquals(1, mutualFund.getStocks().size());
        assertTrue(mutualFund.getStocks().contains("DABUR INDIA LIMITED"));
    }

    @Test
    @DisplayName("should be able to remove stock")
    public void shouldBeAbleToRemoveStock() {
        MutualFund mutualFund = new MutualFund("ICICI_PRU_NIFTY_NEXT_50_INDEX", new HashSet<>());
        mutualFund.addStock("DABUR INDIA LIMITED");
        mutualFund.addStock("BAJAJ HOLDINGS & INVESTMENT LIMITED");

        mutualFund.removeStock("DABUR INDIA LIMITED");

        assertEquals(1, mutualFund.getStocks().size());
        assertTrue(mutualFund.getStocks().contains("BAJAJ HOLDINGS & INVESTMENT LIMITED"));
    }
}
    
