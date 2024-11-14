package com.example.geektrust.services;

import com.example.geektrust.entities.Investor;
import com.example.geektrust.entities.MutualFund;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class CommandExecutor {
    private static CommandExecutor instance;
    private final ExchangeService exchangeService = ExchangeService.getInstance();
    private final Investor investor = new Investor("I1");
    private List<String> commands;

    private CommandExecutor(String fileName) {
        this.commands = readCommands(fileName);
    }

    public static synchronized CommandExecutor getInstance(String fileName) {
        if (instance == null) {
            instance = new CommandExecutor(fileName);
        }

        return instance;
    }

    public static void setInstance(CommandExecutor instance) {
        CommandExecutor.instance = instance;
    }

    public List<String> readCommands(String fileName) {
        List<String> commands = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(fileName)) {
            Scanner scanner = new Scanner(fis);

            while (scanner.hasNextLine()) {
                commands.add(scanner.nextLine());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (IOException e) {
            System.out.println("IOException while reading file: " + fileName);
        }

        return commands;
    }

    public void execute() {
        for (String command : commands) {
            String[] split = command.split(" ");
            String operation = split[0];

            switch (operation) {
                case "ADD_STOCK": {
                    String mfName = split[1];

                    Optional<MutualFund> mf = exchangeService.getMutualFunds().stream()
                            .filter(mutualFund -> mutualFund.getFundName().equals(mfName))
                            .findFirst();

                    if (!mf.isPresent()) {
                        throw new IllegalArgumentException("Mutual fund not found: " + mfName);
                    }

                    mf.get().addStock(mfName);
                    break;
                }

                case "CURRENT_PORTFOLIO": {
                    String[] fundNames = Arrays.copyOfRange(split, 1, split.length);

                    for (String fundName : fundNames) {
                        Optional<MutualFund> optionalMutualFund = exchangeService.getMutualFundByName(fundName);

                        if (!optionalMutualFund.isPresent()) {
                            throw new IllegalArgumentException("Mutual fund not found: " + fundName);
                        } else {
                            MutualFund mutualFund = optionalMutualFund.get();
                            investor.getPortfolio().addMutualFund(mutualFund);
                        }
                    }

                    break;
                }

                case "CALCULATE_OVERLAP": {
                    String mfName = split[1];
                    Optional<MutualFund> optionalMutualFund = exchangeService.getMutualFundByName(mfName);

                    if (!optionalMutualFund.isPresent()) {
                        System.out.println("FUND_NOT_FOUND");
                        break;
                    }

                    MutualFund mf = optionalMutualFund.get();
                    StringBuilder calculatedOverlapping = OverlapService.calculateOverlapping(investor.getPortfolio().getMutualFunds(), mf);
                    System.out.print(calculatedOverlapping);
                    break;
                }

                default: {
                    throw new IllegalArgumentException("Unknown operation: " + operation);
                }
            }
        }
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
