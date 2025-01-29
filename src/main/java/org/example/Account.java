package org.example;

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
        String date = clock.todayAsString();
        transactions.add(new Transaction(date, amount));
    }

    public void withdraw(int amount) {
        String date = clock.todayAsString();
        transactions.add(new Transaction(date, -amount));
    }

    public void printStatement() {
        statementPrinter.print(transactions);
    }
}
