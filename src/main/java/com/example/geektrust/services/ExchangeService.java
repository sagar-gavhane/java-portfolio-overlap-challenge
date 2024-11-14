package com.example.geektrust.services;

import com.example.geektrust.entities.MutualFund;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class ExchangeService {
    private static ExchangeService instance;
    private Set<MutualFund> mutualFunds;

    private ExchangeService() {
        initializeMutualFunds();
    }

    public static synchronized ExchangeService getInstance() {
        if (instance == null) {
            instance = new ExchangeService();
        }

        return instance;
    }

    private void initializeMutualFunds() {
        try {
            Set<MutualFund> mutualFundSet = new LinkedHashSet<>();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(new File("config/stock_data.json"));

            for (JsonNode fund : root.path("funds")) {
                String name = fund.path("name").asText();
                MutualFund mutualFund = new MutualFund(name, new HashSet<>());

                for (JsonNode stock : fund.path("stocks")) {
                    mutualFund.addStock(stock.asText());
                }

                mutualFundSet.add(mutualFund);
            }

            this.mutualFunds = mutualFundSet;
        } catch (JsonProcessingException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Set<MutualFund> getMutualFunds() {
        return mutualFunds;
    }

    public Optional<MutualFund> getMutualFundByName(String name) {
        return mutualFunds.stream().filter(mutualFund -> mutualFund.getFundName().equals(name)).findFirst();
    }
}
