package training.supportbank;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String name;
    private float balance = 0.0f;

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

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    // The transaction being added below belongs to this Account.
    public void add(Transaction transaction) {
        transactions.add(transaction); // just add the transaction to this account.
        if(name.equals(transaction.getTo())) { // if the name of this account is on the side of "To".
            balance += transaction.getAmount(); // increase the balance by the amount of transaction.
        }
        else if(name.equals(transaction.getFrom())) {
            balance -= transaction.getAmount();
        }
    }
}
