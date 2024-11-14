package com.example.geektrust.commands;

import com.example.geektrust.entities.MutualFund;
import com.example.geektrust.services.ExchangeService;

import java.util.Optional;

public class AddStockCommand implements Command {
    private final ExchangeService exchangeService = ExchangeService.getInstance();
    private final String stockName;

    public AddStockCommand(String stockName) {
        this.stockName = stockName;
    }

    @Override
    public void execute() {
        Optional<MutualFund> mf = exchangeService.getMutualFunds().stream()
                .filter(mutualFund -> mutualFund.getFundName().equals(stockName))
                .findFirst();

        if (!mf.isPresent()) {
            throw new IllegalArgumentException("Mutual fund not found: " + stockName);
        }

        mf.get().addStock(stockName);
    }
}
