package com.example.geektrust.entities;

import java.util.Set;

public class MutualFund {
    private final String fundName;
    private final Set<String> stocks;

    public MutualFund(String fundName, Set<String> stocks) {
        this.fundName = fundName;
        this.stocks = stocks;
    }

    public void addStock(String stock) {
        this.stocks.add(stock);
    }
    public void removeStock(String stock) {
        this.stocks.remove(stock);
    }

    public String getFundName() {
        return fundName;
    }

    public Set<String> getStocks() {
        return stocks;
    }
}
