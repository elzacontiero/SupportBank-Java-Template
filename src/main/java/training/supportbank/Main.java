package training.supportbank;

import java.util.List;

public class Main {
    public static void main(String args[]) {
        System.out.println("Welcome to Support Bank");
        List<Transaction> transactionList;
        try {
            transactionList = ReadTransactions.transactionsReader("Transaction2014.csv");
        }
        catch(Exception e) {
            System.out.println("Error: "+e.getMessage()); // method inside exception provides error message.

            return;// to quit application if there is error.
        }

        /* TODO: 1.	From the list of transactions create all accounts and save into a HashMap as I discussed with you today.
                 2. Distribute transactions to each of the accounts.
                 3. Loop for user input as per specification (List All or List [Account])
         */

    }
}
