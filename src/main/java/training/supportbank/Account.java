package training.supportbank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String name;
    private BigDecimal balance = new BigDecimal(0);
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

    public void add(Transaction transaction) {
        transactions.add(transaction);
        if(name.equals(transaction.getTo())) {
            balance = balance.add(transaction.getAmount());
        }
        else if(name.equals(transaction.getFrom())) {
            balance = balance.subtract(transaction.getAmount());
        }
    }
    List<Transaction> getTransactions() {
        return transactions;
    }

}




