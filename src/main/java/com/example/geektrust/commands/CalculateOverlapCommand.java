package com.example.geektrust.commands;

import com.example.geektrust.entities.Investor;
import com.example.geektrust.entities.MutualFund;
import com.example.geektrust.services.ExchangeService;
import com.example.geektrust.services.OverlapService;

import java.util.List;
import java.util.Optional;

public class CalculateOverlapCommand implements Command {
    private final ExchangeService exchangeService = ExchangeService.getInstance();
    private final String mfName;
    private final Investor investor;

    public CalculateOverlapCommand(Investor investor, String mfName) {
        this.investor = investor;
        this.mfName = mfName;
    }

    @Override
    public void execute() {
        Optional<MutualFund> optionalMutualFund = exchangeService.getMutualFundByName(mfName);

        if (!optionalMutualFund.isPresent()) {
            System.out.println("FUND_NOT_FOUND");
            return;
        }

        MutualFund mf = optionalMutualFund.get();
        List<String> calculatedOverlapping = OverlapService.calculateOverlapping(investor.getPortfolio().getMutualFunds(), mf);

        calculatedOverlapping.forEach(System.out::print);
    }
}
