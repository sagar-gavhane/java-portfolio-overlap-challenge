package com.example.geektrust.services;

import com.example.geektrust.entities.MutualFund;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandExecutorTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @TempDir
    Path tempDir;

    private CommandExecutor commandExecutor;
    private ExchangeService exchangeService;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        exchangeService = mock(ExchangeService.class);
        CommandExecutor.setInstance(null);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        CommandExecutor.setInstance(null);
    }

    @Test
    void testSingletonInstance() throws Exception {
        // Create a temporary file
        Path tempFile = tempDir.resolve("test_commands.txt");
        Files.write(tempFile, Collections.singletonList("ADD_STOCK TEST_FUND"));

        CommandExecutor instance1 = CommandExecutor.getInstance(tempFile.toString());
        CommandExecutor instance2 = CommandExecutor.getInstance(tempFile.toString());

        assertSame(instance1, instance2, "Singleton instances should be the same");
    }

    @Test
    void testReadCommands() throws Exception {
        // Create a temporary file with test commands
        Path tempFile = tempDir.resolve("test_commands.txt");
        List<String> testCommands = Arrays.asList(
                "ADD_STOCK FUND1",
                "CURRENT_PORTFOLIO FUND2",
                "CALCULATE_OVERLAP FUND3"
        );
        Files.write(tempFile, testCommands);

        commandExecutor = CommandExecutor.getInstance(tempFile.toString());
        List<String> readCommands = commandExecutor.getCommands();

        assertEquals(testCommands, readCommands, "Commands should be read correctly from file");
    }

    @Test
    void testFileNotFound() {
        CommandExecutor executor = CommandExecutor.getInstance("nonexistent_file.txt");
        assertTrue(executor.getCommands().isEmpty(), "Commands list should be empty for non-existent file");
    }

    @Test
    void testAddStockCommand() throws Exception {
        Path tempFile = tempDir.resolve("test_commands.txt");
        Files.write(tempFile, Collections.singletonList("ADD_STOCK AXIS_BLUECHIP HATSUN AGRO PRODUCT LIMITED"));

        MutualFund mockFund = mock(MutualFund.class);
        when(mockFund.getFundName()).thenReturn("AXIS_BLUECHIP");
        when(exchangeService.getMutualFunds()).thenReturn(new HashSet<>(Collections.singletonList(mockFund)));

        commandExecutor = CommandExecutor.getInstance(tempFile.toString());

        assertDoesNotThrow(() -> commandExecutor.execute(),
                "ADD_STOCK command should execute without throwing exception");
    }

    @Test
    void testCurrentPortfolioCommand() throws Exception {
        Path tempFile = tempDir.resolve("test_commands.txt");
        Files.write(tempFile, Collections.singletonList("CURRENT_PORTFOLIO AXIS_BLUECHIP AXIS_MIDCAP"));

        MutualFund mockFund1 = mock(MutualFund.class);
        MutualFund mockFund2 = mock(MutualFund.class);
        when(exchangeService.getMutualFundByName("FUND1")).thenReturn(Optional.of(mockFund1));
        when(exchangeService.getMutualFundByName("FUND2")).thenReturn(Optional.of(mockFund2));

        commandExecutor = CommandExecutor.getInstance(tempFile.toString());

        assertDoesNotThrow(() -> commandExecutor.execute(),
                "CURRENT_PORTFOLIO command should execute without throwing exception");
    }

    @Test
    void testCalculateOverlapCommand() throws Exception {
        Path tempFile = tempDir.resolve("test_commands.txt");
        Files.write(tempFile, Collections.singletonList("CALCULATE_OVERLAP FUND1"));

        MutualFund mockFund = mock(MutualFund.class);
        when(exchangeService.getMutualFundByName("FUND1")).thenReturn(Optional.of(mockFund));

        commandExecutor = CommandExecutor.getInstance(tempFile.toString());
        commandExecutor.execute();

        // Verify that some output was generated
        assertTrue(outputStream.toString().length() > 0,
                "CALCULATE_OVERLAP command should produce output");
    }

    @Test
    void testCalculateOverlapWithNonExistentFund() throws Exception {
        Path tempFile = tempDir.resolve("test_commands.txt");
        Files.write(tempFile, Collections.singletonList("CALCULATE_OVERLAP NONEXISTENT_FUND"));

        when(exchangeService.getMutualFundByName("NONEXISTENT_FUND")).thenReturn(Optional.empty());

        commandExecutor = CommandExecutor.getInstance(tempFile.toString());
        commandExecutor.execute();

        assertEquals("FUND_NOT_FOUND", outputStream.toString().trim(),
                "Should output FUND_NOT_FOUND for non-existent fund");
    }

    @Test
    void testInvalidCommand() throws Exception {
        Path tempFile = tempDir.resolve("test_commands.txt");
        Files.write(tempFile, Collections.singletonList("INVALID_COMMAND FUND1"));

        commandExecutor = CommandExecutor.getInstance(tempFile.toString());

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> commandExecutor.execute(),
                "Should throw IllegalArgumentException for invalid command");

        assertEquals("Unknown operation: INVALID_COMMAND", exception.getMessage(),
                "Exception message should match expected message");
    }

    @Test
    void testSetCommands() {
        Path tempFile = tempDir.resolve("test_commands.txt");
        commandExecutor = CommandExecutor.getInstance(tempFile.toString());

        List<String> newCommands = Arrays.asList("ADD_STOCK FUND1", "CALCULATE_OVERLAP FUND1");
        commandExecutor.setCommands(newCommands);

        assertEquals(newCommands, commandExecutor.getCommands(),
                "Commands should be updated after using setter");
    }
}