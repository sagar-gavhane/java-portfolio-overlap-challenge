package com.example.geektrust.commands;

import com.example.geektrust.entities.Investor;
import com.example.geektrust.entities.MutualFund;
import com.example.geektrust.services.ExchangeService;

import java.util.Optional;

public class CurrentPortfolioCommand implements Command {
    private final String[] fundNames;
    private final ExchangeService exchangeService = ExchangeService.getInstance();
    private final Investor investor;

    public CurrentPortfolioCommand(Investor investor, String[] fundNames) {
        this.fundNames = fundNames;
        this.investor = investor;
    }

    @Override
    public void execute() {
        for (String fundName : fundNames) {
            Optional<MutualFund> optionalMutualFund = exchangeService.getMutualFundByName(fundName);

            if (!optionalMutualFund.isPresent()) {
                throw new IllegalArgumentException("Mutual fund not found: " + fundName);
            }

            MutualFund mutualFund = optionalMutualFund.get();
            investor.getPortfolio().addMutualFund(mutualFund);
        }
    }
}
