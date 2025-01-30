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

public class AccountTest {
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
    void print_single_deposit() {
        when(clock.today()).thenReturn(LocalDate.of(2024, 1, 30));

        account.deposit(1000);
        account.printStatement();

        String expectedOutput = String.join(System.lineSeparator(),
                "Date       || Amount || Balance",
                "30-01-2024 || 1000   || 1000");

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void print_single_withdrawal(){
        when(clock.today()).thenReturn(LocalDate.of(2024, 1, 31));

        account.deposit(1000);  // Ensure there's money to withdraw
        account.withdraw(1000); // Withdraw the same amount
        account.printStatement();

        String expectedOutput = String.join(System.lineSeparator(),
                "Date       || Amount || Balance",
                "31-01-2024 || -1000   || 0",
                "31-01-2024 || 1000   || 1000");

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    void should_handle_large_transactions(){
        when(clock.today())
                .thenReturn(LocalDate.of(2024, 3, 1))
                .thenReturn(LocalDate.of(2024, 3, 2))
                .thenReturn(LocalDate.of(2024, 3, 3));

        account.deposit(1_000_000);
        account.deposit(2_000_000);
        account.withdraw(500_000);

        account.printStatement();

        String expectedOutput = String.join(System.lineSeparator(),
                "Date       || Amount || Balance",
                "03-03-2024 || -500000   || 2500000",
                "02-03-2024 || 2000000   || 3000000",
                "01-03-2024 || 1000000   || 1000000");

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }
}
