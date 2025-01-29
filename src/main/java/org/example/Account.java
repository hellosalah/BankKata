package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private final List<Transaction> transactions = new ArrayList<>();
    private final Clock clock;
    private final StatementPrinter statementPrinter;

    public Account(Clock clock, StatementPrinter statementPrinter) {
        this.clock = clock;
        this.statementPrinter = statementPrinter;
    }

    public void deposit(int amount) {
        LocalDate date = clock.today();
        transactions.add(new Transaction(date, amount));
    }

    public void withdraw(int amount) {
        LocalDate date = clock.today();
        transactions.add(new Transaction(date, -amount));
    }

    public void printStatement() {
        statementPrinter.print(transactions);
    }
}
