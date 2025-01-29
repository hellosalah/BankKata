package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class StatementPrinter {
    private static final String HEADER = "Date       || Amount || Balance";

    public void print(List<Transaction> transactions) {
        System.out.println(HEADER);

        // Create copy and reverse
        List<Transaction> reversedTransactions = new ArrayList<>(transactions);
        Collections.reverse(reversedTransactions);

        // Calculate balances for each transaction
        int balance = 0;
        Map<Transaction, Integer> balances = new LinkedHashMap<>();

        // First calculate all balances going forward
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
            balances.put(transaction, balance);
        }

        // Print in reverse order using the pre-calculated balances
        for (Transaction transaction : reversedTransactions) {
            String statementLine = formatStatementLine(
                    transaction.getDate(),
                    transaction.getAmount(),
                    balances.get(transaction)
            );
            System.out.println(statementLine);
        }
    }

    private String formatStatementLine(LocalDate date, int amount, int balance) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return String.format("%s || %d   || %d", date.format(formatter), amount, balance);
    }
}
