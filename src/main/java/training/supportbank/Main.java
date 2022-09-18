package training.supportbank;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.List;
import java.util.HashMap;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    private static HashMap<String,Account> returnAllAccounts(List<Transaction> transactionList) {

        HashMap<String,Account> allAccounts = new HashMap<>();
        // loop over transaction list to  pick each transaction and save it in trans.
        for (Transaction trans : transactionList) {
            String name = trans.getFrom();
            if (!allAccounts.containsKey(name)) {
                allAccounts.put(name, new Account(name));
            }

            name = trans.getTo();
            if(!allAccounts.containsKey(name)) {
                allAccounts.put(name, new Account(name));
            }
        }
        return allAccounts;
    }

    public static void main(String args[]) {
        System.out.println("Welcome to Support Bank");

        LOGGER.info( "Program Support Bank Started");

        List<Transaction> transactionList;
        // transactionsReader reads out csv file and save it in transactionList.
        try {
            LOGGER.debug("Starting reading transactions");
            transactionList = ReadTransactions.transactionsReaderCsv("Transactions2014.csv");

            // Commenting out these dodgy transactions.
//          List<Transaction> transactionList2 = ReadTransactions.transactionsReaderCsv( "DodgyTransactions2015.csv");
//          transactionList.addAll(transactionList2); // add all elements of transactionList2 via method allAll into transactionList.

            List<Transaction> transactionList3 = ReadTransactions.transactionReaderJson("Transactions2013.json");
            transactionList.addAll(transactionList3);
            LOGGER.debug("All transactions read successfully.");

        }
        catch(NumberFormatException e) {
            System.out.println("Fatal error. Check logs for details, please.");
            LOGGER.fatal("Error NumberFormatException: "+e.getMessage());
            return;
        }
        catch(DateTimeParseException e) {
            System.out.println("Fatal error. Check logs for details, please.");
            LOGGER.fatal("Error DateTimeParseException: " + e.getMessage());
            return;
        }
        catch(FileNotFoundException e) {
            System.out.println("Fatal error. Check logs for details, please.");
            LOGGER.fatal("Error FileNotFoundException: " + e.getMessage());
            return;
        }
        HashMap<String,Account> allAccounts = returnAllAccounts(transactionList);

        // enhanced loop to go over each element of the list and save it in 't'.
        for (Transaction t:transactionList) {
            String accountName =  t.getFrom();
            Account ac = allAccounts.get(accountName);
            ac.add(t);
            accountName = t.getTo();
            ac = allAccounts.get(accountName);
            ac.add(t);
        }
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter 'List All' or 'List [Account]'");
        String option = scan.nextLine();

        // Remove 'List ' from the beginning of option to make it easier for the logic below.
        String name = option.replace("List ", "");
        if (name.equalsIgnoreCase("All")) {
            for (String accountName : allAccounts.keySet()) {
                Account account = allAccounts.get(accountName);
                System.out.println(accountName + " has " + account.getBalance() + " in balance.");
            }
        }
        else {
            Account account = allAccounts.get(name);
            System.out.println("Balance: " + account.getBalance());
            for (Transaction t : account.getTransactions()) {
                // Get the amount for this transaction and save in amount.
                BigDecimal amount = t.getAmount();
                // If the account name is in 'From' side, then the amount is negative!
                if (name.equals(t.getFrom())) {
                    System.out.println("Date:" + t.getDate() + " " + t.getNarrative() + " £ -" + amount);
                } else {
                    System.out.println("Date:" + t.getDate() + " " + t.getNarrative() + " £ " + amount);
                }
            }
        }
    }
}
