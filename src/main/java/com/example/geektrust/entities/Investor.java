package com.example.geektrust.entities;

public class Investor {
    private final String name;
    private final Portfolio portfolio = new Portfolio();

    public Investor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }
}
