package com.example.geektrust.services;

import com.example.geektrust.entities.MutualFund;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OverlapService {
    private static final int MULTIPLAYER = 2;

    private OverlapService() {
        throw new IllegalArgumentException("Can't have instance of utility class");
    }

    public static List<String> calculateOverlapping(Set<MutualFund> mutualFunds, MutualFund mutualFund) {
        List<String> results = new ArrayList<>();

        for (MutualFund mf : mutualFunds) {
            double overlap = calculateOverlapPercentage(mf.getStocks(), mutualFund.getStocks());
            results.add(formatOverlapResult(mutualFund.getFundName(), mf.getFundName(), overlap));
        }

        return results;
    }

    private static double calculateOverlapPercentage(Set<String> stocks1, Set<String> stocks2) {
        int totalStocks = stocks1.size() + stocks2.size();
        long commonStocksCount = getCommonStocksCount(stocks1, stocks2);
        return MULTIPLAYER * ((double) commonStocksCount / totalStocks * 100);
    }

    public static String formatOverlapResult(String fundName1, String fundName2, double overlapPercentage) {
        return String.format("%s %s %.2f%%", fundName1, fundName2, overlapPercentage);
    }

    private static long getCommonStocksCount(Set<String> stock1, Set<String> stock2) {
        return stock1.stream().filter(stock2::contains).count();
    }
}
