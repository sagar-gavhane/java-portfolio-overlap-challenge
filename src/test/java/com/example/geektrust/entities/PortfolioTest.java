package com.example.geektrust.entities;

import com.example.geektrust.services.ExchangeService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PortfolioTest {
    ExchangeService exchangeService = ExchangeService.getInstance();

    @Test
    void givenNothing_whenCreatingPortfolio_thenReturnEmptyPortfolio() {
        Portfolio portfolio = new Portfolio();
        assertEquals(0, portfolio.getMutualFunds().size());
    }

    @Test
    void givenMutualFund_whenAddingMutualFund_thenReturnAddedMutualFund() {
        Portfolio portfolio = new Portfolio();
        MutualFund mutualFund = exchangeService.getMutualFundByName("AXIS_BLUECHIP").get();
        portfolio.addMutualFund(mutualFund);
        assertEquals(1, portfolio.getMutualFunds().size());
        assertEquals(mutualFund.getFundName(), portfolio.getMutualFunds().stream().filter(mf -> mf.getFundName().equals(mutualFund.getFundName())).findFirst().get().getFundName());
    }
}