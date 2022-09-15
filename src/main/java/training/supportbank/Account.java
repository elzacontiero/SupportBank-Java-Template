package training.supportbank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String name;
    private BigDecimal balance = new BigDecimal(0);

    // This is the list of all transactions relative to this account.
    private List<Transaction> transactions = new ArrayList<>();

    public Account(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    // method below adds transaction to this current account and updates the balance accordingly.
    // in the specs if it is 'To' then add the amount to balance, if it is 'From' deduct from balance.
    public void add(Transaction transaction) {
        transactions.add(transaction); // just add the transaction to this account.
        if(name.equals(transaction.getTo())) { // if the name of this account is on the side of "To".
            balance = balance.add(transaction.getAmount()); // increase the balance by the amount of transaction.
        }
        else if(name.equals(transaction.getFrom())) {
            balance = balance.subtract(transaction.getAmount());
        }
    }

    List<Transaction> getTransactions() {
        return transactions;
    }
}
