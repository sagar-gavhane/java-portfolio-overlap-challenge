package com.example.geektrust.entities;

import java.util.LinkedHashSet;
import java.util.Set;

public class Portfolio {
    private final Set<MutualFund> mutualFunds = new LinkedHashSet<>();

    public void addMutualFund(MutualFund mutualFund) {
        this.mutualFunds.add(mutualFund);
    }

    public Set<MutualFund> getMutualFunds() {
        return mutualFunds;
    }
}
