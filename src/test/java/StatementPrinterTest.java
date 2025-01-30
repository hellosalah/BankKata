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

    @BeforeEach
    void setUp() {
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
                .thenReturn(LocalDate.of(2024, 2, 1))
                .thenReturn(LocalDate.of(2024, 2, 2))
                .thenReturn(LocalDate.of(2024, 2, 3));

        account.deposit(500);
        account.withdraw(100);
        account.deposit(200);

        account.printStatement();

        String expectedOutput = String.join(System.lineSeparator(),
                "Date       || Amount || Balance",
                "03-02-2024 || 200   || 600",
                "02-02-2024 || -100   || 400",
                "01-02-2024 || 500   || 500");

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

}
