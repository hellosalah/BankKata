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

public class StatementPrinterTest {
    private Clock clock;
    private StatementPrinter statementPrinter;
    private Account account;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        System.out.println("Setting up test...");

        clock = mock(Clock.class);
        statementPrinter = new StatementPrinter();
        account = new Account(clock, statementPrinter);

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }
    @Test
    void print_statement_with_no_transactions(){
        account.printStatement();

        String expectedOutput = "Date       || Amount || Balance";

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void should_print_transactions_in_reverse_chronological_order() {
        when(clock.today())
                .thenReturn(LocalDate.of(2024, 1, 31)) // Latest transaction date
                .thenReturn(LocalDate.of(2024, 1, 30)) // Second transaction
                .thenReturn(LocalDate.of(2024, 2, 1)); // Oldest transaction date

        account.deposit(1000); // 31-01-2024
        account.withdraw(500); // 30-01-2024
        account.deposit(2000); // 01-02-2024

        account.printStatement();

        String expectedOutput = String.join(System.lineSeparator(),
                "Date       || Amount || Balance",
                "01-02-2024 || 2000   || 2000",
                "30-01-2024 || -500   || 1500",
                "31-01-2024 || 1000   || 2500");

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

}
