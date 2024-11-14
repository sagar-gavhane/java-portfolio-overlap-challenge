package com.example.geektrust;

import com.example.geektrust.services.CommandExecutor;

public class Main {
    public static void main(String[] args) {
        CommandExecutor executor = CommandExecutor.getInstance(args[0]);
        executor.execute();
    }
}
