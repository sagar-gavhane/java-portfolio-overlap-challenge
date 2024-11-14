package com.example.geektrust.services;

import com.example.geektrust.commands.Command;
import com.example.geektrust.entities.Investor;
import com.example.geektrust.factories.CommandFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandExecutor {
    private static CommandExecutor instance;
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
        List<String> commandList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(fileName)) {
            Scanner scanner = new Scanner(fis);

            while (scanner.hasNextLine()) {
                commandList.add(scanner.nextLine());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (IOException e) {
            System.out.println("IOException while reading file: " + fileName);
        }

        return commandList;
    }

    public void execute() {
        CommandFactory factory = new CommandFactory(investor);

        for (String command : commands) {
            String[] split = command.split(" ");
            Command cmd = factory.createCommand(split);
            cmd.execute();
        }
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
