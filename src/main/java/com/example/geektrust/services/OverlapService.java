package com.example.geektrust.services;

import com.example.geektrust.entities.MutualFund;

import java.util.Set;

public class OverlapService {
    public static StringBuilder calculateOverlapping(Set<MutualFund> mutualFunds, MutualFund mutualFund) {
        StringBuilder sb = new StringBuilder();

        for (MutualFund mf : mutualFunds) {
            Set<String> stocks = mf.getStocks();
            Set<String> stocks1 = mutualFund.getStocks();

            int totalStocks = stocks.size() + stocks1.size();
            long commonStocksCount = getCommonStocksCount(stocks, stocks1);
            double overlap = 2 * ((double) commonStocksCount / (totalStocks) * 100);

            sb.append(String.format("%s %s %.2f%%%n", mutualFund.getFundName(), mf.getFundName(), overlap));
        }

        return sb;
    }

    private static long getCommonStocksCount(Set<String> stock1, Set<String> stock2) {
        return stock1.stream().filter(stock2::contains).count();
    }
}
