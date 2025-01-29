import org.example.Account;
import org.example.Clock;
import org.example.StatementPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AcceptanceTest {
    private Clock clock;
    private StatementPrinter statementPrinter;
    private Account account;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;  // Add this field

    @BeforeEach
    void setUp() {
        originalOut = System.out;  // Store original System.out
        System.out.println("Setting up test...");
        clock = mock(Clock.class);
        statementPrinter = new StatementPrinter();
        account = new Account(clock, statementPrinter);

        // Capture console output
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void printStatement_ShouldPrintCorrectTransactions() {
        // Given controlled dates
        when(clock.today())
                .thenReturn(LocalDate.of(2012, 1, 10))
                .thenReturn(LocalDate.of(2012, 1, 13))
                .thenReturn(LocalDate.of(2012, 1, 14));

        // When transactions occur
        account.deposit(1000);
        account.deposit(2000);
        account.withdraw(500);

        // When printing the statement
        account.printStatement();

        // Then output should match expected
        String expectedOutput = String.join(System.lineSeparator(),
                "Date       || Amount || Balance",
                "14-01-2012 || -500   || 2500",
                "13-01-2012 || 2000   || 3000",
                "10-01-2012 || 1000   || 1000");

        originalOut.println("Actual output:");
        originalOut.println(outputStream.toString());
        originalOut.println("Expected output:");
        originalOut.println(expectedOutput);

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }


}
