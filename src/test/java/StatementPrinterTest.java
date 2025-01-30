import org.example.Account;
import org.example.Clock;
import org.example.StatementPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

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
}
