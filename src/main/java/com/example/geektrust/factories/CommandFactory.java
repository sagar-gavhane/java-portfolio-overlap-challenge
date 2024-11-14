package com.example.geektrust.factories;

import com.example.geektrust.commands.AddStockCommand;
import com.example.geektrust.commands.CalculateOverlapCommand;
import com.example.geektrust.commands.Command;
import com.example.geektrust.commands.CurrentPortfolioCommand;
import com.example.geektrust.entities.Investor;

import java.util.Arrays;

public class CommandFactory {
    private final Investor investor;

    public CommandFactory(Investor investor) {
        this.investor = investor;
    }

    public Command createCommand(String[] split) {
        String operation = split[0];

        switch (operation) {
            case "ADD_STOCK": {
                return new AddStockCommand(split[1]);
            }

            case "CURRENT_PORTFOLIO": {
                String[] fundNames = Arrays.copyOfRange(split, 1, split.length);
                return new CurrentPortfolioCommand(investor, fundNames);
            }

            case "CALCULATE_OVERLAP": {
                String mfName = split[1];
                return new CalculateOverlapCommand(investor, mfName);
            }

            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }
}
