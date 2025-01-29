package org.example;

import java.util.List;

public class StatementPrinter {
    private static final String HEADER = "Date       || Amount || Balance";

    public void print(List<Transaction> transactions) {
        System.out.println(HEADER);
        int runningBalance = 0;

        for (Transaction transaction : transactions) {
            runningBalance += transaction.getAmount();
            String statementLine = formatStatementLine(transaction.getDate(), transaction.getAmount(), runningBalance);
            System.out.println(statementLine);
        }
    }

    private String formatStatementLine(String date, int amount, int balance) {
        return String.format("%s || %d   || %d", date, amount, balance);
    }
}
